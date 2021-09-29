import Camper from "./camper";
import CamperList from "./camperlist";
import React from "react";
//import "./leaderboard.css";
import { UserService } from "../../data/Services/apiUserService";
import { useState, useEffect } from "react";
import reactDom from "react-dom";

function Leaderboard() {
  const [leaderboardEntries, setLeaderboardEntries] = useState({});
  const [ascending, setAscending] = useState(true);

  useEffect(() => {
    setLeaderboardEntries(UserService.getTopFiftyUsers());
  }, []);

  // toggles the column sorting asc/desc if click on current column
  // else calls the api with the given string
  function toggleColumnSort() {
    setLeaderboardEntries((leaderboardEntries) => {
      return leaderboardEntries.reverse();
    });
    setAscending((ascending) => {
      return !ascending;
    });
  }

  return (
    <div className="container">
      <div className="row">
        <div className="col-xs-12">
          <table className="table table-striped table-hover">
            <caption>
              <h2>
                <a href="https://www.freecodecamp.com" target="_blank">
                  FreeCodeCamp
                </a>{" "}
                Leaderboard
              </h2>
            </caption>
            <thead>
              <tr>
                <th>Rank</th>
                <th>Name</th>
                <th className="points">
                  <span className="recent" onClick={() => toggleColumnSort()}>
                    Past 30 Days<span className="glyphiconClass"></span>
                  </span>
                </th>
                <th className="points">
                  <span
                    className="alltimeClass"
                    onClick={() => toggleColumnSort()}
                  >
                    All Time<span className="glyphiconClass"></span>
                  </span>
                </th>
              </tr>
            </thead>
            <CamperList list={leaderboardEntries} />
          </table>
        </div>
      </div>
    </div>
  );
}
export default Leaderboard;
