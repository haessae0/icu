<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      프로필
    </hero-bar>
    <section class="section is-main-section">
      <tiles>
        <profile-update-form class="tile is-child" />
        <card-component title="프로필" icon="account" class="tile is-child">
          <hr />
          <b-field label="이름">
            <b-input :value="fullname" custom-class="is-static" readonly />
          </b-field>
          <hr />
          <b-field label="이메일">
            <b-input :value="userName" custom-class="is-static" readonly />
          </b-field>
        </card-component>
      </tiles>
      <password-update-form />
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import ProfileUpdateForm from "@/components/ProfileUpdateForm";
import Tiles from "@/components/Tiles";
import { fetchUserInfo } from "../api/auth.js";

export default {
  name: "Profile",
  components: {
    Tiles,
    ProfileUpdateForm,
    HeroBar,
    TitleBar,
    CardComponent
  },
  data: () => ({
    loginUser: {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      fullname: this.$store.state.fullname
    }
  }),
  created() {
    fetchUserInfo()
      .then(
        response =>
          (this.$store.state.fullname = response.data.fullname)
      )
      //
      .catch();
  },
  computed: {
    titleStack() {
      return ["마이페이지"];
    },
    ...mapState(["userName", "userEmail", "fullname"])
  }
};
</script>
