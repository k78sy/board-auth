<script setup>
import boardService from '@/services/boardService';
import { onMounted, reactive } from 'vue';

const state = reactive({
    list:[]
})
onMounted(async() => {
    // state.list = await boardService.findAll();

})
</script>

<template>
<h3>게시판 리스트</h3>
</template>

<style scoped>

</style>