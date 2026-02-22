import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from './AuthService';
import './Auth.css';

const EXPERIENCE_LEVELS = [
    { id: 'BEGINNER', label: 'Beginner', description: 'New to computer science' },
    { id: 'INTERMEDIATE', label: 'Intermediate', description: 'Some coursework or basic projects' },
    { id: 'ADVANCED', label: 'Advanced', description: 'Degree or job experience' },
    { id: 'EXPERT', label: 'Expert', description: 'Senior level or competitive coder' }
];

export default function UserQuestions() {
    const [selectedLevel, setSelectedLevel] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadUser = async () => {
            try {
                const userData = await AuthService.fetchCurrentUser();
                setUser(userData);
                // If user already has experience, skip onboarding
                if (userData.experience) {
                    navigate('/');
                }
            } catch (err) {
                console.error("Failed to load user in onboarding", err);
                navigate('/login');
            }
        };
        loadUser();
    }, [navigate]);

    const handleSubmit = async () => {
        if (!selectedLevel || !user) return;

        setLoading(true);
        setError(null);
        try {
            await AuthService.onboardUser(user.id, selectedLevel);
            navigate('/');
        } catch (err) {
            const errorMessage = err?.message || (typeof err === 'string' ? err : "Failed to save experience level");
            setError(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card auth-card-lg">
                <div className="auth-header">
                    <h1 className="auth-logo">Reasonly</h1>
                    <p className="auth-subtitle">Help us tailor your experience by sharing your computer science background.</p>
                </div>

                <div className="experience-options">
                    {EXPERIENCE_LEVELS.map((level) => (
                        <div
                            key={level.id}
                            className={`experience-card ${selectedLevel === level.id ? 'selected' : ''}`}
                            onClick={() => setSelectedLevel(level.id)}
                        >
                            <span className="experience-title">{level.label}</span>
                            <span className="experience-description">{level.description}</span>
                        </div>
                    ))}
                </div>

                {error && <div className="auth-error onboarding-error">{error.toString()}</div>}

                <button
                    className="auth-button"
                    onClick={handleSubmit}
                    disabled={!selectedLevel || loading}
                >
                    {loading ? "Saving..." : "Get Started"}
                </button>
            </div>
        </div>
    );
}