import React from "react";
import {Link} from "react-router-dom";

const RegisterStatus = (props) => {
    const resend = () => {
        props.onResendClicked();
        props.onResend(props.email);
    }
    return (
        <div>
            <p>Kontoen er opprettet</p>
            <p>Vi har sendt deg en verifiseringsepost. Følg instruksene der for å gjennomføre registreringen.</p>
            <Link to="/login">
                <h3>Logg inn</h3>
            </Link>
            <p>Ikke mottatt epost? <a onClick={resend}>Trykk her</a></p>
            {props.isLoading && <progress max="100" value="50"/>}
            {props.isVerificationResent && <p>Sjekk eposten på nytt</p>}
            {props.resendError && <p>{props.resendError}</p>}
        </div>
    );
}

export default RegisterStatus;
