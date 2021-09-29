import "./landing.css";
import "./activity.css";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import activitySki from "./activitySki.svg";
import location from "./location.svg";
import { Link } from "react-router-dom";
import PrivateRoute from "../PrivateRoute";
import { iconMapping } from "../../utils/iconmapping.js";
import owner from "../../assets/king.svg";

function Activities(props) {
  const enrolledActivities = props.enrolledActivities;
  const organizedActivities = props.organizedActivities;

  function renderActivities() {
    let elements = [];
    if (organizedActivities.length != 0) {
      organizedActivities.forEach((activity) => {
        elements.push(
          flatActivityCard(
            activity,
            true,
            elements.length ? elements.length + 1 : 0,
            props.history
          )
        );
      });
    }
    if (enrolledActivities.length != 0) {
      enrolledActivities.forEach((activity) => {
        elements.push(
          flatActivityCard(activity, false, elements.length + 1, props.history)
        );
      });
    }

    return elements;
  }

  return (
    <div className="ActivityContainer">
      <h1>Mine aktiviteter</h1>
      <div className="MyActivityScroll">{renderActivities()}</div>
    </div>
  );
}

const selectActivity = (userStatus, activityId, history) => {
  history.push("/activities/" + activityId);
};

export const flatActivityCard = (activity, organizer, order, history) => {
  return (
    <Link
      className="cardLink"
      to={{
        pathname: `/activities/${activity.id}`,
        state: {
          userStatus: organizer ? "CREATOR" : "ENROLLED",
        },
      }}
    >
      <Card
        className="ActivityCard"
        style={{ "--order": order }}
        key={activity.id}
      >
        <div className="activityCardDetails">
          <img src={iconMapping[activity.activityIcon]} alt="activity icon" />
          <div>
            <Typography variant="h5" component="h5">
              {activity.name}
            </Typography>
            <Typography variant="subtitle1" color="textSecondary">
              {activity.description}
            </Typography>
          </div>
        </div>
        <div className="activityCardMaps">
          {organizer ? <img src={owner} alt="location icon" /> : ""}
        </div>
        <div className="activityCardDate">
          <Typography variant="subtitle2" color="textSecondary">
            {activity.startTime}
          </Typography>
          <Typography variant="subtitle2" color="textSecondary">
            {activity.status}
          </Typography>
          <Typography variant="subtitle2" color="textSecondary">
            {activity.location.place}
          </Typography>
        </div>
        <br />
      </Card>
    </Link>
  );
};

export default Activities;
