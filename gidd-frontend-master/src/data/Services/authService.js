import axios from 'axios';
import React from 'react';
import { Redirect } from 'react-router-dom';
import {API_URL} from "../../utils/api"

axios.defaults.withCredentials = true;
//axios.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080'; 
//axios.defaults.baseURL = 'http://localhost:8080';
//axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

const login = async (userdata) => {
  let response;

  await axios({
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    // headers: {
    //   "Authorization": 'Basic ' + window.btoa(email + ":" + password)
    //},
    url: `${API_URL}login`,
    params: {
      username: userdata.email,
      password: userdata.password,
    },
    // data:{                 
    //   username: userdata.email,                 
    //   password: userdata.password             
    // },
  }).then((res) => {
    response = res;
    console.log(res);
  })
    .catch(function (error) {
      console.error(error);
      return error;
    });

  return response;
};

async function logout() {
  try{
    await axios.post( `${API_URL}logout`)
  }
  catch(e){
    console.log(e)
  }
}

async function authUser() {
  try {
    let res;
    res = await axios({
      url: API_URL + 'auth',
      method: 'GET',
    });
    console.log("AUTH USER:")
    console.log(res)
    return res;
  }
  catch (err) {
    return err;
  }
}

async function verifyUser(data) {
  try {
    const res = await axios({
      url: API_URL + 'user/verify/' + data,
      method: 'POST',
      
    });
    return true;
  } catch (err) {
    return false;
  }
}

export const authenticationService = {
  login,
  logout,
  authUser,
  verifyUser
};