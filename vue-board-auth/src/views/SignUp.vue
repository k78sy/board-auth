<script setup>
import { reactive } from 'vue';
import useService from '@/services/useService';

const state = reactive({
    signup:{
        id:'',
        pw:'',
        name:'',
        gender:2,
    },
    checkPw:''
})

const doSignUp = async () => {
    //비밀번호, 확인 비밀번호 내용이 다르면 alert('비밀번호를 확인해 주세요') 그리고 메소드 종료

    if(state.checkPw !== state.signup.pw){
        alert("비밀번호를 확인해 주세요")
        return;
    }
    const result = await useService.signUp(state.signup);
    console.log('result: ', result);
}
</script>

<template>
    <h3>회원가입 페이지</h3>
    <div><label>아이디: <input type="text" v-model="state.signup.id"></label></div>
    <div><label>비밀번호: <input type="password" v-model="state.signup.pw"></label></div>
    <div><label>확인비밀번호: <input type="password" v-model="state.checkPw"></label></div>
    <div><label>이름: <input type="text" v-model="state.signup.name"></label></div>
    <div>
        성별: {{ state.signup.gender }}
        <label>여성<input type="radio" value="2" v-model="state.signup.gender"></label>
        <label>남성<input type="radio" value="1" v-model="state.signup.gender"></label>
    </div>
    <div>
        <button @click="doSignUp">회원가입</button>
    </div>
</template>

<style scoped>
h3{font-weight: bold;}
</style>