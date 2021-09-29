
import "./myActivities.css";
import ActivitySidebar from "../activitySideBar/activitySidebar";
import CreateActivity from "./createActivity";
import ActivityView from "../activityView/activityView";
import {useState} from 'react';


function MyActivities() {
  const [createActivity, setCreateActivity] = useState("");

  const showActivity = () =>{
    if(createActivity){
      return (
        <div>
          <CreateActivity/>
        </div>
      );
    }else{
      return(
        <ActivityView/>
      );
    }
    
  }


  return (
    <div className="parent">
      <aside>
        <div>
          <h3>Dine aktiviteter:</h3>
          <button onClick={() => setCreateActivity(true)}>Opprett aktivitet</button>
        </div>
        <ActivitySidebar activities={MyActivities}/>
      </aside>
      <div>
        <p>Denne skal enten være en valgt aktivitet, eller hvis knappen er trykket en opprett aktivitetskomponent</p>
        {showActivity()}
      </div>
    </div>
  )
}

 export default MyActivities; 