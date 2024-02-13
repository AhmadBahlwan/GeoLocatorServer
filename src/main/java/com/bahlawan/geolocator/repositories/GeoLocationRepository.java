package com.bahlawan.geolocator.repositories;

import com.bahlawan.geolocator.entities.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Integer> {

    GeoLocation findByAddress(String address);
}
