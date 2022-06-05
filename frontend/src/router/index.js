import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    meta: {
      title: "Home"
    },
    path: "/",
    name: "Home",
    component: () => import("../views/Home.vue")
  },
  {
    meta: {
      title: "Profile"
    },
    path: "/profile",
    name: "Profile",
    component: () =>
      import("../views/Profile.vue")
  },
  {
    meta: {
      title: "TestGuide"
    },
    path: "/testguide",
    name: "TestGuide",
    component: () => import("../views/TestGuide.vue")
  },
  {
    meta: {
      title: "CompleteTest"
    },
    path: "/completetest",
    name: "CompleteTest",
    // beforeEnter: onlyAuthUser,
    component: () => import("../views/CompleteTest.vue")
  },
  {
    meta: {
      title: "StudentTestList"
    },
    path: "/student",
    name: "StudentTestList",
    component: () => import("../views/StudentTestList.vue")
  },
  {
    meta: {
      title: "ModifyTest"
    },
    path: "/modifytest",
    name: "ModifyTest",
    component: () => import("../views/ModifyTest.vue")
  },
  {
    meta: {
      title: "TestSupervision"
    },
    path: "/testsupervision",
    name: "TestSupervision",
    component: () => import("../views/TestSupervision.vue")
  },
  {
    meta: {
      title: "ManageStudent"
    },
    path: "/managestudent",
    name: "ManageStudent",
    component: () => import("../views/ManageStudent.vue")
  },
  {
    meta: {
      title: "AddTest"
    },
    path: "/addtest",
    name: "AddTest",
    component: () => import("../views/AddTest.vue")
  },
  {
    meta: {
      title: "AddTestProblems"
    },
    path: "/problems",
    name: "AddTestProblems",
    component: () => import("../views/AddTestProblems.vue")
  },
  {
    meta: {
      title: "InstructorTest"
    },
    path: "/instructor",
    name: "InstructorTest",
    component: () => import("../views/InstructorTest.vue")
  },
  {
    meta: {
      title: "Sign In"
    },
    path: "/signin",
    name: "Sign In",
    component: () => import("../views/SignIn.vue"),
    props: true
  },
  {
    meta: {
      title: "회원가입"
    },
    path: "/signup",
    name: "Sign Up",
    component: () => import("../views/SignUp.vue"),
    props: true
  },
  {
    path: "/:catchAll(.*)",
    component: () => import("../views/NotFoundPage.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  }
});

export default router;
