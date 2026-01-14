import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1/software-engineers",
})

export const getAllEngineers = () => api.get("");
export const getEngineerById = (id) => api.get(`/${id}`);
export const createEngineer = (engineer) => api.post("/", engineer);
export const updateEngineer = (id, engineer) => api.put(`/${id}`, engineer);
export const deleteEngineer = (id) => api.delete(`/${id}`);