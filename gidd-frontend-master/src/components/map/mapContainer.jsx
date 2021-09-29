import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { useEffect } from 'react'
import LoaderWheel from "../loaderWheel/loaderWheel";
import { getActivities } from '../../data/actions/activities';
import MapDisplay from './map';


function MapContainer(props){

  console.log(props)

    useEffect(() => {
        props.getActivities();
    }, []);

    return showMap(props);
}

const showMap = (props) => {
    if (!props.activities || props.activities.length == 0) {
        return <LoaderWheel/>;
    } else {
      return (
        <MapDisplay activities={props.activities}/>
        
      );
    }
};





const mapStateToProps = (state) => ({
    activities: state.activities.activities,
    user: state.auth.user,
});


export default withRouter(
    connect(mapStateToProps, { getActivities }, null, {
      forwardRef: true,
    })(MapContainer)
  );
  