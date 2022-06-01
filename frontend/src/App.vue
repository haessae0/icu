<template>
  <div id="app">
    <nav-bar />
    <template v-if="userRole === 'ROLE_STUDENT'">
      <aside-menu :menu="student_menu" @menu-click="menuClick" />
    </template>
    <template v-else-if="userRole === 'ROLE_INSTRUCTOR'">
      <aside-menu :menu="instructor_menu" @menu-click="menuClick" />
    </template>
    <template v-else>
      <aside-menu :menu="menu" @menu-click="menuClick" />
    </template>
    <transition name="page">
      <router-view></router-view>
    </transition>
    <footer-bar />
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from "@/components/NavBar";
import AsideMenu from "@/components/AsideMenu";
import FooterBar from "@/components/FooterBar";
import { mapState } from "vuex";
import { getUserRoleFromSession } from "../src/utils/session";

export default {
  name: "home",
  components: {
    FooterBar,
    AsideMenu,
    NavBar
  },
  data() {
    return {
      userRole: getUserRoleFromSession() || ""
    };
  },
  computed: {
    ...mapState(["userName", "userRole"]),
    menu() {
      return [
        "프로필",
        [
          {
            to: "/profile",
            icon: "account-circle",
            label: "마이 프로필"
          }
        ],
        "메뉴",
        [
          {
            label: "시험",
            //submenu가 강사와 어드민 학생에 따라 다르게 나와야함
            subLabel: "Submenus Example",
            icon: "view-list",
            menu: []
          }
        ],
        "HOME",
        [
          {
            href: "/",
            label: "서비스 소개",
            icon: "help-circle"
          }
        ]
      ];
    },
    student_menu() {
      return [
        "프로필",
        [
          {
            to: "/profile",
            icon: "account-circle",
            label: "마이 프로필"
          }
        ],
        "메뉴",
        [
          {
            label: "시험",
            //submenu가 강사와 어드민 학생에 따라 다르게 나와야함
            subLabel: "Submenus Example",
            icon: "view-list",
            menu: [
              {
                href: "/student",
                label: "시험보기"
              }
            ]
          }
        ],
        "HOME",
        [
          {
            href: "/",
            label: "서비스 소개",
            icon: "help-circle"
          }
        ]
      ];
    },
    instructor_menu() {
      return [
        "프로필",
        [
          {
            to: "/profile",
            icon: "account-circle",
            label: "마이 프로필"
          }
        ],
        "메뉴",
        [
          {
            label: "시험",
            //submenu가 강사와 어드민 학생에 따라 다르게 나와야함
            subLabel: "Submenus Example",
            icon: "view-list",
            menu: [
              {
                href: "/instructor",
                label: "시험 감독"
              }
            ]
          }
        ],
        "HOME",
        [
          {
            href: "/",
            label: "서비스 소개",
            icon: "help-circle"
          }
        ]
      ];
    }
  },
  created() {
    this.$store.commit("user", {
      name: "",
      email: "",
      avatar: ""
    });
  },
  methods: {
    menuClick(item) {
      if (item.action && item.action === "dark-mode-toggle") {
        this.$store.commit("darkModeToggle");
      }
    }
  }
};
</script>

<style>
/* Router Transition - 페이지 전환이 부드럽게 하기 위함 (부드러운 페이지 이동) */
.page-enter-active,
.page-leave-active {
  transition: opacity 0.5s;
}
.page-enter, .page-leave-to /* .page-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
