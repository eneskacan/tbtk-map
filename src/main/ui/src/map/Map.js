import React, { useRef, useEffect, useState, Component } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import 'mapbox-gl/dist/mapbox-gl.css';
import "./Map.css"

mapboxgl.accessToken = 'pk.eyJ1IjoiZW5lc2thY2FuIiwiYSI6ImNrbm1mc2RzYjBzMGQyb24wd2U0OXVsbzcifQ.7z7gcpDRmSCbx8gz-2J2jQ';

const MapBox = () => {
    const mapContainer = useRef(null);
    const map = useRef(null);
    const [lng, setLng] = useState(36.7);
    const [lat, setLat] = useState(39.2);
    const [zoom, setZoom] = useState(5);

    useEffect(() => {
        if (map.current) return; // initialize map only once
        map.current = new mapboxgl.Map({
            container: mapContainer.current,
            style: 'mapbox://styles/mapbox/streets-v11',
            center: [lng, lat],
            zoom: zoom
        });
    });

    useEffect(() => {
        if (!map.current) return; // wait for map to initialize
        map.current.on('move', () => {
            setLng(map.current.getCenter().lng.toFixed(4));
            setLat(map.current.getCenter().lat.toFixed(4));
            setZoom(map.current.getZoom().toFixed(2));
        });
    });

    return (
        <div>
            <div className="sidebar">
                Longitude: {lng} | Latitude: {lat} | Zoom: {zoom}
            </div>
            <div ref={mapContainer} className="map-container" />
        </div>
    );
}

class Map extends Component {
    render() {
        return (
            <div>
                <MapBox />
            </div>
        );
    }
}

export default Map;
