<script setup>
import boardService from '@/services/boardService';
import { onMounted, reactive } from 'vue';
import { useAuthenticationStore } from '@/stores/authentication';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

// const authentication = useAuthenticationStore();
// if (!authentication.state.isSigned){
//     router.push('/');
// }

const boardId = route.params.id;
if (boardId) {
    console.log(boardId);
}

const state = reactive({
    board: {
        title: '',
        contents: ''
    },
})

onMounted(() => {
    // console.log('history.state.id: ', history.state.id);

    if (history.state.data) {
        state.board = history.state.data;
    }
})


const submit = async () => {
    if(!state.board.title){
        alert('제목을 작성하세요!');
        return;
    }else if(!state.board.contents){
        alert('내용을 작성하세요!');
        return;
    }
    const result = await (state.board.id
        ? boardService.update(state.board)
        : boardService.post(state.board)
    );
    console.log('result: ', result)

    if (result.resultData) {
        // router.push(
        //     result.resultMessage == "수정성공"
        //     ? `/board/${state.board.id}`
        //     : `/board/${result.resultData}`
        // )
        router.push(`/board/${result.resultData}`);
    } else {
        alert(result.resultMessage);
    }
}
</script>


<template>
    <div class="board-write">
        <h3>{{ state.board.id ? 'Article Modify' : 'New Article' }}</h3>
        <div><input type="text" placeholder="제목" v-model="state.board.title"></div>
        <div><textarea placeholder="내용" v-model="state.board.contents"></textarea></div>
        <div class="btn-container">
            <button class="board-btn" @click="router.go(-1)">back</button>
            <button class="board-btn" @click="submit">{{ state.board.id ? 'MODIFY' : 'SUBMIT' }}</button></div>
    </div>
</template>

<style scoped></style>