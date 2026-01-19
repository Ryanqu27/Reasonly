import api from './api'

export const getAllQuestions = () => api.get('/questions');
//export const getEngineerById = (id) => api.get(`/${id}`);
//export const createEngineer = (engineer) => api.post("/", engineer);
//export const updateEngineer = (id, engineer) => api.put(`/${id}`, engineer);
//export const deleteEngineer = (id) => api.delete(`/${id}`);