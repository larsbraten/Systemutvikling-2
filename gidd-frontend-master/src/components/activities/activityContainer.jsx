import { connect } from "react-redux";
import React, { useEffect, useState } from "react";
import { withRouter } from "react-router";
import { useLocation } from "react-router-dom";
import LoaderWheel from "../loaderWheel/loaderWheel";
import { getFilteredActivities, resetFeedbackMessage } from "../../data/actions/activities";
import Activities from "./activities";
import ActivityBar from "./activityBar/activityBar";
import ActivityPaginator from "./activityPaginator/activityPaginator";
import FilterMenu from "./filterMenu/filterMenu";
import "./activityContainer.css";
import { TranslateRounded } from "@material-ui/icons";
import ActivityCalendarView from "./activityCalendarView";
import MapDisplay from "../map/map";
import Message from "../feedbackMessage/feedbackMessage"

const PAGE_SIZE = 8;

// new Date("2021-05-12")
function ActivityContainer(props) {
  const [page, setPage] = useState(0);
  const [activitySearchCriteria, setActivitySearchCriteria] = useState({
    showPreviousActivities: false,
    startTime: null,
    name: "",
    locations: null,
    interests: null,
    activityLevels: null,
  });
  const [firstCalenderRun, setFirstCalenderRun] = useState(true)

  const [sortingOptions, setSortingOptions] = useState({
    name: "date",
    sortDirection: "ASC",
    sortBy: "startTime",
  });

  const [viewLocation, setViewLocation] = useState({
    kalender: false,
    liste: true,
    kart: false,
  });


  const location = useLocation();

  useEffect(() => {
    if(location !== "/kalender"){
      setFirstCalenderRun(false);
    }
    handleViewLocation(location.pathname);
    
  }, [location.pathname]);
  useEffect(() => {
    if(!firstCalenderRun){
      executeSearch();
    }
    else setFirstCalenderRun(false);
  }, [page, activitySearchCriteria, sortingOptions]);

  

  const handleDateChange = (date) => {
   
    if(Array.isArray(date)){
      date = new Date(date)
    }
    
    setPage(0);
    
    setActivitySearchCriteria((activitySearchCriteria) => {
      return {
        ...activitySearchCriteria,
        startTime: date,
      };
    });
    
  };

  const handleViewLocation = (location) => {
    props.resetFeedbackMessage();
    var name = "liste";
    if (location === "/kart") {
      name = "kart";
      
      handleDateChange(null)
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          showPreviousActivities: false,
        };
      });
    }
    else if (location === "/kalender") {
      name = "kalender";
      handleDateChange(new Date())
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          showPreviousActivities: true,
        };
      });
    }
    else {
      handleDateChange(null)
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          showPreviousActivities: false,
        };
      });
    }

    const value = true;
    const type = name;
    
    setViewLocation({
      [type]: value,
    });
  };

  const handleSort = (event) => {
    setPage(0);
    switch (event.target.value) {
      case "date":
        setSortingOptions({
          name: "date",
          sortDirection: "ASC",
          sortBy: "startTime",
        });
        break;
      case "nameAl":
        setSortingOptions({
          name: "nameAl",
          sortDirection: "ASC",
          sortBy: "name",
        });
        break;
      case "nameAn":
        setSortingOptions({
          name: "nameAn",
          sortDirection: "DESC",
          sortBy: "name",
        });
        break;
      default:
        break;
    }
  };

  const handleLocationChange = (value) => {
    setPage((page) => {
      return 0;
    });
    if (activitySearchCriteria.locations === null) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          locations: [value],
        };
      });
    } else if (activitySearchCriteria.locations.includes(value)) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        if (activitySearchCriteria.locations.length === 1) {
          return {
            ...activitySearchCriteria,
            locations: null,
          };
        }
        return {
          ...activitySearchCriteria,
          locations: activitySearchCriteria.locations.filter(
            (checkedLocation) => {
              return checkedLocation !== value;
            }
          ),
        };
      });
    } else
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          locations: [...activitySearchCriteria.locations, value],
        };
      });
  };

  const handleInterestChange = (value) => {
    setPage((page) => {
      return 0;
    });
    if (activitySearchCriteria.interests === null) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          interests: [value],
        };
      });
    } else if (activitySearchCriteria.interests.includes(value)) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        if (activitySearchCriteria.interests.length === 1) {
          return {
            ...activitySearchCriteria,
            interests: null,
          };
        }
        return {
          ...activitySearchCriteria,
          interests: activitySearchCriteria.interests.filter(
            (checkedInterest) => {
              return checkedInterest !== value;
            }
          ),
        };
      });
    } else
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          interests: [...activitySearchCriteria.interests, value],
        };
      });
  };

  const handleLevelChange = (value) => {
    setPage((page) => {
      return 0;
    });
    if (activitySearchCriteria.activityLevels === null) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          activityLevels: [value],
        };
      });
    } else if (activitySearchCriteria.activityLevels.includes(value)) {
      setActivitySearchCriteria((activitySearchCriteria) => {
        if (activitySearchCriteria.activityLevels.length === 1) {
          return {
            ...activitySearchCriteria,
            activityLevels: null,
          };
        }
        return {
          ...activitySearchCriteria,
          activityLevels: activitySearchCriteria.activityLevels.filter(
            (checkedActivityLevel) => {
              return checkedActivityLevel !== value;
            }
          ),
        };
      });
    } else
      setActivitySearchCriteria((activitySearchCriteria) => {
        return {
          ...activitySearchCriteria,
          activityLevels: [...activitySearchCriteria.activityLevels, value],
        };
      });
  };

  const executeSearch = () => {
    props.getFilteredActivities({
      activitySearchCriteria: activitySearchCriteria,
      activityPage: {
        pageNumber: page,
        pageSize: PAGE_SIZE,
        sortDirection: sortingOptions.sortDirection,
        sortBy: sortingOptions.sortBy,
      },
    });
  };

  const handlePageClick = (event, value) => {
    //pageUrl(data.selected + 1);
    let page = Math.ceil(value - 1);
    setPage(page);
  };

  const searchActivities = (value) => {
    setPage((page) => {
      return 0;
    });
  };

  const handleSearchName = (newName) => {
    setPage((page) => {
      return 0;
    });
    setActivitySearchCriteria((activitySearchCriteria) => {
      return { ...activitySearchCriteria, name: newName };
    });
  };

  const renderActivityView = () => {
    if (props.activities && props.activities.content) {
      if (viewLocation.liste) {
        return (
          <div className="activities-view-grid-pagination">
            <Activities activities={props.activities} />
            <ActivityPaginator
              activities={props.activities}
              handlePageClick={handlePageClick}
            />
          </div>
        );
      } else if (viewLocation.kalender) {
        return (
          <div className="activities-view-grid-pagination">
            <ActivityCalendarView
              activities={props.activities}
              handleCalendarChange={handleDateChange}
            />
          </div>
        );
      } else if (viewLocation.kart) {
        return (
          <div className="activities-view-grid-pagination">
            <MapDisplay activities={props.activities.content}/>
          </div>
        );
      }
    }
    else{
      return <LoaderWheel/>;
    }
  };

  const renderFilterMenu = () => {
    return (
      <FilterMenu
        handleInterestChange={handleInterestChange}
        handleLocationChange={handleLocationChange}
        handleLevelChange={handleLevelChange}
      />
    );
  };

  return (
    <div className="activities-base">
      <div className="activity-view-container">
        <ActivityBar
          history={props.history}
          handleDateChange={handleDateChange}
          viewLocation={viewLocation}
          handleSearchName={handleSearchName}
          searchActivities={searchActivities}
          activitySearchCriteria={activitySearchCriteria}
          handleSort={handleSort}
          sortingOptions={sortingOptions}
        />
        <div className="activities-filter-container">{renderFilterMenu()}</div>
        <div className="activities-view-container">{renderActivityView()}</div>
      </div>
      {props.joinActivityMessage!==""?<Message key={props.joinActivityMessage} message={props.joinActivityMessage} time={3000}/>:""}
    </div>
  );
}

const mapStateToProps = (state) => ({
  activities: state.activities.filteredActivities,
  joinActivityMessage: state.activities.joinActivityMessage,
});

export default withRouter(
  connect(mapStateToProps, { getFilteredActivities, resetFeedbackMessage }, null, {
    forwardRef: true,
  })(ActivityContainer)
);
