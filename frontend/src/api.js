import axios from 'axios';
import AuthService from './UserAuth/AuthService';

// Abstraction on axios to make API calls easier and more consistent


const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: { 'Content-Type': 'application/json'},
});

//Automatically attach tokens to every request
api.interceptors.request.use(
    (config) => {
        const token = AuthService.getToken();
        if (token) config.headers.Authorization = `Bearer ${token}`;
        return config;
    },
    (error) => Promise.reject(error)
);

//Automatically logs user out if backend returns a 401 unauthorized status
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            AuthService.logout();
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default api;