// frontend/src/api/auth.js
import axios from 'axios';

export async function loginUser(email, password) {
    const response = await axios.post(
        'http://localhost:8081/join/login',
        {
            email: email,
            password: password
        },
        { withCredentials: true } // 쿠키 등 인증 정보 포함
    );
    return response.data;
}
