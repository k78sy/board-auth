import axios from "axios";
axios.defaults.baseURL = '/api'
axios.defaults.withCredentials = true; //보안 쿠키에 AT, RT 저장

export default axios;