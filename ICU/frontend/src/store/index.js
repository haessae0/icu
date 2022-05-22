import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./action.js";
import {getUserIdFromSession, getUserRoleFromSession} from "@/utils/session";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {

        userId: getUserIdFromSession() || "",
        userRole: getUserRoleFromSession() || "",

        imgURL: "http://localhost:8000/userimg/",

        profileImage: "",
        name: "",
        phoneNumber: "",
        role: "",

        userEmail: null,
        userAvatar: null,
        isNavBarVisible: true,

        isFooterBarVisible: true,

        isAsideVisible: true,
        isAsideMobileExpanded: false,
        isDarkModeActive: false
    },
    actions,
    mutations
});
