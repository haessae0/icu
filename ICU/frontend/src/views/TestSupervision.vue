/* eslint-disable vue/no-parsing-error */ /* eslint-disable vue/no-parsing-error
*/
<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 감독
    </hero-bar>
    <section class="pt-3 pl-3 pr-3 pb-3">
      <div class="tile is-ancestor">
        <div class="tile is-4 is-vertical is-parent">
          <div class="tile is-child box">
            <p class="title is-5">시험감독</p>
            <p>
              응시자의 녹화 영상을 확인하세요.
            </p>
            <h6 class="title is-6">부정행위 감지 시간</h6>
            <h6 class="subtitle is-6 pb-2">CheatTime:{{ cheatTime }}</h6>
            <h6 class="title is-6">응시자 답변</h6>
            <div
              class="pb-2"
              v-for="(value, index) in testAnswer"
              :key="value.key"
            >
              {{ index + 1 }}번: {{ value }}
            </div>
            <h6 class="title is-6">부정행위 유무</h6>
            <h6 class="subtitle is-6 pb-2">isCheating: {{ isCheating }}</h6>
            <h6 class="title is-6">응시자</h6>
            <h6 class="subtitle is-6 pb-2">{{ userName }}</h6>
            <!-- <br />
            videoname: {{ videoName }} -->
            <b-field label="시험 점수를 입력하세요. 부정행위시 0점">
              <b-field>
                <b-input v-model="testResult" placeholder="0"></b-input>
                <p class="control">
                  <b-button
                    type="is-primary"
                    outlined
                    @click="updateStudentScore"
                    label="시험 점수 입력"
                  />
                </p>
              </b-field>
            </b-field>
            <b-field grouped>
              <div class="control">
                <b-button tag="router-link" to="/instructor" type="is-link">
                  시험 감독 완료
                </b-button>
              </div>
            </b-field>
          </div>
        </div>

        <!-- 아래부터 영상이 렌더링 되는 코드 옵션은 안 건들어도 됩니다.-->
        <div class="tile is-parent">
          <div class="tile is-child box">
            <p class="title is-5">영상 확인</p>
            <video width="720" height="640" controls :src="videoName"></video>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import axios from "axios";
export default {
  components: {
    TitleBar,
    HeroBar
  },
  data() {
    return {
      testNum: this.$route.params.testNum,
      userName: this.$route.params.userName,
      testResult: "",
      studentTest: "",
      cheatTime: "",
      isCheating: "",
      testAnswer: [],
      userRealName: "",
      videoName: ""
    };
  },
  computed: {
    titleStack() {
      return ["강사", "응시자 시험 감독"];
    },
    player() {
      return this.$refs.videoPlayer.player;
    }
  },
  methods: {
    //video 정보 가져오기
    getStudentTest() {
      axios
        .get(
          "http://localhost:8000/stutest/get/" +
            this.userName +
            "/" +
            this.testNum,
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
          this.cheatTime = this.studentTest.cheatTime;
          this.isCheating = this.studentTest.isCheating;
          this.testAnswer = this.studentTest.testAnswer;
          console.log(this.testAnswer);
          this.userRealName = this.studentTest.userRealName;
          this.videoName =
            "http://localhost:8000/tproblemvideo/" + this.studentTest.videoName;
          //이거 확인해보세요 비디오 이름 잘 가지고 오는지
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
          "http://localhost:8000/stutest/update-score/" +
            this.userName +
            "/" +
            this.testNum +
            "/" +
            this.testResult
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
    this.getStudentTest();
  }
};
</script>
