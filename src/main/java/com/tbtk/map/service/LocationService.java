package com.tbtk.map.service;

import com.tbtk.map.model.Location;
import com.tbtk.map.model.User;
import com.tbtk.map.repository.LocationRepository;
import com.tbtk.map.repository.UserRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.Feature;

import java.util.*;

import static com.tbtk.map.helper.GeometryHelper.convertJtsGeometryToGeoJson;

@Service
public class LocationService {
    private LocationRepository locationRepository;
    private UserRepository userRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public FeatureCollection listLocations() {
        List<Location> locations = locationRepository.findAll();
        Feature[] features = mapEntityListToFeatures(locations);
        return new FeatureCollection(features);
    }

    public FeatureCollection listLocationsByUser(Long userId) {
        User user = userRepository.findById(userId).get();
        Feature[] features = mapEntityListToFeatures(user.getLocations());
        return new FeatureCollection(features);
    }

    public Long createLocation(Long userId, Location location) {
        User owner = userRepository.getOne(userId);
        location.setOwner(owner);
        Long locationId = locationRepository.saveAndFlush(location).getId();
        return locationId;
    }

    public Location updateLocationName(Long locationId, String updatedName) {
        Location existingLocation = locationRepository.getOne(locationId);
        existingLocation.setName(updatedName);
        return locationRepository.saveAndFlush(existingLocation);
    }

    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    private Feature[] mapEntityListToFeatures(List<Location> locations) {
        Feature[] features = new Feature[locations.size()];

        for(int i = 0; i < locations.size(); i++) {
            features[i] = convertEntityToFeature(locations.get(i));
        }

        return  features;
    }

    private Feature convertEntityToFeature(Location entity) {
        Long id = entity.getId();
        org.wololo.geojson.Geometry geometry = convertJtsGeometryToGeoJson(
                new GeometryFactory().createPoint(new Coordinate(entity.getLatitude(), entity.getLongitude()))
        );

        Map<String, Object> properties = new HashMap<>();
        Arrays.stream(Location.class.getDeclaredFields())
                .filter(i -> !i.isSynthetic())
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (!field.getName().equals("id") && !field.getName().equals("latitude") && !field.getName().equals("longitude")) {
                            properties.put(field.getName(), field.get(entity));
                        }
                    } catch (IllegalAccessException e) {
                        //log.warn(e.getMessage());
                    }
                });

        return new Feature(id, geometry, properties);
    }
}