package com.tbtk.map.controller;

import com.tbtk.map.model.Location;
import com.tbtk.map.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

@RestController
@Api(tags="Location")
@RequestMapping("/api/v1")
public class LocationController {
    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @ApiOperation("Get all locations in GeoJSON format.")
    @RequestMapping(value = "locations.json", method = RequestMethod.GET)
    public ResponseEntity<FeatureCollection> listLocations(@RequestParam(required = false) Long userId) {
        FeatureCollection locations;

        if(userId != null) locations = locationService.listLocationsByUser(userId);
        else locations = locationService.listLocations();

        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @ApiOperation("Create a new location.")
    @RequestMapping(value = "locations", method = RequestMethod.POST)
    public ResponseEntity createLocation(@RequestHeader Long userId, @RequestBody Location location) {
        Long locationId = locationService.createLocation(userId, location);
        return new ResponseEntity(locationId, HttpStatus.CREATED);
    }

    @ApiOperation("Update an existing location's name.")
    @RequestMapping(value = "locations/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateLocationName(@PathVariable Long id, @RequestBody String updatedName) {
        locationService.updateLocationName(id, updatedName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("Delete an existing location.")
    @RequestMapping(value = "locations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
