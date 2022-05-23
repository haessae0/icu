<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      응시자 관리
    </hero-bar>
    <section class="section is-main-section">
      <card-component
        class="has-table has-mobile-sort-spaced"
        title="현 시험 응시자"
        icon="account-multiple"
      >
        <div id="app" class="container">
          <section>
            <b-table :data="applicants" :hoverable="isHoverable">
              <b-table-column
                field="username"
                label="학생ID"
                v-slot="props"
                centered
              >
                {{ props.row.username }}
              </b-table-column>
              <b-table-column
                field="userRealName"
                label="응시자"
                v-slot="props"
                centered
              >
                {{ props.row.userRealName }}
              </b-table-column>
              <b-table-column
                field="testResult"
                label="점수"
                v-slot="props"
                centered
              >
                {{ props.row.testResult }}
              </b-table-column>
              <b-table-column label="감독" v-slot="props" centered>
                <b-button
                  type="is-primary"
                  outlined
                  v-on:click="manageStudentVideo(props.row.username)"
                  position="is-centered"
                  size="is-small"
                  >감독하기</b-button
                >
              </b-table-column>
              <b-table-column label="삭제" v-slot="props" centered>
                <b-button
                  type="is-danger"
                  outlined
                  v-on:click="deleteApplicant(props.row.username)"
                  position="is-centered"
                  size="is-small"
                  >삭제</b-button
                >
              </b-table-column>
            </b-table>
          </section>
        </div>
      </card-component>

      <hr />
      <notification class="is-info">
        <div>
          <b-icon icon="buffer" custom-size="default" />
          <b>응시자들을</b> 추가해보세요.
        </div>
      </notification>
      <card-coponent
        v-model="isCardModalActive"
        class="has-table has-mobile-sort-spaced"
        icon="account-multiple"
      >
        <b-table :data="students" :hoverable="isHoverable">
          <b-table-column
            field="username"
            label="학생ID"
            v-slot="props"
            centered
          >
            {{ props.row.username }}
          </b-table-column>
          <b-table-column
            field="userRealname"
            label="응시자"
            v-slot="props"
            centered
          >
            {{ props.row.userRealname }}
          </b-table-column>

          <b-table-column label="추가" v-slot="props" centered>
            <b-button
              type="is-primary"
              outlined
              v-on:click="addStudent(props.row.username)"
              position="is-centered"
              size="is-small"
              >추가하기</b-button
            >
          </b-table-column>
        </b-table>
      </card-coponent>
      <hr />
    </section>
  </div>
</template>

<script>
import Notification from "@/components/Notification";
import CardComponent from "@/components/CardComponent";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import axios from "axios";

export default {
  name: "ManageStudent",
  components: {
    HeroBar,
    TitleBar,
    CardComponent,
    Notification
  },
  data() {
    return {
      isCardModalActive: false,
      applicants: "",
      students: "",
      testNum: this.$route.params.testNum
    };
  },
  computed: {
    titleStack() {
      return ["강사", "응시자 관리"];
    }
  },
  methods: {
    //특정 시험에 할당된 응시자 검색 로직
    getAllApplicants() {
      axios
        .get("http://localhost:8000/stutest/getstu/" + this.testNum, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.applicants = response.data;
          console.log("응시자 확인");
          console.log(this.applicants);
        })
        .catch(e => {
          console.log(e);
        });
    },
    getAllStudents() {
      axios
        .get("http://localhost:8000/user/notest/" + this.testNum, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.students = response.data;
          console.log("총학생 확인");
          console.log(this.students);
        })
        .catch(e => {
          console.log(e);
        });
    },
    addStudent(userName) {
      let instance = axios.create();
      instance.defaults.headers.common[
        "Authorization"
      ] = sessionStorage.getItem("Authorization");
      instance
        .post(
          "http://localhost:8000/stutest/insert/" +
            userName +
            "/" +
            this.testNum
        )
        .then(response => {
          console.log(response);
          this.success();
          this.getAllApplicants();
          this.getAllStudents();
        })
        .catch(e => {
          this.danger();
          this.console.log(e);
        });
    },
    deleteApplicant(userName) {
      axios
        .delete(
          "http://localhost:8000/stutest/delete/" +
            userName +
            "/" +
            this.testNum,
          {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(() => {
          this.delete();
          this.getAllApplicants();
          this.getAllStudents();
        })
        .catch(e => {
          this.nodelete();
          console.log(e);
        });
      this.getAllApplicants();
    },
    manageStudentVideo(userName) {
      return this.$router.push({
        name: "TestSupervision",
        params: { testNum: this.testNum, userName: userName }
      });
    },
    success() {
      this.$buefy.notification.open({
        message: "학생이 등록되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `학생 등록을 다시 시도해 주세요.`,
        type: "is-danger",
        position: "is-bottom-right"
      });
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
    this.getAllApplicants();
    this.getAllStudents();
  }
};
</script>
