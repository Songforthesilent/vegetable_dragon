import axios from 'axios';

const api = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    withCredentials: true, // 세션 기반 인증 위해 필요
});

export const loginUser = (email, password) => {
    return api.post('/join/login', { email, password });
};

export const getSessionUser = () => {
    return api.get('/join/session');
};

export const logoutUser = () => {
    return api.post('/join/logout');
};
