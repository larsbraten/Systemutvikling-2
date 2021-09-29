import "./landing.css";
import Activities from "./Activities";
import Friends from "./Friends";
import Groups from "./Groups";
import RecommendedActivities from "./RecommendedActivities";
import UserInfo from "./UserInfo";

function Landing(props) {
  return (
    <div className="landingBase">
      <div className="landingGrid">
        <Activities
          enrolledActivities={props.enrolledActivities}
          organizedActivities={props.organizedActivities}
        />
        <RecommendedActivities recommended={props.recommended} />
        <UserInfo user={props.user} />
      </div>
    </div>
  );
}

export default Landing;
