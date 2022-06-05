<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section class="section is-main-section">
      <card-component class="has-table has-mobile-sort-spaced" title="시험" icon="account-multiple">
        <div id="app" class="container">
          <section>
            <b-table :data="isEmpty ? [] : test" :hoverable="isHoverable">
              <b-table-column field="examNumber" label="시험번호" numeric centered v-slot="props">
                {{ props.row.examNumber }}
              </b-table-column>
              <b-table-column field="openTime" label="시작 시간" v-slot="props" centered>
                {{ props.row.openTime }}
              </b-table-column>
              <b-table-column field="closeTime" label="종료 시간" v-slot="props" centered>
                {{ props.row.closeTime }}
              </b-table-column>
              <b-table-column field="examDescribe" label="시험 유의사항" v-slot="props" centered>
                {{ props.row.examDescribe }}
              </b-table-column>
              <b-table-column label="수정 및 삭제" v-slot="props" centered>
                <b-button type="is-primary" outlined v-on:click="updateInstructorTest(props.row.examNumber)"
                  position="is-centered" size="is-small">수정</b-button>
                <b-button type="is-danger" outlined v-on:click="deleteInstructorTest(props.row.examNumber)"
                  position="is-centered" size="is-small">삭제</b-button>
              </b-table-column>
              <b-table-column label="응시자 관리" v-slot="props" centered>
                <b-button type="is-primary" outlined v-on:click="manageStudentList(props.row.examNumber)"
                  position="is-centered" size="is-small">관리</b-button>
              </b-table-column>
            </b-table>
          </section>
          <div class="buttons is-centered">
            <div class="mt-2 pb-4 mb-6">
              <b-button tag="router-link" to="/addtest" type="is-link" class="button is-primary is-pulled-right">시험생성
              </b-button>
            </div>
          </div>
        </div>
      </card-component>
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import axios from "axios";

export default {
  name: "InstructorTest",
  components: {
    TitleBar,
    CardComponent
  },
  data: function () {
    return {
      userName: this.$store.state.userName,
      userRole: this.$store.state.userRole,
      test: "",
    };
  },
  computed: {
    titleStack() {
      return ["시험 관리"];
    },
    ...mapState(["userName", "userRole"])
  },
  methods: {
    getInstructorTest() {
      axios
        .get("http://localhost:8000/exam/get?username=" + this.userName, {
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
    updateInstructorTest(examNumber) {
      return this.$router.push({
        name: "ModifyTest",
        params: { examNumber: examNumber }
      });
    },
    manageStudentList(examNumber) {
      return this.$router.push({
        name: "ManageStudent",
        params: { examNumber: examNumber }
      });
    },
    deleteInstructorTest(examNumber) {
      axios
        .delete(
          "http://localhost:8000/exam/delete?username=" +
          this.userName +
          "&examNumber=" +
          examNumber,
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
