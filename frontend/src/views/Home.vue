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
