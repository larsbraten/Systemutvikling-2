import {
    POST_USER,
    ERROR,
    SUBMITTING_NEW_USER,
    REGISTRATION_IS_LOADING,
    RESEND_VERIFICATION,
    VERIFICATION_IS_LOADING,
    VERIFICATION_RESEND_ERROR
} from "../constants"

const defaultState = {
    isLoading: null,
    isSubmitted: null,
    isRegistered: null,
    error: null,
    isVerificationResent: null,
    isLoadingResend: null,
    resendError: null
};

export default function registrationReducer(state = defaultState, action) {
    switch (action.type) {
        case POST_USER:
            return {
                ...state,
                isRegistered: action.payload,
                isLoading: false,
                error: null
            };

        case SUBMITTING_NEW_USER:
            return {
                ...state,
                isSubmitted: action.isSubmitted
            };

        case REGISTRATION_IS_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            };

        case RESEND_VERIFICATION:
            return {
                ...state,
                isVerificationResent: action.payload,
                isLoading: false,
                resendError: false
            }

        case VERIFICATION_IS_LOADING:
            return {
                ...state,
                isLoadingResend: true
            };

        case VERIFICATION_RESEND_ERROR:
            return  {
                ...state,
                resendError: action.error,
                isLoadingResend: false,
                isVerificationResent: false
            };

        case ERROR:
            return {
                ...state,
                error: action.error,
                isLoading: false,
                isRegistered: false
            };

        default:
            return state;
    }
}