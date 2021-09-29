import React from "react";
import Typography from "@material-ui/core/Typography";
import { TextField } from "@material-ui/core";
import InputAdornment from "@material-ui/core/InputAdornment";
import AccountCircle from "@material-ui/icons/AccountCircle";
import { Controller } from "react-hook-form";

const prepareDescription = (props) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-description cardish">
        <h2>Beskrivelse</h2>
        <Controller
          render={({ field }) => {
            return (
              <TextField
                {...field}
                required
                error={props.errors.activityDescription}
                helperText={
                  props.errors.activityDescription
                    ? props.errors.activityDescription.message
                    : ""
                }
                id="standard-multiline-flexible"
                label="Beskrivelse"
                placeholder="Beskrivelse av min aktivitet"
                multiline
                rowsMax={12}
                fullWidth
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountCircle />
                    </InputAdornment>
                  ),
                }}
              />
            );
          }}
          name="activityDescription"
          control={props.control}
          rules={{
            required: "Påkrevd",
          }}
          defaultValue={props.fieldValue}
        />
      </div>
    );
  } else {
    return (
      <div className="single-activity-description cardish">
        <h2>Beskrivelse</h2>
        <Typography
          className="single-activity-fulldescription"
          variant="body2"
          color="textSecondary"
        >
          {props.fieldValue}
        </Typography>
      </div>
    );
  }
};

const ActivityDescription = (props) => {
  return prepareDescription(props);
};

export default ActivityDescription;
