import React from "react";
import Avatar from "@material-ui/core/Avatar";
import Chip from "@material-ui/core/Chip";

const EnrolledList = (props) => {
  return (
    <div className="single-activity-members cardish">
      <h2>Påmeldte</h2>
      <ul>
        {props.fieldValue.map((user) => (
          <li>
            <Chip
              avatar={<Avatar cloror="primary">{user.firstName[0]}</Avatar>}
              label={`${user.firstName} ${user.surName}`}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EnrolledList;
