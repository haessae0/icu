<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="hero-body">
        <div class="container has-text-centered">
          <h1 class="title ">
            로그인
          </h1>
          <h2 class="subtitle centered">
            즐거운 하루되세요.
          </h2>
        </div>
      </div>
    </section>
    <section>
      <div class="container is-max-desktop">
        <div class="columns is-mobile">
          <div class="column is-half is-offset-one-quarter">
            <form @submit.prevent="submitForm()" class="form">
              <b-field label="이메일" type="" message="" align="left">
                <b-input
                  v-model="username"
                  placeholder="이메일"
                  maxlength="30"
                  size="is-medium"
                ></b-input>
              </b-field>
              <b-field label="비밀번호" type="" message="" align="left">
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
      return validateEmail(this.username);
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
          this.success();
          location.reload();
          location.href = "http://localhost:8080/";
        })
        .catch(error => {
          this.danger();
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    initForm() {
      this.username = "";
      this.password = "";
    },
    success() {
      this.$buefy.notification.open({
        message: "로그인에 성공하셨습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `회원 가입을 다시 시도해 주세요.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  }
};
</script>
