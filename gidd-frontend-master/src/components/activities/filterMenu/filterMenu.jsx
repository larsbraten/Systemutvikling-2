import Checkbox from "@material-ui/core/Checkbox";
import React, { useState, useEffect } from "react";
import { getLocations, getInterests } from "../../../data/actions/activities";
import { connect } from "react-redux";
import "./filterMenu.css";
import Typography from "@material-ui/core/Typography";

function FilterMenu(props) {
  useEffect(() => {
    props.getLocations();
    props.getInterests();
  }, []);

  const renderLocations = () => {
    return (
      <div className="filtermenu-category">
        <p>Byer</p>
        <div className="filtermenu-category-content">
          {props.locations.map((location) => {
            return (
              <div key={location}>
                <Checkbox
                  onChange={() => props.handleLocationChange(location)}
                />
                <Typography variant="paragraph" color="text">
                  {location}
                </Typography>
              </div>
            );
          })}
        </div>
      </div>
    );
  };

  const renderInterests = () => {
    return (
      <div className="filtermenu-category">
        <p>Kategorier</p>
        <div className="filtermenu-category-content">
          {props.interests.map((interest) => {
            return (
              <div key={interest}>
                <Checkbox
                  onChange={() => props.handleInterestChange(interest)}
                />
                <Typography variant="paragraph" color="text">
                  {interest}
                </Typography>
              </div>
            );
          })}
        </div>
      </div>
    );
  };

  const renderLevels = () => {
    return (
      <div className="filtermenu-category">
        <p>Aktivitetsnivåer</p>
        <div className="filtermenu-category-content">
          <div>
            <Checkbox onChange={() => props.handleLevelChange("HIGH")} />
            <Typography variant="paragraph" color="text">
              Høyt
            </Typography>
          </div>
          <div className="filterCheckboxColumnDiv">
            <Checkbox onChange={() => props.handleLevelChange("MEDIUM")} />
            <Typography variant="paragraph" color="text">
              Middels
            </Typography>
          </div>
          <div className="filterCheckboxColumnDiv">
            <Checkbox onChange={() => props.handleLevelChange("LOW")} />
            <Typography variant="paragraph" color="text">
              Lavt
            </Typography>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="filterMenu">
      {renderLocations()}
      {renderInterests()}
      {renderLevels()}
    </div>
  );
}

const mapStateToProps = (state) => ({
  locations: state.activities.locations,
  interests: state.activities.interests,
});

export default connect(mapStateToProps, { getLocations, getInterests }, null, {
  forwardRef: true,
})(FilterMenu);
