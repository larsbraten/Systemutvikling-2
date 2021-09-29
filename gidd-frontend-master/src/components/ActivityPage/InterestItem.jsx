import React from "react";
import { Fab } from "@material-ui/core";
import ClearIcon from "@material-ui/icons/Clear";
import Chip from "@material-ui/core/Chip";

const prepareInterestItem = (props) => {
  if (props.isEditing) {
    return (
      <Fab variant="extended">
        {props.interestItemValue}
        <Fab size="small" color="secondary" aria-label="edit">
          <ClearIcon />
        </Fab>
      </Fab>
    );
  } else {
    return <Chip className="interests-chip" label={props.interestItemValue} />;
  }
};

const InterestItem = (props) => {
  return prepareInterestItem(props);
};

export default InterestItem;
