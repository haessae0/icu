<template>
  <div id="app">
    <nav-bar />
    <transition name="page">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";
import { mapState } from "vuex";
import { getUserRoleFromSession } from "../src/utils/session";

export default {
  name: "home",
  components: {
    NavBar
  },
  data() {
    return {
      userRole: getUserRoleFromSession() || ""
    };
  },
  computed: {
    ...mapState(["userName", "userRole"])
  },
  created() {
    this.$store.commit("user", {
      name: "",
      email: ""
    });
  }
};
</script>

<style>
.page-enter-active,
.page-leave-active {
  transition: opacity 0.5s;
}

.page-enter,
.page-leave-to {
  opacity: 0;
}
</style>
