import axios from './httpRequester';

class BoardService{
    #url = '/board'

    async post(jsonBody){
        const res = await axios.post(this.#url, jsonBody);
        return res.data;
    }
    async getBoardList(params){
        const res = await axios.get(this.#url, { params });
        return res.data;
    }
    async getBoardMaxPage(params){
        const res = await axios.get(`${this.#url}/max_page`, { params });
        return res.data;
    }
}

export default new BoardService();