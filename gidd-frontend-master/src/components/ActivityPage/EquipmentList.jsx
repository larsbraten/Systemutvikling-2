import React from "react";
import { List, ListItem, ListSubheader, TextField } from "@material-ui/core";
import EquipmentItem from "./EquipmentItem";
import { Controller, useFieldArray } from "react-hook-form";
import { Alert, AlertTitle } from "@material-ui/lab";

const prepareEquipmentList = (
  props,
  fields,
  append,
  remove,
  prepend,
  control
) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-equipment cardish">
        <h2>Utstyr</h2>
        <List>
          {fields.map((equipmentItem, index) => {
            console.log("EQUIPMENT FIELDS");
            console.log(fields);
            console.log("EQUIP ITEM");
            console.log(equipmentItem);
            return (
              <ListItem
                key={
                  equipmentItem.id
                    ? equipmentItem.id
                    : Math.floor(Math.random() * 10000)
                }
              >
                <input
                  type="hidden"
                  defaultValue={equipmentItem.id}
                  name={`activityEquipment.${index}.id`}
                  {...props.register}
                />
                <Controller
                  render={({ field }) => {
                    // console.log("EQUIPMENT FIELD")
                    // console.log(field)
                    return <TextField {...field} />;
                  }}
                  defaultValue={equipmentItem.name}
                  // defaultValue={interest["0"] ? interest : ""}
                  name={`activityEquipment.${index}.name`}
                  control={control}
                />
                <input
                  type="hidden"
                  defaultValue={props.activityId}
                  name={`activityEquipment.${index}.activity`}
                  {...props.register}
                />
                <input
                  type="hidden"
                  defaultValue={equipmentItem.claimedByUser}
                  name={`activityEquipment.${index}.claimedByUser`}
                  {...props.register}
                />
                <p
                  onClick={() => {
                    remove(index);
                  }}
                >
                  Fjern
                </p>
              </ListItem>
            );
          })}
        </List>
        <p
          onClick={() => {
            // append
            // (
            //     {
            //         "0":
            //             {
            //                 id: "",
            //                 name: "",
            //                 activity: props.activityId,
            //                 claimedByUser: null
            //             }
            //     }
            // )
            // append
            // (
            //     {"0": ""}
            // )
            append({
              name: "",
              id: "",
              activity: props.activityId,
              claimedByUser: null,
            });
          }}
        >
          Legg til
        </p>
      </div>
    );
  } else if (props.isToggleable) {
    return (
      <div className="single-activity-equipment cardish">
        <h2>Utstyr</h2>
        <List
          subheader={
            <ListSubheader
              component="div"
              id="nested-list-subheader"
            ></ListSubheader>
          }
        >
          {props.fieldValue.map((item) => (
            <EquipmentItem isToggling listItemValue={item} {...props} />
          ))}
          {props.toggleError && (
            <Alert severity="error">
              <AlertTitle>Feil</AlertTitle>
              {props.toggleError}
            </Alert>
          )}
        </List>
      </div>
    );
  } else {
    return (
      <div className="single-activity-equipment cardish">
        <h2>Utstyr</h2>
        <List
          subheader={
            <ListSubheader
              component="div"
              id="nested-list-subheader"
            ></ListSubheader>
          }
        >
          {props.fieldValue.map((item) => (
            <EquipmentItem listItemValue={item.name} />
          ))}
        </List>
      </div>
    );
  }
};

const EquipmentList = (props) => {
  const { control } = props;
  const { fields, append, remove, prepend } = useFieldArray({
    control,
    name: "activityEquipment",
  });
  return prepareEquipmentList(props, fields, append, remove, prepend, control);
};

export default EquipmentList;
