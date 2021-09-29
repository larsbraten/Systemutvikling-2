import "./landing.css";
import checkIcon from "./checkMark.svg";
import starIcon from "./pointIcon.svg";
import activityIcon from "./activityIcon.svg";

function UserInfo(props) {
  const { user } = props;
  console.log(user);
  return (
    <div className="UserInfo">
      <div className="UserName">
        <h1>Hei, {user.persona.firstName}!</h1>
        <img className="userInfoStar" src={checkIcon} alt="UserIsStarred" />
      </div>
      <div className="activitylevel">
        <img className="activityIcon" src={activityIcon} alt="UserIsStarred" />
        <p>Level: {user.activityLevel}</p>
      </div>
      <div className="points">
        <img className="pointIcon" src={starIcon} alt="UserIsStarred" />
        <p>Points: {user.points}</p>
      </div>
    </div>
  );
}

export default UserInfo;
