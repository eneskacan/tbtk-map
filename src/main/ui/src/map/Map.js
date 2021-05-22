import React, { useRef, useEffect, useState, Component } from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import 'mapbox-gl/dist/mapbox-gl.css';
import "./Map.css"

import Sidebar from "./Sidebar";
import Styles from "./Styles";

mapboxgl.accessToken = 'pk.eyJ1IjoiZW5lc2thY2FuIiwiYSI6ImNrbm1mc2RzYjBzMGQyb24wd2U0OXVsbzcifQ.7z7gcpDRmSCbx8gz-2J2jQ';

const MapBox = () => {
    const mapContainer = useRef(null);
    const [map, setMap] = useState(null);
    const [style, setStyle] = useState('mapbox://styles/mapbox/streets-v11');

    const [lng, setLng] = useState(36.7);
    const [lat, setLat] = useState(39.2);
    const [zoom, setZoom] = useState(5);

    // Initialize map when component mounts
    useEffect( () => {
        const map = new mapboxgl.Map({
            container: mapContainer.current,
            style: style,
            center: [lng, lat],
            zoom: zoom
        });

        map.on('load', async () => {
            map.addSource('locations', {
                type: 'geojson',
                data: '/api/v1/locations.json'
            });

            map.addLayer(
                {
                    id: 'locations',
                    type: 'circle',
                    source: 'locations',
                    paint: {
                        'circle-radius': 9,
                        'circle-color': '#c10b0b'
                    },
                    minZoom: 5,
                    maxZoom: 15
                }
            );

            setMap(map);
        });

        let popup; // Initialize popup to show location names

        // Set on mouse enter method
        map.on('mouseenter', 'locations', (event) => {
            popup = new mapboxgl.Popup({closeButton: false})
                .setLngLat(event.lngLat)
                .setHTML(event.features[0].properties.name)
                .addTo(map);
        });

        map.on('mouseleave', 'locations', () => {
            popup.remove();
        });

        // Clean up on unmount
        return () => map.remove();

        // eslint-disable-next-line
    }, [style]);

    useEffect(() => {
        if (!map) return; // wait for map to initialize
        map.on('move', () => {
            setLng(map.getCenter().lng.toFixed(4));
            setLat(map.getCenter().lat.toFixed(4));
            setZoom(map.getZoom().toFixed(2));
        });
    });

    return (
        <div>
            <Styles updateMapStyle={setStyle} />
            <Sidebar latitude={lat} longitude={lng} zoom={zoom} />
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
