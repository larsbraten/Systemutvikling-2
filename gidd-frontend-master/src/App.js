import React, { useState, useEffect } from "react";
import { authenticationService } from "./data/Services/authService";
import "./App.css";
import { BrowserRouter, Route } from "react-router-dom";
import LandingContainer from "./components/landing/LandingContainer";
import Activities from "./components/activities/activityContainer";
import Header from "./components/header/header";
import Account from "./components/Account/accountContainer";
import RegisterContainer from "./components/Register/RegisterContainer";
import loginContainer from "./components/Login/loginContainer";
import MapContainer from "./components/map/mapContainer";
import EnrolledActivities from "./components/enrolledActivities/enrolledActivities";
import MyActivities from "./components/myActivities/myActivityContainer";
import CreateActivityContainer from "./components/myActivities/createActivityContainer";
import { connect } from "react-redux";
import { withRouter } from "react-router";
import { authUser } from "./data/actions/auth";
import PrivateRoute from "./components/PrivateRoute";
import Leaderboard from "./components/leaderboard/leaderboard";
import ActivityPageContainer from "./components/ActivityPage/ActivityPageContainer";
import Verify from "./components/Verify/verify";

function App(props) {
  useEffect(() => {
    props.authUser();
  }, []);

  return props.isLoggedIn !== null ? (
    <BrowserRouter>
      <Header user={props.user} />
      <Route exact path="/login" component={loginContainer} />
      <Route exact path="/register" component={RegisterContainer} />
      <Route path="/verify" component={Verify} />
      <PrivateRoute
        exact
        path="/liste"
        component={Activities}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/kart"
        component={Activities}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/kalender"
        component={Activities}
        isLoggedIn={props.isLoggedIn}
      />

      <PrivateRoute
        exact
        path="/activities/:id"
        component={ActivityPageContainer}
        isLoggedIn={props.isLoggedIn}
      />

      <PrivateRoute
        exact
        path="/map"
        component={MapContainer}
        isLoggedIn={props.isLoggedIn}
      />

      <PrivateRoute
        exact
        path="/enrolledActivities"
        component={EnrolledActivities}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/myActivities"
        component={MyActivities}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/createActivity"
        component={CreateActivityContainer}
        isLoggedIn={props.isLoggedIn}
      />

      <PrivateRoute
        exact
        path="/"
        component={LandingContainer}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/leaderboard"
        component={Leaderboard}
        isLoggedIn={props.isLoggedIn}
      />
      <PrivateRoute
        exact
        path="/account"
        component={Account}
        isLoggedIn={props.isLoggedIn}
        isReset={true}
      />
    </BrowserRouter>
  ) : (
    ""
  );
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.auth.isLoggedIn,
  user: state.auth.user,
});

export default connect(mapStateToProps, { authUser }, null, {
  forwardRef: true,
})(App);
