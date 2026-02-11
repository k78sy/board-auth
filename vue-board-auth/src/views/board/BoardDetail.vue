<script setup>
import boardService from '@/services/boardService';
import { onMounted, reactive } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const state = reactive({
    data:{
        id:0,
        title:'',
        contents:'',
        createdAt:'',
        nm: '',
        userId:0
    }
})

onMounted(async()=>{
    const id = route.params.id;
    console.log('id: ' , id)
    
    const result = await boardService.getBoard(id);

    state.data = result.resultData;
})
</script>

<template>
<h3>Detail</h3>
<main>
    <div>id: {{ state.data.id }}</div>
    <div>title: {{ state.data.title }}</div>
    <div>writer: {{ state.data.nm }}</div>
    <div>contents: {{ state.data.contents }}</div>
    <div>date: {{ state.data.createdAt }}</div>

    <div v-if="state.data.userId">
        <button>삭제</button>
        <button>수정</button>
    </div>
</main>
</template>

<style scoped>

</style>