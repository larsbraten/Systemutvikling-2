import {
  GET_USER,
  ERROR,
  // GET_USER_IS_LOADING,
  PUT_USER_IS_LOADING,
  PUT_USER_SUCCESS,
  PUT_USER_ERROR,
  // GET_USER_ERROR,
  PUT_USER_ERROR_401
} from "../constants"
import {UserService} from "../Services/apiUserService"

const mapErrorMessage = (err) => {
  return (err.response && err.response.data && err.response.data.description) ?
      err.response.data.description : "Noe gikk galt";
}

export const putUser = (id, data) => async (dispatch) => {
  dispatch({
    type: PUT_USER_IS_LOADING
  });
  try{
    await UserService.putUser(id, data);
    dispatch({
      type: PUT_USER_SUCCESS
    });

  }catch (err){
    if(err.response.status === 401){
      console.log("Caught 401 error");
      dispatch({
          type: PUT_USER_ERROR_401
      });  
    }
    dispatch({
      type: PUT_USER_ERROR,
      error: mapErrorMessage(err)
    });
  }
}
