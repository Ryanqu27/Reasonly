import { createContext, useContext, useEffect, useState } from 'react';
import { getUserSettings } from './UserService';
import AuthService from '../UserAuth/AuthService';

const ThemeContext = createContext();

export function ThemeProvider({ children }) {
    const [darkMode, setDarkMode] = useState(true); // default dark until loaded

    useEffect(() => {
        document.documentElement.setAttribute('data-theme', darkMode ? 'dark' : 'light');
    }, [darkMode]);

    useEffect(() => {
        const token = AuthService.getToken();
        if (token) {
            getUserSettings()
                .then(settings => {
                    if (settings && settings.darkMode !== undefined) {
                        setDarkMode(settings.darkMode);
                    }
                })
                .catch(() => {
                    // Fails silently and uses default dark mode
                });
        }
    }, []);

    return (
        <ThemeContext.Provider value={{ darkMode, setDarkMode }}>
            {children}
        </ThemeContext.Provider>
    );
}

export function useTheme() {
    return useContext(ThemeContext);
}
