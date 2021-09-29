import "./activityViewMap.css";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import activitySki from "../landing/activitySki.svg";
import heart from "../landing/heart.svg";
import { Map, Marker } from "pigeon-maps";
import Button from "@material-ui/core/Button";
import CheckIcon from "@material-ui/icons/Check";
import MenuBookIcon from "@material-ui/icons/MenuBook";
import CloseIcon from "@material-ui/icons/Close";
import { iconMapping } from "../../utils/iconmapping.js";
import JoinActivityButton from "../joinActivityButton/joinActivityButton";
import { useMediaQuery } from "react-responsive";
import { Link } from "react-router-dom";

export default function ActivityViewMap(props) {
  const { activity } = props;
  const { closeFunc } = props;
  console.log(activity);
  const isMobile = useMediaQuery({ query: "(max-width: 600px)" });

  return (
    <div className="activityPopup" key={activity.id}>
      <div className="Close-button">
        {!isMobile ? (
          <Button
            onClick={closeFunc}
            endIcon={<CloseIcon />}
            className="closeButton"
            variant="contained"
          >
            Lukk
          </Button>
        ) : (
          <></>
        )}
      </div>
      <div className="RecommendedCardDetails">
        <img src={iconMapping[activity.activityIcon]} alt="activity icon" />
        <div>
          <Typography variant="h5" component="h5">
            {activity.name}
          </Typography>
        </div>
        {isMobile ? (
          <Button
            onClick={closeFunc}
            className="closeButton"
            variant="contained"
          >
            <CloseIcon />
          </Button>
        ) : (
          <></>
        )}
        <div className="RecommendedHeartContainer">
          <img
            className="RecommendedHeart"
            src={heart}
            alt="Favorite activity"
          />
        </div>
      </div>
      <div>
        <Typography align="center" variant="subtitle1" color="black">
          {activity.startTime}
          <br />
          {activity.location.place}
          <br />
          {activity.activityLevel}
        </Typography>
      </div>
      <div className="RecommendedCardDescription">
        <Typography variant="subtitle1" color="black">
          {activity.description}
        </Typography>
      </div>
      <div>
        <JoinActivityButton activity={activity} />
        <Link
          className="cardLink"
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
            Les mer!
          </Button>
        </Link>
      </div>
      <br />
    </div>
  );
}
