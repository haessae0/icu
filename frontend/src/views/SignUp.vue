<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section>
      <SignUpForm></SignUpForm>
    </section>
  </div>
</template>

<script>
import SignUpForm from "@/components/SignUpForm.vue";
import TitleBar from "@/components/TitleBar";

export default {
  components: {
    SignUpForm,
    TitleBar,
  },
  computed: {
    titleStack() {
      return ["회원가입"];
    }
  }
};
</script>
