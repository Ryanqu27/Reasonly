import { createContext, useContext, useEffect, useState } from 'react';
import { getUserSettings } from './UserService';

const ThemeContext = createContext();

export function ThemeProvider({ children }) {
    const [darkMode, setDarkMode] = useState(true); // default dark until loaded

    useEffect(() => {
        document.documentElement.setAttribute('data-theme', darkMode ? 'dark' : 'light');
    }, [darkMode]);

    useEffect(() => {
        getUserSettings()
            .then(settings => setDarkMode(settings.darkMode))
            .catch(() => {}); 
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
