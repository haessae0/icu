<template>
  <div class="container">
    <div class="container pt-6 pb-6 pl-4 pr-4">
      <div class="box">
        <form @submit.prevent="submitForm()" class="form">
          <br />
          <b-field label="이메일" type="" message="" align="left">
            <b-input v-model="username" placeholder="2xxxxxxx@daegu.ac.kr" maxlength="30" size="is-medium"></b-input>
          </b-field>
          <br />
          <b-field label="비밀번호" type="" message="" align="left">
            <b-input v-model="password" type="password" placeholder="비밀번호" maxlength="30" size="is-medium"
              password-reveal>
            </b-input>
          </b-field>
          <br />
          <b-field label="이름" type="" message="" align="left">
            <b-input v-model="fullname" placeholder="이름" maxlength="30" size="is-medium"></b-input>
          </b-field>
          <br />
          <b-field label="핸드폰 번호" type="" align="left">
            <b-input v-model="phoneNumber" placeholder="01x-xxxx-xxxx" maxlength="30" size="is-medium"></b-input>
          </b-field>
          <br />
          <b-field label="직위" type="" message="" align="left">
            <div class="block">
              <b-radio v-model="role" name="instructor" native-value="instructor">
                교수
              </b-radio>
              <b-radio v-model="role" name="student" native-value="student">
                학생
              </b-radio>
            </div>
          </b-field>
          <br />
          <div class="has-text-centered">
            <b-button @click="submitForm()" class="is-primary">
              회원 가입
            </b-button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import { validateEmail } from "@/utils/validation";
import { validatePhoneNumber } from "@/utils/validation";
import axios from "axios";
export default {
  name: "SignUpForm",
  data() {
    return {
      username: "",
      password: "",
      fullname: "",
      phoneNumber: "",
      role: ""
    };
  },
  computed: {
    isIdValid() {
      return validateEmail(this.username);
    },
    isPhoneNumberValid() {
      return validatePhoneNumber(this.phoneNumber);
    }
  },
  methods: {
    submitForm() {
      let formData = new FormData();
      formData.append("username", this.username);
      formData.append("password", this.password);
      formData.append("fullname", this.fullname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("role", this.role);
      axios
        .post("http://localhost:8000/user/signup", formData, {
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }
        })
        .then(response => {
          this.success();
          console.log(response.data);
          this.$router.push({ name: "Sign In" });
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
      this.fullname = "";
      this.phoneNumber = "";
      this.role = "";
    },
    success() {
      this.$buefy.notification.open({
        message: "회원가입에 성공하셨습니다.",
        type: "is-success",
        icon: true,
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
