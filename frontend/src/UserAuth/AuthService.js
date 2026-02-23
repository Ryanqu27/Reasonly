import axios from 'axios';
import api from '../api.js';

const API_BASE_URL = 'http://localhost:8080';
const TOKEN_KEY = 'auth_token';

const AuthService = {
  /**
   * Register a new user
   * @param {string} email - User email
   * @param {string} password - User password
   * @returns {Promise} Response with user data
   */
  register: async (email, password) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/auth/register`, {
        email,
        password
      });
      return response.data;
    }
    catch (error) {
      throw error.response?.data?.message || error.response?.data || error.message || "Registration failed";
    }
  },

  /**
   * Login user
   * @param {string} email - User email
   * @param {string} password - User password
   * @returns {Promise} Response with JWT token
   */
  login: async (email, password) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/auth/login`, {
        email,
        password
      });
      if (response.data.token) {
        localStorage.setItem(TOKEN_KEY, response.data.token);
      }
      return response.data;
    }
    catch (error) {
      throw error.response?.data?.message || error.response?.data || error.message || "Login failed";
    }
  },

  /**
   * Logout user
   */
  logout: () => {
    localStorage.removeItem(TOKEN_KEY);
  },

  /**
   * Get stored token
   * @returns {string|null} JWT token or null
   */
  getToken: () => {
    return localStorage.getItem(TOKEN_KEY);
  },

  /**
   * Fetch current user data from the backend
   * @returns {Promise<User|null>} Fresh user data or null
   */
  fetchCurrentUser: async () => {
    try {
      const response = await api.get('/user/me');
      return response.data;
    } catch (error) {
      throw error.response?.data?.message || error.response?.data || error.message || "An unexpected error occurred";
    }
  },

  /**
   * Check if user is authenticated
   * @returns {boolean} returns true if token exists
   */
  isAuthenticated: () => {
    return !!localStorage.getItem(TOKEN_KEY);
  },

  /**
   * Get authorization header
   * @returns {object} Authorization header object
   */
  getAuthHeader: () => {
    const token = AuthService.getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
  },

  /**
   * Onboard user
   * @param {string} userId - User ID
   * @param {object} data - Onboarding data payload
   * @returns {Promise} Response with user data
   */
  onboardUser: async (userId, data) => {
    try {
      const response = await api.put(`/user/${userId}/onboard`, data);
      return response.data;
    } catch (error) {
      throw error.response?.data?.message || error.response?.data || error.message || "Failed to save experience level";
    }
  }
};



export default AuthService;