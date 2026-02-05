import axios from './httpRequester';

class UserService{
    #url = '/user'

    async signUp(params){
        const res = await axios.post(`${this.#url}/signup`, params)
        return res.data;
    }
}

export default new UserService();