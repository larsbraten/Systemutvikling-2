
import "./activityPane.css";

function activityPane(props) {
    return (
      <div className="activityPaneParent">
        <h1 className="activityPaneTitle">{props.title}</h1>
        <img className="activityPaneImage" src={props.image} alt=""></img>

      </div>
    )
  }

 export default activityPane; 
