import { useState, useEffect } from 'react';
import { getUserSettings, updateUserSettings } from "./UserService";
import { useTheme } from "./ThemeContext";
import "./Settings.css";

export default function Settings() {
    const [userSettingValues, setUserSettingValues] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const { setDarkMode } = useTheme();

    const LANGUAGE_DISPLAY = {
        JAVA: "Java",
        PYTHON: "Python",
        C_PLUS_PLUS: "C++",
        JAVASCRIPT: "JavaScript",
        C_SHARP: "C#",
        GO: "Go"
    };



    const TOPIC_DISPLAY = {
        DATA_STRUCTURES_AND_ALGORITHMS: "Data Structures & Algorithms",
        SYSTEMS: "Systems",
        NETWORKING: "Networking",
        DATABASES: "Databases",
        CONCURRENCY: "Concurrency",
        SOFTWARE_DESIGN: "Software Design",
        LANGUAGE_KNOWLEDGE: "Language Knowledge"
    };

    const EDITOR_THEME_DISPLAY = {
        "vs-dark": "Dark Theme (vs-dark)",
        "vs": "Light Theme (vs)",
        "hc-black": "High Contrast (hc-black)"
    };

    const TAB_SIZE_DISPLAY = {
        2: "2 spaces",
        4: "4 spaces"
    };

    const FONT_SIZE = Array.from({ length: 15 }, (_, i) => i + 10);

    const fetchSettings = async () => {
        setLoading(true);
        try {
            const response = await getUserSettings();
            setUserSettingValues(response);
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchSettings();
    }, []);

    const handleEditButtonClick = () => {
        if (isEditing) {
            // If cancel clicked, revert settings back to original
            fetchSettings();
        }
        setIsEditing(!isEditing);
    }

    const handleSaveChangesClick = async () => {
        try {
            await updateUserSettings(userSettingValues);
            setDarkMode(userSettingValues.darkMode);
            setIsEditing(false);
        }
        catch (err) {
            setError("Failed to save settings. Please try again.");
        }
    }

    const handleSelectionChange = (field, value) => {
        setUserSettingValues(prev => ({
            ...prev,
            [field]: value
        }))
    }

    const handleTopicToggle = (topicKey) => {
        setUserSettingValues(prev => {
            const currentInterests = prev.interests || [];
            if (currentInterests.includes(topicKey)) {
                return { ...prev, interests: currentInterests.filter(t => t !== topicKey) };
            } else {
                return { ...prev, interests: [...currentInterests, topicKey] };
            }
        });
    };

    if (loading) {
        return (
            <div className="profile-container">
                <div className="profile-loading">
                    <div className="loading-spinner"></div>
                    <p>Loading profile...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="settings-container">
                <div className="error-message">
                    Failed to load settings: {error.toString()}
                </div>
            </div>
        );
    }

    if (!isEditing) {
        return (
            <div className="settings-container">
                <header className="preferences-header">
                    <h1>Preferences</h1>
                    <div className="preferences-actions">
                        <span className="preferences-subtitle">Manage your account settings and visual preferences.</span>
                        <button onClick={handleEditButtonClick} className="setting-edit-button">Edit Settings</button>
                    </div>
                </header>

                <div className="settings-list">
                    <div className="setting-row">
                        <span className="setting-name">Programming Language</span>
                        <span className="setting-value">{LANGUAGE_DISPLAY[userSettingValues.preferredLanguage]}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Interests</span>
                        <div className="setting-interests">
                            {userSettingValues.interests?.length > 0 
                                ? userSettingValues.interests.map(t => (
                                    <span key={t} className="setting-value">{TOPIC_DISPLAY[t]}</span>
                                  ))
                                : <span className="setting-value">None selected</span>
                            }
                        </div>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Visual Theme</span>
                        <span className="setting-value">{userSettingValues.darkMode ? "Dark Mode" : "Light Mode"}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Editor Font Size</span>
                        <span className="setting-value">{userSettingValues.editorFontSize}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Editor Theme</span>
                        <span className="setting-value">{EDITOR_THEME_DISPLAY[userSettingValues.editorTheme] || "Dark Theme (vs-dark)"}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Tab Size</span>
                        <span className="setting-value">{TAB_SIZE_DISPLAY[userSettingValues.editorTabSize] || "4 spaces"}</span>
                    </div>
                </div>
            </div>
        )
    }
    else {
        return (
            <div className="settings-container">
                <header className="preferences-header">
                    <h1>Preferences</h1>
                    <div className="preferences-actions">
                        <span className="preferences-subtitle">Manage your account settings and visual preferences.</span>
                        <div className="edit-buttons">
                            <button onClick={handleEditButtonClick} className="setting-edit-button setting-cancel-button">Cancel</button>
                        </div>
                    </div>
                </header>
                <div className="settings-list">
                    <div className="setting-row">
                        <span className="setting-name">Programming Language</span>
                        <select className="setting-select" value={userSettingValues.preferredLanguage} 
                            onChange={e => handleSelectionChange("preferredLanguage", e.target.value)}>
                            {Object.entries(LANGUAGE_DISPLAY).map(([key, value]) => {
                                return <option key={key} value={key}>{value}</option>
                            })}
                        </select>
                    </div>

                    <div className="setting-row" style={{ flexDirection: 'column', alignItems: 'flex-start', gap: '1rem' }}>
                        <span className="setting-name">Interests</span>
                        <div className="setting-interests-edit">
                            {Object.entries(TOPIC_DISPLAY).map(([key, value]) => {
                                const isSelected = userSettingValues.interests?.includes(key);
                                return (
                                    <button 
                                        key={key} 
                                        className={`interest-chip-edit ${isSelected ? 'selected' : ''}`}
                                        onClick={() => handleTopicToggle(key)}
                                    >
                                        {value}
                                    </button>
                                );
                            })}
                        </div>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Visual Theme</span>
                        <select className="setting-select" value={userSettingValues.darkMode} 
                            onChange={e => handleSelectionChange("darkMode", e.target.value === "true")}>
                            <option value="true">Dark Mode</option>
                            <option value="false">Light Mode</option>
                        </select>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Editor Font Size</span>
                        <select className="setting-select" value={userSettingValues.editorFontSize} 
                            onChange={e => handleSelectionChange("editorFontSize", Number(e.target.value))}>
                            {FONT_SIZE.map((element, index) => {
                                return <option key={index} value={element}>{element}</option>
                            })}
                        </select>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Editor Theme</span>
                        <select className="setting-select" value={userSettingValues.editorTheme || "vs-dark"} 
                            onChange={e => handleSelectionChange("editorTheme", e.target.value)}>
                            {Object.entries(EDITOR_THEME_DISPLAY).map(([key, value]) => {
                                return <option key={key} value={key}>{value}</option>
                            })}
                        </select>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Tab Size</span>
                        <select className="setting-select" value={userSettingValues.editorTabSize || 4} 
                            onChange={e => handleSelectionChange("editorTabSize", Number(e.target.value))}>
                            {Object.entries(TAB_SIZE_DISPLAY).map(([key, value]) => {
                                return <option key={key} value={Number(key)}>{value}</option>
                            })}
                        </select>
                    </div>
                </div>
                
                <button onClick={handleSaveChangesClick} className="setting-edit-button">Save Edits</button>
            </div>
        )
    }
}
