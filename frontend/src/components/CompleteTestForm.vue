<template>
  <div id="app" class="container is-max-desktop pt-5">
    <section>
      <div class="container is-max-desktop">
        <b-message
          title="시험을 완료하셨습니다."
          type="is-success"
          has-icon
          aria-close-label="Close message"
        >
          수고하셨습니다. 건승을 빌어요!
        </b-message>
        <center>
          <b-button
            tag="router-link"
            to="/profile"
            type="is-link"
            class="button is-primary is-pulled-center mb-6"
            >마이 페이지로 돌아가기</b-button
          >
        </center>
      </div>
    </section>
  </div>
</template>
<script>
export default {};
</script>

<style>
.btn {
  color: rgb(51, 91, 221);
}
</style>
