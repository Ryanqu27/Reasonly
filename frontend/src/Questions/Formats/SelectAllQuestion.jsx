import React, { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function SelectAllQuestion({ question, onAnswer, selectedAnswer, showFeedback, isSubmitting }) {
    const [localSelection, setLocalSelection] = useState([]);

    // Default the local selection to what the parent holds, in case of re-renders
    useEffect(() => {
        if (selectedAnswer && selectedAnswer.length > 0) {
            setLocalSelection(selectedAnswer);
        } else {
            setLocalSelection([]);
        }
    }, [selectedAnswer, question.id]);

    useEffect(() => {
        const handleKeyDown = (e) => {
            if (e.key === 'Enter' && !showFeedback && !isSubmitting && localSelection.length > 0) {
                e.preventDefault();
                onAnswer(localSelection);
            }
        };

        window.addEventListener('keydown', handleKeyDown);
        return () => window.removeEventListener('keydown', handleKeyDown);
    }, [showFeedback, isSubmitting, localSelection, onAnswer]);

    if (!question || !question.answers) {
        return <div>Loading question...</div>;
    }

    const toggleSelection = (option) => {
        if (showFeedback) return;

        setLocalSelection(prev => {
            if (prev.includes(option)) {
                return prev.filter(item => item !== option);
            } else {
                return [...prev, option];
            }
        });
    };

    const getButtonClass = (option) => {
        const isSelected = localSelection.includes(option);

        if (!showFeedback) {
            return isSelected ? 'format-answer-button selected' : 'format-answer-button';
        }

        const isCorrect = question.correctAnswer.includes(option);

        // Show green if correct and selected, OR if correct and missed
        if (isCorrect) return 'format-answer-button correct';

        // Show red if incorrect but selected
        if (!isCorrect && isSelected) return 'format-answer-button incorrect';

        // Otherwise muted
        return 'format-answer-button muted';
    };

    return (
        <div className="question-card">
            <h3 className="format-question-text">
                {question.question}
                <span className="format-subtitle">
                    Select all that apply.
                </span>
            </h3>

            <div className="format-answers-grid">
                {question.answers.map((option, index) => (
                    <button
                        key={index}
                        onClick={() => toggleSelection(option)}
                        disabled={showFeedback}
                        className={getButtonClass(option)}
                    >
                        <div className="format-checkbox-square">
                            <div className="format-checkbox-fill" />
                        </div>
                        {option}
                    </button>
                ))}
            </div>

            {!showFeedback && (
                <div className="format-submit-block">
                    <button
                        onClick={() => onAnswer(localSelection)}
                        className="format-submit-btn"
                        disabled={localSelection.length === 0 || isSubmitting}
                    >
                        {isSubmitting ? (
                            <span style={{ display: 'inline-flex', alignItems: 'center', gap: '8px' }}>
                                <svg
                                    style={{ animation: 'spin 1s linear infinite' }}
                                    width="16" height="16" viewBox="0 0 24 24" fill="none"
                                    stroke="currentColor" strokeWidth="3" strokeLinecap="round"
                                >
                                    <path d="M12 2v4m0 12v4M4.93 4.93l2.83 2.83m8.48 8.48l2.83 2.83M2 12h4m12 0h4M4.93 19.07l2.83-2.83m8.48-8.48l2.83-2.83" />
                                </svg>
                                <style>{`@keyframes spin { 100% { transform: rotate(360deg); } }`}</style>
                                Submitting...
                            </span>
                        ) : (
                            'Submit'
                        )}
                    </button>
                </div>
            )}

            <div className="format-footer">
                <span>{question.topic}</span>
                <span className="format-difficulty">{question.difficulty}</span>
            </div>
        </div>
    );
}
