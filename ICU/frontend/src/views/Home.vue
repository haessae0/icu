<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      서비스 소개
    </hero-bar>
    <section class="hero is-white is-fullheight">
      <div class="hero-body">
        <div class="container">
          <div class="columns  is-vcentered reverse-columns">
            <div
              class="column
          is-10-mobile is-offset-1-mobile
          is-10-tablet is-offset-1-tablet
          is-5-desktop is-offset-1-desktop
          is-5-widescreen is-offset-1-widescreen
          is-5-fullhd is-offset-1-fullhd"
            >
              <h1 class="title titled is-1 mb-6">
                당신의 시험 <br />
                에듀케어와 함께 하세요.
              </h1>
              <h2
                class=" subtitled subtitle has-text-grey is-4 has-text-weight-normal is-family-sans-serif"
              >
                오늘 시험 보시나요? <br />
                지금 여기, 에듀케어에서 실현시켜 드립니다.<br />
              </h2>
              <div class="buttons">
                <b-button
                  class="button is-black"
                  tag="router-link"
                  to="/profile"
                  type="is-link"
                >
                  내 프로필
                </b-button>
                <b-button
                  class="button is-light"
                  tag="router-link"
                  to="/"
                  type="is-link"
                >
                  서비스
                </b-button>
              </div>
            </div>
            <div
              class="column
          is-10-mobile is-offset-1-mobile
          is-10-tablet is-offset-1-tablet
          is-4-desktop is-offset-1-desktop
          is-4-widescreen is-offset-1-widescreen
          is-4-fullhd is-offset-1-fullhd"
            >
              <figure class="image is-square">
                <img src="../assets/logo.png" />
              </figure>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="hero is-medium has-text-centered">
      <div class="hero-body">
        <div class="container">
          <div class="columns is-centered">
            <div class="column is-8">
              <figure class="image is-medium">
                <img src="../assets/main.png" />
              </figure>
              <h1 class="title titled 40by40">
                학생들과 강사들을 위한 서비스, '에듀케어'
              </h1>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import HeroBar from "@/components/HeroBar";

export default {
  name: "Service",
  components: {
    HeroBar
  }
};
</script>
