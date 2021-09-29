import "./activityView.css"
import {useState} from 'react';
function ActivityView(props) {
  //Hvordan bruke komponenten:
  //<ActivityView isCreatedByUser={true} hasRegistered={true}/>

  const [hasRegistered, setHasRegistered] = useState(false);
  
  const deleteActivity = () => {
    //denne skal slette aktiviteten. Må sende iden til backenden for å slette aktiviteten
  }

  const changeActivity = () => {
    //denne skal lage en CreateActivity-komponent, med data allerede i feltene
  }

  const register = () => {
    setHasRegistered(true);
    //Her skal du ta inn aktivitet og bruker id og sende det til backend for å registrere brukeren
  }

  const unregister = () => {
    setHasRegistered(false);
  }

  const showRelevantButton = () => {
    if(props.isCreatedByUser){
      return (
        <div>
          <button onClick={()=>changeActivity()}>Endre aktivitet</button>
          <button onClick={()=>deleteActivity()}>Slett aktivitet</button>
        </div>
      );
    }
    else if(!props.isCreatedByUser && props.hasRegistered){
      return (
        <div>
          <button onClick={()=> unregister()}>Meld av</button>
        </div>
      )
    }
    else if(!props.isCreatedByUser && !props.hasRegistered){
      return (
        <div>
          <button onClick={()=> register()}>Meld på</button>
        </div>
      )
    }
  }

  return (
    <div className="parent">
      <img src="src.jpg"></img>
      <h1>Tittel</h1>
      <p>Treningsnivå:</p>
      <p>Arrangør</p>
      <p>Tid og sted</p>
      <p>Beskrivelse</p>
      {showRelevantButton()}
    </div>
  );
}

 export default ActivityView;