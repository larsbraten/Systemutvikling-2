import {
    POST_USER,
    ERROR,
    SUBMITTING_NEW_USER,
    REGISTRATION_IS_LOADING,
    RESEND_VERIFICATION,
    VERIFICATION_IS_LOADING,
    VERIFICATION_RESEND_ERROR
} from "../constants"
import {UserService} from "../Services/apiUserService"

const mapErrorMessage = (err) => {
    return (err.response && err.response.data && err.response.data.description) ?
            err.response.data.description : "Noe gikk galt";
}

export const postUser = (data) => async (dispatch) => {

    try {
        console.log("postUser")
        const res = await UserService.postUser(data)
        dispatch({
            type: POST_USER,
            payload: res,
        });
    } catch (err) {
        console.log("ERR", err)
        dispatch({
            type: ERROR,
            error: mapErrorMessage(err)
        });
    }

}

export const resendVerification = (email) => async (dispatch) => {
    try {
        console.log("Resend verification email")
        const res = await UserService.resendVerificationEmail(email)
        dispatch({
            type: RESEND_VERIFICATION,
            payload: res
        })
    } catch (err) {
        console.log("ERR", err)
        dispatch({
            type: VERIFICATION_RESEND_ERROR,
            error: mapErrorMessage(err)
        });
    }
}

export const verificationLoading = () => (dispatch) => {
    dispatch({
        type: VERIFICATION_IS_LOADING
    })
}

export const submitted = () => (dispatch) => {
    dispatch({
        type: REGISTRATION_IS_LOADING,
        isLoading: true
    });
    // dispatch({
    //     type: SUBMITTING_NEW_USER,
    //     isSubmitted: true
    // });
}