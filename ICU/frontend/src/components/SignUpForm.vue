<template>
  <div class="container">
    <div class="container pt-6 pb-6 pl-4 pr-4">
      <div class="box">
        <form class="form" @submit.prevent="submitForm()">
          <br/>
          <b-field align="left" label="ID" message="" type="">
            <b-input
                v-model="username"
                maxlength="30"
                placeholder="2XXXXXXX@daegu.ac.kr"
                size="is-medium"
            ></b-input>
          </b-field>
          <br/>
          <b-field align="left" label="비밀번호" message="" type="">
            <b-input
                v-model="password"
                maxlength="30"
                password-reveal
                placeholder="비밀번호"
                size="is-medium"
                type="password"
            >
            </b-input>
          </b-field>
          <br/>
          <b-field align="left" label="이름" message="" type="">
            <b-input
                v-model="fullname"
                maxlength="30"
                placeholder="이름"
                size="is-medium"
            ></b-input>
          </b-field>
          <br/>
          <b-field align="left" label="핸드폰 번호" type="">
            <b-input
                v-model="phoneNumber"
                maxlength="30"
                placeholder="01x-xxxx-xxxx"
                size="is-medium"
            ></b-input>
          </b-field>
          <br/>
          <!-- 파일 선택 및 업로드 -->
          <b-field align="left" label="프로필 사진">
            <input
                id="file"
                ref="file"
                type="file"
                v-on:change="handleFileUpload()"
            />
          </b-field>
          <b-field align="left" label="권한 설정" message="" type="">
            <div class="block">
              <b-radio
                  v-model="role"
                  name="instructor"
                  native-value="instructor"
              >
                강사
              </b-radio>
              <b-radio v-model="role" name="student" native-value="student">
                학생
              </b-radio>
            </div>
          </b-field>
          <br/>
          <b-button class="is-primary" @click="submitForm()">
            회원 가입
          </b-button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "SignUpForm",
  data() {
    return {
      username: "",
      password: "",
      fullname: "",
      phoneNumber: "",
      userImage: "",
      role: "",
      memberagree: false
    };
  },
  methods: {
    submitForm() {
      let formData = new FormData();
      formData.append("username", this.username);
      formData.append("password", this.password);
      formData.append("fullname", this.fullname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("file", this.file);
      formData.append("role", this.role);
      axios
          .post("http://localhost:8000/user/signup", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              "Access-Control-Allow-Origin": "*"
            }
          })
          .then(response => {
            this.success();
            console.log(response.data);
            this.$router.push({name: "Sign In"});
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
      this.userIamge = "";
      this.role = "";
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
    success() {
      this.$buefy.notification.open({
        message: "회원가입에 성공하셨습니다.",
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
