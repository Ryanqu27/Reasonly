import { useState, useEffect } from 'react';
import { useAuth } from './UserAuth/AuthContext.jsx'
import LoginForm from './UserAuth/LoginForm.jsx';
import './App.css';
import RegisterForm from './UserAuth/RegisterForm.jsx'
import { AuthProvider } from './UserAuth/AuthContext.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Questions from './Questions/Questions.jsx';
import AuthService from './UserAuth/AuthService.js';
import { checkStreak } from './Questions/QuestionService.js';
import ProfilePage from './components/ProfilePage.jsx';
import Sidebar from './components/Sidebar.jsx';
import UserQuestions from './UserAuth/UserQuestions.jsx';
import Settings from './components/Settings.jsx';

// Layout component that includes the sidebar
function AppLayout({ children }) {
  return (
    <div className="app-layout">
      <Sidebar />
      <main className="main-content">
        {children}
      </main>
    </div>
  );
}

function Dashboard() {
  const { loading } = useAuth();
  const [user, setUser] = useState(null);

  const checkUserStreak = async () => {
    await checkStreak();
  }

  const loadUser = async () => {
    try {
      const userData = await AuthService.fetchCurrentUser();
      setUser(userData);
    } catch (err) {
      console.error("Failed to load user", err);
    }
  };

  useEffect(() => {
    checkUserStreak();
    loadUser();
  }, []);

  if (loading) return <div className="loading-screen">Loading...</div>;

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div>
          <h1 className="page-title">Practice Questions</h1>
          <p className="user-email">{user?.email}</p>
        </div>
      </header>

      <div className="stats-row">
        <div className="stat-card">
          <span className="stat-label">Current Rating</span>
          <span className="stat-value stat-rating">{user?.rating || 0}</span>
        </div>
        <div className="stat-card">
          <span className="stat-label">Daily Streak</span>
          <span className="stat-value">{user?.currentStreak || 0} 🔥</span>
        </div>
        <div className="stat-card">
          <span className="stat-label">Longest Streak</span>
          <span className="stat-value">{user?.longestStreak || 0} 🏆</span>
        </div>
      </div>

      <Questions onUserUpdate={loadUser} />
    </div>
  );
}


const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuth();

  if (loading) return <div className="loading-screen">Loading...</div>;
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
          <Route path="/onboarding" element={
            <ProtectedRoute>
              <UserQuestions />
            </ProtectedRoute>
          } />

          {/* Private Routes for logged in users - wrapped with sidebar layout */}
          <Route path="/" element={
            <ProtectedRoute>
              <AppLayout>
                <Dashboard />
              </AppLayout>
            </ProtectedRoute>
          } />
          <Route path="/profile" element={
            <ProtectedRoute>
              <AppLayout>
                <ProfilePage />
              </AppLayout>
            </ProtectedRoute>
          } />
          <Route path="/settings" element={
            <ProtectedRoute>
              <AppLayout>
                <Settings />
              </AppLayout>
            </ProtectedRoute>
          } />
        </Routes>
      </Router>
    </AuthProvider>
  )
}


export default App
