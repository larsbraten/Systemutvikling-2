import React from "react";
import Typography from "@material-ui/core/Typography";
import { TextField } from "@material-ui/core";
import { Controller } from "react-hook-form";
import InputAdornment from "@material-ui/core/InputAdornment";
import AccountCircle from "@material-ui/icons/AccountCircle";

const prepareTitle = (props) => {
  if (props.isEditable && props.isEditing) {
    return (
      <Controller
        render={({ field }) => {
          return (
            <>
              <h2>Tittel</h2>
              <TextField
                className="ActivityTitle"
                {...field}
                required
                error={props.errors.activityTitle}
                helperText={
                  props.errors.activityTitle
                    ? props.errors.activityTitle.message
                    : null
                }
                id="standard-required"
                label="tittel"
                fullWidth
                placeholder="navnet på din nye aktivtet"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountCircle />
                    </InputAdornment>
                  ),
                }}
              />
            </>
          );
        }}
        name="activityTitle"
        control={props.control}
        defaultValue={props.fieldValue}
        rules={{
          required: "Tittel er påkrevd",
        }}
      />
    );
  } else {
    return (
      <Typography variant="h2" component="h2">
        {props.fieldValue}
      </Typography>
    );
  }
};

const ActivityTitle = (props) => {
  return prepareTitle(props);
};

export default ActivityTitle;
