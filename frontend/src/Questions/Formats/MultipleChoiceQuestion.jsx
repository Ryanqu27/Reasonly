import React, { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function MultipleChoiceQuestion({ question, onAnswer, selectedAnswer, showFeedback, isSubmitting }) {
  const [localSelection, setLocalSelection] = useState(null);

  useEffect(() => {
    if (selectedAnswer && selectedAnswer.length > 0) {
      setLocalSelection(selectedAnswer[0]);
    } else {
      setLocalSelection(null);
    }
  }, [selectedAnswer, question.id]);

  if (!question || !question.answers) {
    return <div>Loading question...</div>;
  }

  const toggleSelection = (option) => {
    if (showFeedback) return;
    setLocalSelection(option);
  };

  const getButtonClass = (option) => {
    const isSelected = localSelection === option;

    if (!showFeedback) {
      return isSelected ? 'format-answer-button selected' : 'format-answer-button';
    }

    const isCorrect = question.correctAnswer.includes(option);

    if (isCorrect) return 'format-answer-button correct';
    if (!isCorrect && isSelected) return 'format-answer-button incorrect';
    return 'format-answer-button muted';
  };

  return (
    <div className="question-card">
      <h3 className="format-question-text">
        {question.question}
      </h3>
      <div className="format-answers-grid">
        {question.answers.map((option, index) => (
          <button
            key={index}
            onClick={() => toggleSelection(option)}
            disabled={showFeedback}
            className={getButtonClass(option)}
          >
            <div className="format-radio-circle">
              <div className="format-radio-dot" />
            </div>
            {option}
          </button>
        ))}
      </div>

      {!showFeedback && (
        <div className="format-submit-block">
          <button
            onClick={() => onAnswer([localSelection])}
            className="format-submit-btn"
            disabled={!localSelection || isSubmitting}
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
