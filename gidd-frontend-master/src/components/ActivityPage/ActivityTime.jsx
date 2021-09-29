import React from "react";
import Typography from "@material-ui/core/Typography";
import { TextField } from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import {
  KeyboardDatePicker,
  KeyboardTimePicker,
  MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import { Controller } from "react-hook-form";
import { DataUsageOutlined } from "@material-ui/icons";

const prepareActivityTime = (props) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-date cardish">
        <h2>Dato og Tid</h2>

        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <div className="create-activity-dato-start">
            <Controller
              render={({ field }) => {
                return (
                  <KeyboardTimePicker
                    {...field}
                    error={props.errors.activityStartTime}
                    helperText={
                      props.errors.activityStartTime
                        ? props.errors.activityStartTime.message
                        : null
                    }
                    key="time-picker-start"
                    required
                    margin="normal"
                    id="time-picker"
                    label="Start"
                    variant="inline"
                    // format="HH:mm"
                    ampm={false}
                    KeyboardButtonProps={{
                      "aria-label": "change time",
                    }}
                  />
                );
              }}
              control={props.control}
              {...props.register("activityStartTime", {
                required: "Påkrevd",
              })}
              defaultValue={new Date(props.startTime)}
            />
          </div>
          <div className="create-activity-dato-end">
            <Controller
              render={({ field }) => {
                return (
                  <KeyboardTimePicker
                    {...field}
                    error={props.errors.activityEndTime}
                    helperText={
                      props.errors.activityEndTime
                        ? props.errors.activityEndTime.message
                        : null
                    }
                    required
                    margin="normal"
                    key="time-picker-end"
                    id="time-picker"
                    label="Slutt"
                    variant="inline"
                    // format="HH:mm"
                    ampm={false}
                    KeyboardButtonProps={{
                      "aria-label": "change time",
                    }}
                  />
                );
              }}
              control={props.control}
              {...props.register("activityEndTime", {
                required: "Påkrevd",
              })}
              defaultValue={new Date(props.endTime)}
            />
          </div>
          <div className="create-activity-dato-kalender">
            <Controller
              render={({ field }) => {
                return (
                  <KeyboardDatePicker
                    {...field}
                    error={props.errors.activityDate}
                    helperText={
                      props.errors.activityDate
                        ? props.errors.activityDate.message
                        : null
                    }
                    disableToolbar
                    variant="inline"
                    format="yyyy-MM-dd"
                    margin="normal"
                    id="date-picker-inline"
                    label="Dato"
                    KeyboardButtonProps={{
                      "aria-label": "change date",
                    }}
                  />
                );
              }}
              control={props.control}
              {...props.register("activityDate", {
                required: "Påkrevd",
              })}
              defaultValue={new Date(props.endTime)}
            />
          </div>
        </MuiPickersUtilsProvider>
      </div>
    );
  } else {
    return (
      <div className="single-activity-date cardish">
        <h2>Dato</h2>
        <Typography variant="subtitle1" color="textSecondary">
          Start: {props.startTime}
        </Typography>
        <Typography variant="subtitle1" color="textSecondary">
          Slutt: {props.endTime}
        </Typography>
      </div>
    );
  }
};

const ActivityTime = (props) => {
  return prepareActivityTime(props);
};

export default ActivityTime;
