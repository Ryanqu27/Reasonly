import React, { useState, useEffect } from 'react';
import './QuestionFormats.css';

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
                        disabled={localSelection.length === 0}
                    >
                        Submit
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
