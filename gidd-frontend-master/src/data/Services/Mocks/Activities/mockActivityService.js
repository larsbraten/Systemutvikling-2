
import {activities} from "./ActivitesMock"

const getActivities = (data) => {
    let res;
    try{
        res = activities;
    }catch(err){

    }
    return res;
}

export const ActivityService = {
    getActivities
  };