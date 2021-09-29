import React, { connect } from 'react-redux';
import { withRouter } from 'react-router';
import {createActivityAction} from '../../data/actions/createActivity';
import CreateActivity from "./createActivity";
import {Redirect} from "react-router-dom";

const mapStateToProps = (state) => {
    return {
        activity: state.createActivity.activity,
        isLoading: state.createActivity.isLoading,
        idLastCreatedActivity: state.createActivity.idLastCreatedActivity,
        error: state.createActivity.error,
        redirectToLogin: state.createActivity.redirectToLogin
    }
};

const createActivityContainer = (props) => {

    const renderActivityCreation = () => {
        console.log("logging props");
        console.log(props.createActivityAction);
        if(props.isLoading){
            return <div>Creating your activity...</div>;
        }
        if(props.error.length !== 0){
            console.log('onError')
            console.log(props.error)
            return <CreateActivity createActivity={props.createActivityAction} errors={props.error}/>
        }
        if(props.redirectToLogin){
            console.log('redirected to login')
            window.location.href="http://localhost:3000/login"
        }
        if(props.idLastCreatedActivity !== null){
            console.log('retrieved id')
            console.log(props)
            return <Redirect
                to={{
                    pathname: `/activities/${props.idLastCreatedActivity}`,
                    state: {
                        userStatus: "CREATOR",
                    },
                }}
            />
        }
        return <CreateActivity createActivity={props.createActivityAction}/>;
    }
    return renderActivityCreation();
}


export default withRouter(connect(mapStateToProps, {createActivityAction})(createActivityContainer))