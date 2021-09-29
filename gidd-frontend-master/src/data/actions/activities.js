
import {
  GET_ACTIVITIES,
  ERROR,
  GET_MY_ENROLLED_ACTIVITIES,
  GET_MY_ORGANIZED_ACTIVITIES,
  GET_LOCATIONS,
  JOIN_ACTIVITY,
  GET_INTERESTS,
  GET_FILTERED_ACTIVITIES,
  RESET_MESSAGE,
  DELETE_ACTIVITY
} from "../constants"
import {ActivityService} from "../Services/activityService"
import { authUser } from "./auth"
//import {ActivityService} from "../Services/Mocks/Activities/mockActivityService"

export const getActivities = () => async (dispatch) => {
    try {
      const res = await ActivityService.getActivities()
      dispatch({
        type: GET_ACTIVITIES,
        //payload: res.data,
        payload: res.data.payload,
      });
    } catch (err) {
      dispatch({
        type: ERROR,
        payload: {
          error: err,
        },
      });
    }
  };


  export const getLocations = () => async (dispatch) => {
    try {
      const res = await ActivityService.getLocations()
      dispatch({
        type: GET_LOCATIONS,
        //payload: res.data,
        payload: res.data.payload,
      });
    } catch (err) {
      dispatch({
        type: ERROR,
        payload: {
          error: err,
        },
      });
    }
  };

  export const getInterests = () => async (dispatch) => {
    try {
      const res = await ActivityService.getInterests()
      dispatch({
        type: GET_INTERESTS,
        //payload: res.data,
        payload: res.data.payload,
      });
    } catch (err) {
      dispatch({
        type: ERROR,
        payload: {
          error: err,
        },
      });
    }
  };

  export const getFilteredActivities = (params) => async (dispatch) => {
    try {
      const res = await ActivityService.getFilteredActivities(params)
      dispatch({
        type: GET_FILTERED_ACTIVITIES,
        //payload: res.data,
        payload: res.data.payload,
      });
    } catch (err) {
      dispatch({
        type: ERROR,
        payload: {
          error: err,
        },
      });
    }
  };  

  


  //TODO: 
export const getMyEnrolledActivities = () => async (dispatch) => {
  try {
    const res = await ActivityService.getMyEnrolledActivities()
    dispatch({
      type: GET_MY_ENROLLED_ACTIVITIES,
      //payload: res.data,
      payload: res.data.payload,
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: err,
      },
    });
  }
};


export const getMyOrganizedActivities = () => async (dispatch) => {
  try {
    const res = await ActivityService.getMyOrganizedActivities()
    dispatch({
      type: GET_MY_ORGANIZED_ACTIVITIES,
      //payload: res.data,
      payload: res.data.payload,
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: err,
      },
    });
  }
};

export const joinActivity = (activityId) => async (dispatch) => {
  try {
    const res = await ActivityService.joinActivity(activityId);
    if(!res.data.payload.includes("venteliste")){
      dispatch(getMyEnrolledActivities());
    }
    dispatch(authUser());
    dispatch({
      type: JOIN_ACTIVITY,
      payload: res.data.payload,
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: err,
      },
    });
  }
};

export const unJoinActivity = (activityId) => async (dispatch) => {
  try {
    const res = await ActivityService.unJoinActivity(activityId);
    dispatch(getMyEnrolledActivities());
    dispatch(authUser());
    dispatch({
      type: JOIN_ACTIVITY,
      payload: res.data.payload,
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: err,
      },
    });
  }
};

export const resetFeedbackMessage = () => async (dispatch) => {
  try {
    dispatch({
      type: RESET_MESSAGE,
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: err,
      },
    });
  }
};

const mapErrorMessage = (err) => {
  return (err.response && err.response.data && err.response.data.description) ?
      err.response.data.description : "Noe gikk galt";
}

export const deleteActivity = (activityId) => async (dispatch) => {
  try {
    await ActivityService.deleteActivity(activityId);
    // dispatch(getActivities())
    dispatch(authUser())
    dispatch({
      type: DELETE_ACTIVITY
    })
  } catch (err) {
    dispatch({
      type: ERROR,
      error: mapErrorMessage(err)
    })
  }
}