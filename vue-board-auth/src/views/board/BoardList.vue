<script setup>
import boardService from '@/services/boardService';
import { onMounted, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// const scrollToTop = () => {
//     window.scrollTo({
//         top: 0        
//     });
// }; 

const state = reactive({
    list: [],
    searchText: '',
    size: 50,
    currentPage: 1,
    maxPage: 0,
    relatedSearchList : []
});

const getBoardMaxPage = async () => {
    const params = { size: state.size }
    if (state.searchText) {
        params.search_text = state.searchText
    }
    const result = await boardService.getBoardMaxPage(params);
    state.maxPage = result.resultData;
}

// onMounted(async () => {
//     getBoardMaxPage();

//     const params = {
//         size: state.size,
//         page: state.currentPage
//     }
//     if (state.searchText) {
//         params.search_text = state.searchText
//     }

//     const result = await boardService.getBoardList(params);
//     state.list = result.resultData;
// })


const getBoardList = async () => {
    const params = {
        page: state.currentPage
        , size: state.size
    };
    if(state.searchText) {
        params.search_text = state.searchText;
    }
    const result = await boardService.getBoardList( params );
    state.list = result.resultData;
}

onMounted(() => {
    doSearch();
});

const goToPage = page => {
    console.log(typeof page);
    state.currentPage = page;
    getBoardList();
    //scrollToTop();
}

const doSearch = () => {
    state.currentPage = 1;
    getBoardMaxPage();
    getBoardList(); 
    state.relatedSearchList = [];
}

//페이징 그룹의 번호 갯수
const pageGroupSize = 10; 
//현재 페이지 그룹 계산
const currentGroup = computed( () => Math.ceil(state.currentPage / pageGroupSize) );
//현재 그룹의 시작 페이지 번호
const startPage = computed( () => ((currentGroup.value - 1) * pageGroupSize + 1) );
const endPage = computed( () => Math.min(currentGroup.value * pageGroupSize, state.maxPage) );
const displayedPages = computed( () => {
    const pages = [];
    for(let i=startPage.value; i<=endPage.value; i++) {
        pages.push(i);
    }
    return pages;
} );

const goToFirstPage = () => {
    goToPage(1);
}
const goToPrevPage = () => {
    const prevPage = startPage.value - 1;
    if(prevPage < 1) { return; }
    goToPage(prevPage);
}

const goToNextPage = () => {
    const nextPage = endPage.value + 1;
    if(nextPage > state.maxPage) { return; }
    goToPage(nextPage);
}
const goToLastPage = () => {
    goToPage(state.maxPage);
}

const moveToDetail = (id) =>{
    router.push(`/board/${id}`)
}


let timer;
const typing = () =>{
    if(timer) {clearTimeout(timer);}
    timer = setTimeout(() => {
        getRelatedTitles();
    }, 700)
}

const getRelatedTitles = async () =>{
    if(state.searchText.length === 0){
        state.relatedSearchList = [];
        return;
    }
    const params = { search_text:state.searchText }
    const result = await boardService.getBoardRealatedTitles(params);
    state.relatedSearchList = result.resultData;
}
const clickRelatedText = (idx) =>{
    state.searchText = state.relatedSearchList[idx];
    doSearch();
}
</script>

<template>
<div class="search-container">
    <input type="search" v-model="state.searchText" @keyup="typing" @keyup.enter="doSearch">
    <button @click="doSearch">검색</button>
    <div class="related-search-container" v-if="state.relatedSearchList.length > 0">
        <div v-for="item, idx in state.relatedSearchList" @click="clickRelatedText(idx)">{{ item }}</div>
    </div>
</div>
<div v-if="state.list.length === 0">게시글이 없습니다.</div>
<div v-else class="board-list">
    <table>
        <thead>
            <tr>
                <th>no</th>
                <th>title</th>
                <th>writer</th>
                <th>created at</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in state.list" :key="item.id" @click="moveToDetail(item.id)">
                <td>{{ item.id }}</td>
                <td>{{ item.title }}</td>
                <td>{{ item.nm }}</td>
                <td>{{ item.createdAt }}</td>
            </tr>
        </tbody>
    </table>
    <div class="pagination">
        <button @click="goToFirstPage" :disabled="startPage === 1">&lt;&lt;</button>
        <button @click="goToPrevPage" :disabled="startPage === 1">&lt;</button>
        <span class="page" v-for="item in displayedPages" 
            :key="item" :class="{selected: item == state.currentPage}" @click="goToPage(item)">
            {{ item }}
        </span>
        <button @click="goToNextPage" :disabled="endPage === state.maxPage">&gt;</button>
        <button @click="goToLastPage" :disabled="endPage === state.maxPage">&gt;&gt;</button>
    </div>
</div>

</template>

<style scoped>
*{box-sizing: border-box;}


.pagination *{cursor: pointer;}
.pagination button{}
.pagination span{padding: 0 3px;}

.page { cursor: pointer; }
.page:not(:first-child) { margin-left: 8px; }
.selected { color: red; font-weight: bold; }

</style>