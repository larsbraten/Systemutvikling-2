import fetch from "cross-fetch";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";
import LocationOnIcon from "@material-ui/icons/LocationOn";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import { useState, useEffect, Fragment } from "react";

const GEOCODING_API_URL =
  "http://penguin.flagship.casa:8080/search.php?addressdetails=1&q=";

/**
 * ActivityLocationInput Component
 *
 * @param props setLocation
 * @returns {JSX.Element}
 * @constructor
 */
function ActivityLocationInput(props) {
  const [open, setOpen] = useState(false);
  const [options, setOptions] = useState([]);
  const loading = open && options.length === 0;

  useEffect(() => {
    setOptions(props.mapAddress);
  }, [props.mapAddress]);

  useEffect(() => {
    if(options.length !== 0){
      setOpen(true)
    }
  }, [options])

  const onChangeHandler = async (value) => {
    const response = await fetch(GEOCODING_API_URL + value);
    const result = await response.json();

    setOptions(Object.keys(result).map((key) => result[key]));
  };

  const buildOptionLabel = (option) => {
    if (!option || typeof option == "string") return;
    const label = [];

    if (
      option["address"] &&
      option["address"]["road"] &&
      option["address"]["house_number"]
    ) {
      label.push(
        option["address"]["road"] + " " + option["address"]["house_number"]
      );

      if (option["address"]["postcode"])
        label.push(option["address"]["postcode"]);
      if (option["address"]["city"]) label.push(option["address"]["city"]);
      if (option["address"]["town"]) label.push(option["address"]["town"]);
      if (option["address"]["village"])
        label.push(option["address"]["village"]);

      console.log(label.join(", "));
      option.label = label.join(", ");

      return option.label;
    }

    option.label = option["display_name"]
      ? option["display_name"]
      : "Ukjent sted (?)";
    return option["display_name"];
  };

  const handleOnChange = (event, option) => {
    // SET SELECTED OPTION HERE
    if (!option) return;

  
    if (props.SetAddress) {
      if (!option["address"]) return;
    
      props.SetAddress({
        place:
          option["address"]["road"] && option["address"]["house_number"]
            ? option["address"]["road"] +
              " " +
              option["address"]["house_number"]
            : option["address"]["road"],
        city: option["address"]["city"] ? option["address"]["city"] : "",
        country: option["address"]["country"]
          ? option["address"]["country"]
          : "",
        longitude: option["lon"],
        latitude: option["lat"],

      });

      props.setLocation([option.lat, option.lon])
    }
  };


  return (
    <Autocomplete
      freeSolo
      autoHighlight
      id="async-geolocation"
      open={open}
      onOpen={() => {
        setOpen(true);
      }}
      onClose={() => {
        setOpen(false);
      }}
      getOptionSelected={(option, value) =>
        option.display_name !== value.display_name
      }
      getOptionLabel={(option) => buildOptionLabel(option)}
      onChange={handleOnChange}
      options={options}
      loading={loading}
      size="small"
      loadingText={"Laster..."}
      noOptionsText={"Ingen resultat"}
      renderInput={(params) => (
        <TextField
          {...params}
          label="Legg til et stedsnavn"
          variant="outlined"
          onChange={(ev) => {
            if (ev.target.value !== "") {
              onChangeHandler(ev.target.value);
            }
          }}
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <Fragment>
                {loading ? (
                  <CircularProgress color="inherit" size={20} />
                ) : null}
              </Fragment>
            ),
          }}
        />
      )}
      renderOption={(option) => {
        return (
          <Grid container alignItems="center">
            <Grid item>
              <LocationOnIcon style={{ marginRight: 13 + "px" }} />
            </Grid>
            <Grid item xs>
              {option.label}
              <Typography variant="body2" color="textSecondary">
                {option.address.county ? option.address.county : ""}
              </Typography>
            </Grid>
          </Grid>
        );
      }}
    />
  );
}

export default ActivityLocationInput;
