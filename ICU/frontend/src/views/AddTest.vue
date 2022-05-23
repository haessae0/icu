<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="section is-main-section">
      <card-component title="시험" icon="ballot">
        <b-field label="시험명" message="과목명을 적어주세요." horizontal>
          <b-input
            placeholder="시험명 : 소제목"
            v-model="form.testName"
            name="testName"
            maxlength="150"
            required
          />
        </b-field>
        <b-field label="시험 시간 지정" horizontal>
          <b-field
            :label-position="labelPosition"
            message="시험 시작 시간 지정"
          >
            <b-datetimepicker
              rounded
              label="시험 시작 시간"
              icon="calendar-today"
              v-model="form.startTime"
              :localISOdt="localISOdt"
              :datepicker="{ showWeekNumber }"
              :timepicker="{ enableSeconds, hourFormat }"
              horizontal-time-picker
            >
            </b-datetimepicker>
          </b-field>
          <b-field
            :label-position="labelPosition"
            name="endTime"
            message="시험 종료 시간 지정"
          >
            <b-datetimepicker
              rounded
              icon="calendar-today"
              v-model="form.endTime"
              :localISOdt="localISOdt"
              :datepicker="{ showWeekNumber }"
              :timepicker="{ enableSeconds, hourFormat }"
              horizontal-time-picker
            >
            </b-datetimepicker>
          </b-field>
        </b-field>
        <b-field
          label="시험 유의사항"
          message="당신의 시험 유의사항을 255자 이내로 작성하세요."
          horizontal
        >
          <b-input
            type="textarea"
            placeholder="해당 시험 유의사항 만들기"
            v-model="form.testGuide"
            maxlength="255"
            required
          />
        </b-field>
        <div class="has-text-centered">
          <b-button size="is-large is-primary" v-on:click="testForm()"
            >시험 만들기</b-button
          >
        </div>
        <hr />
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
        testName: "",
        endTime: "",
        startTime: "",
        testGuide: ""
      },
      testNum: "",
      departments: ["JAVA", "SPRINGBOOT", "VUE", "SQL"]
    };
  },
  computed: {
    titleStack() {
      return ["강사", "시험 생성"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    testForm() {
      const addTestData = {
        testName: this.form.testName,
        endTime: this.form.endTime,
        startTime: this.form.startTime,
        testGuide: this.form.testGuide
      };
      axios
        .post(
          "http://localhost:8000/test/create?username=" + this.userName,
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
          this.testNum = response.data;
          this.$router.push({
            name: "AddTestProblems",
            params: { testNum: this.testNum }
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
        proId: this.proId,
        proNum: this.proNum,
        proDes: this.proDes,
        proSel: this.proSel,
        proImage: this.proImage,
        proAnswer: this.proAnswer
      };
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      axios
        .post(
          "http://localhost:8000/testpro/create/" + this.testService,
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
    this.proId = "";
    this.pronum = "";
    this.proDes = "";
    this.proSel = "";
    this.proImage = "";
    this.proAnswer = "";
  }
};
</script>
