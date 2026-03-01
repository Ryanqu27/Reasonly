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

export const checkStreak = async () => {
    const user = await AuthService.fetchCurrentUser();
    return api.post(`/user/${user.id}/check-streak`);
};

export const resetQuestionAttempts = async () => {
    const user = await AuthService.fetchCurrentUser();
    return api.delete(`/question-attempts/reset/${user.id}`);
};