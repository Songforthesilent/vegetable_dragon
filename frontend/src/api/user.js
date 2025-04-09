// src/api/user.js (또는 user.js)
import axios from 'axios';

const API_URL = 'http://localhost:8081/join';

export async function registerUser(userData) {
    const response = await axios.post(`${API_URL}/register`, userData, {
        headers: { 'Content-Type': 'application/json' },
        withCredetials: true // CORS, 쿠키 관련
    });
    return response.data;
}
