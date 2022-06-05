<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="pt-3 pl-3 pr-3 pb-3">
      <div class="tile is-ancestor">

        <div class="tile is-child box">
          <p class="title is-5" style="text-align:center">시험 응시자 : {{ userName }} 시험감독</p>
          <hr />
          <h6 class=" title is-6">의심 행위 감지 횟수 : {{ cheatingTime }}</h6>
          <h6 class="title is-6">의심 행동 발견 : {{ lier }} </h6>
          <h6 class="title is-6">시험 응시자 답안</h6>
          <div class="pb-2" v-for="(value, index) in studentAnswer" :key="value.key">
            {{ index + 1 }}번 : {{ value }} 번
          </div>
          <b-field label="시험 점수를 입력하세요. 부정행위시 0점">
            <b-input v-model="quizResult" placeholder="0"></b-input>
            <p class="control">
              <b-button type="is-primary" outlined @click="updateStudentScore" label="시험 점수 입력" />
            </p>
          </b-field>
          <div class="has-text-centered">
            <b-button tag="router-link" to="/instructor" type="is-link">
              시험 감독 완료
            </b-button>
          </div>
        </div>
      </div>
      <div class="tile is-parent">
        <div class="tile is-child box">
          <p class="title is-5" style="text-align:center">영상 확인</p>
          <video width="720" height="640" controls :src="videoName"></video>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import axios from "axios";
export default {
  components: {
    TitleBar
  },
  data() {
    return {
      examNumber: this.$route.params.examNumber,
      userName: this.$route.params.userName,
      quizResult: "",
      studentTest: "",
      cheatingTime: "",
      lier: "",
      studentAnswer: [],
      fullname: "",
      videoName: ""
    };
  },
  computed: {
    titleStack() {
      return ["학생 시험 감독"];
    },
    player() {
      return this.$refs.videoPlayer.player;
    }
  },
  methods: {
    //video 정보 가져오기
    getQuizForStudent() {
      axios
        .get(
          "http://localhost:8000/quizforstudent/get/" +
          this.userName +
          "/" +
          this.examNumber,
          {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(response => {
          this.studentTest = response.data;
          console.log("확인");
          console.log(this.studentTest);
          this.cheatingTime = this.studentTest.cheatingTime;
          this.lier = this.studentTest.lier;
          this.studentAnswer = this.studentTest.studentAnswer;
          console.log(this.studentAnswer);
          this.fullname = this.studentTest.fullname;
          this.videoName =
            "http://localhost:8000/uservideo/" + this.studentTest.videoName;
          console.log(this.videoName);
        })
        .catch(e => {
          console.log(e);
        });
    },
    updateStudentScore() {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .put(
          "http://localhost:8000/quizforstudent/update-score/" +
          this.userName +
          "/" +
          this.examNumber +
          "/" +
          this.quizResult
        )
        .then(response => {
          this.success();
          console.log(response);
        })
        .catch(e => {
          this.danger();
          console.log(e);
        });
    },
    success() {
      this.$buefy.notification.open({
        message: "점수가 등록되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `점수 등록을 다시 시도해 주세요.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  },
  mounted() {
    this.getQuizForStudent();
  }
};
</script>
