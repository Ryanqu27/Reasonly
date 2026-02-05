import { useState, useEffect } from 'react';
import { useAuth } from './UserAuth/AuthContext.jsx'
import LoginForm from './UserAuth/LoginForm.jsx';
import './App.css';
import RegisterForm from './UserAuth/RegisterForm.jsx'
import { AuthProvider } from './UserAuth/AuthContext.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import Questions from './Questions/Questions.jsx';
import AuthService from './UserAuth/AuthService.js';
import { checkStreak } from './Questions/QuestionService.js';
import ProfilePage from './User/ProfilePage.jsx';

function Dashboard() {
  const { logout, loading } = useAuth();
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  checkStreak();
  const loadUser = async () => {
    try {
      const userData = await AuthService.fetchCurrentUser();
      setUser(userData);
    } catch (err) {
      console.error("Failed to load user", err);
    }
  };

  useEffect(() => {
    loadUser();
  }, []);




  if (loading) return <div>Loading...</div>;

  return (
    <div className="dashboard-container">
      <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <div>
          <h1 style={{ margin: 0, fontSize: '1.5rem', color: 'var(--primary)' }}>Reasonly</h1>
          <p style={{ margin: 0, fontSize: '0.875rem', color: 'var(--text-muted)' }}>{user?.email}</p>
        </div>
        <div>
          <button
            onClick={() => navigate('/profile')}
            className="btn-secondary"
            style={{ marginRight: '0.5rem', background: 'var(--secondary)', color: 'var(--text-main)', fontSize: '0.875rem' }}
          >
            Profile
          </button>
          <button onClick={logout} className="btn-primary" style={{ background: 'var(--primary)', color: 'var(--text-main)', fontSize: '0.875rem' }}>
            Logout
          </button>
        </div>
      </header>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem' }}>
        <div className="stat-card">
          <span style={{ fontSize: '0.75rem', fontWeight: 600, color: 'var(--text-muted)', textTransform: 'uppercase' }}>Current Rating</span>
          <span style={{ fontSize: '1.5rem', fontWeight: 700, color: 'var(--primary)' }}>{user?.rating || 0}</span>
        </div>
        <div className="stat-card">
          <span style={{ fontSize: '0.75rem', fontWeight: 600, color: 'var(--text-muted)', textTransform: 'uppercase' }}>Daily Streak</span>
          <span style={{ fontSize: '1.5rem', fontWeight: 700 }}>{user?.currentStreak || 0} 🔥</span>
        </div>
        <div className="stat-card">
          <span style={{ fontSize: '0.75rem', fontWeight: 600, color: 'var(--text-muted)', textTransform: 'uppercase' }}>Longest Streak</span>
          <span style={{ fontSize: '1.5rem', fontWeight: 700 }}>{user?.longestStreak || 0} 🏆</span>
        </div>
      </div>

      <Questions onUserUpdate={loadUser} />
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
          <Route path="/profile" element={
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          } />
        </Routes>
      </Router>
    </AuthProvider>


  )
}


export default App
