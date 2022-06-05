<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="section is-main-section">
      <card-component title="프로필" icon="account" class="tile is-child">
        <b-field label="이름">
          <b-input :value="fullname" custom-class="is-static" readonly />
        </b-field>
        <hr />
        <b-field label="이메일">
          <b-input :value="userName" custom-class="is-static" readonly />
        </b-field>
        <hr />
        <b-field label="전화 번호">
          <b-input :value="phoneNumber" custom-class="is-static" readonly />
        </b-field>
        <hr />
        <b-field label="직위">
          <b-input :value="userRole" custom-class="is-static" readonly />
        </b-field>
      </card-component>
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import { fetchUserInfo } from "../api/auth.js";

export default {
  name: "Profile",
  components: {
    TitleBar,
    CardComponent
  },
  data: () => ({
    loginUser: {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      fullname: this.$store.state.fullname,
      phoneNumber: this.$store.state.phoneNumber,
    }
  }),
  created() {
    fetchUserInfo()
      .then(
        response =>
          (this.$store.state.fullname = response.data.fullname)
      )
      .catch();
  },
  computed: {
    titleStack() {
      return ["프로필"];
    },
    ...mapState(["userName", "userEmail", "fullname", "phoneNumber", "userRole"])
  }
};
</script>
