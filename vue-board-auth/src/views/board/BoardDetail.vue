<script setup>
import { onMounted, reactive } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const state = reactive({
    data:{
        id:0,
        title:'',
        contents:'',
        createdAt:''
    }
})

onMounted(()=>{
    const id = route.params.id;
    console.log('id: ' , id)
})
</script>

<template>
<h3>Detail</h3>
</template>

<style scoped>

</style>