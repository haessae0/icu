<template>
  <card-component title="비밀번호 변경" icon="lock">
    <form @submit.prevent="submit">
      <b-field
        horizontal
        label="현재 비밀번호"
        message="기존 비밀번호를 입력해주세요."
      >
        <b-input
          name="password_current"
          type="password"
          v-model="form.password_current"
          required
          autcomplete="current-password"
        />
      </b-field>
      <hr />
      <b-field
        horizontal
        label="새 비밀번호"
        message="새로운 비밀번호를 입력하세요."
      >
        <b-input
          name="password"
          type="password"
          v-model="form.password"
          required
          autocomplete="new-password"
        />
      </b-field>
      <b-field
        horizontal
        label="새 비밀번호 확인"
        message="변경할 비밀번호를 다시 입력해주세요."
      >
        <b-input
          name="password_confirmation"
          type="password"
          v-model="form.password_confirmation"
          required
          autocomplete="new-password"
        />
      </b-field>
      <hr />
      <b-field horizontal>
        <div class="control">
          <button
            type="submit"
            class="button is-primary"
            :class="{ 'is-loading': isLoading }"
          >
            수정완료
          </button>
        </div>
      </b-field>
    </form>
  </card-component>
</template>

<script>
import CardComponent from "@/components/CardComponent";

export default {
  name: "PasswordUpdateForm",
  components: {
    CardComponent
  },
  data() {
    return {
      isLoading: false,
      form: {
        password_current: null,
        password: null,
        password_confirmation: null
      }
    };
  },
  methods: {
    submit() {
      this.isLoading = true;
      setTimeout(() => {
        this.isLoading = false;
        this.$buefy.snackbar.open(
          {
            message: "Updated",
            queue: false
          },
          500
        );
      });
    }
  }
};
</script>
