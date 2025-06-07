import axios from 'axios';

const API_URL = `${process.env.VUE_APP_API_URL}/join`;

export async function registerUser(userData) {
    const response = await axios.post(`${API_URL}/register`, userData, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true //
    });
    return response.data;
}
