
import {API_URL} from "../../utils/api"
import axios from "axios"
axios.defaults.withCredentials = true;

const postActivity = async (data) => {
    console.log("Request body sent to server: ")
    console.log(data)
    const res = await axios.post(`${API_URL}activity`,data);
    return res.data.payload.requestId;
}

const getActivities = async (data) =>{
    let res;
    
    try{
        res = await axios.get(`${API_URL}activity`);

    }catch(err){

    }
    return res;
}

const getLocations = async () =>{
    let res;
    
    try{
        res = await axios.get(`${API_URL}activities/locations/unique`);

    }catch(err){

    }
    return res;
}

const getInterests = async () =>{
    let res;
    
    try{
        res = await axios.get(`${API_URL}activities/interests/unique`);

    }catch(err){

    }
    return res;
}

const getFilteredActivities = async (params) =>{
    let res;
    
    try{
        res = await axios({
            method: 'POST',
            url: `${API_URL}activities/filter`,
            
             data:{                 
                activitySearchCriteria: params.activitySearchCriteria,                 
                activityPage: params.activityPage        
             },
            })

    }catch(err){

    }
    return res;
}

const getMyEnrolledActivities = async () =>{
    let res;
    
    try{
        res = await axios.get(`${API_URL}activities/enrolled`);

    }catch(err){
        console.log(err.response)
    }
    return res;
}

const getMyOrganizedActivities = async () =>{
    let res;
    
    try{
        res = await axios.get(`${API_URL}activities/organized`);

    }catch(err){
        console.log(err.response)
    }
    return res;
}

const getActivity = async (id) => {
    console.log("GET ACTIVITY")
    return new Promise((resolve, reject) => {
        return axios.get(`${API_URL}activities/${id}`)
            .then(res => {
                console.log(res.data.payload)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const updateActivity = async (id, data) => {
    return new Promise((resolve, reject) => {
        console.log("UPDATING ACTIVITY")
        console.log(data)
        return axios.put(`${API_URL}activity/${id}/update`, data)
            .then(res => {
                console.log(res)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const joinActivity = async (activityId) => {
    let res
    try{
        res = await axios.post(`${API_URL}activities/`+activityId+`/join`);
    }catch(err){}
    return res;
}

const checkIntoActivity = async (activityId, data) => {
    return axios.post(`${API_URL}activities/${activityId}/checkin`, data);
}

const unJoinActivity = async (activityId) => {
    let res
    try{
        res = await axios.delete(`${API_URL}activities/`+activityId+`/join`);
    }catch(err){}
    return res;
}

const claimEquipment = async (equipmentId) => {
    return new Promise((resolve, reject) => {
        return axios.put(`${API_URL}equipment/${equipmentId}/claim`)
            .then(res => {
                console.log(res)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const unclaimEquipment = async (equipmentId) => {
    return new Promise((resolve, reject) => {
        return axios.delete(`${API_URL}equipment/${equipmentId}/claim`)
            .then(res => {
                console.log(res)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const getEquipmentList = async (activityId) => {
    return new Promise((resolve, reject) => {
        return axios.delete(`${API_URL}activity/${activityId}/equipment`)
            .then(res => {
                console.log(res)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const deleteActivity = async (activityId) => {
    return new Promise((resolve, reject) => {
        return axios.delete(`${API_URL}activity/${activityId}`)
            .then(res => {
                console.log(res)
                return resolve(res)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

export const ActivityService = {
    postActivity,
    getLocations,
    getInterests,
    getActivities,
    getFilteredActivities,
    getMyEnrolledActivities,
    joinActivity,
    unJoinActivity,
    getMyOrganizedActivities,
    getActivity,
    updateActivity,
    checkIntoActivity,
    claimEquipment,
    unclaimEquipment,
    getEquipmentList,
    deleteActivity,
  };