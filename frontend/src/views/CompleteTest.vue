<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <section>
      <CompleteTestForm></CompleteTestForm>
    </section>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import CompleteTestForm from "@/components/CompleteTestForm";

export default {
  components: {
    TitleBar,
    CompleteTestForm
  },
  computed: {
    titleStack() {
      return ["시험 종료"];
    }
  }
};
</script>

<style lang="scss" scoped></style>
