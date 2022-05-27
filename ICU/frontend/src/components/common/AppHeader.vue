<template>
	<header>
		<div>
			<router-link :to="logoLink" class="logo">
				EduCare
			</router-link>
		</div>
		<div class="navigations">
			<template v-if="isUserSignin">
				<span class="id">{{ $store.state.id }}</span>
				<a href="javascript:;" @click="signoutUser">Signout</a>
			</template>
			<template v-else>
				<router-link to="/signin">로그인</router-link>
				<router-link to="/signup">회원가입</router-link>
			</template>
		</div>
	</header>
</template>

<script>
import { deleteCookie } from '@/utils/cookies';
import { LogoutUser } from '@/api/auth.js';

export default {
	data() {
		return {
			userData: null,
		};
	},
	computed: {
		isUserSignin() {
			return this.$store.getters.isSignin;
		},
		logoLink() {
			return this.$store.getters.isSignin ? '/main' : '/signin';
		},
	},
	methods: {
		async signoutUser() {
			try {
				this.$store.commit('clearId');
				this.$store.commit('clearToken');
				deleteCookie('waiting_auth');
				deleteCookie('waiting_user');

				const response = await LogoutUser(this.$session.get('userinfo')); //로그아웃
				this.$session.remove('userinfo');
				this.$router.push(response.data);
			} catch (error) {
				alert('로그아웃 중 문제가 발생했습니다.');
				console.log(error);
			}
		},
	},
};
</script>

<style scoped>
.id {
	color: white;
}
header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px 20px;
	background-color: #927dfc;
	z-index: 2;
	box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.05);
}
a {
	color: #dedede;
	font-size: 18px;
}
a.logo {
	font-size: 30px;
	font-weight: 900;
	color: white;
}
.logo > span {
	font-size: 14px;
	font-weight: normal;
}
.navigations a {
	margin-left: 10px;
}
.fixed {
	position: fixed;
	top: 0;
	width: 100%;
}
a.router-link-exact-active {
	color: white;
	font-weight: bold;
}
</style>
