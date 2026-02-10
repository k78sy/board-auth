<script setup>
import boardService from '@/services/boardService';
import { onMounted, reactive } from 'vue';

const state = reactive({
    list: [],
    searchText: '',
    size: 50,
    currentPage: 1,
    maxPage: 0
})

const getBoardMaxPage = async () => {
    const params = { size: state.size }
    if (state.searchText) {
        params.search_text = state.searchText
    }
    const result = await boardService.getBoardMaxPage(params);
    state.maxPage = result.resultData;
}

onMounted(async () => {
    getBoardMaxPage();

    const params = {
        size: state.size,
        page: state.currentPage
    }
    if (state.searchText) {
        params.search_text = state.searchText
    }

    const result = await boardService.getBoardList(params);
    state.list = result.resultData;
})
</script>

<template>
    <h3>게시판 리스트</h3>
    <div>
        <input type="search" v-model="state.searchText">
        <button>검색</button>
    </div>
    <main>
        <div v-if="state.list.length === 0">
            게시글이 없습니다.
        </div>
        <table v-else>
            <thead>
                <tr>
                    <th>NO</th>
                    <th>TITLE</th>
                    <th>NAME</th>
                    <th>DATE</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in state.list" :key="item.id">
                    <td>{{ item.id }}</td>
                    <td>{{ item.title }}</td>
                    <td>{{ item.nm }}</td>
                    <td>{{ item.createdAt }}</td>
                </tr>
            </tbody>
        </table>
        <div class="page">
            <span v-for="item in state.maxPage" :class="state.currentPage ? 'selectee':''" :key="i">{{ item }}</span>
        </div>
    </main>

</template>

<style scoped>
table{border-collapse: collapse;width: 100%;}
thead{background-color: darkslategrey;color: #fff; }
tr{border: 1px solid #ddd;}
tbody tr:hover{background-color: linen;cursor: pointer;}
th{font-weight: bold;}
th,td{padding: 5px;}
th:nth-of-type(1),td:nth-of-type(1){width: 40px;}
th:nth-of-type(2),td:nth-of-type(2){width: calc(100% - 300px);}
th:nth-of-type(3),td:nth-of-type(3){width: 100px;}
th:nth-of-type(4),td:nth-of-type(4){width: 160px;}

.page{width: 100%;}
.page span:not(:first-child){margin-left: 5px;}
</style>