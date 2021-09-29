import {
  LOGIN,
  ERROR,
  AUTH_USER,
  LOGOUT,
  GET_USER,
  GET_USER_ERROR,
  REFRESH_USER,
  REFRESH_USER_SUCCESS, REFRESH_USER_ERROR, REFRESH_USER_IS_LOADING, NO_SESSION_COOKIE
} from "../constants"
import {authenticationService} from "../Services/authService"
import {UserService} from "../Services/apiUserService";


const mapErrorMessage = (err) => {
  return (err.response && err.response.data && err.response.data.description) ?
      err.response.data.description : "Noe gikk galt";
}

export const login = (data) => async (dispatch) => { 
    try {
      console.log("login");
      const res = await authenticationService.login(data);
      dispatch({
        type: LOGIN,
        payload: res.data,
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

  export const logout = () => async (dispatch) => { 
    try {
    
      const res = await authenticationService.logout();
      dispatch({
        type: LOGOUT,
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

  export const authUser = () => async (dispatch) => {
    try {
      const result = await authenticationService.authUser();
      if(result instanceof Error) throw result;
      dispatch({
        type: AUTH_USER,
        payload: result.data,
      });
    }catch(e){
      console.log("HEIDER");
      dispatch({
        type: NO_SESSION_COOKIE,
        payload: {
          error: e,
        },
      });
    }
  }

  export const refreshUser = (id) => async (dispatch) => {
    dispatch({
      type: REFRESH_USER_IS_LOADING
    });

    try {
      console.log("getUser")
      const res = await UserService.getUser(id)
      dispatch({
        type: REFRESH_USER_SUCCESS,
        payload: res,
      });
    } catch (err) {
      dispatch({
        type: REFRESH_USER_ERROR,
        error: mapErrorMessage(err)
      });
    }
  }