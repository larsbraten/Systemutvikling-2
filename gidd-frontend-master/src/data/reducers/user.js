import {
  GET_USER,
  ERROR,
  PUT_USER_SUCCESS,
  PUT_USER_ERROR,
  PUT_USER_IS_LOADING,
  PUT_USER_ERROR_401
} from "../constants"
const defaultState = {
    putUserIsLoading: null,
    putUserError: null,
    putUserSuccess: null,
    redirectToLogin: false
  };
  
  export default function userReducer(state = defaultState, action) {
    switch (action.type) {

      case PUT_USER_SUCCESS:
        return {
          ...state,
          putUserSuccess: true,
          putUserError: null,
          putUserIsLoading: null
        };

      case PUT_USER_ERROR:
        return {
          ...state,
          putUserSuccess: null,
          putUserError: action.error,
          putUserIsLoading: null
        };

      case PUT_USER_IS_LOADING:
        return {
          ...state,
          putUserIsLoading: true,
          putUserError: null,
          putUserSuccess: null
        };


      case PUT_USER_ERROR_401:
        return {
          ...state,
          redirectToLogin: true,
          putUserIsLoading: null,
          putUserError: null,
          putUserSuccess: null,
        };
  
      default:
        return state;
    }
  }