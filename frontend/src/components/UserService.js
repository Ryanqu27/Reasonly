import api from '../api'

export const getUserProfile = async () => {
    try {
        const response = await api.get("/user/profile");
        return response.data;
    } 
    catch (error) {
        throw error.response?.data || error.message;
    }

};

export const getUserSettings = async () => {
    try {
        const response = await api.get("/user/settings");
        return response.data;
    } 
    catch (error) {
        throw error.response?.data || error.message;
    }
}