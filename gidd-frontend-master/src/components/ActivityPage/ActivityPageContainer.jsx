import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import { useParams, withRouter } from "react-router";
import {
  getActivity,
  getActivityLoading,
  startEditingActivity,
  stopEditingActivity,
  updateActivity,
  resetActivityPage,
  claimEquipment,
  unclaimEquipment,
} from "../../data/actions/activity";
import {
  joinActivity,
  unJoinActivity,
  deleteActivity,
} from "../../data/actions/activities";
import LoaderWheel from "../loaderWheel/loaderWheel";
import EquipmentList from "./EquipmentList";
import EnrolledList from "./EnrolledList";
import { useForm } from "react-hook-form";
import ActivityTitle from "./ActivityTitle";
import ActivityCreator from "./ActivityCreator";
import ActivityLevel from "./ActivityLevel";
import ActivityInterests from "./ActivityInterests";
import ActivityDescription from "./ActivityDescription";
import ActivityTextData from "./ActivityTextData";
import ActivityTime from "./ActivityTime";
import ActivityCapacity from "./ActivityCapacity";
import { Alert, AlertTitle } from "@material-ui/lab";
import { format } from "date-fns";
import ActivityLocation from "./ActivityLocation";
import { Redirect, useHistory } from "react-router-dom";
import "./activityPage.css";

const mapStateToProps = (state) => {
  return {
    user: state.auth.user,
    activity: state.activity.activity,
    isLoading: state.activity.isLoading,
    error: state.activity.error,
    isEditing: state.activity.isEditing,
    isUpdated: state.activity.isUpdated,
    isUpdateLoading: state.activity.isUpdateLoading,
    updateError: state.activity.updateError,
    isReload: state.activity.isReload,
    isToggledEquipment: state.activity.isToggledEquipment,
    isToggleEquipmentLoading: state.activity.isToggleEquipmentLoading,
    toggleEquipmentError: state.activity.toggleEquipmentError,
    isRefreshEquipment: state.activity.isRefreshEquipment,
  };
};

const handleJoin = (activityId, props) => {
  props.joinActivity(activityId);
  props.resetActivityPage();
  props.getActivityLoading();
  props.getActivity(activityId);
};

const handleUnJoin = (activityId, props) => {
  props.unJoinActivity(activityId);
  props.resetActivityPage();
  props.getActivityLoading();
  props.getActivity(activityId);
};

const handleDelete = (activityId, props, routeChange) => {
  props.deleteActivity(activityId);
  routeChange();
};

const mapUserStatusToButton = (userStatus, activityId, props, routeChange) => {
  switch (userStatus) {
    case "CREATOR":
      return (
        <button onClick={() => handleDelete(activityId, props, routeChange)}>
          Avlys
        </button>
      );
    // case "ENROLLED" || "WAITING":
    //     return <button onClick={() => handleUnJoin(activityId, props)}>Meld av</button>
    // case "NONE":
    //     return <button onClick={() => handleJoin(activityId, props)}>Bli med!</button>
    default:
      if (
        props.user.enrolledActivities.filter((a) => {
          return a.id === props.activity.id;
        }).length > 0
      ) {
        return (
          <button onClick={() => handleUnJoin(activityId, props)}>
            Meld av
          </button>
        );
      } else {
        return (
          <button onClick={() => handleJoin(activityId, props)}>
            Bli med!
          </button>
        );
      }
    // return <button>ERROR</button>
  }
};

const ActivityPageContainer = (props) => {
  const { id } = useParams();

  const history = useHistory();

  const routeChange = () => {
    let path = `/`;
    history.push(path);
  };

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset();
    }
    props.getActivityLoading();
    props.getActivity(id);
    // before unmount
    return () => {
      props.resetActivityPage();
    };
  }, [props.isReload, props.isRefreshEquipment]);

  // useEffect(() => {
  //     props.getActivityLoading();
  //     props.getActivity(id);
  //     console.log("EQUIPMENT PAGE RELOAD")
  //     // before unmount
  // }, [props.isRefreshEquipment])

  useEffect(() => {
    if (props.activity) {
      reset({
        activityInterests: [...props.activity.interests.map((i) => [i])],
        activityEquipment: [...props.activity.equipment],
      });
    }
  }, [props.activity]);

  const {
    register,
    control,
    handleSubmit,
    watch,
    setValue,
    reset,
    formState: {
      errors,
      isValid,
      isSubmitting,
      isSubmitSuccessful,
      isSubmitted,
    },
  } = useForm({
    mode: "onBlur",
    criteriaMode: "firstError",
    shouldFocusError: true,
    reValidateMode: "onSubmit",
    shouldUnregister: true, // todo keep ???
    // defaultValues: {
    //     activityInterests: [{"0": ""}],
    //     // activityLevel: ""
    //     activityEquipment: [
    //         // {"0": ""}
    //         {
    //         id: "",
    //         name: "",
    //         activity: "",
    //         claimedByUser: ""
    //         }
    //         ]
    // }
  });

  const onSubmit = (data, e) => {
    e.preventDefault();
    console.log("FORM DATA");
    console.log(data);

    props.updateActivity(id, {
      name: data.activityTitle,
      description: data.activityDescription,
      // location: data.activityLocation, // todo change
      location: props.activity.location, // todo change
      capacity: data.activityCapacity,
      activityLevel: data.activityLevel,
      interests: data.activityInterests.reduce(function (a, b) {
        return a.concat(b);
      }, []),
      startTime: `${format(data.activityDate, "yyyy-MM-dd")} ${format(
        data.activityStartTime,
        "HH:mm"
      )}`,
      endTime: `${format(data.activityDate, "yyyy-MM-dd")} ${format(
        data.activityEndTime,
        "HH:mm"
      )}`,
      equipment: data.activityEquipment,
    });
  };

  const onErrors = (errors) => {
    console.log("ERRORS:", errors);
  };

  const onReset = () => {
    reset();
    props.stopEditingActivity();
  };

  const redirect = () => {
    return <Redirect to={props.history.location} />;
  };

  const prepareActivityView = () => {
    const { userStatus } = props.location.state; // userStatus is sent as a parameter in App

    if (props.isLoading) {
      return (
        <div className="ActivityPageContainer-Loading">
          <LoaderWheel />
        </div>
      );
    } else if (props.error) {
      return (
        <div className="ActivityPageContainer-Error">
          <p>{props.error}</p>
        </div>
      );
    } else if (props.activity) {
      return (
        <div className="single-activity-base">
          <form
            id="viewActivityForm"
            className="single-activity-grid"
            onSubmit={handleSubmit(onSubmit, onErrors)}
          >
            <div className="single-activity-cancel">
              {mapUserStatusToButton(userStatus, id, props, routeChange)}
            </div>
            <div className="single-activity-title">
              <ActivityTitle
                fieldValue={props.activity.name}
                isEditable={userStatus === "CREATOR"}
                isEditing={props.isEditing}
                register={register}
                errors={errors}
                control={control}
              />
            </div>

            <ActivityCreator
              fieldValue={`${props.activity.creator.firstName} ${props.activity.creator.surName}`}
              isEditable={false}
            />

            <ActivityLevel
              fieldValue={props.activity.activityLevel}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />

            <ActivityInterests
              fieldValue={props.activity.interests}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />

            <ActivityCapacity
              fieldValue={props.activity.capacity}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />

            <ActivityTime
              startTime={props.activity.startTime}
              endTime={props.activity.endTime}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />
            {/** 
              <ActivityTextData
                fieldName="locationPlace"
                labelName=""
                fieldValue={props.activity.location.place}
                isEditable={false}
/> */}

            <EnrolledList
              fieldValue={props.activity.enrolledUsers}
              isEditable={false}
            />

            <ActivityDescription
              fieldValue={props.activity.description}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />

            <ActivityLocation
              fieldValue={props.activity.location}
              isEditable={userStatus === "CREATOR"}
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
            />

            <EquipmentList
              fieldValue={props.activity.equipment}
              isEditable={userStatus === "CREATOR"}
              isToggleable={
                userStatus === "CREATOR" || userStatus === "ENROLLED" || props.user.enrolledActivities.filter((a) => {
                  return a.id === props.activity.id;
                }).length > 0
              }
              isEditing={props.isEditing}
              register={register}
              errors={errors}
              control={control}
              activityId={parseInt(id)}
              userId={props.user.id}
              onClaim={props.claimEquipment}
              onUnclaim={props.unclaimEquipment}
              isToggled={props.isToggledEquipment}
              isToggleLoading={props.isToggleEquipmentLoading}
              toggleError={props.toggleEquipmentError}
            />

            {props.updateError && (
              <Alert severity="error">
                <AlertTitle>Feil</AlertTitle>
                {props.updateError}
              </Alert>
            )}
            <div className="single-activity-join">
              {props.isEditing && (
                <button className="single-activity-oppdater" type="submit">
                  Oppdater
                </button>
              )}
              {props.isEditing && (
                <input
                  className="single-activity-angre"
                  type="button"
                  value="Angre"
                  onClick={onReset}
                />
              )}
              {userStatus === "CREATOR" && !props.isEditing && (
                <button onClick={props.startEditingActivity}>Rediger</button>
              )}
            </div>
          </form>
        </div>
      );
    } else {
      return <p>WTF!!</p>;
    }
  };

  return prepareActivityView();
};

export default withRouter(
  connect(mapStateToProps, {
    getActivity,
    getActivityLoading,
    startEditingActivity,
    stopEditingActivity,
    updateActivity,
    resetActivityPage,
    claimEquipment,
    unclaimEquipment,
    joinActivity,
    unJoinActivity,
    deleteActivity,
  })(ActivityPageContainer)
);
