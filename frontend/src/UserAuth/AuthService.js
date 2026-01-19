import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';
const TOKEN_KEY = 'auth_token';
const USER_KEY = 'user';
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
        return response.data
    }
    catch (error) {
        throw error.response?.data || error.message;
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
            localStorage.setItem(USER_KEY, response.data.user);
        }
        return response.data;
    }
    catch (error) {
        throw error.response?.data || error.message;
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
   * Get stored user
   * @returns {User|null} User object or null
   */
  getUser: () => {
    return localStorage.getItem(USER_KEY);
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
    }
}

export default AuthService;