import LoaderWheel from "../loaderWheel/loaderWheel"
import {authenticationService} from "../../data/Services/authService"
import { useEffect, useState } from "react";
import { Redirect } from "react-router";

function Verify(props) {

    const [redirect, setRedirect] = useState(false)

    useEffect(() => {
        console.log(props.history.location.pathname.split("/")[2])
        setRedirect(authenticationService.verifyUser(props.history.location.pathname.split("/")[2])) 
    }, [])
    return (
        <div>
            <LoaderWheel/>

            {redirect ? <Redirect to="/login"/>: ""}
        </div>
    )
}

export default Verify;