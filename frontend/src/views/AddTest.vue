<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="section is-main-section">
      <card-component title="시험 만들기" icon="ballot">
        <b-input placeholder="시험 과목명" v-model="form.examName" name="examName" maxlength="150" required />
        <b-input type="textarea" placeholder="시험에 필요한 규칙 또는 설명을 작성해주시오." v-model="form.examDescribe" maxlength="255"
          required />
        <b-field :label-position="labelPosition" message="시험 시작 시간 지정">
          <b-datetimepicker rounded label="시험 시작 시간" icon="calendar-today" v-model="form.openTime"
            :localISOdt="localISOdt" :datepicker="{ showWeekNumber }" :timepicker="{ enableSeconds, hourFormat }"
            horizontal-time-picker>
          </b-datetimepicker>
        </b-field>
        <b-field :label-position="labelPosition" name="closeTime" message="시험 종료 시간 지정">
          <b-datetimepicker rounded icon="calendar-today" v-model="form.closeTime" :localISOdt="localISOdt"
            :datepicker="{ showWeekNumber }" :timepicker="{ enableSeconds, hourFormat }" horizontal-time-picker>
          </b-datetimepicker>
        </b-field>

        <div class="has-text-centered">
          <b-button size="is-large is-primary" v-on:click="testForm()">시험 만들기</b-button>
        </div>

      </card-component>
    </section>
  </div>
</template>
<script>
import { mapState } from "vuex";
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import axios from "axios";

export default {
  name: "AddTest",
  components: {
    CardComponent,
    TitleBar
  },
  data() {
    return {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      showWeekNumber: false,
      enableSeconds: true,
      hourFormat: undefined, // Browser locale
      locale: undefined, // Browser locale
      testService: "",
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      form: {
        examName: "",
        closeTime: "",
        openTime: "",
        examDescribe: ""
      },
      examNumber: "",
      departments: ["JAVA", "SPRINGBOOT", "VUE", "SQL"]
    };
  },
  computed: {
    titleStack() {
      return ["시험 관리"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    testForm() {
      const addTestData = {
        examName: this.form.examName,
        closeTime: this.form.closeTime,
        openTime: this.form.openTime,
        examDescribe: this.form.examDescribe
      };
      axios
        .post(
          "http://localhost:8000/exam/create?username=" + this.userName,
          addTestData,
          {
            headers: {
              contentType: false,
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(response => {
          this.success();
          console.log(response.data);
          this.examNumber = response.data;
          this.$router.push({
            name: "AddTestProblems",
            params: { examNumber: this.examNumber }
          });
        })
        .catch(error => {
          this.danger();
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    testproblemForm() {
      const addtestproblemData = {
        quizId: this.quizId,
        quizNum: this.quizNum,
        quizDescribe: this.quizDescribe,
        quizSelection: this.quizSelection,
        quizAnswer: this.quizAnswer
      };
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      axios
        .post(
          "http://localhost:8000/quiz/create/" + this.testService,
          addtestproblemData
        )
        .then(Headers => {
          this.success();
          console.log(Headers);
          this.$router.push({ name: "InstructorTest" });
        })
        .catch(error => {
          this.danger();
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    success() {
      this.$buefy.notification.open({
        message: "시험 작성이 완료되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `시험 작성 내용을 정확히 입력해주세요.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  },
  initForm() {
    this.quizId = "";
    this.quizNum = "";
    this.quizDescribe = "";
    this.quizSelection = "";
    this.quizAnswer = "";
  }
};
</script>
