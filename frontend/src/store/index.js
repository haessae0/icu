import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import { getUserIdFromSession, getUserRoleFromSession } from "../utils/session";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userName: getUserIdFromSession() || "",
    userRole: getUserRoleFromSession() || "",

    fullname: "",
    phoneNumber: "",
    role: "",

    userEmail: null,
    isNavBarVisible: true
  },
  mutations
});
