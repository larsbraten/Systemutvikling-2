import {CREATE_ACTIVITY_ERROR, CREATE_ACTIVITY_SUCCESS, SUBMITTING_NEW_ACTIVITY, CREATE_ACTIVITY_ERROR_401} from "../constants";

const emptyActivity = {
    name: "",
    description: "",
    location: {
        place: "",
        city: "",
        country: "",
        latitude: "",
        longitude: "",
    },
    capacity: 1,
    equipment: [],
    interests: [],
    startTime: "",
    endTime: ""
}

const defaultState = {
    activity: emptyActivity,
    isLoading: false,
    idLastCreatedActivity: null,
    error: [],
    redirectToLogin: false
}
// todo case reset create activity page state
export default function createActivityReducer(state = defaultState, action) {
    switch (action.type) {
        case SUBMITTING_NEW_ACTIVITY:
            return {
                ...state,
                isLoading: true,
                error: [],
                redirectToLogin: false,
                idLastCreatedActivity: null
            };

        case CREATE_ACTIVITY_SUCCESS:
            return {
                ...state,
                isLoading: false,
                idLastCreatedActivity: action.payload,
                activity: emptyActivity,
                redirectToLogin: false
            };

        case CREATE_ACTIVITY_ERROR:
            return {
                ...state,
                isLoading: false,
                error: [action.error],
                redirectToLogin: false
            };

        case CREATE_ACTIVITY_ERROR_401:
            return {                
                ...state,
                redirectToLogin: true,
                activity: emptyActivity,
                isLoading: false,
                idLastCreatedActivity: null,
                error: []
            };
        default:
            return state;
    }
}