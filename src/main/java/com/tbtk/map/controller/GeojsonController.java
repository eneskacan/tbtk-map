package com.tbtk.map.controller;

import com.tbtk.map.repository.GeojsonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags="Geojson")
@RequestMapping(value = "/api/v1/geojson")
public class GeojsonController {
    @Autowired
    private GeojsonRepository geojsonRepository;

    @ApiOperation(value = "This method is used to get locations in GeoJSON format.")
    @RequestMapping(value = "locations.json", method = RequestMethod.GET)
    public String getGeoJson(@RequestParam(required = false) Long userId) {
        if(userId != null) { // if user id parameter is provided
            // find locations by user id
            return geojsonRepository.getGeoJson(userId);
        } else { // if user id parameter is not provided
            // return all locations
            return geojsonRepository.getGeoJson();
        }
    }
}
