<template>
  <nav v-show="isNavBarVisible" id="navbar-main" class="navbar is-fixed-top">
    <div class="navbar-brand">
      <a
          class="navbar-item is-hidden-desktop"
          @click.prevent="menuToggleMobile"
      >
        <b-icon :icon="menuToggleMobileIcon"/>
      </a>
    </div>
    <div class="navbar-brand is-right">
      <a
          class="navbar-item navbar-item-menu-toggle is-hidden-desktop"
          @click.prevent="menuNavBarToggle"
      >
        <b-icon :icon="menuNavBarToggleIcon" custom-size="default"/>
      </a>
    </div>
    <div
        :class="{ 'is-active': isMenuNavBarActive }"
        class="navbar-menu fadeIn animated faster"
    >
      <div class="navbar-end">
        <nav-bar-menu class="has-divider has-user-avatar">
          <user-avatar/>
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
              <b-icon custom-size="default" icon="account"></b-icon>
            </a>
            <hr class="navbar-divider"/>
            <a class="navbar-item">
              <b-icon custom-size="default" icon="logout"></b-icon>
              <template v-if="authorization != null">
                <span @click="logout()">로그아웃</span>
              </template>
            </a>
          </div>
        </nav-bar-menu>
        <a
            class="navbar-item is-desktop-icon-only"
            title="Log out"
            @click="logout"
        >
        </a>
      </div>
    </div>
  </nav>
</template>

<script>
import {mapState} from "vuex";
import axios from "axios";

export default {
  name: "NavBar",
  components: {
    UserAvatar,
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
      "isDarkModeActive",
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
    darkModeToggle() {
      this.$store.commit("darkModeToggle");
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
  color: #00b274 !important;
}
</style>
