package com.tbtk.map.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface GeojsonRepository {
    // This interface is implemented in service/GeojsonService.java
    String getGeoJson();
    String getGeoJson(Long userID);
}
