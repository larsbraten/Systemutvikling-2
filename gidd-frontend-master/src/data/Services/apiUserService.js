import {API_URL, NEW_USER, NEW_VERIFICATION_EMAIL} from "../../utils/api"
import axios from "axios"

const getUser = async (data) => {
    let res;
    try{
        res = await axios.get(`${API_URL}/users}`);
    }catch(err){    }
    return res;
}

/*const getUser = async (id) => {
    return new Promise((resolve, reject) => {
        console.log("GETTING NEW USER WITH ID")
        return axios.get(`${API_URL}user/${id}`)
            .then(res => {
                console.log(res.data);
                return resolve(res.data)
            })
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            })
    });
}*/

const putUser = async (id, data) => {
    return new Promise((resolve, reject) => {
        return axios.put(`${API_URL}user/${id}`, data)
            .then(res => resolve(res.data))
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            })
    })
}

const getTopFiftyUsers = async () => {
    let res;
    try{
        res = await axios.get(`${API_URL}users/leaderboard`);
    }catch(err){

    }
    return res;
}

const postUser = async (data) => {
    return new Promise((resolve, reject) => {
        return axios.post(`${API_URL}${NEW_USER}`, data)
            .then(res => resolve(res.data))
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
}

const apiCall = async (method, path, data) => {
    return new Promise((resolve, reject) => {
        return axios[method](path, data)
            .then(res => resolve(res.data))
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    });
}

const resendVerificationEmail = async (email) => {
    return new Promise((resolve, reject) => {
        return axios.post(`${API_URL}${NEW_VERIFICATION_EMAIL}`, email)
            .then(res => resolve(res.data))
            .catch(err => {
                console.log( {...err})
                return reject({...err})
            });
    })
    // return apiCall(
    //     "POST",
    //     `${API_URL}${NEW_VERIFICATION_EMAIL}`,
    //     email)
}

export const UserService = {
    getUser,
    postUser,
    apiCall,
    resendVerificationEmail,
    getTopFiftyUsers,
    resendVerificationEmail,
    putUser
  };