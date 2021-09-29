import React from "react";
import Typography from "@material-ui/core/Typography";
import { TextField } from "@material-ui/core";
import { Controller } from "react-hook-form";

const prepareCapacity = (props) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-capacity cardish">
        <h2>Kapasitet</h2>
        <Controller
          render={({ field }) => {
            return (
              <TextField
                {...field}
                error={props.errors.activityCapacity}
                helperText={
                  props.errors.activityCapacity
                    ? props.errors.activityCapacity.message
                    : ""
                }
                fullWidth
                id="standard-number"
                label="Antall"
                type="number"
                placeholder="Hvor mange deltakere?"
                InputLabelProps={{
                  shrink: true,
                }}
              />
            );
          }}
          name="activityCapacity"
          control={props.control}
          defaultValue={props.fieldValue}
          rules={{
            required: "Påkrevd",
            pattern: {
              value: /[0-9]*/,
              message: "Feil format",
            },
          }}
        />
      </div>
      // <TextField
      //     className="ActivityCapacity"
      //     defaultValue={props.fieldValue}
      //     {...props.register("activityCapacity", {
      //         required: "Max antal deltakere er påkrevd",
      //         pattern: {
      //             value: /[0-9]*/,
      //             message: "Feil format"
      //         }
      //     })}
      //     error
      // />
    );
  } else {
    return (
      <div className="single-activity-capacity cardish">
        <h2>Kapasitet</h2>
        <Typography variant="subtitle" color="textSecondary">
          {props.fieldValue}
        </Typography>
      </div>
    );
  }
};

const ActivityCapacity = (props) => {
  return prepareCapacity(props);
};

export default ActivityCapacity;
