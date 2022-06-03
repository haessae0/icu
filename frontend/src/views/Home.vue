<template>
  <div>
    <title-bar :title-stack="titleStack" />
    <hero-bar>
      홈
    </hero-bar>
    <section class="hero is-white is-fullheight">
      <div class="hero-body">
        <div class="container">
          <div class="columns  is-vcentered reverse-columns">
            <div class="column
          is-10-mobile is-offset-1-mobile
          is-10-tablet is-offset-1-tablet
          is-5-desktop is-offset-1-desktop
          is-5-widescreen is-offset-1-widescreen
          is-5-fullhd is-offset-1-fullhd">
              <figure class="image is-square">
                <img src="../assets/logo.png" />
              </figure>
              <b-carousel>
                <b-carousel-item v-for="(carousel, i) in carousels" :key="i">
                  <section :class="`hero is-medium is-${carousel.color}`">
                    <div class="hero-body has-text-centered">
                      <h1 class="title">{{carousel.text}}</h1>
                    </div>
                  </section>
                </b-carousel-item>
              </b-carousel>
            </div>
            <div class="column
          is-10-mobile is-offset-1-mobile
          is-10-tablet is-offset-1-tablet
          is-4-desktop is-offset-1-desktop
          is-4-widescreen is-offset-1-widescreen
          is-4-fullhd is-offset-1-fullhd">
              <h1 class="title titled is-1 mb-6">
                ICU <br />
              </h1>
              <h2 class=" subtitled subtitle has-text-grey is-4 has-text-weight-normal is-family-sans-serif">
                더 쉽게 시험을 감독 하세요<br />
              </h2>
              <div class="buttons">
                <b-button class="button is-danger" tag="router-link" to="/profile" type="is-link">
                  내 프로필
                </b-button>
                <b-button class="button is-info" tag="router-link" to="/" type="is-link">
                  서비스
                </b-button>
              </div>
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
  },
  data() {
    return {
      carousels: [
        { text: 'Slide 1', color: 'primary' },
        { text: 'Slide 2', color: 'info' },
        { text: 'Slide 3', color: 'success' },
        { text: 'Slide 4', color: 'warning' },
        { text: 'Slide 5', color: 'danger' }
      ]
    }
  }
};
</script>
