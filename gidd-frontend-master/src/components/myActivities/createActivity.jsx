import "./createActivity.css";
import "date-fns";
import { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import { TextField } from "@material-ui/core";
import { Map, Marker } from "pigeon-maps";

import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import ActivityMan from "./ActivityMan.svg";
import WalkingMan from "./walking-man.svg";
import Strength from "./strength.svg";
import axios from "axios";
import InputAdornment from "@material-ui/core/InputAdornment";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Chip from "@material-ui/core/Chip";
import DoneIcon from "@material-ui/icons/Done";
import Paper from "@material-ui/core/Paper";
import InputBase from "@material-ui/core/InputBase";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import ActivityLocationInput from "./activityLocationInput";
import { KeyboardSharp } from "@material-ui/icons";
import { iconMapping } from "../../utils/iconmapping.js"
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';

function CreateActivity(props) {
  const [selectedImg, setSelectedImg] = useState("VOLLEYBALL");
  const [equipmentList, setEquipmentList] = useState([]);
  const [newEquipment, setNewEquipment] = useState({});
  const [interestList, setInterestList] = useState([]);
  const [newInterest, setNewInterest] = useState("");
  const [location, setLocation] = useState([]);
  const [date, setDate] = useState(new Date("2022-11-18T21:11:54"));
  const [Starttime, SetStarttime] = useState(new Date("2022-11-18T21:11:54"));
  const [EndTime, SetEndTime] = useState(new Date("2022-11-18T23:12:54"));
  const [Address, SetAddress] = useState("");
  const [mapAddress, setMapAddress] = useState([])
  const [activityLevel, setActivityLevel] = useState({
    rolig: false,
    aktivt: true,
    intens: false,
  });
  const [Beskrivelse, setBeskrivelse] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const showImage = () => {
    if (selectedImg != "") {
      return <img src={selectedImg} alt=""></img>;
    } else {
      return (
        <div>
          <p>Ingen bilde valgt</p>
        </div>
      );
    }
  };

  const theme = {
    primary: {
      light: "#4f4f4f",
      main: "#f5f5f5",
      dark: "#4f4f4f",
    },
    grey: {
      700: "#f5f5f5",
      900: "#4f4f4f",
    },
    background: {
      default: "#4f4f4f",
    },
    text: {
      disabled: "#BABABA",
    },
  };

  function onChangeDate(date) {
    setDate(date);
  }

  function formatDate(date, time){
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDate();

    if(month < 10){
      month = `0${month}`
    }

    if(day < 10){
      day = `0${day}`
    }

    let hours = time.getHours();
    let minutes = time.getMinutes();

    if(hours < 10){
      hours = `0${hours}`
    }

    if(minutes < 10){
      minutes = `0${minutes}`
    }

    return `${year}-${month}-${day} ${hours}:${minutes}`.toString()
  }

  const onSubmit = async (data) => {
    var submitEquipment = [];
    equipmentList.map((equipment) => submitEquipment.push(equipment.label));
    var submitInterest = [];
    interestList.map((interest) => submitInterest.push(interest.label));
    Object.assign(data, { equipment: submitEquipment });
    Object.assign(data, { interests: submitInterest });
    Object.assign(data, {
      location: { city: Address.city, place: Address.place, country: Address.country,
        latitude: Address.latitude,  longitude: Address.longitude},
    });
    delete data.address;

    Object.assign(data, {
      startTime: formatDate(date, Starttime),
    });
    Object.assign(data, {
      endTime: formatDate(date, EndTime),
    });

    if (activityLevel.rolig) {
      Object.assign(data, {
        activityLevel: "LOW",
      });
    } else if (activityLevel.aktivt) {
      Object.assign(data, {
        activityLevel: "MEDIUM",
      });
    } else {
      Object.assign(data, {
        activityLevel: "HIGH",
      });
    }

    Object.assign(data, {
      activityIcon: selectedImg
    })
    console.log(data);

    props.createActivity(data);
  };

  const onErrors = (errors) => {
    console.log(errors);
  };

  function handleActivityChange(type) {
    const value = true;
    const name = type;

    setActivityLevel({
      [name]: value,
    });
  }

  const renderMenuItems = () => {
    let menuItems = [];
    for(let key in iconMapping){
      menuItems.push(
        <MenuItem value = {key}>
          <img
            className="activityIcon"
            src={iconMapping[key]}
            alt={key}
          />
        </MenuItem>
      );
    }
    return menuItems;
  }

  const registerOptions = {
    name: {
      required: "Tittel på aktiviteten er påkrevd",
      minLength: {
        value: 4,
        message: "Tittelen må være lengre enn fire bokstaver",
      },
    },
    description: {
      required: "En beskrivelse på aktiviteten er påkrevd",
    },
    capacity: {
      required: "Maks antall deltakere er påkrevd",
      min: 1,
      max: 200,
    },
    startTime: {
      required: "Start-tid for aktiviteten er påkrevd",
    },
    endTime: {
      required: "Slutt-tid for aktiviteten er påkrevd",
    },
    city: {
      required: "By er påkrevd",
    },
    address: {
      required: "Gateadresse er påkrevd",
      minLength: {
        value: 4,
        message: "Tittelen må være lengre enn fire bokstaver",
      },
    },
  };

  const registerLocation = async (event) => {
    setLocation([event.latLng[0], event.latLng[1]]);
    
  };

  useEffect(() => {
    mapCoordinatesToAddress();
  }, [location]);

  const mapCoordinatesToAddress = async () => {
    const response = await fetch( `http://penguin.flagship.casa:8080/search.php?addressdetails=1&q=${location[0]},${location[1]}`);
    const result = await response.json();

    setMapAddress(Object.keys(result).map((key) => result[key]))
  };


  const addEquipment = () => {
    setEquipmentList((list) => [...list, newEquipment]);
    if (!equipmentList[0]) {
      setNewEquipment({ label: "", id: 1 });
    } else {
      setNewEquipment({ label: "", id: equipmentList.length + 1 });
    }
  };

  const handleEquipmentInputChange = (e) => {
    setNewEquipment({
      id: equipmentList.length + 1,
      label: e.currentTarget.value,
    });
  };

  const handleEquipmentKeyPress = (e) => {
    if (e.keyCode === 13) addEquipment();
  };

  const removeEquipment = (i) => {
    const newEquipments = [...equipmentList];
    newEquipments.splice(i, 1);

    setEquipmentList(newEquipments);
  };

  const addInterest = () => {
    setInterestList((list) => [...list, newInterest]);

    if (!interestList[0]) {
      setNewInterest({ label: "", id: 1 });
    } else {
      setNewInterest({ label: "", id: interestList.length + 1 });
    }
  };

  const handleInterestInputChange = (e) => {
    setNewInterest({
      id: equipmentList.length + 1,
      label: e.currentTarget.value,
    });
  };

  const handleInterestKeyPress = (e) => {
    if (e.keyCode === 13) addInterest();
  };

  const removeInterest = (i) => {
    const newInterests = [...interestList];
    newInterests.splice(i, 1);

    setInterestList(newInterests);
  };
  const handleDelete = (chipToDelete) => () => {
    setEquipmentList(
      equipmentList.filter((chip) => chip.id !== chipToDelete.id)
    );
  };
  const handleDeleteInterest = (chipToDelete) => () => {
    setInterestList(interestList.filter((chip) => chip.id !== chipToDelete.id));
  };

  return (
    /* "handleSubmit" will validate your inputs before invoking "onSubmit" */
    <div className="create-activity-base">
      <form
        onSubmit={handleSubmit(onSubmit, onErrors)}
        className="create-activity-grid"
      >
        <div className="essentials-grid">
          <h1 className="create-activity-title">Lag aktivitet</h1>
          <div className="create-activity-navn">
            <h2>Tittel</h2>
            <TextField
              {...register("name", registerOptions.name)}
              required
              error={errors.name ? true : false}
              helperText={errors.name ? errors.name.message : ""}
              id="standard-required"
              label="tittel"
              fullWidth
              placeholder="navnet på din nye aktivtet"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <AccountCircle />
                  </InputAdornment>
                ),
              }}
            />
          </div>
          <div className="create-activity-nivaa">
            <h2>Aktivitetsnivå</h2>          
            <div className="create-activity-nivaa-container">
              <button
                className={
                  activityLevel.rolig
                    ? "create-activity-nivaa-selected"
                    : "create-activity-nivaa-unselected"
                }
                key="rolig"
                type="button"
                id="rolig"
                name="rolig"
                onClick={() => handleActivityChange("rolig")}
              >
                <img
                  className="create-activity-nivaa-img-left"
                  src={WalkingMan}
                  alt="icon"
                />{" "}
                <h2>Rolig</h2>
              </button>

              <button
                className={
                  activityLevel.aktivt
                    ? "create-activity-nivaa-selected"
                    : "create-activity-nivaa-unselected"
                }
                key="aktivt"
                type="button"
                id="aktivt"
                name="aktivt"
                onClick={() => handleActivityChange("aktivt")}
              >
                <h2>Aktivt</h2>
                <img
                  className="create-activity-nivaa-img-right"
                  src={ActivityMan}
                  alt="icon"
                />
              </button>

              <button
                className={
                  activityLevel.intens
                    ? "create-activity-nivaa-selected"
                    : "create-activity-nivaa-unselected"
                }
                key="intens"
                type="button"
                id="intens"
                name="intens"
                onClick={() => handleActivityChange("intens")}
              >
                <img
                  className="create-activity-nivaa-img-left"
                  src={Strength}
                  alt="icon"
                />
                <h2>intens</h2>
              </button>
            </div>
          </div>

          <div className="create-activity-beskrivelse">
            <h2>Beskrivelse</h2>
            <TextField
              {...register("description", registerOptions.description)}
              required
              error={errors.description ? true : false}
              helperText={errors.description ? errors.description.message : ""}
              id="standard-multiline-flexible"
              label="Beskrivelse"
              placeholder="Beskrivelse av min aktivitet"
              multiline
              rowsMax={4}
              fullWidth
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <AccountCircle />
                  </InputAdornment>
                ),
              }}
            />
          </div>
          <div className="create-activity-adresse">
            <h2>Adresse</h2>
            <ActivityLocationInput
              Address={Address}
              SetAddress={SetAddress}
              registerLocation={registerLocation}
              setLocation={setLocation}
              location={location}
              mapAddress={mapAddress}
            />          
          </div>
          <div className="create-activity-kart">
            <Map
              zoom={17}
              defaultCenter={[63.43246019515227, 10.397509451568368]}
              defaultZoom={11}
              onClick={registerLocation}
            >
              <Marker
                    key="key"
                    color={"red"}
                    width={40}
                    anchor={[new Number(location[0]), new Number(location[1])]}                    
                  />            
            </Map>
          </div>
          <div className="create-activity-oprett">
            <input className="loginFormButton" type="submit" />
            <div>{props.errors}</div>
          </div>
        </div>
        <div className="extra-grid">
          <div className="create-activity-ekstra">
            <h1>Ekstra</h1>
          </div>

          <Paper
            elevation={5}
            component="form"
            className="create-activity-utsyr"
          >
            <div className="create-activity-utsyr-text">
              <TextField
                required
                id="standard-required"
                label="Utsyr"
                value={newEquipment.label}
                onChange={handleEquipmentInputChange}
                placeholder="Add new equipment!"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountCircle />
                    </InputAdornment>
                  ),
                }}
              />

              <Divider
                className="create-activity-utsyr-divider"
                orientation="vertical"
              />
              <IconButton
                color="primary"
                aria-label="add equipment"
                onClick={addEquipment}
                type="button"
              >
                <DoneIcon />
              </IconButton>
            </div>
            <ul>
              {equipmentList.map((data) => {
                let icon;

                return (
                  <li key={data.key}>
                    <Chip
                      elevation={5}
                      icon={icon}
                      label={data.label}
                      onDelete={handleDelete(data)}
                    />
                  </li>
                );
              })}
            </ul>
          </Paper>

          <Paper
            elevation={5}
            component="form"
            className="create-activity-dato"
          >
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <div className="create-activity-dato-start">
                <KeyboardTimePicker
                  required
                  key="time-picker-start"
                  margin="normal"
                  id="time-picker"
                  label="Start"
                  value={Starttime}
                  variant="inline"
                  ampm={false}
                  onChange={(time) => SetStarttime(time)}
                  KeyboardButtonProps={{
                    "aria-label": "change time",
                  }}
                />
              </div>
              <div className="create-activity-dato-end">
                <KeyboardTimePicker
                  required
                  margin="normal"
                  key="time-picker-end"
                  id="time-picker"
                  label="Slutt"
                  value={EndTime}
                  variant="inline"
                  ampm={false}
                  onChange={(time) => SetEndTime(time)}
                  KeyboardButtonProps={{
                    "aria-label": "change time",
                  }}
                />
              </div>
              <div className="create-activity-dato-kalender">
                <KeyboardDatePicker
                  disableToolbar
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  id="date-picker-inline"
                  label="Dato"
                  value={date}
                  onChange={(date) => setDate(date)}
                  KeyboardButtonProps={{
                    "aria-label": "change date",
                  }}
                />
              </div>
            </MuiPickersUtilsProvider>
          </Paper>
          <Paper
            elevation={5}
            component="form"
            className="create-activity-bilde"
          >
            <InputLabel id="icon-label">Velg ikon</InputLabel>
            <div id="wrapper-icon-selection">
              <Select
                labelId="select-label"
                id="select"
                label="test"
                value={selectedImg}
                onChange={(event) => {setSelectedImg(event.target.value)}}
              >
                {renderMenuItems()}
              </Select>
              <p id="icon-select-infortext">Valgt ikon: </p>
              <img
                id="selected-activity-icon"
                src={iconMapping[selectedImg]}
                alt=""
              />
            </div>
          </Paper>

          <Paper
            className="create-activity-tags"
            elevation={5}
            component="form"
          >
            <div className="create-activity-tags-text">
              <TextField
                required
                label="Interesser"
                value={newInterest.label}
                onChange={handleInterestInputChange}
                placeholder="Legg til flere interesser!"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountCircle />
                    </InputAdornment>
                  ),
                }}
              />

              <Divider
                className="create-activity-utsyr-divider"
                orientation="vertical"
              />
              <IconButton
                color="primary"
                aria-label="add equipment"
                onClick={addInterest}
                type="button"
              >
                <DoneIcon />
              </IconButton>
            </div>
            <ul>
              {interestList.map((data) => {
                let icon;

                return (
                  <li key={data.key}>
                    <Chip
                      icon={icon}
                      label={data.label}
                      onDelete={handleDeleteInterest(data)}
                    />
                  </li>
                );
              })}
            </ul>
          </Paper>

          <Paper
            elevation={5}
            component="form"
            className="create-activity-deltakere"
          >
            <div className="create-activity-deltakere-text">
              <TextField
                {...register("capacity", registerOptions.capacity)}
                error={errors.capacity ? true : false}
                helperText={errors.capacity ? errors.capacity.message : ""}
                required
                fullWidth
                id="standard-number"
                label="Antall"
                type="number"
                placeholder="Hvor mange deltakere?"
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </div>
          </Paper>
        </div>
      </form>
    </div>
  );
}

export default CreateActivity;

//  <p>
//         {/* register your input into the hook by invoking the "register" function */}
//         Skriv inn tittel på aktiviteten:
//         <br></br>
//         <br></br>
//         {/*Velg bilde:
//           <br></br>
//         <br></br>
//         {/*
//           <br></br>
//           Sted:
//           <input className="formInput" defaultValue="place" {...register("place")} />
//           <br></br>
//           */}
//         <br></br>
//
//         <br></br>
//
//         <br></br><p>
//         {/* register your input into the hook by invoking the "register" function */}
//         Skriv inn tittel på aktiviteten:
//         <br></br>
//         <br></br>
//         {/*Velg bilde:
//           <br></br>
//
//         <br></br>
//         By:
//         <br></br>
//
//         {/*{errors.city && errors.city.type === "required" && (
//               <div className="error">Du må skrive inn by</div>
//           )}*/}
//         <br></br>
//         Gateadresse
//         <br></br>
//
//         <br></br>
//
//         <ul>
//           {equipmentList.map((equipment, i) => {
//             return (
//               <li>
//                 {equipment}{" "}
//                 <button
//                   type="button"
//                   onClick={() => {
//                     removeEquipment(i);
//                   }}
//                 >
//                   +
//                 </button>
//               </li>
//             );
//           })}
//         </ul>
//
//         <button onClick={addInterest} type="button">
//           Legg til
//         </button>
//         <ul>
//           {interestList.map((equipment, i) => {
//             return (
//               <li>
//                 {equipment}{" "}
//                 <button
//                   type="button"
//                   onClick={() => {
//                     removeInterest(i);
//                   }}
//                 >
//                   +
//                 </button>
//               </li>
//             );
//           })}
//         </ul>
//         {/* include validation with required or other standard HTML validation rules
//           <input className="loginFormInput" {...register("exampleRequired", { required: true })} />
//           {/* errors will return when field validation fails  }
//           {errors.exampleRequired && <span>This field is required</span>} */}
//         <input className="loginFormButton" type="submit" />
//         <div>{props.errors}</div>
//         {errors.name && <span>{errors}</span>}
//       </p>

/* "handleSubmit" will validate your inputs before invoking "onSubmit" */
// <div className="parent">
//   <h1>Lag aktivitet</h1>
//     <p>
//       {/* register your input into the hook by invoking the "register" function */}
//       Skriv inn tittel på aktiviteten:
//       <br />
//       <TextField className="formInput" defaultValue="" {...register("name", registerOptions.name)} />
//       {errors.name && (<div class="error">Aktiviteten må ha en tittel</div>)}
//       <br />

//       {/*Velg bilde:
//       <br></br>
//       <select onChange={(e)=> setSelectedImg(e.currentTarget.value)} {...register("imgPath")}>
//         <option value="">Velg bilde</option>
//         <option value="bilde1.jpg">bilde 1</option>
//       </select>
//       <br></br>
//       {showImage()}
//       <br></br>
//       */}

//       Velg treningsnivå:
//       <br />
//       <select {...register("activityLevel")}>
//         <option value="LOW">Lav</option>
//         <option value="MEDIUM">Medium</option>
//         <option value="HIGH">Høy</option>
//       </select>
//       <br />

//       Beskrivelse av aktiviteten:
//       <br />
//       <TextField className="formInput" defaultValue="" {...register("description", registerOptions.description)} />
//       {errors.description && (<div class="error">Du må skrive en kort beskrivelse av aktiviteten</div>)}
//       <br />

//       Antall deltakere
//       <br />
//       <TextField className="formInput" type="number" {...register("capacity", registerOptions.capacity)} />
//       {errors.capacity && (<div class="error">Maks. antall deltakere er påkrevd</div>)}
//       <br />

//       Tidspunkt: Start
//       <br />
//       <TextField className="formInput" type="text" {...register("startTime", registerOptions.startTime)} />
//       {errors.startTime && (<div class="error">Tidspunkt for start er påkrevd</div>)}
//       <br />

//       Tidspunkt: Slutt
//       <br />
//       <TextField className="formInput" type="text" {...register("endTime", registerOptions.endTime)} />
//       {errors.endTime && (<div class="error">Tidspunkt for slutt er påkrevd</div>)}
//       <br />

//       By:
//       <br />
//       <TextField className="formInput" type="text" {...register("city", registerOptions.city)} />
//       {errors.city && (<div class="error">By er påkrevd</div>)}
//       <br />

//       Gateadresse:
//       <br />
//       <TextField className="formInput" type="text" {...register("address", registerOptions.address)} />
//       {errors.address && (<div class="error">Gateadresse er påkrevd</div>)}
//       <br />

//       Utstyr:
//       <br />
//       <TextField className="formInput" type="text" value={newEquipment} onChange={handleEquipmentInputChange} onKeyDown={handleEquipmentKeyPress}/>
//       <Button className="activityFormAddToListButton" onClick={addEquipment} type="button">Legg til</Button>
//       <ul>
//         {equipmentList.map((equipment, i) => {
//           return <li>{equipment} <button type="button" onClick={() => { removeEquipment(i); }}>-</button></li>
//         })}
//       </ul>
//       <br />

//       Interesser:
//       <br />
//       <TextField className="formInput" type="text"   value={newInterest} onChange={handleInterestInputChange} onKeyDown={handleInterestKeyPress}/>
//       <Button className="activityFormAddToListButton" onClick={addInterest} type="button">Legg til</Button>
//       <ul>
//         {interestList.map((equipment, i) => {
//           return <li>{equipment} <button type="button" onClick={() => { removeInterest(i); }}>-</button></li>
//         })}
//       </ul>
//       <br />

//       {/* include validation with required or other standard HTML validation rules
//       <input className="loginFormInput" {...register("exampleRequired", { required: true })} />
//       {/* errors will return when field validation fails  }
//       {errors.exampleRequired && <span>This field is required</span>} */}

//       <input className="loginFormButton" type="submit" />

//

//       {/*errors.name && <span>{errors}</span>*/}
//     </p>
//   </form>
