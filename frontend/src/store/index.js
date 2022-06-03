import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./action.js";
import { getUserIdFromSession, getUserRoleFromSession } from "../utils/session";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    /* User */
    // userName: sessionStorage.getItem("userName")
    //   ? JSON.parse(sessionStorage.getItem("userName"))
    //   : null,
    // Bearer eyJjb20iOiJFRFVDQVJFIiwidHlwIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhYWFAbmF2ZXIuY29tIiwiYXV0aCI6IlJPTEVfU1RVREVOVCIsImV4cCI6MTYxODMxOTEwNX0.qZ7cmn3tIDtoHpja_ynSDyOerW9-HG1SzXALxpEdJ9-KY-QNhJ_CqSvSu3HDWQ_sL7Jap87sK1WhwUqlCOcEFQ

    //JWT에서 가져온 user_id와 role
    userName: getUserIdFromSession() || "",
    userRole: getUserRoleFromSession() || "",

    //기본 이미지 URL
    imgURL: "http://localhost:8000/userimg/",

    //store를 이용해서 image,Realname 가져오기
    userImage: "",
    fullname: "",
    phoneNumber: "",
    role: "",

    userEmail: null,
    /* NavBar */
    isNavBarVisible: true,

    /* FooterBar */
    isFooterBarVisible: true,

    /* Aside */
    isAsideVisible: true,
    isAsideMobileExpanded: false,

    /* Dark mode */
    isDarkModeActive: false
  },
  actions,
  mutations
});
