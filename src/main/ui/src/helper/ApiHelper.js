import axios from "axios";

/**
 * https://medium.com/swlh/react-api-call-best-practice-c78e14a7e150
 */
export default class ApiHelper {
    // Singleton structure
    static myInstance = null;
    static getInstance() {
        if(ApiHelper.myInstance == null) {
            ApiHelper.myInstance = new ApiHelper();
        }
        return this.myInstance;
    }

    constructor() {
        // this.api_token = null;
        // this.client = null;
        this.api_url = "/api/v1";
        this.user_id = 1; // TODO: Get current user's id
    }

    // Get all locations from the database.
    listLocations = () => {
        return axios(
            {
                method: 'get',
                url: this.api_url + "/locations.json",
            }
        );
    }

    // Get the user's own locations from the database.
    listUserLocations = () => {
        return axios(
            {
                method: 'get',
                url: this.api_url + "/locations.json?userId=" + this.user_id,
            }
        );
    }

    // Save a new location to the database.
    createLocation = (name, longitude, latitude) => {
        const header = {
            userId: this.user_id
        };

        const body = {
            name: name,
            longitude: longitude,
            latitude: latitude
        }

        return axios(
            {
                method: 'post',
                url: this.api_url + "/locations",
                headers: header,
                data: body
            }
        );
    }
}