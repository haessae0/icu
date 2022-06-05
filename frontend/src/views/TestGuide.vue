<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section>
      <TestGuideForm></TestGuideForm>
    </section>
  </div>
</template>

<script>
import TestGuideForm from "@/components/TestGuideForm.vue";
import TitleBar from "@/components/TitleBar";

export default {
  components: {
    TestGuideForm,
    TitleBar
  },
  computed: {
    titleStack() {
      return ["시험 안내사항"];
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
