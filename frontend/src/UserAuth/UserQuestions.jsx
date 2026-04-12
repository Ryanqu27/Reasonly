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

const MOTIVATION_LEVELS = [
    { id: 'INTERVIEW_PREP', label: 'Interview Prep', description: 'Preparing for coding interviews' },
    { id: 'ACADEMIC', label: 'Academic', description: 'Help with school or coursework' },
    { id: 'CAREER_TRANSITION', label: 'Career Transition', description: 'Switching to a software career' },
    { id: 'HOBBY', label: 'Hobby', description: 'Learning for fun' }
];

const LANGUAGE_OPTIONS = [
    { id: 'JAVA', label: 'Java'},
    { id: 'PYTHON', label: 'Python'},
    { id: 'JAVASCRIPT', label: 'JavaScript'},
    { id: 'C_PLUS_PLUS', label: 'C++'},
    { id: 'C_SHARP', label: 'C#'},
    { id: 'GO', label: 'Go'}
];

const INTEREST_OPTIONS = [
    { id: 'DATA_STRUCTURES_AND_ALGORITHMS', label: 'DSA' },
    { id: 'SYSTEMS', label: 'Systems' },
    { id: 'NETWORKING', label: 'Networking' },
    { id: 'DATABASES', label: 'Databases' },
    { id: 'CONCURRENCY', label: 'Concurrency' },
    { id: 'SOFTWARE_DESIGN', label: 'Software Design' },
    { id: 'LANGUAGE_KNOWLEDGE', label: 'Language Knowledge' }
];

export default function UserQuestions() {
    const [step, setStep] = useState(1);
    const [selectedExperience, setSelectedExperience] = useState(null);
    const [selectedMotivation, setSelectedMotivation] = useState(null);
    const [selectedLanguage, setSelectedLanguage] = useState(null);
    const [selectedInterests, setSelectedInterests] = useState([]);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadUser = async () => {
            try {
                const userData = await AuthService.fetchCurrentUser();
                setUser(userData);
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

    const handleNext = () => setStep(prev => prev + 1);
    const handleBack = () => setStep(prev => prev - 1);

    const toggleInterest = (id) => {
        setSelectedInterests(prev =>
            prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]
        );
    };

    const handleSubmit = async () => {
        if (!user) return;
        setLoading(true);
        setError(null);
        try {
            await AuthService.onboardUser(user.id, {
                experience: selectedExperience,
                motivation: selectedMotivation,
                preferredLanguage: selectedLanguage,
                interests: selectedInterests
            });
            navigate('/');
        } catch (err) {
            const errorMessage = err?.message || (typeof err === 'string' ? err : "Failed to save onboarding data");
            setError(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="user-onboarding-container">
            <div className="user-onboarding-card">

                <div className="user-wizard-progress">
                    Question {step} of 4
                </div>

                {step === 1 && (
                    <>
                        <div className="user-onboarding-header">
                            <h1 className="user-onboarding-logo">Experience</h1>
                            <p className="user-onboarding-subtitle">What is your current computer science background?</p>
                        </div>
                        <div className="user-onboarding-options">
                            {EXPERIENCE_LEVELS.map((level) => (
                                <div
                                    key={level.id}
                                    className={`user-onboarding-card ${selectedExperience === level.id ? 'selected' : ''}`}
                                    onClick={() => setSelectedExperience(level.id)}
                                >
                                    <span className="user-onboarding-title">{level.label}</span>
                                    <span className="user-onboarding-description">{level.description}</span>
                                </div>
                            ))}
                        </div>
                    </>
                )}

                {step === 2 && (
                    <>
                        <div className="user-onboarding-header">
                            <h1 className="user-onboarding-logo">Motivation</h1>
                            <p className="user-onboarding-subtitle">What is your primary goal for using Reasonly?</p>
                        </div>
                        <div className="user-onboarding-options">
                            {MOTIVATION_LEVELS.map((level) => (
                                <div
                                    key={level.id}
                                    className={`user-onboarding-card ${selectedMotivation === level.id ? 'selected' : ''}`}
                                    onClick={() => setSelectedMotivation(level.id)}
                                >
                                    <span className="user-onboarding-title">{level.label}</span>
                                    <span className="user-onboarding-description">{level.description}</span>
                                </div>
                            ))}
                        </div>
                    </>
                )}

                {step === 3 && (
                    <>
                        <div className="user-onboarding-header">
                            <h1 className="user-onboarding-logo">Language</h1>
                            <p className="user-onboarding-subtitle">Which programming language do you prefer?</p>
                        </div>
                        <div className="user-onboarding-options">
                            {LANGUAGE_OPTIONS.map((lang) => (
                                <div
                                    key={lang.id}
                                    className={`user-onboarding-card ${selectedLanguage === lang.id ? 'selected' : ''}`}
                                    onClick={() => setSelectedLanguage(lang.id)}
                                >
                                    <span className="user-onboarding-title">{lang.label}</span>
                                    <span className="user-onboarding-description">{lang.description}</span>
                                </div>
                            ))}
                        </div>
                    </>
                )}

                {step === 4 && (
                    <>
                        <div className="user-onboarding-header">
                            <h1 className="user-onboarding-logo">Interests</h1>
                            <p className="user-onboarding-subtitle">Select the areas you want to focus on (Pick as many as you'd like).</p>
                        </div>
                        <div className="user-interest-options">
                            {INTEREST_OPTIONS.map((interest) => (
                                <div
                                    key={interest.id}
                                    className={`user-interest-card ${selectedInterests.includes(interest.id) ? 'selected' : ''}`}
                                    onClick={() => toggleInterest(interest.id)}
                                >
                                    {interest.label}
                                </div>
                            ))}
                        </div>
                    </>
                )}

                {error && <div className="user-onboarding-error">{error.toString()}</div>}

                <div className="user-wizard-buttons">
                    {step > 1 && (
                        <button className="user-onboarding-button user-onboarding-button-secondary" onClick={handleBack} disabled={loading}>
                            Back
                        </button>
                    )}
                    {step < 4 ? (
                        <button
                            className="user-onboarding-button"
                            onClick={handleNext}
                            disabled={
                                (step === 1 && !selectedExperience) ||
                                (step === 2 && !selectedMotivation) ||
                                (step === 3 && !selectedLanguage)
                            }
                        >
                            Next
                        </button>
                    ) : (
                        <button
                            className="user-onboarding-button"
                            onClick={handleSubmit}
                            disabled={loading || selectedInterests.length === 0}
                        >
                            {loading ? "Saving..." : "Get Started"}
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
}