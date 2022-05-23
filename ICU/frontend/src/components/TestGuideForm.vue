<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="container is-max-desktop">
        <b-message
          title="시험 주의사항"
          type="is-success"
          has-icon
          aria-close-label="Close message"
        >
          {{ this.test.testGuide }}
        </b-message>
        <b-notification
          type="is-success is-light"
          aria-close-label="Close notification"
        >
          <b>
            시험 시작 전, 시험 외 다른 창이 열려 있을 경우 모두 로그아웃 후 닫아
            주시기 바랍니다. PC 사용 및 Chrome 브라우져 응시를 권장드립니다.</b
          >
        </b-notification>
        <b-notification
          type="is-success is-light"
          aria-close-label="Close notification"
        >
          <b>
            시험응시 중에는 브라우저 내 메뉴를 누르거나 다른 창으로 이동할 경우
            오류가 발생할 수 있으니 유의하여 주시기 바랍니다.</b
          >
        </b-notification>
        <b-notification
          type="is-success is-light"
          aria-close-label="Close notification"
        >
          <b>
            교수/강사님의 공지사항을 참고하시어, 시험시간이 되면 시험시작 버튼을
            클릭합니다.시험 응시시작은 ‘바로 퀴즈에 응시’ 버튼을 누르는 시점부터
            시작합니다.</b
          >
        </b-notification>

        <b-notification
          type="is-success is-light"
          aria-close-label="Close notification"
        >
          <b
            >오른쪽 상단에 시간이 얼마나 남았는지 확인하실 수 있습니다. 적절한
            시간분배는 필수!</b
          >
        </b-notification>
        <center>
          <!-- 이 시험시작 버튼은 flask 화면 단으로 이동해야 함 -->
          <b-button class="is-primary" size="is-large" @click="sendInfo()">
            시험 시작
          </b-button>
        </center>
      </div>
    </section>
  </div>
</template>
<script>
import axios from "axios";
import { setCookie } from "../utils/cookies";

export default {
  data: function() {
    return {
      testNum: this.$route.params.testNum,
      username: this.$store.state.username,
      test: ""
    };
  },
  computed: {
    titleStack() {
      return ["Student", "TestGuide"];
    }
  },
  methods: {
    getTest() {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .get("http://localhost:8000/test/get/" + this.testNum)
        .then(response => {
          this.test = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    // flask로 username과 testnum 정보를 전송
    sendInfo() {
      setCookie("Authorization", sessionStorage.getItem("Authorization"), 1);
      location.href = `http://localhost:5000/${this.username}/${this.testNum}`;
    }
  },
  mounted() {
    this.getTest();
  }
};
</script>
