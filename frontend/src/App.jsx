import { useState } from 'react';
import { useEffect } from 'react';
import './App.css';
import { useAuth } from './UserAuth/AuthContext.jsx'
import LoginForm from './UserAuth/LoginForm.jsx';
import RegisterForm from './UserAuth/RegisterForm.jsx'
import { AuthProvider } from './UserAuth/AuthContext.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';


function Dashboard() {
  const [color, setColor] = useState("blue");
  function switchColor() {
    if (color == "blue") {
      setColor("red");
    }
    else {
      setColor("blue");
    }
  }
  const { logout, user } = useAuth();
  return (
    <div>
      <p>Current color is {color}</p>
      <button onClick={switchColor}>Click me</button>
      <button onClick={logout}>Logout</button>
    </div>
  )
  
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
