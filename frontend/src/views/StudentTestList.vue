<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="section is-main-section">
      <card-component class="has-table has-mobile-sort-spaced" title="시험" icon="account-multiple">
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : quizlist" :hoverable="isHoverable">
              <b-table-column field="examNumber" label="시험번호" numeric centered v-slot="props">
                {{ props.row.examNumber }}
              </b-table-column>
              <b-table-column field="examName" label="시험명" v-slot="props" centered>
                {{ props.row.examName }}
              </b-table-column>
              <b-table-column field="status" label="시험응시여부" v-slot="props" centered>
                <b-field>
                  <span v-if="props.row.status == 'True'" class="tag is-danger"
                    v-on:click="takeQuizForStudent(props.row.examNumber)">
                    시험 응시 하기
                  </span>
                  <span v-else class="tag is-success">
                    시험 응시 완료
                  </span>
                </b-field>
              </b-table-column>
              <b-table-column field="quizResult" label="채점결과" v-slot="props" centered>
                <b-field>
                  <span v-if="props.row.quizResult == null" class="tag is-warn">
                    채점중
                  </span>
                  <span v-else-if="(props.row.status = !null)" class="tag is-success">
                    {{ props.row.quizResult }}
                  </span>
                </b-field>
              </b-table-column>
            </b-table>
          </section>
        </div>
      </card-component>

      <hr />
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import axios from "axios";

export default {
  name: "StudentTestList",
  components: {
    TitleBar,
    CardComponent
  },
  data: function () {
    return {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,

      quizlist: ""
    };
  },
  computed: {
    titleStack() {
      return ["시험 목록"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    getQuizForStudent() {
      axios
        .get("http://localhost:8000/quizforstudent/get/" + this.userName, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.quizlist = response.data;
          console.log("확인");
          console.log(this.quizlist);
        })
        .catch(e => {
          console.log(e);
        });
    },
    takeQuizForStudent(examNumber) {
      return this.$router.push({
        name: "TestGuide",
        params: { examNumber: examNumber }
      });
    }
  },
  mounted() {
    this.getQuizForStudent();
  }
};
</script>
