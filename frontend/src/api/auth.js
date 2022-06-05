import { instance } from "./index";
import { getUserIdFromSession } from "../utils/session";
import axios from "axios";

const config = {
  baseUrl: "http://localhost:8000/"
};

function fetchUserInfo() {
  const username = getUserIdFromSession();
  let instance = axios.create();
  instance.defaults.headers.common["Authorization"] = sessionStorage.getItem(
    "Authorization"
  );
  return instance.get(`${config.baseUrl}user/myinfo?username=${username}`);
}

function signupUser(data) {
  console.log("data");
  return instance.post("/user/signup", data);
}

function signinUser(data) {
  return instance.post("/user/signin", data);
}

function LogoutUser(userData) {
  return instance.post("/logout", userData);
}

export {
  signupUser,
  signinUser,
  LogoutUser,
  fetchUserInfo
};
