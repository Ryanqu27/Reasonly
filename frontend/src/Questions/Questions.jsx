import QuestionCard from "./QuestionCard";
import { useState, useEffect } from 'react';
import { getQuestions, updateCompletedDate } from './QuestionService'

// Define available question types (matching backend enum)
const QUESTION_TYPES = [
  { value: '', label: 'All Types' },
  { value: 'DATA_STRUCTURES_AND_ALGORITHMS', label: 'Data Structures & Algorithms' },
  { value: 'SYSTEMS', label: 'Systems' },
  { value: 'NETWORKING', label: 'Networking' },
  { value: 'DATABASES', label: 'Databases' },
  { value: 'CONCURRENCY', label: 'Concurrency' },
  { value: 'SOFTWARE_DESIGN', label: 'Software Design' },
  { value: 'DEBUGGING', label: 'Debugging' },
  { value: 'CODE_REASONING', label: 'Code Reasoning' },
];

function Questions() {
  const [questions, setQuestions] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [showFeedback, setShowFeedback] = useState(false);
  const [score, setScore] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);

  const [selectedType, setSelectedType] = useState('');
  const [quizStarted, setQuizStarted] = useState(false);

  const fetchQuestions = async (type) => {
    try {
      setLoading(true);
      setError(null);
      const response = await getQuestions(type || null);
      setQuestions(response.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleStartQuiz = () => {
    fetchQuestions(selectedType);
    setQuizStarted(true);
    setCurrentIndex(0);
    setScore(0);
    setQuizFinished(false);
    setSelectedAnswer(null);
    setShowFeedback(false);
  };

  const handleAnswerClick = (option) => {
    const currentQuestion = questions[currentIndex];
    setSelectedAnswer(option);
    setShowFeedback(true);

    if (option === currentQuestion.correctAnswer) {
      setScore(score + 1);
      updateCompletedDate();
    }
  };

  const handleNextQuestion = () => {
    const nextIndex = currentIndex + 1;
    if (nextIndex < questions.length) {
      setCurrentIndex(nextIndex);
      setSelectedAnswer(null);
      setShowFeedback(false);
    } else {
      setQuizFinished(true);
    }
  };

  const restartQuiz = () => {
    setCurrentIndex(0);
    setScore(0);
    setQuizFinished(false);
    setSelectedAnswer(null);
    setShowFeedback(false);
    setQuizStarted(false);
  };

  // Selection screen before quiz starts
  if (!quizStarted) {
    return (
      <div style={{ padding: '20px', maxWidth: '500px', margin: '0 auto' }}>
        <h2 style={{ marginBottom: '20px' }}>Choose Question Type</h2>

        <div style={{ marginBottom: '20px' }}>
          <label htmlFor="type-select" style={{ display: 'block', marginBottom: '10px', fontWeight: 'bold' }}>
            Select a category:
          </label>
          <select
            id="type-select"
            value={selectedType}
            onChange={(e) => setSelectedType(e.target.value)}
            style={{
              width: '100%',
              padding: '12px',
              fontSize: '16px',
              borderRadius: '8px',
              border: '1px solid #ccc',
              cursor: 'pointer'
            }}
          >
            {QUESTION_TYPES.map((type) => (
              <option key={type.value} value={type.value}>
                {type.label}
              </option>
            ))}
          </select>
        </div>

        <button
          onClick={handleStartQuiz}
          style={{
            width: '100%',
            padding: '15px',
            fontSize: '18px',
            backgroundColor: '#4CAF50',
            color: 'white',
            border: 'none',
            borderRadius: '8px',
            cursor: 'pointer',
            fontWeight: 'bold'
          }}
        >
          Start Quiz
        </button>
      </div>
    );
  }

  if (loading) return <div>Loading questions...</div>;
  if (error) return <div>Error: {error}</div>;
  if (questions.length === 0) return (
    <div style={{ textAlign: 'center', padding: '20px' }}>
      <p>No questions found for this category.</p>
      <button onClick={restartQuiz} style={{ padding: '10px 20px', marginTop: '10px' }}>
        Choose Another Category
      </button>
    </div>
  );

  if (quizFinished) {
    return (
      <div style={{ textAlign: 'center' }}>
        <h2>Quiz Complete!</h2>
        <p>You scored {score} out of {questions.length}</p>
        <button onClick={restartQuiz} style={{ padding: '10px 20px', marginRight: '10px' }}>
          Choose Another Category
        </button>
        <button onClick={handleStartQuiz} style={{ padding: '10px 20px' }}>
          Play Again (Same Category)
        </button>
      </div>
    );
  }

  return (
    <div style={{ padding: '20px' }}>
      <div style={{ marginBottom: '10px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2 style={{ margin: 0 }}>Question {currentIndex + 1} / {questions.length}</h2>
        <button
          onClick={restartQuiz}
          style={{ padding: '8px 16px', fontSize: '14px' }}
        >
          Change Category
        </button>
      </div>

      <QuestionCard
        question={questions[currentIndex]}
        onAnswer={handleAnswerClick}
        selectedAnswer={selectedAnswer}
        showFeedback={showFeedback}
      />

      {showFeedback && (
        <button
          onClick={handleNextQuestion}
          style={{ marginTop: '20px', padding: '10px 20px' }}
        >
          {currentIndex === questions.length - 1 ? "Finish Quiz" : "Next Question"}
        </button>
      )}
    </div>
  );
}

export default Questions