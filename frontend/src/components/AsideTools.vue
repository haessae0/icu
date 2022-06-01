<template>
  <div class="aside-tools">
    <div class="aside-tools-label">
      <b-icon v-if="icon" :icon="icon" custom-size="default" />
      <img src="../assets/logo2.jpg" width="30" height="28" />
      <slot name="label" />
    </div>
  </div>
</template>

<script>
export default {
  name: "AsideTools",
  props: {
    icon: {
      type: String,
      default: null
    },
    label: {
      type: String,
      default: null
    }
  }
};
</script>
