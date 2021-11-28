package com.tbtk.map.repository;

import com.tbtk.map.model.Location;
import com.tbtk.map.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Location Repository")
public class LocationRepositoryIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    // test lists
    private static List<User> users;
    private static List<Location> locations;

    @BeforeAll
    static void init() {
        // initialize test lists
        users = new ArrayList<>();
        locations = new ArrayList<>();

        // add test users
        users.add(new User());
        users.get(0).setName("User 1");
        users.get(0).setPass("Pass.1");

        users.add(new User());
        users.get(1).setName("User 2");
        users.get(1).setPass("Pass.2");

        // add test locations
        locations.add(new Location());
        locations.get(0).setName("Ankara");
        locations.get(0).setLongitude(32.7);
        locations.get(0).setLatitude(39.12);
        locations.get(0).setOwner(users.get(0));

        locations.add(new Location());
        locations.get(1).setName("Istanbul");
        locations.get(1).setLongitude(28.7);
        locations.get(1).setLatitude(41.12);
        locations.get(1).setOwner(users.get(1));
    }

    @Test
    @Order(1)
    @DisplayName("should save test users to database")
    void shouldSaveTestUsersToDatabase() {
        // when
        User user1 = userRepository.saveAndFlush(users.get(0));
        User user2 = userRepository.saveAndFlush(users.get(1));

        // expect
        assertThat(user1).usingRecursiveComparison().ignoringFields("id").isEqualTo(users.get(0));
        assertThat(user1.getId()).isEqualTo(1L);

        assertThat(user2).usingRecursiveComparison().ignoringFields("id").isEqualTo(users.get(1));
        assertThat(user2.getId()).isEqualTo(2L);
    }

    @Test
    @Order(2)
    @DisplayName("should find no locations if database is empty")
    void shouldFindNoLocationsIfDatabaseIsEmpty() {
        // when
        List<Location> tutorials = locationRepository.findAll();

        // expect
        assertThat(tutorials).isEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("should create a new location")
    void shouldCreateNewLocation() {
        // when
        Location location1 = locationRepository.saveAndFlush(locations.get(0));
        Location location2 = locationRepository.saveAndFlush(locations.get(1));

        // expect
        assertThat(location1).usingRecursiveComparison().ignoringFields("id").isEqualTo(locations.get(0));
        assertThat(location1.getId()).isEqualTo(1L);

        assertThat(location2).usingRecursiveComparison().ignoringFields("id").isEqualTo(locations.get(1));
        assertThat(location2.getId()).isEqualTo(2L);
    }

    @Test
    @Order(4)
    @Transactional
    @DisplayName("should return all locations from database")
    void shouldReturnAllLocationsFromDatabase() {
        // when
        List<Location> result = locationRepository.findAll();

        // expect
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).usingRecursiveComparison().ignoringFields("id", "owner").isEqualTo(locations.get(0));
        assertThat(result.get(1)).usingRecursiveComparison().ignoringFields("id", "owner").isEqualTo(locations.get(1));
    }

    // TODO: Fix the failing test!
    @Disabled
    @Test
    @Order(5)
    @DisplayName("should not include passwords when returning locations")
    void shouldNotIncludePasswordsWhenReturningLocations() {
        // when
        List<Location> result = locationRepository.findAll();

        // check if result contains any passwords
        boolean containsPassword = false;
        for(Location location : result)
            if(location.getOwner().getPass() != null) containsPassword = true;

        // expect
        assertThat(result.size()).isEqualTo(2);
        assertThat(containsPassword).isEqualTo(false);
    }

    @Test
    @Order(6)
    @Transactional
    @DisplayName("should delete locations from database")
    void shouldDeleteLocationsFromDatabase() {
        // given
        locations.remove(0);

        // when
        locationRepository.deleteById(1L);
        List<Location> result = locationRepository.findAll();

        // expect
        assertThat(result.size()).isEqualTo(1);
    }
}