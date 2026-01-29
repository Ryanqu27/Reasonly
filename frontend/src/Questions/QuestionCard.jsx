import React from 'react';

export default function QuestionCard({ question, onAnswer, selectedAnswer, showFeedback }) {
  if (!question || !question.answers) {
    return <div>Loading question...</div>;
  }

  const getButtonClass = (option) => {
    if (!showFeedback) return 'answer-button';
    if (option === question.correctAnswer) return 'answer-button correct';
    if (selectedAnswer === option) return 'answer-button incorrect';
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
            onClick={() => onAnswer(option)}
            disabled={showFeedback}
            className={getButtonClass(option)}
          >
            {option}
          </button>
        ))}
      </div>

      <div style={{
        display: 'flex',
        justifyContent: 'space-between',
        marginTop: '1.5rem',
        fontSize: '0.875rem',
        color: 'var(--text-muted)'
      }}>
        <span>{question.type}</span>
        <span style={{ fontWeight: '600', color: 'var(--primary)' }}>{question.difficulty}</span>
      </div>
    </div>
  );
}
