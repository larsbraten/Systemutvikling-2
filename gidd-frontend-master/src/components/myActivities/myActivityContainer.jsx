
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { getMyEnrolledActivities } from '../../data/actions/activities';
import MyActivities from './myActivities';

const mapStateToProps = (state) => ({
    myActivities: state.myActivities,
});

// const mapDispatchToProps = (dispatch) => ({ getCourses });

const MyActivityContainer = connect(mapStateToProps, { getMyEnrolledActivities }, null, {
  forwardRef: true,
})(MyActivities);

export default withRouter(MyActivityContainer);