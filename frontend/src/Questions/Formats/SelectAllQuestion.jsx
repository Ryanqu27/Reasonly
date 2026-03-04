import React, { useState, useEffect } from 'react';

export default function SelectAllQuestion({ question, onAnswer, selectedAnswer, showFeedback }) {
    const [localSelection, setLocalSelection] = useState([]);

    // Default the local selection to what the parent holds, in case of re-renders
    useEffect(() => {
        if (selectedAnswer && selectedAnswer.length > 0) {
            setLocalSelection(selectedAnswer);
        } else {
            setLocalSelection([]);
        }
    }, [selectedAnswer, question.id]);

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
            return isSelected ? 'answer-button selected' : 'answer-button';
        }

        const isCorrect = question.correctAnswer.includes(option);

        // Show green if correct and selected, OR if correct and missed
        if (isCorrect) return 'answer-button correct';

        // Show red if incorrect but selected
        if (!isCorrect && isSelected) return 'answer-button incorrect';

        // Otherwise muted
        return 'answer-button muted';
    };

    return (
        <div className="question-card">
            <h3 style={{ fontSize: '1.25rem', marginBottom: '1.5rem', textAlign: 'left', color: 'var(--text-main)' }}>
                {question.question}
                <span style={{ fontSize: '0.875rem', color: 'var(--text-muted)', display: 'block', marginTop: '0.5rem', fontWeight: 'normal' }}>
                    Select all that apply.
                </span>
            </h3>

            <div className="answers-grid" style={{ display: 'grid', gap: '0.75rem' }}>
                {question.answers.map((option, index) => (
                    <button
                        key={index}
                        onClick={() => toggleSelection(option)}
                        disabled={showFeedback}
                        className={getButtonClass(option)}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            gap: '0.75rem',
                            textAlign: 'left',
                            // Add a subtle border highlight when selected pre-submission
                            borderColor: (!showFeedback && localSelection.includes(option)) ? 'var(--primary)' : undefined
                        }}
                    >
                        <div style={{
                            width: '18px',
                            height: '18px',
                            borderRadius: '4px',
                            border: '2px solid',
                            borderColor: localSelection.includes(option) ? 'currentColor' : 'var(--text-muted)',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            flexShrink: 0
                        }}>
                            {localSelection.includes(option) && (
                                <div style={{ width: '10px', height: '10px', backgroundColor: 'currentColor', borderRadius: '2px' }} />
                            )}
                        </div>
                        {option}
                    </button>
                ))}
            </div>

            {!showFeedback && (
                <button
                    onClick={() => onAnswer(localSelection)}
                    className="btn-primary"
                    style={{ width: '100%', marginTop: '1.5rem', padding: '1rem' }}
                    disabled={localSelection.length === 0}
                >
                    Submit
                </button>
            )}

            <div style={{
                display: 'flex',
                justifyContent: 'space-between',
                marginTop: '1.5rem',
                fontSize: '0.875rem',
                color: 'var(--text-muted)'
            }}>
                <span>{question.topic}</span>
                <span style={{ fontWeight: '600', color: 'var(--primary)' }}>{question.difficulty}</span>
            </div>
        </div>
    );
}
