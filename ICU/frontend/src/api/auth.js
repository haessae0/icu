// 로그인, 회원 가입, 회원 탈퇴
import { instance } from "./index";
import { getUserIdFromSession } from "../utils/session";
import axios from "axios";

const config = {
  baseUrl: "http://localhost:8000/"
};

//회원 이미지 파일 가져오기
function fetchUserInfo() {
  const username = getUserIdFromSession();
  let instance = axios.create();
  instance.defaults.headers.common["Authorization"] = sessionStorage.getItem(
    "Authorization"
  );
  return instance.get(`${config.baseUrl}user/myinfo?username=${username}`);
}

// 회원가입 API
function signupUser(data) {
  console.log("data");
  return instance.post("/user/signup", data);
}

// 약관동의 API
function memberAgreeUser(data) {
  return instance.post("MemberAgree", data);
}

// 로그인 API
function signinUser(data) {
  return instance.post("/user/signin", data);
}

// 전체회원정보 get API
function allClient(data) {
  return instance.get("/getMemberList", data);
}

//로그아웃
function LogoutUser(userData) {
  return instance.post("/logout", userData);
}

export {
  signupUser,
  signinUser,
  memberAgreeUser,
  allClient,
  LogoutUser,
  fetchUserInfo
};
