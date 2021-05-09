package com.tbtk.map.service;

import com.tbtk.map.model.Location;
import com.tbtk.map.model.User;
import com.tbtk.map.repository.LocationRepository;
import com.tbtk.map.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Location Service")
public class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationService locationService = new LocationService(locationRepository, userRepository);

    @Test
    @DisplayName("should list locations")
    void shouldListLocations() {
        // given
        List<Location> locations = new ArrayList<>();
        Feature[] features = new Feature[5];

        for(int i = 0; i < 5; i++) {
            Long id = (long) i;
            String name = "Location " + i;
            Geometry geometry = new GeometryFactory().createPoint(new Coordinate(10 * i, 10 * i));
            User owner = new User();
            owner.setId((long) i);

            org.wololo.geojson.Geometry geoJson = new GeoJSONWriter().write(geometry);
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", name);
            properties.put("owner", owner);

            features[i] = new Feature(id, geoJson, properties);

            Location location = new Location();
            location.setId(id);
            location.setName(name);
            location.setGeometry(geometry);
            location.setOwner(owner);

            locations.add(location);
        }

        FeatureCollection featureCollection = new FeatureCollection(features);

        // mock
        when(locationRepository.findAll()).thenReturn(locations);

        // when
        FeatureCollection result = locationService.listLocations();

        // expect
        assertThat(featureCollection).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("should list locations by user id")
    void shouldListLocationsByUserId() {
        // given
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        List<Location> locations = new ArrayList<>();
        Feature[] features = new Feature[5];

        for(int i = 0; i < 5; i++) {
            Long id = (long) i;
            String name = "Location " + i;
            Geometry geometry = new GeometryFactory().createPoint(new Coordinate(10 * i, 10 * i));

            org.wololo.geojson.Geometry geoJson = new GeoJSONWriter().write(geometry);
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", name);
            properties.put("owner", user);

            features[i] = new Feature(id, geoJson, properties);

            Location location = new Location();
            location.setId(id);
            location.setName(name);
            location.setGeometry(geometry);
            location.setOwner(user);

            locations.add(location);
        }

        user.setLocations(locations);
        FeatureCollection featureCollection = new FeatureCollection(features);

        // mock
        when(userRepository.getOne(userId)).thenReturn(user);

        // when
        FeatureCollection result = locationService.listLocationsByUser(userId);

        // expect
        assertThat(result).usingRecursiveComparison().isEqualTo(featureCollection);
    }

    @Test
    @DisplayName("should create locations")
    void shouldCreateLocations() {
        // given
        Long id = 1L;
        String name = "Location 1";
        Geometry geometry = new GeometryFactory().createPoint(new Coordinate(35, 32));
        User owner = new User();
        owner.setId(1L);

        org.wololo.geojson.Geometry geoJson = new GeoJSONWriter().write(geometry);
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("owner", owner);

        Feature feature = new Feature(id, geoJson, properties);

        // mock
        when(userRepository.getOne(id)).thenReturn(owner);
        when(locationRepository.saveAndFlush(any(Location.class)))
                .thenAnswer((Answer<Location>) i -> {
                    Location location = (Location) i.getArguments()[0];
                    location.setId(id);
                    return location;
                });

        // when
        Long result = locationService.createLocation(id, feature);

        // expect
        assertThat(result).isEqualTo(id);
        verify(locationRepository, atLeastOnce()).saveAndFlush(any(Location.class));
    }

    @Test
    @DisplayName("should update locations")
    void shouldUpdateLocations() {
        // given
        Long id = 1L;
        String name = "Location 1";
        String updatedName = "Location 2";
        Geometry geometry = new GeometryFactory().createPoint(new Coordinate(43, 17));
        User owner = new User();
        owner.setId(1L);

        Location location = new Location();
        location.setId(id);
        location.setName(name);
        location.setGeometry(geometry);
        location.setOwner(owner);

        Location updatedLocation = new Location();
        updatedLocation.setId(id);
        updatedLocation.setName(updatedName);
        updatedLocation.setGeometry(geometry);
        updatedLocation.setOwner(owner);

        // mock
        when(locationRepository.getOne(id)).thenReturn(location);
        when(locationRepository.saveAndFlush(any(Location.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // when
        Location result = locationService.updateLocationName(id, updatedName);

        // expect
        assertThat(result).usingRecursiveComparison().isEqualTo(updatedLocation);
        verify(locationRepository, atLeastOnce()).saveAndFlush(any(Location.class));
    }

    @Captor
    ArgumentCaptor<Long> captor;
    @Test
    @DisplayName("should delete locations")
    void shouldDeleteLocations() {
        // given
        Long id = 1L;

        // when
        locationService.deleteLocation(id);

        // expect
        verify(locationRepository, atLeastOnce()).deleteById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(id);
    }
}