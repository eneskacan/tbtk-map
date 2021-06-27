import React, {useRef, useEffect, useState, Component} from 'react';
import mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import 'mapbox-gl/dist/mapbox-gl.css';
import "./Map.css"

import Sidebar from "./Sidebar";
import Styles from "./Styles";
import AddButton from "./AddButton"
import MapMarker from "./MapMarker";
import CreateLocationDialog from "./CreateLocationDialog";
import UpdateLocationDialog from "./UpdateLocationDialog";

mapboxgl.accessToken = 'pk.eyJ1IjoiZW5lc2thY2FuIiwiYSI6ImNrbm1mc2RzYjBzMGQyb24wd2U0OXVsbzcifQ.7z7gcpDRmSCbx8gz-2J2jQ';

const MapBox = () => {
    const mapContainer = useRef();
    const [map, setMap] = useState(null);
    const [style, setStyle] = useState('mapbox://styles/mapbox/streets-v11');

    const [lng, setLng] = useState(36.7);
    const [lat, setLat] = useState(39.2);
    const [zoom, setZoom] = useState(5);

    // Hide and show Create and Update Location Dialog
    const [showCreate, setShowCreate] = useState(false);
    const [showUpdate, setShowUpdate] = useState(false);

    //to update and delete marker
    const [marker, setMarker] = useState(null);

    // Change cursor icon
    const [cursor, setCursor] = useState('pointer');


    // Initialize map when component mounts
    useEffect(() => {
        const map = new mapboxgl.Map({
            container: mapContainer.current,
            style: style,
            center: [lng, lat],
            zoom: zoom
        });

        map.doubleClickZoom.disable();

        map.on('load', async () => {
            map.addSource('locations', {
                type: 'geojson',
                data: '/api/v1/locations.json'
            });

            setMap(map);
        });

        // Clean up on unmount
        return () => map.remove();

        // eslint-disable-next-line
    }, [style]);

    // Update coordinates on the side bar
    useEffect(() => {
        if (!map) return; // wait for map to initialize

        map.on('move', () => {
            setLng(map.getCenter().lng.toFixed(4));
            setLat(map.getCenter().lat.toFixed(4));
            setZoom(map.getZoom().toFixed(2));
        });
    });

    // Handle on map click
    useEffect(() => {
        if (!map) return; // wait for map to initialize

        map.on('click', function (e) {
            setLng(e.lngLat.lng.toFixed(4));
            setLat(e.lngLat.lat.toFixed(4));

            // Show create location dialog
            setShowCreate(cursor === 'crosshair');
            setCursor('pointer');
        });

        // eslint-disable-next-line
    }, [cursor]);

    // Load location markers
    useEffect(() => {
        new MapMarker(map, setShowUpdate, setMarker).addMarkers().then();

        // eslint-disable-next-line
    }, [map])

    return (
        <div style={{cursor: cursor}}>
            <Styles updateMapStyle={setStyle}/>
            <Sidebar latitude={lat} longitude={lng} zoom={zoom}/>
            <AddButton cursor={cursor} setCursor={setCursor}/>
            <CreateLocationDialog show={showCreate} setShow={setShowCreate} lat={lat} lng={lng} map={map}
                                  setCursor={setCursor}/>
            <UpdateLocationDialog show={showUpdate} setShow={setShowUpdate} map={map} marker={marker}/>
            <div ref={mapContainer} className="map-container" style={{cursor: cursor}}/>
        </div>
    );
}

class Map extends Component {
    render() {
        return (
            <div>
                <MapBox/>
            </div>
        );
    }
}

export default Map;
