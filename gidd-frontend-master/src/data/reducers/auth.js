import {
  LOGIN,
  ERROR,
  AUTH_USER,
  LOGOUT,
  REFRESH_USER,
  REFRESH_USER_SUCCESS,
    REFRESH_USER_ERROR,
  REFRESH_USER_IS_LOADING, NO_SESSION_COOKIE
} from "../constants"
const defaultState = {
    isLoggedIn: null,
    user: false,
    userIsLoading: null,
    refreshUserError: null
  };
  
  export default function userReducer(state = defaultState, action) {
    switch (action.type) {
      case LOGIN:
        
        return {
          ...state,
          isLoggedIn: true
        };
      
      case AUTH_USER:
        return {
          ...state,
          user: action.payload,
          isLoggedIn: true
        }
        
      case LOGOUT:
        return{
          state
        }
      case NO_SESSION_COOKIE:
        return{
          ...state,
          isLoggedIn: false
        }
      case ERROR:
        return {
          ...state,
        };

        // todo revurder om user og isLoggedIn settes til null i refreshErr og userIsLoad
      case REFRESH_USER_IS_LOADING:
        return {
          ...state,
          refreshUserError: null,
          userIsLoading: true
        }

      case REFRESH_USER_SUCCESS:
        return {
          ...state,
          user: action.payload,
          isLoggedIn: true,
          userIsLoading: null,
          refreshUserError: null
        };

      case REFRESH_USER_ERROR:
        return {
          ...state,
          userIsLoading: null,
          refreshUserError: action.error
        };
  
      default:
        return state;
    }
  }