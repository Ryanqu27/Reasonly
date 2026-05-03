import api from '../api'
export const getQuestions = () => {
    return api.get('/questions/play');
};

export const runCode = (codeRunningRequest) => {
    return api.post('/questions/run', codeRunningRequest);
};

export const updateCompletedDate = async () => {
    return api.put('/user/complete-today');
};

export const submitQuestionAttempt = async (questionAttempt) => {
    return api.post('/question-attempts', questionAttempt);
};

export const checkStreak = async () => {
    return api.post('/user/check-streak');
};

export const resetQuestionAttempts = async () => {
    return api.delete('/question-attempts/reset');
};