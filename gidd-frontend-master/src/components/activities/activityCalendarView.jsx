import "./activitycalendar.css";
import { flatActivityCard } from "../landing/Activities";
import { DatePicker, RangePicker, theme } from "react-trip-date";
import { useEffect, useRef, useState } from "react";
import { Chip, Paper } from "@material-ui/core";
import user from "../../assets/User.svg";

import { Redirect } from "react-router-dom";

function ActivitiesCalendarView(props) {
  function renderActivities() {
    return props.activities.content.map((activity) => {
      return flatActivityCard(
        activity,
        props.activities.content,
        props.activities.content.indexOf(activity)
      );
    });
  }

  const table = createActivityTimeTable(props.activities.content);
  const card = createTimeTableCards(table);

  return (
    <div className="calendar-grid-view">
      <div className="activity-calendar">
        <DatePicker
          numberOfMonths={1}
          numberOfSelectableDays={1}
          onChange={(date) => {
            
            props.handleCalendarChange(date)
          }}
        />
      </div>
      <div className="calendar-activities">{card}</div>
    </div>
  );
}

export default ActivitiesCalendarView;

export function createActivityTimeTable(activities) {
  //sort activities
  let timeArray = [];
  for (let i = 0; i < 24; i++) {
    timeArray.push([]);
  }

  //console.log(timeArray);
  const sorted = activities.sort((activity1, activity2) => {
    //console.log(activity1.startTime.split(" ")[1].split(":")[0]);
    return (
      parseInt(activity1.startTime.split(" ")[1].split(":")[0]) +
      parseInt(activity1.startTime.split(" ")[1].split(":")[1]) -
      (parseInt(activity2.startTime.split(" ")[1].split(":")[0]) +
        parseInt(activity2.startTime.split(" ")[1].split(":")[1]))
    );
  });

  //console.log(sorted);

  sorted.map((singleActivity) => {
    let index = parseInt(singleActivity.startTime.split(" ")[1].split(":")[0]);

    //console.log(index);
    //console.log(singleActivity);
    timeArray[index].push(singleActivity);
    //console.log(timeArray[index]);
  });
  //console.log(timeArray);
  return timeArray;
}

export function createTimeTableCards(sortedActivityArray) {
  //console.log(sortedActivityArray);

  const elements = [];

  for (let i = 0; i < sortedActivityArray.length; i++) {
    if (sortedActivityArray[i] !== []) {
      elements.push(
        <TimeTableCard
          activityElement={sortedActivityArray[i]}
          sortedActivityArray={sortedActivityArray}
        />
      );
    }
  }
  //console.log(elements);
  return elements;
}

export function TimeTableCard(props) {
  const handleOnclick = (event) => {
    if (buttonActive) {
      setButtonActive(false);
    } else {
      setButtonActive(true);
    }
  };
  const myRef = useRef(null);

  useEffect(() => {
    if (props.sortedActivityArray.indexOf(props.activityElement) === 7) {
      myRef.current.scrollIntoView();
    }
  });

  const [buttonActive, setButtonActive] = useState(false);

  return (
    <div
      ref={myRef}
      className="kalendar-activity-wrapper"
      key={props.sortedActivityArray.indexOf(props.activityElement)}
      style={{
        "--order": props.sortedActivityArray.indexOf(props.activityElement),
      }}
    >
      <Paper
        elevation={5}
        square={false}
        className={
          buttonActive
            ? "kalendar-time-entry-active"
            : "kalendar-time-entry-disabled"
        }
      >
        <button
          type="button"
          className="kalendar-activity-button"
          onClick={handleOnclick}
        >
          <p>Kl: {props.sortedActivityArray.indexOf(props.activityElement)}</p>
          {props.activityElement.map((activity) => {
            return (
              <Chip
                elevation={5}
                className={
                  activity.activityLevel === "HIGH"
                    ? "chip-kalendar-hard"
                    : activity.activityLevel === "MEDIUM"
                    ? "chip-kalendar-medium"
                    : "chip-kalendar-easy"
                }
                icon={
                  <img className="chip-kalendar-button" src={user} alt="icon" />
                }
                onClick={() => <Redirect to={`/activities/${activity.id}`} />}
                label={activity.name}
              />
            );
          })}
        </button>
        <div
          className={
            buttonActive
              ? "kalendar-activity-content-visible"
              : "kalendar-activity-content-hidden"
          }
          style={{ "max-heigth": "100%" }}
        >
          {props.activityElement.map((activity) => {
            return flatActivityCard(activity, "w", 1);
          })}
        </div>
      </Paper>
      <hr />
    </div>
  );
}

export function rearrangeTime(sortedActivityArray) {
  let array = sortedActivityArray;
  for (let i = 0; i < 7; i++) {
    array.push(sortedActivityArray[i]);
  }
  array.splice(0, 7);
  return array;
}
