import React from "react";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Map from "./map/Map";
import App from "./app/App"

function Routes() {
    return (
        <Router>
            <Switch>
                <Route path="/map">
                    <Map />
                </Route>
                <Route path="/">
                    <App />
                </Route>
            </Switch>
        </Router>
    );
}

export default Routes;