import SearchBar from "material-ui-search-bar";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import "./activityBar.css";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";

function ActivityBar(props) {
  const renderSearchBar = () => {
    return (
      <SearchBar
        className="searchbar"
        value={props.activitySearchCriteria.name}
        onChange={(newValue) => props.handleSearchName(newValue)}
        onCancelSearch={() => props.handleSearchName(null)}
        onRequestSearch={() => props.searchActivities()}
      />
    );
  };

  const renderSort = () => {
    return (
      <div className="sortDiv">
        <InputLabel htmlFor="age-native-simple">Sorter</InputLabel>
        <Select
          className="sortSelect"
          value={props.sortingOptions.name}
          onChange={props.handleSort}
        >
          <option value={"date"}>Dato</option>
          <option value={"nameAl"}>Navn - Alfabetisk</option>
          <option value={"nameAn"}>Navn - Analfabestisk</option>
        </Select>
      </div>
    );
  };

  return (
    <div className=" activities-search-bar">
      <div>
        <h1>Activities</h1>
      </div>
      <div className="activities-bar-container">
        <div className="activities-bar-buttons">
          <Button
            variant={props.viewLocation.kalender ? "contained" : "outlined"}
            color="primary"
            onClick={() => {
              props.history.push("/kalender");
            }}
          >
            Kalender
          </Button>

          <Link to="/liste">
            <Button
              variant={props.viewLocation.liste ? "contained" : "outlined"}
              color="primary"
            >
              Liste
            </Button>
          </Link>
          <Link to="/kart">
            <Button
              elevation={5}
              variant={props.viewLocation.kart ? "contained" : "outlined"}
              color="primary"
            >
              Kart
            </Button>
          </Link>
        </div>
        <div className="activities-bar-search-sort">
          {renderSearchBar()}
          {renderSort()}
        </div>
      </div>
    </div>
  );
}

export default ActivityBar;
