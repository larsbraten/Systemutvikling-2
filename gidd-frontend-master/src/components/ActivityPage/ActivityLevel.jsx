import React, { useState } from "react";
import Typography from "@material-ui/core/Typography";
import WalkingMan from "../myActivities/walking-man.svg";
import ActivityMan from "../myActivities/ActivityMan.svg";
import Strength from "../myActivities/strength.svg";
import {
  ButtonGroup,
  FormControl,
  FormControlLabel,
  FormHelperText,
  Paper,
  Radio,
  RadioGroup,
} from "@material-ui/core";
import { ToggleButton, ToggleButtonGroup } from "@material-ui/lab";
import { Controller } from "react-hook-form";

const prepareActivityLevel = (props) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-level cardish">
        <h2>Aktivitetsnivå</h2>
        <Controller
          render={({ field }) => {
            return (
              <FormControl
                error={props.errors.activityLevel}
                helperText={
                  props.errors.activityLevel
                    ? props.errors.activityLevel.message
                    : ""
                }
              >
                <RadioGroup
                  className="edit-activity-levels"
                  orientation="vertical"
                  {...field}
                >
                  <div
                    className="edit-activity-levels-div"
                    // className={
                    //     activityLevel.rolig
                    //         ? "create-activity-nivaa-selected"
                    //         : "create-activity-nivaa-unselected"
                    // }
                    key="rolig"
                    id="rolig"
                    // onClick={() => handleActivityChange("rolig")}
                  >
                    <FormControlLabel
                      classes={null}
                      value="LOW"
                      control={<Radio />}
                      label=""
                    />
                    <img
                      className="create-activity-nivaa-img-left"
                      src={WalkingMan}
                      alt="icon"
                    />{" "}
                    <h2>Rolig</h2>
                  </div>

                  <div className="edit-activity-levels-div">
                    <FormControlLabel
                      classes={null}
                      value="MEDIUM"
                      control={<Radio />}
                      label=""
                    />
                    <img
                      className="edit-activity-man"
                      src={ActivityMan}
                      alt="icon"
                    />
                    <h2>Aktivt</h2>
                  </div>

                  <div className="edit-activity-levels-div">
                    <FormControlLabel
                      classes={null}
                      value="HIGH"
                      control={<Radio />}
                      label=""
                    />
                    <img
                      className="create-activity-nivaa-img-left"
                      src={Strength}
                      alt="icon"
                    />
                    <h2>Intens</h2>
                  </div>
                </RadioGroup>
                {props.errors.activityLevel && (
                  <FormHelperText>
                    {props.errors.activityLevel.message}
                  </FormHelperText>
                )}
              </FormControl>
            );
          }}
          name="activityLevel"
          control={props.control}
          defaultValue={props.fieldValue}
          rules={{ required: "Påkrevd" }}
        />
      </div>
    );
  } else {
    return (
      <div className="single-activity-level cardish">
        <h2>Nivå</h2>
        <Typography variant="subtitle" color="textSecondary">
          {props.fieldValue}
        </Typography>
      </div>
    );
  }
};

const ActivityLevel = (props) => {
  return prepareActivityLevel(props);
};

export default ActivityLevel;
