import React, { useEffect, useState } from "react";
import { ListItem, ListItemText } from "@material-ui/core";
import LoaderWheel from "../loaderWheel/loaderWheel";
import Chip from "@material-ui/core/Chip";

const prepareToggleButton = (props, isLoading, setIsLoading) => {
  if (isLoading) {
    return <LoaderWheel />;
  } else if (props.listItemValue.claimedByUser) {
    if (props.listItemValue.claimedByUser === props.userId) {
      return (
        <ListItem
          button
          onClick={(e) => {
            e.preventDefault();
            setIsLoading(true);
            props.onUnclaim(props.listItemValue.id);
          }}
        >
          Tar ikke med!
        </ListItem>
      );
    } else {
      return <ListItem button>Fikset!</ListItem>;
    }
  } else {
    return (
      <ListItem
        button
        onClick={(e) => {
          e.preventDefault();
          setIsLoading(true);
          props.onClaim(props.listItemValue.id);
        }}
      >
        Tar med!
      </ListItem>
    );
  }
};

const prepareEquipmentItem = (props, isLoading, setIsLoading) => {
  if (props.isToggling) {
    return (
      <ListItem>
        <ListItemText>
          <Chip className="chipEquipment" label={props.listItemValue.name} />
        </ListItemText>
        {prepareToggleButton(props, isLoading, setIsLoading)}
      </ListItem>
    );
  } else if (props.isEditing) {
    return (
      <ListItem>
        <ListItemText>
          <Chip className="chipEquipment" label={props.listItemValue.name} />
        </ListItemText>
        <ListItem button>Fjern</ListItem>
      </ListItem>
    );
  } else {
    return (
      <ListItem>
        <ListItemText>
          <Chip className="chipEquipment" label={props.listItemValue.name} />
        </ListItemText>
      </ListItem>
    );
  }
};

const EquipmentItem = (props) => {
  const [isLoading, setIsLoading] = useState(false);

  return prepareEquipmentItem(props, isLoading, setIsLoading);
};

export default EquipmentItem;
