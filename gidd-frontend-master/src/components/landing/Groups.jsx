import "./landing.css";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import "./groups.css";

function Groups(props) {
  const { groups } = props;

  function renderGroups() {
    return groups.map((group) => {
      return flatActivityCard(group);
    });
  }

  return (
    <div className="Groups">
      <h1>My groups</h1>
      <div>{renderGroups(groups)}</div>
    </div>
  );
}

export default Groups;

export const flatActivityCard = (group) => {
  return (
    <Card className="GroupCard" key={group.Name}>
      <div className="GroupCardDetails">
        <Avatar aria-label="recipe" className="GroupAvatar">
          {group.Name[0]}
        </Avatar>
        <div>
          <Typography variant="h5" component="h5">
            {group.Name}
          </Typography>
        </div>
      </div>

      <br />
    </Card>
  );
};
