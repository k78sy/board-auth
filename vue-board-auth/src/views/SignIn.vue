<script setup>
import { reactive } from 'vue';
import userService from '@/services/userService';

const state = reactive({
    signin :{
        uid: 'mic4',
        upw: '1234'
    },
    modeShowPw:false
})

const pwView = () => {
    state.modeShowPw = !state.modeShowPw
}

const signIn = async () => {
    const result = await userService.signIn(state.signin);
    console.log('result : ', result)
}
</script>

<template>
    <h3>로그인</h3>
    <div><input type="text" placeholder="아이디" v-model="state.signin.uid"></div>
    <div><input :type="state.modeShowPw ? 'text' : 'password'" placeholder="패스워드" v-model="state.signin.upw"><button @click="pwView">비밀번호 보기</button></div>
    <div><button @click="signIn">로그인</button></div>
</template>

<style scoped></style>