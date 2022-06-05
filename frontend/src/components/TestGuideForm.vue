<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="container is-max-desktop">
        <b-message title="시험 주의사항" type="is-info" has-icon aria-close-label="Close message">
          {{ this.test.examDescribe }}
        </b-message>
        <b-notification type="is-info is-light" aria-close-label="Close notification">
          <b>
            시험을 치르기에 앞서 교수님이 설정한 공지 사항을 숙지하시기 바랍니다.<br /><br />

            컴퓨터나 랩톱에 있는 카메라가 브라우저에 잘 설정되어 있나 확인 바랍니다.<br />
            만약 되어있지 않다면 시험 응시 전에 설정하시기 바랍니다.<br /><br />

            시험 응시 시간은 시험 페이지 우측 상단에 표시되어있으니 시간 분배를 통해 늦지 않게 제출해주시기를 바랍니다.<br /><br />
          </b>
        </b-notification>

        <b-notification type="is-danger" aria-close-label="Close notification">
          <b>
            시험을 응시한 후 카메라를 통해 모니터링을 위해 응시자의 모습을 녹화하고 있습니다.<br /><br />
            영상은 시험을 위해 사용된 후 일정 기간이 지난 후 삭제됩니다.
          </b>
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
  data: function () {
    return {
      examNumber: this.$route.params.examNumber,
      userName: this.$store.state.userName,
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
        .get("http://localhost:8000/exam/get/" + this.examNumber)
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
      location.href = `http://localhost:5000/${this.userName}/${this.examNumber}`;
    }
  },
  mounted() {
    this.getTest();
  }
};
</script>
