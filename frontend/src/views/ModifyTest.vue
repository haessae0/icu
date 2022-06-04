<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 수정하기
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="시험" icon="ballot" @submit.prevent="submitForm">
        <b-field label="시험명" message="과목명을 적어주세요." horizontal>
          <b-input placeholder="시험명 : 소제목" v-model="form.examName" name="examName" maxlength="150" required />
        </b-field>
        <b-field label="시험 시간 지정" horizontal>
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
        </b-field>
        <b-field label="시험 유의사항" message="당신의 시험 유의사항을 255자 이내로 작성하세요." horizontal>
          <b-input type="textarea" placeholder="해당 시험 유의사항 만들기" v-model="form.examDescribe" maxlength="255" required />
        </b-field>
        <b-button class="is-primary" @click="updateTestForm()">시험 수정</b-button>
      </card-component>
      <br />
      <br />
      <card-component class="has-table has-mobile-sort-spaced" title="문제/보기 확인" icon="account-multiple">
        <section class="pl-3 pr-3">
          <b-table :data="problems">
            <b-table-column label="문제 번호" field="문제 번호" sortable v-slot="props">
              {{ props.row.quizNum }}
            </b-table-column>
            <b-table-column label="문제" field="문제" sortable v-slot="props">
              {{ props.row.quizDescribe }}
            </b-table-column>
            <b-table-column label="보기 1번" field="보기 1번" sortable v-slot="props">
              {{ props.row.quizSelection[0] }}
            </b-table-column>
            <b-table-column label="보기 2번" field="보기 2번" sortable v-slot="props">
              {{ props.row.quizSelection[1] }}
            </b-table-column>
            <b-table-column label="보기 3번" field="보기 3번" sortable v-slot="props">
              {{ props.row.quizSelection[2] }}
            </b-table-column>
            <b-table-column label="보기 4번" field="보기 4번" v-slot="props">
              {{ props.row.quizSelection[3] }}
            </b-table-column>
            <b-table-column label="답" field="보기 4번" v-slot="props">
              {{ props.row.quizAnswer }}
            </b-table-column>
            <b-table-column label="수정 및 삭제" v-slot="props" centered>
              <b-button type="is-success" outlined v-on:click="updateTestProblems(props.row.quizId)"
                position="is-centered" size="is-small">수정</b-button>
              <b-button type="is-danger" outlined v-on:click="deleteTestProblems(props.row.quizId)"
                position="is-centered" size="is-small">삭제</b-button>
            </b-table-column>
          </b-table>
        </section>
      </card-component>
      <br />
      <br />
      <card-component>
        <b-field label="문항 번호" horizontal>
          <b-numberinput step="1" v-model="form.quizNum" style="width: 200px;"></b-numberinput>
        </b-field>
        <b-field label="문제" message="당신의 문제를 255자 이내로 작성하세요." horizontal>
          <b-input type="textarea" placeholder="시험 문제 만들기" v-model="form.quizDescribe" maxlength="255" />
        </b-field>
        <b-field label="보기" message="당신이 내고 싶은 문항을 작성하세요." horizontal>
          <b-field label="1번" :label-position="labelPosition">
            <b-input placeholder="1번 보기를 입력하세요." name="form.quizSelection[]" v-on:input="changed"
              v-model="form.quizSelection[0]" maxlength="150"></b-input>
          </b-field>
          <b-field label="2번" :label-position="labelPosition">
            <b-input placeholder="2번 보기를 입력하세요." v-on:input="changed" name="form.quizSelection[]"
              v-model="form.quizSelection[1]" maxlength="150">
            </b-input>
          </b-field>
          <b-field label="3번" :label-position="labelPosition">
            <b-input placeholder="3번 보기를 입력하세요." v-on:input="changed" name="form.quizSelection[]"
              v-model="form.quizSelection[2]" maxlength="150"></b-input>
          </b-field>
          <b-field label="4번" :label-position="labelPosition">
            <b-input placeholder="4번 보기를 입력하세요." v-on:input="changed" name="form.quizSelection[]"
              v-model="form.quizSelection[3]" maxlength="150"></b-input>
          </b-field>
        </b-field>
        <b-field label="정답" name="quizAnswer" message="당신이 낸 문항의 정답을 작성하세요." horizontal>
          <b-field label="정답" :label-position="labelPosition">
            <b-input v-model="form.quizAnswer" placeholder="정답을 입력하세요." maxlength="150"></b-input>
          </b-field>
        </b-field>
        <div style="text-align: center;">
          <b-field grouped>
            <div class="control">
              <b-button @click="updateTestProblem2()">문제 수정</b-button>
            </div>
          </b-field>
        </div>
      </card-component>
      <div class="has-text-centered">
        <b-button size="is-large" tag="router-link" to="/instructor" type="is-link">
          시험 수정 완료
        </b-button>
      </div>
    </section>
  </div>
</template>
<script>
import { mapState } from "vuex";
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import HeroBar from "@/components/HeroBar";
import axios from "axios";
export default {
  components: {
    HeroBar,
    CardComponent,
    TitleBar
  },
  data() {
    return {
      test: "",
      problems: "",
      problems2: "",
      examNumber: this.$route.params.examNumber,
      userName: this.$store.state.userName,
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
        examDescribe: "",
        quizId: "",
        quizNum: "",
        quizDescribe: "",
        quizSelection: "",
        quizAnswer: ""
      }
    };
  },
  computed: {
    titleStack() {
      return ["강사", "시험 변경"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    getTestForm() {
      axios
        .get("http://localhost:8000/exam/get/" + this.examNumber, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.test = response.data;
          console.log("확인");
          console.log(this.test);
          //데이터 바인딩
          this.form.closeTime = this.test.closeTime;
          this.form.openTime = this.test.openTime;
          this.form.examName = this.test.examName;
          this.form.examDescribe = this.test.examDescribe;
        })
        .catch(e => {
          console.log(e);
          this.errors.push(e);
        });
    },
    getTestProblems() {
      axios
        .get("http://localhost:8000/quiz/get?examNumber=" + this.examNumber, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.problems = response.data;
          console.log(this.test);
          console.log("확인");
        })
        .catch(e => {
          console.log(e);
        });
    },
    updateTestForm() {
      const addTestData = {
        examNumber: this.examNumber,
        examName: this.form.examName,
        closeTime: this.form.closeTime,
        openTime: this.form.openTime,
        examDescribe: this.form.examDescribe
      };
      axios
        .put("http://localhost:8000/exam/update", addTestData, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          // this.test = response.data;
          console.log(response);
          this.update();
          this.getTestForm();
        })
        .catch(e => {
          this.noupdate();
          console.log(e);
          this.errors.push(e);
        });
    },
    deleteTestProblems(quizId) {
      axios
        .delete(
          `http://localhost:8000/quiz/delete?quizId=${quizId}&examNumber=${this.examNumber}`,
          {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(() => {
          this.success();
          this.getTestProblems();
        })
        .catch(e => {
          console.log(e);
        });
    },
    updateTestProblem2() {
      let formData = new FormData();
      formData.append("quizId", this.form.quizId);
      formData.append("quizNum", this.form.quizNum);
      formData.append("quizDescribe", this.form.quizDescribe);
      formData.append("quizSelection", this.form.quizSelection);
      formData.append("quizAnswer", this.form.quizAnswer);
      axios
        .put("http://localhost:8000/quiz/update", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.success();
          // this.test = response.data;
          console.log(response);
          this.getTestProblems();
        })
        .catch(e => {
          console.log(e);
          this.errors.push(e);
        });
    },
    updateTestProblems(quizId) {
      axios
        .get("http://localhost:8000/quiz/get/" + quizId, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.problems2 = response.data;
          console.log(this.problems2);
          console.log("확인");
          //데이터 바인딩
          this.form.quizId = this.problems2.quizId;
          this.form.quizDescribe = this.problems2.quizDescribe;
          this.form.quizNum = this.problems2.quizNum;
          this.form.quizSelection = this.problems2.quizSelection;
          this.form.quizAnswer = this.problems2.quizAnswer;
        })
        .catch(e => {
          console.log(e);
        });
    },
    update() {
      this.$buefy.notification.open({
        message: "수정이 완료되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    noupdate() {
      this.$buefy.notification.open({
        message: "수정 실패",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    success() {
      this.$buefy.notification.open({
        message: "삭제가 완료되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `삭제 실패.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  },
  mounted() {
    this.getTestForm();
    this.getTestProblems();
  }
};
</script>