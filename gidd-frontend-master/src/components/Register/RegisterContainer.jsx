import React, {useState} from "react";
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { postUser, submitted, resendVerification, verificationLoading} from "../../data/actions/registration";
import Register from "./Register";
import RegStatus from "./RegStatus";

const mapStateToProps = (state) => {
    return {
        isSubmitted: state.registration.isSubmitted,
        isRegistered: state.registration.isRegistered,
        isLoading: state.registration.isLoading,
        error: state.registration.error,
        isVerificationResent: state.registration.isVerificationResent,
        isLoadingResend: state.registration.isLoadingResend,
        resendError: state.registration.resendError
    }
};

const prepareRegister = (props, email, saveEmail) => {
    if (props.isRegistered) {
        // todo change email to if from post user res
        return <RegStatus
            onResend={props.resendVerification}
            onResendClicked={props.verificationLoading}
            isVerificationResent={props.isVerificationResent}
            isLoading={props.isLoadingResend}
            resendError={props.resendError}
            email={email}
        />;
    } else {
        return <Register
                isRegistered={props.isRegistered}
                isLoading={props.isLoading}
                regError={props.error}
                onRegister={props.postUser}
                onSubmitClicked={props.submitted}
                saveEmail={saveEmail}
        />
    }
}

const  RegisterContainer = (props) => {
    const [email, setEmail] = useState("");
    const saveEmail = (email) => {
        console.log("Called save email")
        setEmail(email);
    }
    return prepareRegister(props, email, saveEmail);
}

export default withRouter(connect(mapStateToProps, {postUser, submitted, resendVerification, verificationLoading})(RegisterContainer))