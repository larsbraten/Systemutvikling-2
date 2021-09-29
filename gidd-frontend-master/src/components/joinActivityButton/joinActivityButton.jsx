import { connect } from "react-redux";
import { joinActivity, unJoinActivity } from "../../data/actions/activities";
import CheckIcon from "@material-ui/icons/Check";
import Button from "@material-ui/core/Button";
import CloseIcon from '@material-ui/icons/Close';

function JoinActivityButton(props) {
  return renderButton(props);
}

const handleJoin = (activityId, props) => {
  props.joinActivity(activityId);
};

const handleUnJoin = (activityId, props) => {
  props.unJoinActivity(activityId);
}

const renderButton = (props) => {
  if (!props.activity) {
    return "";
  }
  else {
    if(props.user.enrolledActivities.filter(a => {return a.id===props.activity.id}).length > 0){
      return (
        <Button
          onClick={() => handleUnJoin(props.activity.id, props)}
          startIcon={<CloseIcon />}
          className="joinActivityButton"
          variant="contained"
        >
          Meld deg av
        </Button>
        
      );
    } else{
      return (
        <Button
          onClick={() => handleJoin(props.activity.id, props)}
          startIcon={<CheckIcon />}
          className="joinActivityButton"
          color="primary"
          variant="contained"
        >
          Meld deg på!
        </Button>
      );
    }
  }
};

const mapStateToProps = (state) => ({
  user: state.auth.user
});

export default connect(mapStateToProps, { joinActivity, unJoinActivity }, null, {
  forwardRef: true,
})(JoinActivityButton);
