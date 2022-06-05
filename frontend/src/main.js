import "@/scss/main.scss";
import Vue from "vue";
import Buefy from "buefy";
import 'buefy/dist/buefy.css'
import router from "./router";
import store from "./store";
import "./utils/registerServiceWorker";
import App from "./App.vue";
const defaultDocumentTitle = "ICU";
router.afterEach(to => {
  store.commit("asideMobileStateToggle", false);

  if (to.meta.title) {
    document.title = `${to.meta.title} — ${defaultDocumentTitle}`;
  } else {
    document.title = defaultDocumentTitle;
  }
});

Vue.config.productionTip = false;

Vue.use(Buefy);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
