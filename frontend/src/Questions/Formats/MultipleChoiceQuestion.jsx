import React, { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function MultipleChoiceQuestion({ question, onAnswer, selectedAnswer, showFeedback }) {
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
            disabled={!localSelection}
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
