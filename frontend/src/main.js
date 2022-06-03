/* Styles */
import "@/scss/main.scss";

/* Core */
import Vue from "vue";
import Buefy from "buefy";
import 'buefy/dist/buefy.css'

/* Router & Store */
import router from "./router";
import store from "./store";

/* Service Worker */
import "./registerServiceWorker";

/* Vue. Main component */
import App from "./App.vue";

/* Default title tag */
const defaultDocumentTitle = "ICU";

/* Collapse mobile aside menu on route change & set document title from route meta */
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
