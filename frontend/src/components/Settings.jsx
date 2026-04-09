import { useState, useEffect } from 'react';
import { getUserSettings, updateUserSettings } from "./UserService";
import "./Settings.css";

export default function Settings() {
    const [userSettingValues, setUserSettingValues] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);

    const LANGUAGE_DISPLAY = {
        JAVA: "Java",
        PYTHON: "Python",
        C_PLUS_PLUS: "C++",
        JAVASCRIPT: "JavaScript",
        C_SHARP: "C#",
        GO: "Go"
    };

    const EXPERIENCE_DISPLAY = {
        BEGINNER: "Beginner",
        INTERMEDIATE: "Intermediate",
        ADVANCED: "Advanced",
        EXPERT: "Expert"
    };

    const MOTIVATION_DISPLAY = {
        INTERVIEW_PREP: "Interview Prep",
        ACADEMIC: "Academic",
        CAREER_TRANSITION: "Career Transition",
        HOBBY: "Hobby"
    }



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

    const handleSaveChangesClick = () => {
        updateUserSettings(userSettingValues);
        setIsEditing(false);
    }

    const handleSelectionChange = (field, value) => {
        setUserSettingValues(prev => ({
            ...prev,
            [field]: value
        }))
    }

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
                        <span className="setting-value">{LANGUAGE_DISPLAY[userSettingValues.preferredLanguage] || "Not selected"}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Experience Level</span>
                        <span className="setting-value">{EXPERIENCE_DISPLAY[userSettingValues.experience] || "Not selected"}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Primary Motivation</span>
                        <span className="setting-value">{MOTIVATION_DISPLAY[userSettingValues.motivation] || "Not selected"}</span>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Visual Theme</span>
                        <span className="setting-value">{userSettingValues.darkMode ? "Dark Mode" : "Light Mode"}</span>
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

                    <div className="setting-row">
                        <span className="setting-name">Experience Level</span>
                        <select className="setting-select" value={userSettingValues.experience} 
                            onChange={e => handleSelectionChange("experience", e.target.value)}>
                            {Object.entries(EXPERIENCE_DISPLAY).map(([key, value]) => {
                                return <option key={key} value={key}>{value}</option>
                            })}
                        </select>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Primary Motivation</span>
                        <select className="setting-select" value={userSettingValues.motivation} 
                            onChange={e => handleSelectionChange("motivation", e.target.value)}>
                            {Object.entries(MOTIVATION_DISPLAY).map(([key, value]) => {
                                return <option key={key} value={key}>{value}</option>
                            })}
                        </select>
                    </div>

                    <div className="setting-row">
                        <span className="setting-name">Visual Theme</span>
                        <select className="setting-select" value={userSettingValues.darkMode} 
                            onChange={e => handleSelectionChange("darkMode", e.target.value === "true")}>
                            <option value="true">Dark Mode</option>
                            <option value="false">Light Mode</option>
                        </select>
                    </div>
                </div>
                
                <button onClick={handleSaveChangesClick} className="setting-edit-button">Save Edits</button>
            </div>
        )
    }
}
