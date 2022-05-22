<template>
  <div>
    <title-bar :title-stack="titleStack"/>
    <hero-bar>
      내 시험
      <router-link slot="right" class="button" to="/profile">
        마이 페이지
      </router-link>
    </hero-bar>
    <section class="section is-main-section">
      <card-component
          class="has-table has-mobile-sort-spaced"
          icon="account-multiple"
          title="시험"
      >
        <div id="app" class="container">
          <section>
            <b-table
                :data="isEmpty ? [] : studenttest"
                :hoverable="isHoverable"
            >
              <b-table-column
                  v-slot="props"
                  centered
                  field="testNum"
                  label="시험번호"
                  numeric
              >
                {{ props.row.testNum }}
              </b-table-column>
              <b-table-column
                  v-slot="props"
                  centered
                  field="testName"
                  label="시험명"
              >
                {{ props.row.testName }}
              </b-table-column>
              <b-table-column
                  v-slot="props"
                  centered
                  field="testStatus"
                  label="시험응시여부"
              >
                <b-field>
                  <span
                      v-if="props.row.testStatus === 'T'"
                      class="tag is-danger"
                      v-on:click="takeStudentTest(props.row.testNum)"
                  >
                    시험 응시 하기
                  </span>
                  <span v-else class="tag is-success">
                    시험 응시 완료
                  </span>
                </b-field>
              </b-table-column>
              <b-table-column
                  v-slot="props"
                  centered
                  field="testResult"
                  label="채점결과"
              >
                <b-field>
                  <span v-if="props.row.testResult == null" class="tag is-warn">
                    채점중
                  </span>
                  <span
                      v-else-if="(props.row.testStatus = !null)"
                      class="tag is-success"
                  >
                    {{ props.row.testResult }}
                  </span>
                </b-field>
              </b-table-column>
            </b-table>
          </section>
        </div>
      </card-component>

      <hr/>
    </section>
  </div>
</template>

<script>
import {mapState} from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import axios from "axios";

export default {
  name: "StudentTestList",
  components: {
    HeroBar,
    TitleBar,
    CardComponent
  },
  data: function () {
    return {
      userId: this.$store.state.userId,
      userRole: this.$store.state.userRole,

      studenttest: ""
    };
  },
  computed: {
    titleStack() {
      return ["시험보기", "내 시험 목록"];
    },
    ...mapState(["userId", "userRole"])
  },
  methods: {
    getStudentTest() {
      axios
          .get("http://localhost:8000/stutest/get/" + this.userId, {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          })
          .then(response => {
            this.studenttest = response.data;
            console.log("확인");
            console.log(this.studenttest);
          })
          .catch(e => {
            console.log(e);
          });
    },
    takeStudentTest(testNum) {
      //  수정버튼 클릭시 ModifyTest로 이동
      return this.$router.push({
        name: "TestGuide",
        params: {testNum: testNum}
      });
    }
  },
  mounted() {
    this.getStudentTest();
  }
};
</script>
