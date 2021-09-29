import "./landing.css";
import "./recommended.css";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import activitySki from "./activitySki.svg";
import heart from "./heart.svg";
import { Map, Marker } from "pigeon-maps";
import Button from "@material-ui/core/Button";
import CheckIcon from "@material-ui/icons/Check";
import MenuBookIcon from "@material-ui/icons/MenuBook";
import WeatherBox from "../weather/WeatherBox";
import JoinActivityButton from "../joinActivityButton/joinActivityButton";
import {Link, Redirect} from "react-router-dom";
import { v4 as uuid } from "uuid";
import { iconMapping } from "../../utils/iconmapping.js";

function RecommendedActivities(props) {
  const recommended = props.recommended;

  function renderActivities() {
    return recommended.map((activity) => {
      return standingRecommendedCard(
        activity,
        recommended,
        recommended.indexOf(activity)
      );
    });
  }

  return (
    <div className="recommended">
      <h1>Kommende aktiviteter</h1>
      <div className="RecommendedContainer">{renderActivities()}</div>
    </div>
  );
}

export default RecommendedActivities;

export const standingRecommendedCard = (activity, activities, order) => {
  return (
    <Card
      elevation={5}
      className="RecommendedCard"
      style={{ "--order": order }}
      key={uuid()}
    >
      <div className="RecommendedCardDetails">
        <img src={iconMapping[activity.activityIcon]} alt="activity icon" />
        <div>
          <Typography variant="h5" component="h5">
            {activity.name}
          </Typography>
          <Typography variant="subtitle1" color="textSecondary">
            {activity.startTime}
            {"  -  "}
            {activity.location.place}
          </Typography>
        </div>
        <div className="RecommendedWeatherContainer">
          <WeatherBox
            latitude={activity.location.latitude}
            longitude={activity.location.longitude}
            date={activity.startTime}
            size="small"
          />
        </div>
      </div>
      <div className="RecommendedCardMaps">
        <Map
          zoom={17}
          defaultCenter={[
            activity.location.latitude,
            activity.location.longitude,
          ]}
          defaultZoom={11}
        >
          <Marker
            key={activity.id}
            color={"red"}
            width={20}
            anchor={[activity.location.latitude, activity.location.longitude]}
          />
          {activities.map((secondaryActivity) => {
            if (secondaryActivity.coordinates === activity.coordinates) {
              return <></>;
            } else {
              return (
                <Marker
                  key={uuid()}
                  color={"grey"}
                  width={15}
                  anchor={[
                    secondaryActivity.location.latitude,
                    secondaryActivity.location.longitude,
                  ]}
                />
              );
            }
          })}
        </Map>
      </div>
      <div className="RecommendedCardDescription">
        <Typography variant="subtitle1" color="textSecondary">
          {activity.description}
        </Typography>
      </div>
      <div className="RecommendedCardButtons">
        <JoinActivityButton activity={activity} />
        <Link
            className = "cardLink"
            to={{
              pathname: `/activities/${activity.id}`,
              state: {
                userStatus: null,
              },
            }}
        >
          <Button
              endIcon={<MenuBookIcon />}
              className="RecommendedCardButtonReadMore"
              color="secondary"
              variant="contained"
          >
            Read more!
          </Button>
        </Link>

      </div>
      <br />
    </Card>
  );
};
