import React, { useEffect, useState } from "react";
import ActivityLocationInput from "../myActivities/activityLocationInput";
import { Map, Marker } from "pigeon-maps";
import { Controller } from "react-hook-form";
import { TextField } from "@material-ui/core";
import { KeyboardTimePicker } from "@material-ui/pickers";

const ActivityLocation = (props) => {
  const [Address, SetAddress] = useState("");
  const [mapAddress, setMapAddress] = useState([]);
  const [location, setLocation] = useState([]);

  const registerLocation = async (event) => {
    setLocation([event.latLng[0], event.latLng[1]]);
  };

  useEffect(() => {
    mapCoordinatesToAddress();
  }, [location]);

  const mapCoordinatesToAddress = async () => {
    const response = await fetch(
      `http://penguin.flagship.casa:8080/search.php?addressdetails=1&q=${location[0]},${location[1]}`
    );
    const result = await response.json();

    setMapAddress(Object.keys(result).map((key) => result[key]));
  };

  const prepareLocation = () => {
    if (props.isEditable && props.isEditing) {
      return (
        <div className="single-activity-map">
          <div>
            <h2>Adresse</h2>
            <p>
              {props.fieldValue.place}, {props.fieldValue.city},{" "}
              {props.fieldValue.country}
            </p>
          </div>
          <div>
            <Map
              zoom={17}
              defaultCenter={[
                props.fieldValue.latitude,
                props.fieldValue.longitude,
              ]}
              defaultZoom={11}
            >
              <Marker
                key="key"
                color={"red"}
                width={40}
                anchor={[
                  new Number(props.fieldValue.latitude),
                  new Number(props.fieldValue.longitude),
                ]}
              />
            </Map>
          </div>
        </div>
        // <div>
        //     <div className="create-activity-adresse">
        //         <h2>Adresse</h2>
        //         <ActivityLocationInput
        //             Address={Address}
        //             SetAddress={SetAddress}
        //             registerLocation={registerLocation}
        //             setLocation={setLocation}
        //             location={location}
        //             mapAddress={mapAddress}
        //         />
        //     </div>
        //     <div className="create-activity-kart">
        //         <Map
        //             zoom={17}
        //             defaultCenter={[props.fieldValue.latitude, props.fieldValue.longitude]}
        //             defaultZoom={11}
        //             onClick={registerLocation}
        //         >
        //             <Marker
        //                 key="key"
        //                 color={"red"}
        //                 width={40}
        //                 anchor={[new Number(location[0]), new Number(location[1])]}
        //             />
        //         </Map>
        //     </div>
        // </div>
      );
    } else {
      return (
        <div className="single-activity-map">
          <div>
            <h2>Adresse</h2>
            <p>
              {props.fieldValue.place}, {props.fieldValue.city},{" "}
              {props.fieldValue.country}
            </p>
          </div>
          <div className="single-activity-map-map">
            <Map
              zoom={17}
              defaultCenter={[
                props.fieldValue.latitude,
                props.fieldValue.longitude,
              ]}
              defaultZoom={11}
            >
              <Marker
                key="key"
                color={"red"}
                width={40}
                anchor={[
                  new Number(props.fieldValue.latitude),
                  new Number(props.fieldValue.longitude),
                ]}
              />
            </Map>
          </div>
        </div>
      );
    }
  };

  return prepareLocation();
};

export default ActivityLocation;
