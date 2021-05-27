package com.tbtk.map.controller;

import com.tbtk.map.model.Location;
import com.tbtk.map.service.LocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.geojson.Geometry;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Location Controller")
public class LocationControllerTest {
    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController = new LocationController(locationService);

    @Test
    @DisplayName("create location should return HTTP status Created")
    void createLocationShouldReturnHttpStatusCreated() {
        // given
        Long id = 1L;
        String name = "Test Location";
        double lng = 34.15;
        double lat = 12.42;

        Long userId = 1L;

        Location location = new Location();
        location.setName(name);
        location.setLongitude(lng);
        location.setLatitude(lat);

        // mock
        when(locationService.createLocation(userId, location)).thenReturn(id);

        // when
        ResponseEntity<?> result = locationController.createLocation(userId, location);

        // expect
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("create location should return id in response body")
    void createLocationShouldReturnIdInResponseBody() {
        // given
        Long id = 1L;
        String name = "Test Location";
        double lng = 34.15;
        double lat = 12.42;

        Long userId = 1L;

        Location location = new Location();
        location.setName(name);
        location.setLongitude(lng);
        location.setLatitude(lat);

        // mock
        when(locationService.createLocation(userId, location)).thenReturn(id);

        // when
        ResponseEntity<?> result = locationController.createLocation(userId, location);

        // expect
        assertThat(result.getBody()).isEqualTo(id);
    }

    @Captor ArgumentCaptor<Location> captor;
    @Test
    @DisplayName("create location should save new locations")
    void createLocationShouldSaveNewLocations() {
        // given
        Long id = 1L;
        String name = "Test Location";
        double lng = 34.15;
        double lat = 12.42;

        Long userId = 1L;

        Location location = new Location();
        location.setName(name);
        location.setLongitude(lng);
        location.setLatitude(lat);

        // mock
        when(locationService.createLocation(userId, location)).thenReturn(id);

        // when
        locationController.createLocation(userId, location);

        // expect
        verify(locationService, atLeastOnce()).createLocation(eq(userId), captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(location);
    }

    @Test
    @DisplayName("list locations should return HTTP status Ok")
    void listLocationsShouldReturnHttpStatusOk() {
        // given
        int id = 1;
        String name = "Test Location";
        String geoJsonString = "{\"type\": \"Point\", \"coordinates\": [125.6, 10.1]}";
        Geometry geoJson = (Geometry) GeoJSONFactory.create(geoJsonString);
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);

        Feature feature = new Feature(geoJson, properties);
        Feature[] features = {feature};
        FeatureCollection featureCollection = new FeatureCollection(features);

        // mock
        when(locationService.listLocations()).thenReturn(featureCollection);

        // when
        ResponseEntity<?> result = locationController.listLocations(null);

        // expect
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("list locations should return Feature Collection")
    void listLocationsShouldReturnFeatureCollection() {
        // given
        String name = "Test Location";
        String geoJsonString = "{\"type\": \"Point\", \"coordinates\": [125.6, 10.1]}";
        Geometry geoJson = (Geometry) GeoJSONFactory.create(geoJsonString);

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);

        Feature feature = new Feature(geoJson, properties);
        Feature[] features = {feature};
        FeatureCollection featureCollection = new FeatureCollection(features);

        // mock
        when(locationService.listLocations()).thenReturn(featureCollection);

        // when
        ResponseEntity<?> result = locationController.listLocations(null);

        // expect
        assertThat(result.getBody()).isEqualTo(featureCollection);
    }

    @Test
    @DisplayName("update location name should return HTTP status Ok")
    void updateLocationNameShouldReturnHttpStatusOk() {
        // given
        Long locationId = 1L;
        String updatedName = "Updated Name";

        // when
        ResponseEntity<?> result = locationController.updateLocationName(locationId, updatedName);

        // expect
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update location name should update locations")
    void updateLocationNameShouldUpdateLocations() {
        // given
        Long locationId = 1L;
        String updatedName = "Updated Name";

        // when
        locationController.updateLocationName(locationId, updatedName);

        // expect
        verify(locationService, atLeastOnce()).updateLocationName(eq(locationId), eq(updatedName));
    }

    @Test
    @DisplayName("delete location should return HTTP status Ok")
    void deleteLocationShouldReturnHttpStatusOk() {
        // given
        Long locationId = 1L;

        // when
        ResponseEntity<?> result = locationController.deleteLocation(locationId);

        // expect
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete location should delete locations")
    void deleteLocationShouldDeleteLocations() {
        // given
        Long locationId = 1L;

        // when
        locationService.deleteLocation(locationId);

        // expect
        verify(locationService, atLeastOnce()).deleteLocation(eq(locationId));
    }
}