import React from 'react';

export default function QuestionCard({ question, onAnswer, selectedAnswer, showFeedback }) {
  if (!question || !question.answers) {
    return <div>Loading question...</div>;
  }

  // Determine button color based on answer status
  const getButtonColor = (option) => {
    if (!showFeedback) return ''; 
    if (option === question.correctAnswer) return 'green'; 
    if (selectedAnswer === option) return 'red'; 
    return '';
  };

  return (
    <div className="question-card" style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', maxWidth: '600px' }}>
      <h3>{question.question}</h3>
      <div className="answers-grid" style={{ display: 'grid', gap: '10px' }}>
        {question.answers.map((option, index) => (
          <button
            key={index}
            onClick={() => onAnswer(option)}
            disabled={showFeedback} 
            style={{ 
                backgroundColor: getButtonColor(option), 
                padding: '10px', 
                cursor: showFeedback ? 'default' : 'pointer'
            }}
          >
            {option}
          </button>
        ))}
      </div>
      
      <p style={{ fontSize: '0.8rem', color: '#666', marginTop: '15px' }}>
        {question.difficulty} | {question.type}
      </p>
    </div>
  );
}