
import { combineReducers } from 'redux';
import user from "./user"
import auth from "./auth"
import registration from "./registration";
import activities from "./activities"
import createActivity from "./createActivity";
import activity from "./activity"


const createRootReducer = () =>
  combineReducers({
      user: user,
      auth: auth,
      registration: registration,
      activities: activities,
      activity: activity,

      createActivity: createActivity,
  });
  
export default createRootReducer;