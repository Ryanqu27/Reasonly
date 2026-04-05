import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getUserProfile } from "./UserService";
import "./ProfilePage.css";

export default function ProfilePage() {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await getUserProfile();
                setProfile(response);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };
        fetchProfile();
    }, []);

    const formatDate = (dateString) => {
        if (!dateString) return "N/A";
        const date = new Date(dateString);
        return date.toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric",
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
            <div className="profile-container">
                <div className="profile-error">
                    <span className="error-icon">⚠️</span>
                    <p>Failed to load profile</p>
                    <button onClick={() => navigate("/")} className="btn-primary">
                        Go Back
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="profile-container">
            <header className="profile-header">
                <h1>Your Profile</h1>
            </header>

            <div className="profile-card">
                <div className="profile-avatar">
                    <span>{profile.email?.charAt(0).toUpperCase()}</span>
                </div>
                <div className="profile-info">
                    <h2>{profile.email}</h2>
                    <p className="member-since">Member since {formatDate(profile.createdAt)}</p>
                </div>
            </div>

            <div className="stats-grid">
                <div className="stat-card stat-rating">
                    <div className="stat-content">
                        <span className="stat-value">{profile.rating}</span>
                        <span className="stat-label">Rating</span>
                    </div>
                </div>

                <div className="stat-card stat-streak">
                    <div className="stat-content">
                        <span className="stat-value">{profile.currentStreak}</span>
                        <span className="stat-label">Day Streak</span>
                    </div>
                </div>

                <div className="stat-card stat-best">
                    <div className="stat-content">
                        <span className="stat-value">{profile.longestStreak}</span>
                        <span className="stat-label">Best Streak</span>
                    </div>
                </div>

                <div className="stat-card stat-answered">
                    <div className="stat-content">
                        <span className="stat-value">{profile.questionsAnsweredCorrectly
                            + profile.questionsAnsweredIncorrectly}</span>
                        <span className="stat-label">Questions Answered</span>
                    </div>
                </div>

                <div className="stat-card stat-accuracy">
                    <div className="stat-content">
                        <span className="stat-value">{(profile.accuracy * 100).toFixed(1)}%</span>
                        <span className="stat-label">Accuracy</span>
                    </div>
                </div>
            </div>
        </div>
    );
}