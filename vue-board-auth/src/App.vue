<script setup>
import { useAuthenticationStore } from '@/stores/authentication';
const authentication = useAuthenticationStore();
</script>

<template>
  <header>
    <div v-if="authentication.state.isSigned">
      {{ authentication.state.signedUser.nm }}님 환영합니다.
      <router-link to="/board/write">글쓰기</router-link>
    </div>
    <div v-else>
      <router-link to="/signup">회원가입</router-link>
      <router-link to="/signin">로그인</router-link>
    </div>

  </header>
  <main>
    <router-view></router-view>
  </main>
</template>

<style scoped></style>