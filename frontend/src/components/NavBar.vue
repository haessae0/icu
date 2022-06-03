<template>
  <nav v-show="isNavBarVisible" id="navbar-main" class="navbar is-fixed-top">
  <img src="../assets/logo2.jpg" width="30" height="28" />
  <span slot="label"> <a id="custom" href="/"> ICU</a></span>
    <div class="navbar-brand">
      <a
        class="navbar-item is-hidden-desktop"
        @click.prevent="menuToggleMobile"
      >
        <b-icon :icon="menuToggleMobileIcon" />
      </a>
    </div>
    <div class="navbar-brand is-right">
      <a
        class="navbar-item navbar-item-menu-toggle is-hidden-desktop"
        @click.prevent="menuNavBarToggle"
      >
        <b-icon :icon="menuNavBarToggleIcon" custom-size="default" />
      </a>
    </div>
    <div
      class="navbar-menu fadeIn animated faster"
      :class="{ 'is-active': isMenuNavBarActive }"
    >
      <div class="navbar-end">
        <nav-bar-menu class="has-divider">
          <div class="is-user-name">
            <template v-if="authorization != null">
              <span>{{ userName }}</span>
            </template>
            <template v-else>
              <span>로그인</span>
            </template>
          </div>
          <div slot="dropdown" class="navbar-dropdown">
            <a class="navbar-item">
              <b-icon icon="account" custom-size="default"></b-icon>
              <template v-if="authorization != null">
                <router-link id="custom" to="/profile">마이 페이지</router-link>
              </template>
              <template v-else>
                <router-link id="custom" to="/signin">로그인</router-link>
              </template>
            </a>
            <hr class="navbar-divider" />
            <a class="navbar-item">
              <b-icon icon="logout" custom-size="default"></b-icon>
              <template v-if="authorization != null">
                <span @click="logout()">로그아웃</span>
              </template>
              <template v-else>
                <router-link id="custom" to="/signup">회원가입</router-link>
              </template>
            </a>
          </div>
        </nav-bar-menu>
        <a
          class="navbar-item is-desktop-icon-only">
        </a>
        <a
          class="navbar-item is-desktop-icon-only">
        </a>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapState } from "vuex";
import NavBarMenu from "@/components/NavBarMenu";

import axios from "axios";
export default {
  name: "NavBar",
  components: {
    NavBarMenu
  },
  data() {
    return {
      isMenuNavBarActive: false,
      authorization: sessionStorage.getItem("Authorization")
    };
  },
  computed: {
    menuNavBarToggleIcon() {
      return this.isMenuNavBarActive ? "close" : "dots-vertical";
    },
    menuToggleMobileIcon() {
      return this.isAsideMobileExpanded ? "backburger" : "forwardburger";
    },
    darkModeToggleIcon() {
      return this.isDarkModeActive ? "white-balance-sunny" : "weather-night";
    },
    ...mapState([
      "isNavBarVisible",
      "isAsideMobileExpanded",
      "userName"
    ])
  },
  mounted() {
    this.$router.afterEach(() => {
      this.isMenuNavBarActive = false;
    });
  },
  methods: {
    menuToggleMobile() {
      this.$store.commit("asideMobileStateToggle");
    },
    menuNavBarToggle() {
      this.isMenuNavBarActive = !this.isMenuNavBarActive;
    },
    logout() {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .post("http://localhost:8000/user/logout")
        .then(() => {
          this.success();
          sessionStorage.removeItem("Authorization");
          // this.$router.push({ name: "Home" });
          location.reload();
          location.href = "http://localhost:8080/";
        })
        .catch(error => {
          this.danger();
          console.log(error);
        });
    },
    success() {
      this.$buefy.notification.open({
        message: "로그아웃 완료되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `로그아웃 실패`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  }
};
</script>
<style scoped>
a#custom {
  color: #ffb300 !important;
}
</style>
