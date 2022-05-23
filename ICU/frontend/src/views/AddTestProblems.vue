<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 문제 만들기
    </hero-bar>
    <section class="section is-main-section">
      <card-component title="시험 문제" icon="ballot">
        <hr />
        <b-field label="문항 번호" horizontal>
          <b-numberinput step="1" v-model="form.proNum"></b-numberinput>
        </b-field>
        <b-field
          label="문제"
          message="당신의 문제를 255자 이내로 작성하세요."
          horizontal
        >
          <b-input
            type="textarea"
            placeholder="시험 문제 만들기"
            v-model="form.proDes"
            maxlength="255"
          />
        </b-field>
        <b-field
          label="보기"
          message="당신이 내고 싶은 문항을 작성하세요."
          horizontal
        >
          <b-field label="1번" :label-position="labelPosition">
            <b-input
              placeholder="1번 보기를 입력하세요."
              name="form.proSel[]"
              v-on:input="changed"
              v-model="form.proSel[0]"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="2번" :label-position="labelPosition">
            <b-input
              placeholder="2번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[1]"
              maxlength="150"
            >
            </b-input>
          </b-field>
          <b-field label="3번" :label-position="labelPosition">
            <b-input
              placeholder="3번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[2]"
              maxlength="150"
            ></b-input>
          </b-field>
          <b-field label="4번" :label-position="labelPosition">
            <b-input
              placeholder="4번 보기를 입력하세요."
              v-on:input="changed"
              name="form.proSel[]"
              v-model="form.proSel[3]"
              maxlength="150"
            ></b-input>
          </b-field>
        </b-field>
        <b-field
          label="정답"
          name="proAnswer"
          message="당신이 낸 문항의 정답을 작성하세요."
          horizontal
        >
          <b-field label="정답" :label-position="labelPosition">
            <b-input
              v-model="form.proAnswer"
              placeholder="정답을 입력하세요."
              maxlength="150"
            ></b-input>
          </b-field>
        </b-field>
        <b-button v-on:click="testproblemForm()">문제/보기 등록</b-button>
        <hr />
        <hr />
        <card-component
          class="has-table has-mobile-sort-spaced"
          title="문제/보기 확인"
          icon="account-multiple"
        >
          <b-table :data="test">
            <b-table-column
              label="문제 번호"
              field="문제 번호"
              sortable
              v-slot="props"
            >
              {{ props.row.proNum }}
            </b-table-column>
            <b-table-column label="문제" field="문제" sortable v-slot="props">
              {{ props.row.proDes }}
            </b-table-column>
            <b-table-column
              label="보기 1번"
              field="보기 1번"
              sortable
              v-slot="props"
            >
              {{ props.row.proSel[0] }}
            </b-table-column>
            <b-table-column
              label="보기 2번"
              field="보기 2번"
              sortable
              v-slot="props"
            >
              {{ props.row.proSel[1] }}
            </b-table-column>
            <b-table-column
              label="보기 3번"
              field="보기 3번"
              sortable
              v-slot="props"
            >
              {{ props.row.proSel[2] }}
            </b-table-column>
            <b-table-column label="보기 4번" field="보기 4번" v-slot="props">
              {{ props.row.proSel[3] }}
            </b-table-column>
            <b-table-column label="답" field="보기 4번" v-slot="props">
              {{ props.row.proAnswer }}
            </b-table-column>
            <b-table-column label="삭제" v-slot="props" centered>
              <b-button
                type="is-danger"
                outlined
                v-on:click="deleteTestProblems(props.row.proId)"
                position="is-centered"
                size="is-small"
                >삭제</b-button
              >
            </b-table-column>
          </b-table>
        </card-component>
        <div style="text-align: center;">
          <b-field grouped>
            <div class="control">
              <b-button tag="router-link" to="/instructor" type="is-link">
                시험 출제
              </b-button>
            </div>
          </b-field>
        </div>
      </card-component>
    </section>
  </div>
</template>
<script>
import TitleBar from "@/components/TitleBar";
import CardComponent from "@/components/CardComponent";
import HeroBar from "@/components/HeroBar";
import axios from "axios";
export default {
  name: "AddTestProblems",
  components: {
    HeroBar,
    CardComponent,
    TitleBar
  },
  data() {
    return {
      labelPosition: "on-border",
      selectedOptions: [],
      isLoading: false,
      testNum: this.$route.params.testNum,
      test: "",
      spliList: [],
      form: {
        proNum: 1,
        proDes: "",
        proSel: [],
        proImage: "",
        proAnswer: ""
      }
    };
  },
  computed: {
    titleStack() {
      return ["강사", "시험 문제 생성"];
    }
  },
  methods: {
    testproblemForm() {
      let formData = new FormData();
      formData.append("proNum", this.form.proNum);
      formData.append("proDes", this.form.proDes);
      formData.append("proSel", this.form.proSel);
      formData.append("proImage", this.form.proImage);
      formData.append("proAnswer", this.form.proAnswer);
      axios
        .post(
          "http://localhost:8000/testpro/create/" + this.testNum,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(Headers => {
          this.success();
          console.log(Headers);
          this.getTestProblems();
        })
        .catch(error => {
          this.danger();
          console.log(error);
        })
        .finally(() => {
          this.initForm();
        });
    },
    initForm() {
      this.proNum = "";
      this.proId = "";
      this.proDes = "";
      this.proSel = "";
      this.proImage = "";
      this.proAnswer = "";
    },
    getTestProblems() {
      axios
        .get("http://localhost:8000/testpro/get?testnum=" + this.testNum, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then(response => {
          this.test = response.data;
          console.log(this.test);
          console.log("확인");
        })
        .catch(e => {
          console.log(e);
        });
    },
    deleteTestProblems(proid) {
      axios
        .delete(
          `http://localhost:8000/testpro/delete?proid=${proid}&testnum=${this.testNum}`,
          {
            headers: {
              Authorization: sessionStorage.getItem("Authorization")
            }
          }
        )
        .then(() => {
          this.delete();
          this.getTestProblems();
        })
        .catch(e => {
          this.nodelete();
          console.log(e);
        });
    },
    success() {
      this.$buefy.notification.open({
        message: "시험 문제가 생성되었습니다.",
        type: "is-success",
        position: "is-bottom-right"
      });
    },
    danger() {
      this.$buefy.notification.open({
        message: `시험 문제 만들기에 실패하였습니다.다시 시도해 주세요.`,
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
  deleteTestProblems(testNum) {
    axios
      .delete(
        "http://localhost:8000/testpro/delete?username=java@educare.com&testnum=" +
          testNum,
        {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        }
      )
      .then(() => {
        this.delete();
        this.getTestProblems();
      })
      .catch(e => {
        this.nodelete();
        console.log(e);
      });
    this.getTestProblems();
  },
  mounted() {
    this.getTestProblems();
  }
};
</script>
