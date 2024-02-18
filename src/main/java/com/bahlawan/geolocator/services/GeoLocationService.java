package com.bahlawan.geolocator.services;

import com.bahlawan.geolocator.dtos.UserRequest;
import com.bahlawan.geolocator.entities.GeoLocation;
import com.bahlawan.geolocator.repositories.GeoLocationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeoLocationService {

    @Value("${GEO_CODE_API_URL}")
    private String API;
    private final GeoLocationRepository geoLocationRepository;
    private final WebClient.Builder webClient;
    private final EmailService emailService;

    public GeoLocationService(GeoLocationRepository geoLocationRepository, WebClient.Builder webClient, EmailService emailService) {
        this.geoLocationRepository = geoLocationRepository;
        this.webClient = webClient;
        this.emailService = emailService;
    }


    public List<GeoLocation> getLocationInfo(UserRequest request) throws MessagingException, UnsupportedEncodingException {
        GeoLocation geoLocation = geoLocationRepository.findByAddress(request.address());

        if (geoLocation == null) {
            List<GeoLocation> apiResult = mapApiDataToGeoLocations(getLocationInfoFromAPI(request.address()));
            saveAndUpdateLocations(apiResult);
            sendEmailIfRequested(request, apiResult);
            return apiResult;
        } else {
            sendEmailIfRequested(request, List.of(geoLocation));
            return List.of(geoLocation);
        }
    }

    private void saveAndUpdateLocations(List<GeoLocation> locations) {
        for (GeoLocation result : locations) {
            GeoLocation existingLocation = geoLocationRepository.findByAddress(result.getAddress());
            if (existingLocation == null) {
                geoLocationRepository.save(result);
            }
        }
    }

    private void sendEmailIfRequested(UserRequest request, List<GeoLocation> locations) throws MessagingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(request.email())) {
            emailService.sendMail(request.email(), locations);
        }
    }


    private String getLocationInfoFromAPI(String query) {
        return webClient.build().
                get().
                uri(API.concat(query)).
                retrieve().
                bodyToMono(String.class).
                block();
    }

    private List<GeoLocation> mapApiDataToGeoLocations(String apiResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(apiResponse);
            JsonNode itemsNode = rootNode.path("items");

            List<GeoLocation> geoLocations = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                JsonNode addressNode = itemNode.path("address");
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.setAddress(addressNode.path("label").asText());
                geoLocation.setLatitude(itemNode.path("position").path("lat").asDouble());
                geoLocation.setLongitude(itemNode.path("position").path("lng").asDouble());

                geoLocations.add(geoLocation);
            }

            return geoLocations;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing API response JSON", e);
        }
    }

}
