<template>
  <div class="is-user-avatar">
    <img :alt="userId" :src="newUserAvatar"/>
  </div>
</template>

<script>
import {mapState} from "vuex";
import axios from "axios";
import {getUserIdFromSession} from "../utils/session";
import {fetchUserInfo} from "../api/auth.js";

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
      userId: this.$store.state.userId,
      userRole: this.$store.state.userRole,
      profileImage: this.$store.state.profileImage
    }
  }),
  // this.userImage =
  created() {
    fetchUserInfo()
        .then(response => (this.$store.state.profileImage = response.data.profileImage))
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
      if (this.userId) {
        // 로그인 했을 경우../../
        return `http://localhost:8000/userimg/${this.profileImage}`;
      } else {
        // 로그인 안했을 경우
        return `http://localhost:8000/userimg/img.png`;
      }
    },
    ...mapState(["userAvatar", "userId", "userRole", "imgURL", "profileImage"])
  },
  methods: {
    fetch_Image() {
      const userId = getUserIdFromSession();
      axios
          .get("http://localhost:8000/user/myinfo", userId)
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
