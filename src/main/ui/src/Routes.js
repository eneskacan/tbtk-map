import React from "react";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Map from "./map/Map";
import Login from "./login/Login";

function Routes() {
    return (
        <Router>
            <Switch>
                <Route path = "/" exact component = {Login}></Route>
                <Route path = "/map" exact component = {Map}></Route>
            </Switch>
        </Router>
    );
}

export default Routes;