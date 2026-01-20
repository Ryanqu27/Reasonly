import { useState, useEffect } from 'react';
import { useAuth } from './UserAuth/AuthContext.jsx'
import LoginForm from './UserAuth/LoginForm.jsx';
import './App.css';
import RegisterForm from './UserAuth/RegisterForm.jsx'
import { AuthProvider } from './UserAuth/AuthContext.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Questions from './Questions/Questions.jsx';
import AuthService from './UserAuth/AuthService.js';

function Dashboard() {
  const [color, setColor] = useState("blue");
  const { logout, loading } = useAuth();
  const [user, setUser] = useState(null);

  useEffect(() => {
    setUser(AuthService.getUser());
  }, []);
  
  function switchColor() {
    setColor(color === "blue" ? "red" : "blue");
  }
  
  if (loading) return <div>Loading...</div>;
  
  return (
    <div>
      <p>Current color is {color}</p>
      <button onClick={switchColor}>Click me</button>
      <button onClick={logout}>Logout</button>
      
      <p>Logged in as: {user?.email}</p>
      <p>Id is : {user?.id}</p>
      <p>Current streak is : {user?.currentStreak}</p>
      
      <Questions />
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
            </ProtectedRoute>
          } />
        </Routes>
      </Router>
    </AuthProvider>
    
    
  )
}


export default App
