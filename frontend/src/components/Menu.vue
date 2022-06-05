<template>
  <section>
    <b-sidebar type="is-light" :fullheight="fullheight" :fullwidth="fullwidth" :overlay="overlay" :right="right"
      v-model="open">
      <div class="p-1">
        <img src="../assets/logo2.jpg" />
        <b-menu>
          <b-menu-list label="Menu">
            <b-menu-item icon="home" label="메인 화면" tag="router-link" to="/"></b-menu-item>
            <b-menu-item icon="account" label="내 정보">
              <b-menu-item label="프로필" tag="router-link" to="/profile"></b-menu-item>
            </b-menu-item>
          </b-menu-list>
          <template v-if="userRole === 'ROLE_INSTRUCTOR'">
            <b-menu-list>
              <b-menu-item label="시험 출제" icon="link" tag="router-link" to="/instructor"></b-menu-item>
            </b-menu-list>
          </template>
          <template v-else-if="userRole === 'ROLE_STUDENT'">
            <b-menu-list>
              <b-menu-item label="시험 보기" icon="link" tag="router-link" to="/student"></b-menu-item>
            </b-menu-list>
          </template>
          <b-menu-list label="Actions">
            <template v-if="authorization != null">
              <span @click="logout()">
                <b-menu-item label="로그아웃"></b-menu-item>
              </span>
            </template>
            <template v-else>
              <router-link id="custom" to="/signin">로그인</router-link>
            </template>
          </b-menu-list>
        </b-menu>
      </div>
    </b-sidebar>
    <b-button @click="open = true">Menu</b-button>
  </section>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
import { getUserRoleFromSession } from "@/utils/session";


export default {
  data() {
    return {
      open: false,
      overlay: false,
      fullheight: true,
      fullwidth: false,
      right: false,
      authorization: sessionStorage.getItem("Authorization"), 
      userRole: getUserRoleFromSession() || ""
    };
  },
  computed: {
    ...mapState(["userName", "userRole"])
  },
  methods: {
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

<style>
.p-1 {
  padding: 1em;
}
</style>