<template>
  <div>
    <title-bar :title-stack="titleStack"/>
    <hero-bar>
      프로필
    </hero-bar>
    <section class="section is-main-section">
      <tiles>
        <profile-update-form class="tile is-child"/>
        <card-component class="tile is-child" icon="account" title="프로필">
          <user-avatar class="image has-max-width is-aligned-center"/>
          <hr/>
          <b-field label="이름">
            <b-input :value="name" custom-class="is-static" readonly/>
          </b-field>
          <hr/>
          <b-field label="이메일">
            <b-input :value="userId" custom-class="is-static" readonly/>
          </b-field>
        </card-component>
      </tiles>
      <password-update-form/>
    </section>
  </div>
</template>

<script>
import {mapState} from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import ProfileUpdateForm from "@/components/ProfileUpdateForm";
import Tiles from "@/components/Tiles";
import UserAvatar from "@/components/UserAvatar";
import {fetchUserInfo} from "@/api/auth";

export default {
  name: "Profile",
  components: {
    UserAvatar,
    Tiles,
    ProfileUpdateForm,
    HeroBar,
    TitleBar,
    CardComponent
  },
  data: () => ({
    loginUser: {
      userId: this.$store.state.userId,
      userRole: this.$store.state.userRole,
      profileImage: this.$store.state.profileImage,
      name: this.$store.state.name
    }
  }),
  created() {
    fetchUserInfo()
        .then(
            response =>
                (this.$store.state.name = response.data.name)
        )
        //
        .catch();
  },
  computed: {
    titleStack() {
      return ["마이페이지"];
    },
    ...mapState(["userId", "userEmail", "name"])
  }
};
</script>
