import React from "react";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Map from "./map/Map";
import App from "./app/App"

function Routes() {
    return (
        <Router>
            <Switch>
                <Route path = "/" exact component = {App}></Route>
                <Route path = "/map" exact component = {Map}></Route>
            </Switch>
        </Router>
    );
}

export default Routes;