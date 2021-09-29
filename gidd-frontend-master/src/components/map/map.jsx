import "./map.css";
import { Map, Marker, ZoomControl } from "pigeon-maps";
import { useState } from "react";
import ActivityViewMap from "./activityViewMap";

function MapDisplay(props) {
  const [selectedActivity, setSelectedActivity] = useState({});

  const generatePopup = () => {
    if (Object.keys(selectedActivity).length === 0) {
      return "";
    } else {
      return (
        <ActivityViewMap
          activity={selectedActivity}
          closeFunc={() => setSelectedActivity({})}
        />
      );
    }
  };

  const renderMarkers = () => {
    console.log(props.activities);
    return props.activities.map((activity) => {
      return (
        <Marker
          key={activity.id}
          color={"red"}
          width={35}
          anchor={[activity.location.latitude, activity.location.longitude]}
          onClick={() => {
            setSelectedActivity(activity);
          }}
        />
      );
    });
  };

  return (
    <div className="mapDiv">
      <Map
        onClick={() => setSelectedActivity({})}
        zoom={16}
        defaultZoom={11}
        defaultCenter={[63.4305, 10.3951]}
      >
        <ZoomControl />
        {renderMarkers()}
      </Map>
      {generatePopup()}
    </div>
  );
}

export default MapDisplay;
