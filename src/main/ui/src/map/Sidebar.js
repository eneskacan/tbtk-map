import React from "react";

// Sidebar showing current locations
const Sidebar = ({longitude, latitude, zoom}) => {
    return (
        <div className="sidebar">
            Longitude: {longitude} | Latitude: {latitude} | Zoom: {zoom}
        </div>
    );
}

export default Sidebar;