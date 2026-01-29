import QuestionCard from "./QuestionCard";
import { useState, useEffect } from 'react';
import { getQuestions, updateCompletedDate, submitQuestionAttempt } from './QuestionService'
import AuthService from '../UserAuth/AuthService'

function Questions() {
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [attemptResult, setAttemptResult] = useState(null);
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
      setAttemptResult(null);
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

    try {
      const response = await submitQuestionAttempt(questionAttempt);
      setAttemptResult(response.data);
      if (option === currentQuestion.correctAnswer) {
        updateCompletedDate();
      }
    } catch (err) {
      console.error("Failed to submit attempt", err);
    }
  };

  const handleNextQuestion = () => {
    fetchNextQuestion();
  };

  if (!sessionStarted) {
    return (
      <div className="dashboard-container">
        <div className="stat-card" style={{ textAlign: 'center', padding: '3rem' }}>
          <h2 style={{ fontSize: '2rem', marginBottom: '1rem', color: 'var(--primary)' }}>Adaptive Practice</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '2.5rem', lineHeight: '1.6' }}>
            Get questions tailored to your current rating.
          </p>
          <div>
            <button
              onClick={handleStartSession}
              className="btn-primary"
              style={{ padding: '1rem 2.5rem', fontSize: '1.125rem' }}
            >
              Start Session
            </button>
          </div>
        </div>
      </div>
    );
  }

  if (loading) return (
    <div className="dashboard-container" style={{ textAlign: 'center', padding: '3rem' }}>
      <p style={{ color: 'var(--text-muted)' }}>Preparing your next challenge...</p>
    </div>
  );

  if (error) return (
    <div className="dashboard-container">
      <div className="feedback-container incorrect">
        Error: {error}
      </div>
    </div>
  );

  if (!currentQuestion) return (
    <div className="dashboard-container">
      <div className="stat-card" style={{ textAlign: 'center', padding: '3rem' }}>
        <p style={{ color: 'var(--text-muted)', marginBottom: '1.5rem' }}>
          No more questions available for your current level. Great job!
        </p>
        <button className="btn-primary" onClick={() => window.location.reload()}>
          Refresh Dashboard
        </button>
      </div>
    </div>
  );

  return (
    <div className="dashboard-container">
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '-1rem' }}>
        <button
          onClick={() => setSessionStarted(false)}
          style={{
            background: 'none',
            border: 'none',
            color: 'var(--text-muted)',
            cursor: 'pointer',
            fontSize: '0.875rem'
          }}
        >
          &larr; Exit Session
        </button>
      </div>

      <QuestionCard
        question={currentQuestion}
        onAnswer={handleAnswerClick}
        selectedAnswer={selectedAnswer}
        showFeedback={showFeedback}
      />

      {showFeedback && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          {attemptResult && (
            <div className={`feedback-container ${attemptResult.correct ? 'correct' : 'incorrect'}`}>
              <h3 style={{ margin: '0 0 0.5rem 0', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
                {attemptResult.correct ? 'Correct!' : 'Incorrect'}
              </h3>
              <p style={{ margin: 0, fontWeight: '500' }}>
                Rating:
                <span style={{ margin: '0 0.5rem' }}>{attemptResult.newRating - attemptResult.ratingChange}</span>
                <span style={{
                  color: attemptResult.ratingChange >= 0 ? 'var(--success)' : 'var(--error)',
                  fontWeight: '700'
                }}>
                  {attemptResult.ratingChange >= 0 ? `+${attemptResult.ratingChange}` : attemptResult.ratingChange}
                </span>
                <span style={{ margin: '0 0.5rem' }}>&rarr;</span>
                <span style={{ color: 'var(--text-main)', fontWeight: '700' }}>{attemptResult.newRating}</span>
              </p>
            </div>
          )}

          <div style={{ textAlign: 'center' }}>
            <button
              onClick={handleNextQuestion}
              className="btn-primary"
              style={{ width: '100%', padding: '1rem' }}
            >
              Continue to Next Question
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Questions
