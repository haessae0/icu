<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="hero-body">
        <div class="container has-text-centered">
          <h1 class="title ">
            로그인
          </h1>
          <h2 class="subtitle centered">
            오늘도 환영합니다.
          </h2>
        </div>
      </div>
    </section>
    <section>
      <div class="container is-max-desktop">
        <div class="columns is-mobile">
          <div class="column is-half is-offset-one-quarter">
            <form @submit.prevent="submitForm()" class="form">
              <b-field label="ID" type="" message="" align="left">
                <b-input
                  v-model="username"
                  placeholder="아이디"
                  maxlength="30"
                  size="is-medium"
                ></b-input>
              </b-field>
              <b-field label="Password" type="" message="" align="left">
                <b-input
                  v-model="password"
                  type="password"
                  placeholder="비밀번호"
                  maxlength="30"
                  size="is-medium"
                >
                </b-input>
              </b-field>
              <div class="buttons is-centered">
                <b-button class="btn is-primary" @click="submitForm()">
                  로그인
                </b-button>
                <b-button
                  class="is-primary"
                  tag="router-link"
                  to="/signup"
                  type="is-link"
                >
                  회원가입
                </b-button>
              </div>
              <br /><br />
            </form>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import { validateEmail } from "@/utils/validation";
import axios from "axios";
export default {
  data() {
    return {
      username: "",
      password: ""
    };
  },
  computed: {
    isIdValid() {
      return validateEmail(this.username); //id가 이메일 형식이 맞는지 체크
    }
  },
  methods: {
    submitForm() {
      const loginData = {
        username: this.username,
        password: this.password
      };
      axios
        .post("http://localhost:8000/user/signin", loginData, {
          headers: {
            "Content-Type": "application/json"
          }
        })
        .then(response => {
          console.log(response.headers.authorization);
          sessionStorage.setItem(
            "Authorization",
            response.headers.authorization
          );
          // this.$router.push({ name: "Home" });
          location.reload();
          location.href = "http://localhost:8081/";
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    initForm() {
      this.username = "";
      this.password = "";
    }
  }
};
</script>
