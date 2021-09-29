import "./calendar.css"
function Calendar() {
    return (
      <div className="parent">
          <button className="listView">Listevisning</button>
          <button className="mapView">Kartvisning</button>
          <button className="calendarView">Kalendervisning</button>
          <button className="enrolledActivities">Påmeldte aktiviteter</button>
          <button className="myActivites">Mine aktiviteter</button>
          <button className="myProfile">Min profil</button>
          <button className="logOut">Logg ut</button>
          
          
      </div>
    )
  }

 export default Calendar; 