import mapboxgl from "mapbox-gl";
import ApiHelper from "../helper/ApiHelper";

let apiHelper = ApiHelper.getInstance();

export default class MapMarker {
    constructor(map, showUpdateDialog, setMarker) {
        this.map = map;
        this.showUpdateDialog = showUpdateDialog;
        this.setMarker = setMarker;
    }

    async addMarkers() {
        if (!this.map) return;

        const map = this.map;

        // Get locations from database
        const resp = await apiHelper.listLocations();

        if (resp.statusText === "OK") {
            // Set markers
            resp.data.features.forEach((marker) => {
                let el = document.createElement('div');
                el.id = 'marker-' + marker.properties.id;
                el.className = 'marker';

                el.addEventListener('mouseenter', () => this.createPopUp(marker));
                el.addEventListener('mouseleave', () => this.closePopUp());
                el.addEventListener('dblclick', () => this.handleUpdateDialog(marker));

                new mapboxgl.Marker(el, {offset: [0, -23]})
                    .setLngLat(marker.geometry.coordinates)
                    .addTo(map);
            });
        } else {
            console.log("failed to get locations");
        }
    }

    createPopUp(currentFeature) {
        const map = this.map;

        this.closePopUp(); // Close open popups

        new mapboxgl.Popup({closeOnClick: false})
            .setLngLat(currentFeature.geometry.coordinates)
            .setHTML(
                '<h3> ' + currentFeature.properties.owner.name + '</h3>' +
                '<h4>' + currentFeature.properties.name + '</h4>'
            )
            .addTo(map);
    }

    closePopUp() {
        const popUps = document.getElementsByClassName('mapboxgl-popup');
        if (popUps[0]) popUps[0].remove();
    }

    handleUpdateDialog(currentFeature) {
        this.showUpdateDialog(true);
        this.setMarker(currentFeature);
    }
}