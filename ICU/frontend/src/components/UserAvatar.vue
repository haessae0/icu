<template>
  <div class="is-user-avatar">
    <img :src="newUserAvatar" :alt="userName" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";
import { getUserIdFromSession } from "../utils/session";
import { fetchUserInfo } from "../api/auth.js";

export default {
  name: "UserAvatar",
  props: {
    avatar: {
      type: String,
      default: null
    }
  },
  data: () => ({
    loginUser: {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      userImage: this.$store.state.userImage
    }
  }),
  // this.userImage =
  created() {
    fetchUserInfo()
      .then(response => (this.$store.state.userImage = response.data.userImage))
      .catch();
  },
  computed: {
    newUserAvatar() {
      if (this.avatar) {
        return this.avatar;
      }

      if (this.userAvatar) {
        return this.userAvatar;
      }
      if (this.userName) {
        // 로그인 했을 경우../../
        return `http://localhost:8000/userimg/${this.userImage}`;
      } else {
        // 로그인 안했을 경우
        return `http://localhost:8000/userimg/default.png`;
      }
    },
    ...mapState(["userAvatar", "userName", "userRole", "imgURL", "userImage"])
  },
  methods: {
    fetch_Image() {
      const username = getUserIdFromSession();
      axios
        .get("http://localhost:8000/user/myinfo", username)
        .then(response => {
          alert("이미지 조회 성공");
          this.userImage = response.data.userImage;
          return this.userImage;
        })
        .catch(() => {
          alert("이미지 조회 실패");
        });
    }
  }
};
</script>
