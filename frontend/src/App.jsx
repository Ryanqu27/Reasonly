import { useState, useEffect } from 'react';
import './App.css';
import { useAuth } from './UserAuth/AuthContext.jsx'
import LoginForm from './UserAuth/LoginForm.jsx';
import RegisterForm from './UserAuth/RegisterForm.jsx'
import { AuthProvider } from './UserAuth/AuthContext.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { getAllQuestions } from './QuestionService'


function Dashboard() {
  const [color, setColor] = useState("blue");
  const { logout, user, loading } = useAuth();
  
  function switchColor() {
    setColor(color === "blue" ? "red" : "blue");
  }
  
  if (loading) return <div>Loading...</div>;
  
  return (
    <div>
      <p>Current color is {color}</p>
      <button onClick={switchColor}>Click me</button>
      <button onClick={logout}>Logout</button>
        <p>Logged in as: {user.email}</p>
        <p>Id is : {user.id}</p>
        <p>Current streak is : {user.currentStreak}</p>
    </div>
  );
}

function Questions() {
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        setLoading(true);
        const response = await getAllQuestions();
        setQuestions(response.data);
      } catch (err) {
        setError(err.message);
        console.error('Error fetching questions:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchQuestions();
  }, []); // Empty dependency array - runs once on mount

  if (loading) return <div>Loading questions...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Questions</h2>
      {questions.length === 0 ? (
        <p>No questions found</p>
      ) : (
        <ul>
          {questions.map((question, index) => (
            <li key={question.id || index}>
              {JSON.stringify(question)}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuth();
  
  if (loading) return <div>Loading...</div>;
  return isAuthenticated ? children : <Navigate to="/login" />;
};

function App() {

  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Public Routes */}
          <Route path="/login" element={<LoginForm />} />
          <Route path="/register" element={<RegisterForm />} />

          {/* Private Routes for logged in users */}
          <Route path="/" element={
            <ProtectedRoute>
              <Dashboard />
              <Questions />
            </ProtectedRoute>
          } />
        </Routes>
      </Router>
    </AuthProvider>
    
    
  )
}


export default App
