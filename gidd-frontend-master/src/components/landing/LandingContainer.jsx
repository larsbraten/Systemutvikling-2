import { connect } from "react-redux";
import { withRouter } from "react-router";
import Landing from "./Landing";
import { useEffect, useState } from "react";
import { getFilteredActivities, getMyEnrolledActivities, getMyOrganizedActivities } from "../../data/actions/activities";
import LoaderWheel from "../loaderWheel/loaderWheel";
import Message from "../feedbackMessage/feedbackMessage"

function LandingContainer(props) {


  useEffect(() => {
    
    props.getFilteredActivities({
      activitySearchCriteria: {
        showPreviousActivities: false,
        name: "",
        locations: null,
        interests: null,
        activityLevels: null,
      },
      activityPage: {
        pageNumber: 0,
        pageSize: 20,
        sortDirection: "ASC",
        sortBy: "startTime",
      },
    });
    props.getMyEnrolledActivities();
    props.getMyOrganizedActivities();
  }, []);

  return prepareLanding(props);
}

const prepareLanding = (props) => {
  if (!props.enrolledActivities ||!props.organizedActivities ||!props.activities.content || !props.user) {
    return <LoaderWheel/>;
  } else { 
    return (
      <div>
        <Landing
          user={props.user}
          groups={group}
          recommended={props.activities.content}
          enrolledActivities={props.enrolledActivities}
          organizedActivities={props.organizedActivities}
          friends={friends}
        />
        {/* <Message message="test"></Message> */}
        {props.joinActivityMessage!==""?<Message key={props.joinActivityMessage} message={props.joinActivityMessage} time={3000}/>:""}
      </div>
    );
  }
};

const mapStateToProps = (state) => ({
  activities: state.activities.filteredActivities,
  enrolledActivities: state.activities.enrolledActivities,
  organizedActivities: state.activities.organizedActivities,
  user: state.auth.user,
  joinActivityMessage: state.activities.joinActivityMessage,
});

export default withRouter(
  connect(mapStateToProps, { getFilteredActivities, getMyEnrolledActivities, getMyOrganizedActivities }, null, {
    forwardRef: true,
  })(LandingContainer)
);




//_____________________DUMMY:

const group = [
  {
    Name: "Tennis på Grynerløkka",
  },
  {
    Name: "Seiling i Karribien",
  },
  {
    Name: "Strikkeklubb",
  },
];

const friends = [
  {
    Name: "Kristin Wahl",
    SkillLevel: "VeryGOOD",
    Points: 100,
  },
  {
    Name: "Lisa haugen",
    SkillLevel: "VeryGOOD",
    Points: 100,
  },
  {
    Name: "Peder Aas",
    SkillLevel: "VeryGOOD",
    Points: 100,
  },
];
