import api from '../api'
import AuthService from '../UserAuth/AuthService'

export const getQuestions = () => {
    return api.get('/questions/play');
};

export const updateCompletedDate = async () => {
    const user = await AuthService.fetchCurrentUser();
    return api.put(`/user/${user.id}/complete-today`);
};

export const submitQuestionAttempt = async (questionAttempt) => {
    return api.post('/question-attempts', questionAttempt);
};

//export const getEngineerById = (id) => api.get(`/${id}`);
//export const createEngineer = (engineer) => api.post("/", engineer);
//export const updateEngineer = (id, engineer) => api.put(`/${id}`, engineer);
//export const deleteEngineer = (id) => api.delete(`/${id}`);