import React, { useState, useEffect } from 'react';

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
      return isSelected ? 'answer-button selected' : 'answer-button';
    }

    const isCorrect = question.correctAnswer.includes(option);

    if (isCorrect) return 'answer-button correct';
    if (!isCorrect && isSelected) return 'answer-button incorrect';
    return 'answer-button muted';
  };

  return (
    <div className="question-card">
      <h3 style={{ fontSize: '1.25rem', marginBottom: '1.5rem', textAlign: 'left', color: 'var(--text-main)' }}>
        {question.question}
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
              borderColor: (!showFeedback && localSelection === option) ? 'var(--primary)' : undefined
            }}
          >
            <div style={{
              width: '18px',
              height: '18px',
              borderRadius: '50%',
              border: '2px solid',
              borderColor: localSelection === option ? 'currentColor' : 'var(--text-muted)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              flexShrink: 0
            }}>
              {localSelection === option && (
                <div style={{ width: '10px', height: '10px', backgroundColor: 'currentColor', borderRadius: '50%' }} />
              )}
            </div>
            {option}
          </button>
        ))}
      </div>

      {!showFeedback && (
        <button
          onClick={() => onAnswer([localSelection])}
          className="btn-primary"
          style={{ width: '100%', marginTop: '1.5rem', padding: '1rem' }}
          disabled={!localSelection}
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
