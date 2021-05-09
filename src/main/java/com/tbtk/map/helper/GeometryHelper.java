package com.tbtk.map.helper;

import org.locationtech.jts.geom.Geometry;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;


/**
 * Geometry Helper class contains methods to transform JTS Geometry
 * objects to GeoJSON format, and vice versa.
 * <p>
 * https://github.com/lomasz/spatial-spring/blob/master/src/main/java/xyz/lomasz/spatialspring/helper/GeometryHelper.java
 */
public class GeometryHelper {
    public static org.wololo.geojson.Geometry convertJtsGeometryToGeoJson(Geometry geometry) {
        return new GeoJSONWriter().write(geometry);
    }

    public static Geometry convertGeoJsonToJtsGeometry(org.wololo.geojson.Geometry geoJson) {
        return new GeoJSONReader().read(geoJson);
    }
}