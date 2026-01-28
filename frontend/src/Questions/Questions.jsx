import QuestionCard from "./QuestionCard";
import { useState, useEffect } from 'react';
import { getQuestions, updateCompletedDate, submitQuestionAttempt } from './QuestionService'
import AuthService from '../UserAuth/AuthService'

function Questions() {
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [showFeedback, setShowFeedback] = useState(false);
  const [sessionStarted, setSessionStarted] = useState(false);

  const fetchNextQuestion = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await getQuestions();
      // Backend returns a list with 1 item
      if (response.data && response.data.length > 0) {
        setCurrentQuestion(response.data[0]);
      } else {
        setCurrentQuestion(null);
      }
      setSelectedAnswer(null);
      setShowFeedback(false);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleStartSession = () => {
    setSessionStarted(true);
    fetchNextQuestion();
  };

  const handleAnswerClick = async (option) => {
    if (!currentQuestion) return;

    setSelectedAnswer(option);
    setShowFeedback(true);
    const user = await AuthService.fetchCurrentUser();
    const questionAttempt = {
      userId: user.id,
      questionId: currentQuestion.id,
      answer: option
    };

    submitQuestionAttempt(questionAttempt);
    if (option === currentQuestion.correctAnswer) {
      updateCompletedDate();
    }
  };

  const handleNextQuestion = () => {
    fetchNextQuestion();
  };

  if (!sessionStarted) {
    return (
      <div style={{ padding: '20px', maxWidth: '500px', margin: '0 auto', textAlign: 'center' }}>
        <h2 style={{ marginBottom: '20px' }}>Adaptive Practice</h2>
        <p style={{ marginBottom: '20px' }}>
          Questions will be selected based on your current rating.
          Correct answers increase your rating, incorrect answers decrease it.
        </p>
        <button
          onClick={handleStartSession}
          style={{
            padding: '15px 30px',
            fontSize: '18px',
            backgroundColor: '#4CAF50',
            color: 'white',
            border: 'none',
            borderRadius: '8px',
            cursor: 'pointer',
            fontWeight: 'bold'
          }}
        >
          Start Practice
        </button>
      </div>
    );
  }

  if (loading) return <div>Loading next question...</div>;
  if (error) return <div>Error: {error}</div>;

  if (!currentQuestion) return (
    <div style={{ textAlign: 'center', padding: '20px' }}>
      <p>No more questions available for your current difficulty level!</p>
      <button onClick={() => window.location.reload()} style={{ padding: '10px 20px', marginTop: '10px' }}>
        Refresh
      </button>
    </div>
  );

  return (
    <div style={{ padding: '20px' }}>
      <div style={{ marginBottom: '10px', display: 'flex', justifyContent: 'flex-end', alignItems: 'center' }}>
        <button
          onClick={() => setSessionStarted(false)}
          style={{ padding: '8px 16px', fontSize: '14px' }}
        >
          End Session
        </button>
      </div>

      <QuestionCard
        question={currentQuestion}
        onAnswer={handleAnswerClick}
        selectedAnswer={selectedAnswer}
        showFeedback={showFeedback}
      />

      {showFeedback && (
        <button
          onClick={handleNextQuestion}
          style={{ marginTop: '20px', padding: '10px 20px' }}
        >
          Next Question
        </button>
      )}
    </div>
  );
}

export default Questions