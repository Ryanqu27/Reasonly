import QuestionCard from "./QuestionCard";
import { useState, useEffect } from 'react';
import { getAllQuestions } from './QuestionService'

function Questions() {
  const [questions, setQuestions] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [showFeedback, setShowFeedback] = useState(false);
  const [score, setScore] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        setLoading(true);
        const response = await getAllQuestions();
        setQuestions(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchQuestions();
  }, []);

  const handleAnswerClick = (option) => {
    const currentQuestion = questions[currentIndex];
    setSelectedAnswer(option);
    setShowFeedback(true);

    if (option === currentQuestion.correctAnswer) {
      setScore(score + 1);
      // TODO: Call backend here to increment user streak!
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
  }

  if (loading) return <div>Loading questions...</div>;
  if (error) return <div>Error: {error}</div>;
  if (questions.length === 0) return <div>No questions found.</div>;

  if (quizFinished) {
    return (
      <div style={{ textAlign: 'center' }}>
        <h2>Quiz Complete!</h2>
        <p>You scored {score} out of {questions.length}</p>
        <button onClick={restartQuiz}>Play Again</button>
      </div>
    );
  }

  return (
    <div style={{ padding: '20px' }}>
      <h2>Question {currentIndex + 1} / {questions.length}</h2>
      
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