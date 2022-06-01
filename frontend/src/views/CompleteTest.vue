<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      시험 종료 안내
      <router-link slot="right" to="/profile" class="button">
        마이 페이지
      </router-link>
    </hero-bar>
    <section>
      <CompleteTestForm></CompleteTestForm>
    </section>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import HeroBar from "@/components/HeroBar";
import CompleteTestForm from "@/components/CompleteTestForm";

export default {
  components: {
    TitleBar,
    HeroBar,
    CompleteTestForm
  },
  computed: {
    titleStack() {
      return ["학생", "시험 종료"];
    }
  }
};
</script>

<style lang="scss" scoped></style>
