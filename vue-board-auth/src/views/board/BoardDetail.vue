<script setup>
import boardService from '@/services/boardService';
import { useAuthenticationStore } from '@/stores/authentication';
import { onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const authentication = useAuthenticationStore();

const route = useRoute();
const router = useRouter();

const state = reactive({
    data: {
        id: 0,
        title: '',
        contents: '',
        createdAt: '',
        nm: '',
        userId: 0
    }
})

onMounted(async () => {
    const id = route.params.id;
    const result = await boardService.getBoard(id);
    state.data = result.resultData;
})

const doDelete = async () => {
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }
    const params = { id: route.params.id }

    const result = await boardService.deleteBoard(params);
    if (result.resultData === 1) {
        router.push('/board/list')
    }
}
const goToMod = () => {
    router.push({
        path: '/board/write',
        state: {
            data: {
                id: route.params.id,
                title: state.data.title,
                contents: state.data.contents
            }
        }
    })
}
</script>

<template>
    <h3>Detail</h3>
    <main>
        <div>id: {{ state.data.id }}</div>
        <div>title: {{ state.data.title }}</div>
        <div>writer: {{ state.data.nm }}</div>
        <div>contents: {{ state.data.contents }}</div>
        <div>date: {{ state.data.createdAt }}</div>

        <div v-if="state.data.userId === authentication.state.signedUser.signedUserId">
            <button @click="doDelete">삭제</button>
            <button @click="goToMod">수정</button>
        </div>
    </main>
</template>

<style scoped></style>