import api from '../api'
import AuthService from '../UserAuth/AuthService'

const API_BASE_URL = 'http://localhost:8080';
export const getQuestions = (type) => {
    const endpoint = type ? `/questions?type=${type}` : '/questions';
    return api.get(endpoint);
};
export const updateCompletedDate = async () => {
    const user = await AuthService.fetchCurrentUser();
    return api.put(`/user/${user.id}/complete-today`);
};

//export const getEngineerById = (id) => api.get(`/${id}`);
//export const createEngineer = (engineer) => api.post("/", engineer);
//export const updateEngineer = (id, engineer) => api.put(`/${id}`, engineer);
//export const deleteEngineer = (id) => api.delete(`/${id}`);