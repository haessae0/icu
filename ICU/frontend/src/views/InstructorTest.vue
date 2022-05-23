<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 관리
    </hero-bar>
    <section class="section is-main-section">
      <card-component
        class="has-table has-mobile-sort-spaced"
        title="시험"
        icon="account-multiple"
      >
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : test" :hoverable="isHoverable">
              <b-table-column
                field="testNum"
                label="시험번호"
                numeric
                centered
                v-slot="props"
              >
                {{ props.row.testNum }}
              </b-table-column>

              <b-table-column
                field="startTime"
                label="시작 시간"
                v-slot="props"
                centered
              >
                {{ props.row.startTime }}
              </b-table-column>

              <b-table-column
                field="endTime"
                label="종료 시간"
                v-slot="props"
                centered
              >
                {{ props.row.endTime }}
              </b-table-column>

              <b-table-column
                field="testGuide"
                label="시험 유의사항"
                v-slot="props"
                centered
              >
                {{ props.row.testGuide }}
              </b-table-column>
              <b-table-column label="수정 및 삭제" v-slot="props" centered>
                <b-button
                  type="is-primary"
                  outlined
                  v-on:click="updateInstructorTest(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >수정</b-button
                >
                <b-button
                  type="is-danger"
                  outlined
                  v-on:click="deleteInstructorTest(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >삭제</b-button
                >
              </b-table-column>
              <b-table-column label="응시자 관리" v-slot="props" centered>
                <b-button
                  type="is-primary"
                  outlined
                  v-on:click="manageStudentList(props.row.testNum)"
                  position="is-centered"
                  size="is-small"
                  >관리</b-button
                >
              </b-table-column>
            </b-table>
          </section>
          <div class="mt-2 pb-4 mb-6">
            <b-button
              tag="router-link"
              to="/addtest"
              type="is-link"
              class="button is-primary is-pulled-right"
              >시험생성</b-button
            >
          </div>
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
import HeroBar from "@/components/HeroBar";
import axios from "axios";

export default {
  name: "InstructorTest",
  components: {
    HeroBar,
    TitleBar,
    CardComponent
  },
  data: function() {
    return {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      test: ""
    };
  },
  computed: {
    titleStack() {
      return ["강사", "시험 목록 관리"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    getInstructorTest() {
      axios
        .get("http://localhost:8000/test/get?username=" + this.userName, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.test = response.data;
          console.log(this.test);
        })
        .catch(e => {
          console.log(e);
        });
    },
    updateInstructorTest(testNum) {
      //  수정버튼 클릭시 ModifyTest로 이동
      return this.$router.push({
        name: "ModifyTest",
        params: { testNum: testNum }
      });
    },
    manageStudentList(testNum) {
      //  수정버튼 클릭시 ModifyTest로 이동
      return this.$router.push({
        name: "ManageStudent",
        params: { testNum: testNum }
      });
    },
    deleteInstructorTest(testNum) {
      axios
        .delete(
          "http://localhost:8000/test/delete?username=" +
            this.userName +
            "&testnum=" +
            testNum,
          {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(() => {
          this.delete();
          this.getInstructorTest();
        })
        .catch(e => {
          this.nodelete();
          console.log(e);
        });
      this.getInstructorTest();
    },
    delete() {
      this.$buefy.notification.open({
        message: `성공적으로 삭제되었습니다.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    },
    nodelete() {
      this.$buefy.notification.open({
        message: `삭제가 되지 않았습니다. 다시 삭제해 주세요`,
        type: "is-danger",
        position: "is-bottom-right"
      });
    }
  },
  mounted() {
    this.getInstructorTest();
  }
};
</script>
