import { useState, useEffect } from 'react';
import { getUserSettings } from "./UserService";
import "./Settings.css";

export default function Settings() {
    const [userSettingValues, setUserSettingValues] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSettings = async () => {
            try {
                const response = await getUserSettings();
                setUserSettingValues(response);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        }
        fetchSettings();
    }, [])

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
    
    return (
        <div className="settings-container">
            <header className="preferences-header">
                <h1>Preferences</h1>
                <p className="preferences-subtitle">Manage your account settings and visual preferences.</p>
            </header>
            
            <div className="settings-list">
                <div className="setting-row">
                    <span className="setting-name">Programming Language</span>
                    <span className="setting-value">{userSettingValues.preferredLanguage || "Not selected"}</span>
                </div>

                <div className="setting-row">
                    <span className="setting-name">Experience Level</span>
                    <span className="setting-value">{userSettingValues.experience || "Not selected"}</span>
                </div>

                <div className="setting-row">
                    <span className="setting-name">Primary Motivation</span>
                    <span className="setting-value">{userSettingValues.motivation || "Not selected"}</span>
                </div>

                <div className="setting-row">
                    <span className="setting-name">Visual Theme</span>
                    <span className="setting-value">{userSettingValues.darkMode ? "Dark Mode" : "Light Mode"}</span>
                </div>
            </div>
        </div>
    )
}
