import {
    EDITING_ACTIVITY, EQUIPMENT_LIST_ERROR,
    ERROR,
    GET_ACTIVITY,
    GET_ACTIVITY_IS_LOADING, GET_EQUIPMENT_LIST, RESET_ACTIVITY_PAGE,
    STOP_EDITING_ACTIVITY, TOGGLE_EQUIPMENT, TOGGLE_EQUIPMENT_ERROR, TOGGLE_EQUIPMENT_IS_LOADING,
    UPDATE_ACTIVITY, UPDATE_ACTIVITY_ERROR, UPDATE_ACTIVITY_LOADING
} from "../constants";

const defaultState = {
    activity: null,
    isLoading: null,
    error: null,
    isEditing: null,
    isUpdated: null,
    isUpdateLoading: null,
    updateError: null,
    isReload: false,
    isToggledEquipment: null,
    isToggleEquipmentLoading: null,
    toggleEquipmentError: null,
    equipmentList: null,
    equipmentError: null,
    isRefreshEquipment: null
}

export default function activityReducer(state=defaultState, action) {
    switch (action.type) {
        case GET_ACTIVITY:
            console.log("REDUX GET ACTIVITY")
            return {
                ...state,
                activity: action.payload,
                isLoading: null,
                error: null
            };

        case GET_ACTIVITY_IS_LOADING:
            console.log("REDUX GET ACTIVITY LOADING")
            return {
               ...state,
               activity: null,
               isLoading: true,
               error: null
            };

        case ERROR:
            console.log("REDUX ERROR")
            return {
                ...state,
                activity: null,
                isLoading: null,
                error: action.error
            };

        case EDITING_ACTIVITY:
            console.log("REDUX EDITING ACTIVITY")
            return {
                ...state,
                isEditing: true
            }

        case STOP_EDITING_ACTIVITY:
            console.log("REDUX STOP EDITING ACTIVITY")
            return {
                ...state,
                isEditing: false
            }

        case UPDATE_ACTIVITY:
            console.log("REDUX UPDATE ACTIVITY")
            return {
                ...state,
                isEditing: false,
                isUpdateLoading: false,
                updateError: null,
                isUpdated: true,
                isReload: !state.isReload
            }

        case UPDATE_ACTIVITY_LOADING:
            console.log("REDUX UPDATE ACTIVITY LOADING")
            return {
                ...state,
                isUpdateLoading: true,
                updateError: null,
                isUpdated: false
            }

        case UPDATE_ACTIVITY_ERROR:
            console.log("REDUX UPDATE ACTIVITY ERROR")
            return {
                ...state,
                isUpdateLoading: null,
                isUpdated: false,
                updateError: action.error
            };

        case RESET_ACTIVITY_PAGE:
            console.log("REDUX RESET ACTIVITY PAGE")
            return {
                ...state,
                isLoading: null,
                error: null,
                isEditing: null,
                isUpdated: null,
                isUpdateLoading: null,
                updateError: null,
                // isReload: false,
                isToggledEquipment: null,
                isToggleEquipmentLoading: null,
                toggleEquipmentError: null,
                equipmentList: null,
                equipmentError: null,
                // isRefreshEquipment: null
            }

        case TOGGLE_EQUIPMENT:
            console.log("REDUX TOGGLE EQUIPMENT")
            return {
                ...state,
                isToggledEquipment: true,
                // isToggleEquipmentLoading: false,
                toggleEquipmentError: null,
                isRefreshEquipment: !state.isRefreshEquipment
                // isReload: !state.isReload
            }

        case TOGGLE_EQUIPMENT_IS_LOADING:
            console.log("REDUX TOGGLE EQUIPMENT LOADING")
            return {
                ...state,
                // isToggledEquipment: false,
                // isToggleEquipmentLoading: true,
                // toggleEquipmentError: null
            }

        case TOGGLE_EQUIPMENT_ERROR:
            console.log("REDUX TOGGLE EQUIPMENT ERROR")
            return {
                ...state,
                isToggledEquipment: false,
                // isToggleEquipmentLoading: false,
                toggleEquipmentError: action.error
            }

        case GET_EQUIPMENT_LIST:
            console.log("REDUX GET EQUIP LIST")
            return {
                ...state,
                equipmentList: action.payload,
                equipmentError: null,
            }

        case EQUIPMENT_LIST_ERROR:
            console.log("REDUX GET EQUIP LIST ERROR")
            return {
                ...state,
                equipmentError: action.error,
                equipmentList: null
            }

        default:
            console.log("REDUX DEFAULT")
            return state;
    }
}