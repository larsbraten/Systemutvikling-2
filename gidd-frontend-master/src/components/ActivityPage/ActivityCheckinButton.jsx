import { useEffect, useState, Fragment } from "react";
import Button from "@material-ui/core/Button";
import { ActivityService } from "../../data/Services/activityService.js";
import Alert from "@material-ui/lab/Alert";

/**
 * ActivityCheckinButton
 *
 * @param props activityid, userLongitude, userLatitude, isCheckedIn (optional)
 * @returns {JSX.Element}
 * @constructor
 */
function ActivityCheckinButton(props) {
    const [disabled, setDisabled] = useState(false);
    const [checkedIn, setCheckedIn] = useState(false);
    const [error, setError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        console.log('ActivityCheckinButton mounted');
        if (props.isCheckedIn) {
            setCheckedIn(true);
            setDisabled(true);
        }
    }, []);

    const setEnabledTimeout = () => {
        setTimeout(() => {
            setDisabled(false);
        }, 2000);
    }

    const reset = () => {
        setDisabled(false);
        setError(false);
        setErrorMessage("");
    }

    const onClick = () => {
        reset();
        setDisabled(true);

        const locationData = {
            longitude: props.userLongitude,
            latitude: props.userLatitude
        }

        ActivityService.checkIntoActivity(props.activityid, locationData)
            .then((response) => response.data)
            .then((data) => {
                console.log(data);

                if (data.status === "Ok") setCheckedIn(true);
            })
            .catch((err) => {
                const status = err.response.data.status;
                console.log(status);
                setError(true);
                switch (status) {
                    case "ACTIVITY_NOT_ENROLLED":
                        setErrorMessage("Du er ikke påmeldt denne aktiviteten");
                        break;
                    case "ACTIVITY_NOT_STARTED":
                        setErrorMessage("Aktiviteten er ennå ikke begynt");
                        break;
                    case "ACTIVITY_ALREADY_CHECKED_IN":
                        setErrorMessage("Du er allerede sjekket inn");
                        break;
                    case "ACTIVITY_TOO_FAR_AWAY":
                        setErrorMessage("Du er for langt unna aktiviteten");
                        setDisabled(false);
                        break;
                    default:
                        setErrorMessage("Ukjent feil ved innsjekking");
                }

                setEnabledTimeout();
            });
    }

    return (
        <Fragment>
            <Button
                variant="contained"
                color="primary"
                onClick={onClick}
                disabled={disabled}
            >
                { checkedIn ? "Sjekket inn" : "Sjekk inn"}
            </Button>

            { checkedIn
                ? <Alert severity="success">Du er sjekket inn! Du har fått 50 poeng for din deltakelse.</Alert>
                : ""}
            { error && errorMessage
                ? <Alert severity="error">{errorMessage}</Alert>
                : "" }
        </Fragment>
    );
}

export default ActivityCheckinButton;