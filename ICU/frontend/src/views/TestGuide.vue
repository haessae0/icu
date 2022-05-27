<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 안내사항
    </hero-bar>
    <section>
      <TestGuideForm></TestGuideForm>
    </section>
  </div>
</template>

<script>
import TestGuideForm from "@/components/TestGuideForm.vue";
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";

export default {
  components: {
    TestGuideForm,
    TitleBar,
    HeroBar
  },
  computed: {
    titleStack() {
      return ["학생", "안내사항"];
    }
  }
};
</script>

<style lang="scss" scoped></style>
