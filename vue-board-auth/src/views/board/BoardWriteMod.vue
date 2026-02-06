<script setup>
import boardService from '@/services/boardService';
import { reactive } from 'vue';

const state = reactive({
    board: {
        title: '',
        contents: ''
    }
})

const submit = async () => {
    const result = await boardService.post(state.board);
    console.log('result: ', result)

}
</script>


<template>
    <h3>글쓰기</h3>
    <div><input type="text" placeholder="제목" v-model="state.board.title"></div>
    <div><textarea placeholder="내용" v-model="state.board.contents"></textarea></div>
    <div><button @click="submit">등록</button></div>
</template>

<style scoped></style>