package com.bahlawan.geolocator.controllers;

import com.bahlawan.geolocator.dtos.UserRequest;
import com.bahlawan.geolocator.entities.GeoLocation;
import com.bahlawan.geolocator.services.GeoLocationService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("geolocation/api/v1")
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    public GeoLocationController(GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @PostMapping("/submit-address")
    public List<GeoLocation> submitAddress(@RequestBody UserRequest request) throws MessagingException, UnsupportedEncodingException {
        return geoLocationService.getLocationInfo(request);
    }
}
