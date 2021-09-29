import React from "react";
import {Map, Marker} from "pigeon-maps";
import Typography from "@material-ui/core/Typography";

const prepareEditableData = (props) => {
    if (props.isEditable) {
        return(<p>IM NOT DONE</p>);
    } else {
        return (
            <div className="ActivityEditableData">
                <div className="ActivityEditableData-Title">
                    <Typography variant="h2" component="h2">
                        {props.title}
                    </Typography>
                </div>

                <div className="ActivityEditableData-Time">
                    <Typography variant="subtitle1" color="textSecondary">
                        {props.startTime}
                        {"  -  "}
                        {props.endTime}
                    </Typography>
                </div>

                <div className="ActivityEditableData-Capacity">
                    <p>Kapasitet: {props.capacity}</p>
                </div>

                <div className="ActivityEditableData-Maps">
                    <Map zoom={17} defaultCenter={[props.location.latitude,props.location.longitude]} defaultZoom={11}>
                        <Marker
                            key={props.activityId}
                            color={"red"}
                            width={20}
                            anchor={[props.location.latitude,props.location.longitude]}
                        />
                    </Map>
                </div>

                <div className="ActivityEditableData-Description">
                    <Typography variant="subtitle1" color="textSecondary">
                        <p>Beskrivelse: {props.description}</p>
                    </Typography>
                </div>

                <div className="ActivityEditableData-Level">
                    <Typography variant="subtitle1" color="textSecondary">
                        <p>Nivå: {props.activityLevel}</p>
                    </Typography>
                </div>

                <div className="ActivityEditableData-Interests">
                    <Typography variant="subtitle1" color="textSecondary">
                        <p>Interesser: </p>
                        <ul>
                            {props.interests.map(interest => (<li>{interest}</li>))}
                        </ul>
                    </Typography>
                </div>

            </div>
        );
    }
}

const ActivityEditableData = (props) => {

    return prepareEditableData(props);
}

export default ActivityEditableData;