import "./WeatherBox.css";
import WeatherSymbol from './WeatherSymbol';
import {useEffect, useState, Fragment} from 'react';

// https://api.met.no/weatherapi/locationforecast/2.0/compact.json?lat=63&lon=10

function WeatherBox(props) {
    const [symbolCode, setSymbolCode] = useState("");
    const [temperature, setTemperature] = useState(0);
    const [noWeatherData, setNoWeatherData] = useState(true);

    const fetchWeatherData = async (lat, lon) => {
        if (!(lat && lon)) return;

        const response = await fetch('https://api.met.no/weatherapi/locationforecast/2.0/complete.json?lat=' + lat + '&lon=' + lon)
        return await response.json();
    }

    // Sets weather data
    const setWeatherData = (symbolCode, airTemperature) => {
        setTemperature(airTemperature);
        setSymbolCode(symbolCode);
    }

    // Method to change activity time into compatible format for the API
    const setCompatibleDate = async (datetime) => {
        if (!datetime) return;

        const substrings = datetime.split(" ");
        return await substrings[0] + 'T' + substrings[1].substring(0,3) + '00:00Z';
    }

    useEffect(() => {
        if (!props.latitude && !props.longitude && !props.date && !props.size) return;

        // We convert the time first, then fetch data
        setCompatibleDate(props.date).then((compatibleTime) => {

            fetchWeatherData(props.latitude, props.longitude)
                .then((result) => {
                    if (!result || !result['properties'] || !result['properties']['timeseries']) return;

                    const timeseries = Object.keys(result.properties.timeseries).map((key) => result.properties.timeseries[key]);

                    //timeseries.forEach(t => console.log(t.time));
                    const weatherdata = timeseries.find(o => o.time.includes(compatibleTime));

                    // We stop here if we got no valid data
                    if (!weatherdata) return;

                    let summary;
                    let details;
                    // Weatherdata
                    if(weatherdata && weatherdata.data && weatherdata.data.next_1_hours && weatherdata.data.next_6_hours && weatherdata.data.next_1_hours.summary && weatherdata.data.next_6_hours.details){
                        summary = weatherdata.data.next_1_hours.summary;
                        details = weatherdata.data.next_6_hours.details;
                        setNoWeatherData(false);
                        setWeatherData(summary.symbol_code, Math.floor(details.air_temperature_max));
                    }
                    

                    //if (summary && details) 

                    //Round the temperature down to the closest integer
                    
                });
        });
    });

    return (
        <div className="WeatherBox">

            {props.size === "small" &&
            <div className="WeatherBoxSmall">
                {noWeatherData &&
                    <WeatherSymbol name="noweatherdata" />
                }
                {!noWeatherData &&
                    <Fragment>
                        <WeatherSymbol name={symbolCode} />
                        <div className="temperature">
                            {temperature}&#730;
                        </div>
                    </Fragment>
                }
            </div>
            }

            {props.size === "normal" &&
            <div className="WeatherBoxNormal">
                <WeatherSymbol name={symbolCode} />

                <div className="temperature">
                    {temperature}&#730;
                </div>
            </div>
            }
        </div>
    );
}

export default WeatherBox