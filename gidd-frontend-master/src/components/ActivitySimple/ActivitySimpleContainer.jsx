import React, {useEffect, useState} from "react";
import { connect } from 'react-redux';
import {useParams, withRouter} from 'react-router';
import {getActivity, getActivityLoading} from "../../data/actions/activity";

const mapStateToProps = (state) => {
    return {
        activity: state.activity.activity,
        isLoading: state.activity.isLoading,
        error: state.activity.error
    };
}

const prepareSimpleActivity = () => {

}

const ActivitySimpleContainer = (props) => {
    const {id} = useParams();

    useEffect(() => {
        props.getActivityLoading();
        props.getActivity(id);
    }, []);

    return(<p>ILL BE BACK</p>);
}


export default withRouter(connect(mapStateToProps, {getActivity, getActivityLoading})(ActivitySimpleContainer))
