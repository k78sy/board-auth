<script setup>
import { useAuthenticationStore } from '@/stores/authentication';
import userService from './services/userService';
const authentication = useAuthenticationStore();

const doSignout = async () => {
  const result = await userService.signOut();
  console.log("result: ", result);
  if (result.resultData === 1) {
    authentication.signOut();
  } else {
    alert('로그아웃 실패!');
  }
}
</script>

<template>
  <header>
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/board/list">List</router-link>
    </nav>

    <div class="user-container">
      <template v-if="authentication.state.isSigned">
        <span><strong>{{ authentication.state.signedUser.nm }}</strong>님 환영합니다.</span>
        <a href="" @click.prevent="doSignout">Logout</a>
      </template>
      <template v-else>
        <router-link to="/signup">Sign up</router-link>
        <router-link to="/signin">sign in</router-link>
      </template>
    </div>
  </header>
  <main>
    <router-view></router-view>
  </main>
</template>

<style scoped></style>