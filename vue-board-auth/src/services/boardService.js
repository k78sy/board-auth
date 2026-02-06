import axios from './httpRequester';

class BoardService{
    #url = '/board'

    async post(jsonBody){
        const res = await axios.post(this.#url, jsonBody)
        return res.data;
    }
}

export default new BoardService();