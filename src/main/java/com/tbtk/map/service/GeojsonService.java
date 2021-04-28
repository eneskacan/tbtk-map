package com.tbtk.map.service;

import com.tbtk.map.repository.GeojsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GeojsonService implements GeojsonRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // SQL command template that returns related queries ('%s') in GeoJSON format
    private String geojsonTemplate = "SELECT jsonb_build_object('type', 'FeatureCollection', 'features', jsonb_agg(feature) "
            + ") FROM (SELECT jsonb_build_object('type', 'Feature', 'id', geopoint_id,"
            + "'geometry', ST_AsGeoJSON(geom)::jsonb,'properties', to_jsonb(row) - 'geom'"
            + ") AS feature FROM (%s) row) features";

    // Return all saved locations in GeoJSON format
    @Override
    public String getGeoJson() {
        String query = "SELECT * FROM geopoint";

        return jdbcTemplate.queryForObject(String.format(geojsonTemplate, query), String.class);
    }

    // Find saved locations by user id and return in GeoJSON format
    @Override
    public String getGeoJson(Long userId) {
        String query = "SELECT G.geopoint_id, G.name, G.geom " +
                "FROM geopoint AS G, users AS U, user_geopoint AS J " +
                "WHERE U.users_id = J.users_id AND G.geopoint_id = J.geopoint_id " +
                "AND J.users_id = " + userId;

        return jdbcTemplate.queryForObject(String.format(geojsonTemplate, query), String.class);
    }
}
