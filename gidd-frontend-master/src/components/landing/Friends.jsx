import "./landing.css";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import message from "./message.svg";
import Avatar from "@material-ui/core/Avatar";
import "./friends.css";

function Friends(props) {
  const { friends } = props;

  function renderActivities() {
    return friends.map((friend) => {
      return flatActivityCard(friend);
    });
  }
  return (
    <div className="Friends">
      <h1>My friends</h1>
      {renderActivities()}
    </div>
  );
}

export default Friends;

export const flatActivityCard = (friend) => {
  return (
    <Card className="FriendCard" key={friend.Name}>
      <div className="friendCardDetails">
        <Avatar aria-label="recipe" className="friendAvatar">
          {friend.Name[0]}
        </Avatar>
        <div>
          <Typography variant="h5" component="h5">
            {friend.Name}
          </Typography>
        </div>
      </div>
      <div className="friendCardMessage">
        <img src={message} alt="Send message" />
      </div>

      <br />
    </Card>
  );
};
