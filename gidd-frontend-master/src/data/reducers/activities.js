
import {
  GET_ACTIVITIES,
  ERROR,
  GET_MY_ENROLLED_ACTIVITIES,
  GET_MY_ORGANIZED_ACTIVITIES,
  JOIN_ACTIVITY,
  GET_FILTERED_ACTIVITIES,
  GET_INTERESTS,
  GET_LOCATIONS,
  RESET_MESSAGE,
  DELETE_ACTIVITY
} from "../constants"


const defaultState = {
    activities: [],
    enrolledActivities: [],
    organizedActivities: [],
    joinActivityMessage: "",
    filteredActivities: [],
    locations: [],
    interests: [],
    
  };
  
  export default function activityReducer(state = defaultState, action) {
    switch (action.type) {
      case GET_ACTIVITIES:
        return {
          ...state,
          activities:action.payload
        };
      case GET_MY_ENROLLED_ACTIVITIES:
        return {
          ...state,
          enrolledActivities:action.payload
        };
      case GET_MY_ORGANIZED_ACTIVITIES:
        return {
          ...state,
          organizedActivities:action.payload
        };

      case JOIN_ACTIVITY:
        return {
          ...state,
          joinActivityMessage:action.payload
        }
      case RESET_MESSAGE:
        return {
          ...state,
          joinActivityMessage:""
        }
      case GET_FILTERED_ACTIVITIES:
        return {
          ...state,
          filteredActivities:action.payload
        };
      case GET_INTERESTS:
        return {
          ...state,
          interests:action.payload
        };
      case GET_LOCATIONS:
        return {
          ...state,
          locations:action.payload
        };

      case ERROR:
        return {
          ...state,
        };
      case DELETE_ACTIVITY:
        return {
          ...state,

        }
  
      default:
        return state;
    }
  }