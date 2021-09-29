import {
    EDITING_ACTIVITY, EQUIPMENT_LIST_ERROR,
    ERROR,
    GET_ACTIVITY,
    GET_ACTIVITY_IS_LOADING, GET_EQUIPMENT_LIST,
    RESET_ACTIVITY_PAGE,
    STOP_EDITING_ACTIVITY,
    TOGGLE_EQUIPMENT,
    TOGGLE_EQUIPMENT_ERROR,
    TOGGLE_EQUIPMENT_IS_LOADING,
    UPDATE_ACTIVITY,
    UPDATE_ACTIVITY_ERROR,
    UPDATE_ACTIVITY_LOADING
} from "../constants";
import {ActivityService} from "../Services/activityService"

const mapErrorMessage = (err) => {
    return (err.response && err.response.data && err.response.data.description) ?
        err.response.data.description : "Noe gikk galt";
}

export const getActivity = (id) => async (dispatch) => {
    try {
        console.log("getActivity")
        const res = await ActivityService.getActivity(id)
        dispatch({
            type: GET_ACTIVITY,
            payload: res.data.payload,
        });
    } catch (err) {
        console.log("ERR", err)
        dispatch({
            type: ERROR,
            error: mapErrorMessage(err)
        });
    }
}

export const updateActivity = (id, data) => async (dispatch) => {
    dispatch({
        type: UPDATE_ACTIVITY_LOADING
    });

    try {
        await ActivityService.updateActivity(id, data)
        console.log("DONE UPD ACTIVITY")
        dispatch({
            type: UPDATE_ACTIVITY
        });
    } catch (err) {
        console.log("DID NOT UPD ACTIVITY", err)
        dispatch({
            type: UPDATE_ACTIVITY_ERROR,
            error: mapErrorMessage(err)
        });
    }
}

export const getActivityLoading = () => (dispatch) => {
    dispatch({
        type: GET_ACTIVITY_IS_LOADING
    });
}

export const startEditingActivity = () => (dispatch) => {
    dispatch({
        type: EDITING_ACTIVITY
    });
}

export const stopEditingActivity = () => (dispatch) => {
    dispatch({
        type: STOP_EDITING_ACTIVITY
    });
}

export const resetActivityPage = () => (dispatch) => {
    dispatch({
        type: RESET_ACTIVITY_PAGE
    });
}

export const claimEquipment = (equipmentId) => async (dispatch) => {
    dispatch({
        type: TOGGLE_EQUIPMENT_IS_LOADING
    })

    try {
        await ActivityService.claimEquipment(equipmentId)
        console.log("CLAIMED EQUIPMENT")
        dispatch({
            type: TOGGLE_EQUIPMENT
        });
    } catch (err) {
        console.log("DID NOT CLAIM EQUIPMENT", err)
        dispatch({
            type: TOGGLE_EQUIPMENT_ERROR,
            error: mapErrorMessage(err)
        });
    }
}

export const unclaimEquipment = (equipmentId) => async (dispatch) => {
    dispatch({
        type: TOGGLE_EQUIPMENT_IS_LOADING
    })

    try {
        await ActivityService.unclaimEquipment(equipmentId)
        console.log("UNCLAIMED EQUIPMENT")
        dispatch({
            type: TOGGLE_EQUIPMENT
        });
    } catch (err) {
        console.log("DID NOT UNCLAIM EQUIPMENT", err)
        dispatch({
            type: TOGGLE_EQUIPMENT_ERROR,
            error: mapErrorMessage(err)
        });
    }
}

// export const getEquipmentList = (activityId) => async (dispatch) => {
//     try {
//         const res = await ActivityService.getEquipmentList(activityId)
//         console.log("EQUIPMENT LIST")
//         console.log(res)
//         dispatch({
//             type: GET_EQUIPMENT_LIST,
//             payload: res.data.payload
//         });
//     } catch (err) {
//         console.log("DID NOT UNCLAIM EQUIPMENT", err)
//         dispatch({
//             type: EQUIPMENT_LIST_ERROR,
//             error: mapErrorMessage(err)
//         });
//     }
// }