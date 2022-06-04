<template>
  <div>
    <title-bar :title-stack="titleStack" />
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

export default {
  name: "Service",
  components: {
  }
};
</script>
