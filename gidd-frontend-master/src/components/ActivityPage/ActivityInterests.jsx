import React from "react";
import Typography from "@material-ui/core/Typography";
import { Input, List, ListItem, TextField } from "@material-ui/core";
import InterestItem from "./InterestItem";
import { useFieldArray, Controller } from "react-hook-form";

const prepareInterests = (props, fields, append, remove, prepend, control) => {
  if (props.isEditable && props.isEditing) {
    return (
      <div className="single-activity-interests cardish">
        <h2>Interesser</h2>
        <List>
          {fields.map((interest, index) => {
            return (
              <ListItem key={interest.id}>
                <Controller
                  render={({ field }) => {
                    return <TextField {...field} />;
                  }}
                  defaultValue={interest["0"]}
                  // defaultValue={interest["0"] ? interest : ""}
                  name={`activityInterests.${index}[0]`}
                  control={control}
                />
                {/*<input*/}
                {/*    defaultValue={interest["0"] ? interest : ""}*/}
                {/*    {...props.register(`activityInterests.${index}`)}*/}
                {/*/>*/}
                <p onClick={() => remove(index)}>Fjern</p>
              </ListItem>
            );
          })}
        </List>
        <p
          onClick={() => {
            append({ 0: "" });
          }}
        >
          Legg til
        </p>
      </div>

      // <div>
      //     <List>
      //         <Typography variant="subtitle1" color="textSecondary">
      //             Interesser:
      //         </Typography>
      //
      //         {fields.map((interest, index) => {
      //             console.log("PREPARING NEW INTEREST FIELD")
      //             console.log(interest)
      //             console.log(fields)
      //             return (
      //                 // <div>
      //                 //     <Controller
      //                 //         render={({field}) =>
      //                 //             <ListItem key={interest.id}>
      //                 //                 <TextField
      //                 //                     defaultValue={interest["0"] ? interest : ""}
      //                 //                 />
      //                 //                 <p onClick={() => remove(index)}>Fjern</p>
      //                 //             </ListItem>}
      //                 //         name={`activityInterests.${index}`}
      //                 //         control={control}
      //                 //     />
      //                 // </div>
      //                 <ListItem key={interest.id}>
      //                     <Controller
      //                         render={({field}) => <input {...field} />}
      //                         name={`activityInterests.${index}`}
      //                         defaultValue={interest["0"] ? interest : ""}
      //                         control={control}
      //                      />
      //                     <p onClick={() => remove(index)}>Fjern</p>
      //                 </ListItem>
      //
      //                 // <ListItem key={interest.id}>
      //                 //     <TextField
      //                 //         defaultValue={interest["0"] ? interest : ""}
      //                 //         type="text"
      //                 //         name={`activityInterests.${index}`}
      //                 //         inputRef={props.register()}
      //                 //     />
      //                 //     <p onClick={() => remove(index)}>Fjern</p>
      //                 // </ListItem>
      //             );
      //         })}
      //     </List>
      //     <p onClick={() => {append({})}}>Legg til</p>
      // </div>

      // <div>
      //     <Typography variant="subtitle1" color="textSecondary">
      //         Interesser:
      //         {props.fieldValue.map(interest => (<InterestItem isEditing interestItemValue={interest}/>))}
      //     </Typography>
      //     <TextField
      //         className="EquipmentList-new"
      //         placeholder="Legg til interesser"
      //         {...props.register("activityNewInterest")}
      //     />
      // </div>
    );
  } else {
    return (
      <div className="single-activity-interests cardish ">
        <h2>Interesser</h2>
        <Typography
          className="interests-chip-flex"
          variant="subtitle"
          color="textSecondary"
        >
          {props.fieldValue.map((interest) => (
            <InterestItem interestItemValue={interest} />
          ))}
        </Typography>
      </div>
    );
  }
};

const ActivityInterests = (props) => {
  const { control } = props;
  const { fields, append, remove, prepend } = useFieldArray({
    control,
    name: "activityInterests",
  });
  return prepareInterests(props, fields, append, remove, prepend, control);
};

export default ActivityInterests;
