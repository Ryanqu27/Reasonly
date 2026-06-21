import MultipleChoiceQuestion from "./Formats/MultipleChoiceQuestion";
import SelectAllQuestion from "./Formats/SelectAllQuestion";
import FindTheBugQuestion from "./Formats/FindTheBugQuestion";
import FillInTheBlankQuestion from "./Formats/FillInTheBlankQuestion";
import OrderCodeQuestion from "./Formats/OrderCodeQuestion";
import CodeWritingQuestion from "./Formats/CodeWritingQuestion";
import { useState, useEffect } from 'react';
import { getQuestions, updateCompletedDate, submitQuestionAttempt, resetQuestionAttempts, runCode } from './QuestionService'
import { useAuth } from '../UserAuth/AuthContext.jsx';

function Questions() {
  const { user, setUser } = useAuth();

  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [attemptResult, setAttemptResult] = useState(null);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [showFeedback, setShowFeedback] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [sessionStarted, setSessionStarted] = useState(false);
  const [preferredLanguage, setPreferredLanguage] = useState(null);
  const [editorFontSize, setEditorFontSize] = useState(null);
  const [editorTheme, setEditorTheme] = useState('vs-dark');
  const [editorTabSize, setEditorTabSize] = useState(4);

  useEffect(() => {
        if (user?.userSettings) {
            setPreferredLanguage(user.userSettings.preferredLanguage);
            setEditorFontSize(user.userSettings.editorFontSize);
            setEditorTheme(user.userSettings.editorTheme || 'vs-dark');
            setEditorTabSize(user.userSettings.editorTabSize || 4);
        }
    }, [user]);  

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

  const handleAnswerClick = async (answerArray) => {
    if (!currentQuestion) return;

    setSelectedAnswer(answerArray);
    setIsSubmitting(true);

    const questionAttempt = {
      userId: user?.id,
      questionId: currentQuestion.id,
      answer: answerArray
    };

    try {
      const response = await submitQuestionAttempt(questionAttempt);
      setAttemptResult(response.data);

      if (response.data.correct) {
        const updateResponse = await updateCompletedDate();
        setUser(updateResponse.data);
      } else {
        setUser(response.data.user);
      }
    } catch (err) {
      console.error("Failed to submit attempt", err);
    } finally {
      setIsSubmitting(false);
      setShowFeedback(true);
    }
  };

  const handleRunCode = async (userCodeRequest) => {
    const runCodeRequest = {
      userCode: userCodeRequest[0],
      questionId: currentQuestion.id,
      language: userCodeRequest[1],
    };

    try {
      const response = await runCode(runCodeRequest);
      return response;
    } catch (err) {
      console.error("Failed to run code", err);
      throw err;
    }
  };

  const handleNextQuestion = () => {
    fetchNextQuestion();
  };

  const [showResetConfirm, setShowResetConfirm] = useState(false);

  const handleResetQuestions = async () => {
    setShowResetConfirm(false);
    await resetQuestionAttempts();
    fetchNextQuestion();
  };

  if (!sessionStarted) {
    return (
      <div className="dashboard-container">
        <div className="stat-card" style={{ textAlign: 'center', padding: '3rem' }}>
          <h2 style={{ fontSize: '2rem', marginBottom: '1rem', color: 'var(--primary)' }}>Adaptive Practice</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '2.5rem', lineHeight: '1.6' }}>
            Solve questions tailored to your current rating
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
      <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
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
      <div className="stat-card" style={{ textAlign: 'center', padding: '3rem' }}>
        <p style={{ color: 'var(--text-muted)', marginBottom: '1.5rem' }}>
          No more questions available for your current level today.
          <br />
          Come back tomorrow for more questions or reset all question history to start over.
        </p>

        {!showResetConfirm ? (
          <button className="btn-primary" onClick={() => setShowResetConfirm(true)}>
            Reset All Question History
          </button>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', alignItems: 'center' }}>
            <p style={{ color: 'var(--text-muted)', fontWeight: 'bold' }}>
              Are you sure? This will delete all your progress.
            </p>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <button
                className="btn-primary"
                onClick={handleResetQuestions}
                style={{ backgroundColor: 'var(--error)', borderColor: 'var(--error)' }}
              >
                Confirm Reset
              </button>
              <button
                className="btn-secondary"
                onClick={() => setShowResetConfirm(false)}
                style={{
                  background: 'transparent',
                  border: '1px solid var(--text-muted)',
                  color: 'var(--text-muted)',
                  padding: '0.5rem 1rem',
                  borderRadius: '0.25rem',
                  cursor: 'pointer'
                }}
              >
                Cancel
              </button>
            </div>
          </div>
        )}
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

      {/* Question Component rendering based on type */}
      {currentQuestion.type === 'MULTIPLE_CHOICE' ? (
        <MultipleChoiceQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
        />
      ) : currentQuestion.type === 'SELECT_ALL' ? (
        <SelectAllQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
        />
      ) : currentQuestion.type === 'FIND_THE_BUG' ? (
        <FindTheBugQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
        />
      ) : currentQuestion.type === 'FILL_IN_THE_BLANK' ? (
        <FillInTheBlankQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
        />
      ) : currentQuestion.type === 'ORDER_CODE' ? (
        <OrderCodeQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
        />
      ) : currentQuestion.type === 'CODE_WRITING' ? (
        <CodeWritingQuestion
          question={currentQuestion}
          onAnswer={handleAnswerClick}
          selectedAnswer={selectedAnswer}
          showFeedback={showFeedback}
          isSubmitting={isSubmitting}
          runCode={handleRunCode}
          preferredLanguage={preferredLanguage}
          editorFontSize={editorFontSize}
          editorTheme={editorTheme}
          editorTabSize={editorTabSize}
        />
      ) : (
        <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>
          Unsupported question format: {currentQuestion.type}
        </div>
      )}

      {/* Feedback & Next */}
      {showFeedback && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          {attemptResult && (
            <div className={`feedback-container ${attemptResult.correct ? 'correct' : 'incorrect'}`}>
              <h3 style={{ margin: '0 0 0.5rem 0', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
                {attemptResult.correct ? 'Correct!' : 'Incorrect'}
              </h3>
              
              {attemptResult.errorMessage && (
                  <div style={{ textAlign: 'left', backgroundColor: '#1e1e1e', color: '#ff7b72', padding: '1rem', borderRadius: '0.5rem', fontFamily: 'monospace', whiteSpace: 'pre-wrap', marginBottom: '1rem', fontSize: '0.9rem', overflowX: 'auto', border: '1px solid #444' }}>
                      <strong style={{color: '#fff', display: 'block', marginBottom: '0.5rem'}}>Execution Error / Failed Test:</strong>
                      {attemptResult.errorMessage}
                  </div>
              )}
              {attemptResult.consoleOutput && !attemptResult.errorMessage && (
                  <div style={{ textAlign: 'left', backgroundColor: '#1e1e1e', color: '#7ee787', padding: '1rem', borderRadius: '0.5rem', fontFamily: 'monospace', whiteSpace: 'pre-wrap', marginBottom: '1rem', fontSize: '0.9rem', overflowX: 'auto', border: '1px solid #444' }}>
                      <strong style={{color: '#fff', display: 'block', marginBottom: '0.5rem'}}>Execution Output:</strong>
                      {attemptResult.consoleOutput}
                  </div>
              )}

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
