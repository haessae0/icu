<template>
  <card-component title="프로필 수정" icon="account-circle">
    <form @submit.prevent="submit">
      <b-field horizontal label="사진">
        <input
          type="file"
          id="file"
          ref="file"
          v-on:change="handleFileUpload()"
        />
      </b-field>
      <hr />
      <b-field horizontal label="이름" message="필수 입력 사항">
        <b-input
          v-model="userRealname"
          name="name"
          placeholder="이름을 재입력해주세요"
          required
        />
      </b-field>
      <b-field horizontal label="이메일" message="">
        <b-input v-model="userName" name="email" type="email" required />
      </b-field>
      <b-field horizontal label="번호" message="필수 입력 사항">
        <b-input
          v-model="phoneNumber"
          name="phonenumber"
          type="text"
          placeholder="번호를 재입력해주세요"
          required
        />
      </b-field>
      <b-field horizontal label="비밀번호" message="필수 입력 사항">
        <b-input
          name="password"
          type="password"
          v-model="password"
          placeholder="새로운 비밀번호를 입력하세요"
          required
        />
      </b-field>
      <hr />
      <b-field horizontal>
        <div class="control">
          <button
            type="submit"
            class="button is-primary"
            :class="{ 'is-loading': isLoading }"
            @click="updateProfileForm()"
          >
            수정완료
          </button>
        </div>
      </b-field>
    </form>
  </card-component>
</template>
<script>
import { mapState } from "vuex";
import CardComponent from "@/components/CardComponent";
import { fetchUserInfo } from "../api/auth.js";
import axios from "axios";
import { validateEmail } from "@/utils/validation";
import { validatePhoneNumber } from "@/utils/validation";
export default {
  name: "ProfileUpdateForm",
  components: {
    CardComponent
  },
  data: () => ({
    username: "",
    password: "",
    userRealname: "",
    phoneNumber: "",
    userImage: "",
    role: ""
  }),
  created() {
    fetchUserInfo()
      .then(response => {
        this.$store.state.userName = response.data.username;
        this.$store.state.userRealname = response.data.userRealname;
        this.$store.state.phoneNumber = response.data.phoneNumber;
      })
      .catch();
  },
  computed: {
    isIdValid() {
      return validateEmail(this.username); //id가 이메일 형식이 맞는지 체크
    },
    isPhoneNumberValid() {
      return validatePhoneNumber(this.phoneNumber); //phonenumber가 핸드폰 번호 형식이 맞는지 체크
    },
    ...mapState(["userName", "userRealname", "phoneNumber"])
  },
  mounted() {
    this.form.name = this.userName;
    this.form.realname = this.userRealname;
  },
  methods: {
    updateProfileForm() {
      let formData = new FormData();
      formData.append("username", this.userName);
      formData.append("password", this.password);
      formData.append("userRealname", this.userRealname);
      formData.append("phoneNumber", this.phoneNumber);
      formData.append("file", this.file);
      formData.append("role", this.role);
      axios
        .put("http://localhost:8000/user/update", formData, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization"),
            "Content-Type": "multipart/form-data",
            "Access-Control-Allow-Origin": "*"
          }
        })
        .then(response => {
          this.success();
          console.log(response.data);
          this.$router.push({ name: "Home" });
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
      this.userRealName = "";
      this.phoneNumber = "";
      this.userImage = "";
      this.role = "";
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
    created() {
      fetchUserInfo()
        .then(response => (this.$store.state.userInfo = response.data))
        .catch();
    },
    success() {
      this.$buefy.notification.open({
        message: "회원 정보가 수정되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `회원 정보 수정 실패.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  }
};
</script>
