import React from "react";
import Typography from "@material-ui/core/Typography";

const ActivityTextData = (props) => {
    return (
        <Typography variant="subtitle1" color="textSecondary">
            <p>
                {props.labelName && `${props.labelName}:`}
                {props.fieldValue}
            </p>
        </Typography>
    );
};

export default ActivityTextData;