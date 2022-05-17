<template>
  <div id="app">
    <nav-bar/>
    <template v-if="userRole === 'ROLE_STUDENT'">
      <aside-menu :menu="student_menu" @menu-click="menuClick"/>
    </template>
    <template v-else-if="userRole === 'ROLE_INSTRUCTOR'">
      <aside-menu :menu="instructor_menu" @menu-click="menuClick"/>
    </template>
    <template v-else>
      <aside-menu :menu="menu" @menu-click="menuClick"/>
    </template>
    <transition name="page">
      <router-view></router-view>
    </transition>
    <footer-bar/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
