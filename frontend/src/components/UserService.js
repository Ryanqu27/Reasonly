import api from '../api'

export const getUserSettings = async () => {
    try {
        const response = await api.get("/user/settings");
        return response.data;
    } 
    catch (error) {
        throw error.response?.data || error.message;
    }
}

export const updateUserSettings = async (newUserSettings) => {
    try {
        return api.put("/user/settings", newUserSettings);
    }
    catch (error) {
        throw error.response?.data || error.message;
    }
}