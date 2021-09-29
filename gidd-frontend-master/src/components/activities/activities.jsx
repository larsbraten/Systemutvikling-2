import "./activities.css";
import "../landing/recommended.css";
import { standingRecommendedCard } from "../landing/RecommendedActivities";

function Activities(props) {
  function renderActivities() {
    return props.activities.content.map((activity) => {
      return standingRecommendedCard(
        activity,
        props.activities.content,
        props.activities.content.indexOf(activity)
      );
    });
  }

  return <div className="activityGridView">{renderActivities()}</div>;
}

export default Activities;
