import {ActivityService} from "../Services/activityService";
import {CREATE_ACTIVITY_ERROR, CREATE_ACTIVITY_SUCCESS, SUBMITTING_NEW_ACTIVITY, CREATE_ACTIVITY_ERROR_401} from "../constants";


export const createActivityAction = (data) => async (dispatch) => {
    dispatch({
        type: SUBMITTING_NEW_ACTIVITY,
    })

    try {
        console.log(`submittet activityAction: ${data}`)
        const res = await ActivityService.postActivity(data)
        dispatch({
            type: CREATE_ACTIVITY_SUCCESS,
            payload: res,
        });

    } catch (err) {
        console.log("ERROR CAUGHT")
        console.log(err.response)
        if(err.response.status === 401){
            console.log("Caught 401 error");
            dispatch({
                type: CREATE_ACTIVITY_ERROR_401
            });  
        }
        
        dispatch({
            type: CREATE_ACTIVITY_ERROR,                    
            error: err.response.data.description
        });  
    }
}
