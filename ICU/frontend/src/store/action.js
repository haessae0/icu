import { fetchUserInfo } from "../api/auth.js";

export default {
  FETCH_IMAGE({ commit }) {
    fetchUserInfo()
      .then(({ data }) => commit("FETCH_IMAGE", data))
      .catch(error => console.log(error));
  }
};
