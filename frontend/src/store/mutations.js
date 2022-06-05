export default {
  fetchImage: (state, payload) => {
    state.userImage = payload;
  },

  basic(state, payload) {
    state[payload.key] = payload.value;
  },

  user(state, payload) {
    console.log(payload.name);
    console.log(state.userName);
    if (payload.name) {
      state.userName = payload.name;
    }
    if (payload.email) {
      state.userEmail = payload.email;
    }
  },

  asideMobileStateToggle(state, payload = null) {
    const htmlClassName = "has-aside-mobile-expanded";

    let isShow;

    if (payload !== null) {
      isShow = payload;
    } else {
      isShow = !state.isAsideMobileExpanded;
    }

    if (isShow) {
      document.documentElement.classList.add(htmlClassName);
    } else {
      document.documentElement.classList.remove(htmlClassName);
    }

    state.isAsideMobileExpanded = isShow;
  },
};
