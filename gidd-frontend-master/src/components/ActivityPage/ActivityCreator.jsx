import React from "react";

const ActivityCreator = (props) => {
  return (
    <div className="single-activity-master cardish">
      <h2>Aktivitetsmaster</h2>
      <p>{props.fieldValue}</p>
    </div>
  );
};

export default ActivityCreator;
