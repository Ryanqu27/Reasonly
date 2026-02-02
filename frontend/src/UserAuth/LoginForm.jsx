import { useState } from 'react';
import { useAuth } from './AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import './Auth.css';

export default function LoginForm() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        if (!email || !password) {
            setError("Please fill all fields");
            return;
        }
        setLoading(true);
        try {
            await login(email, password);
            navigate("/");
        }
        catch (err) {
            setError(err?.message || err?.error || "Login failed");
        }
        finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card">
                <div className="auth-header">
                    <h1 className="auth-logo">Reasonly</h1>
                    <p className="auth-subtitle">Welcome back! Sign in to continue.</p>
                </div>

                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="auth-input-group">
                        <label className="auth-label">Email</label>
                        <input
                            className="auth-input"
                            type="email"
                            placeholder="you@example.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="auth-input-group">
                        <label className="auth-label">Password</label>
                        <input
                            className="auth-input"
                            type="password"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    {error && <div className="auth-error">{error}</div>}

                    <button className="auth-button" type="submit" disabled={loading}>
                        {loading ? "Signing in..." : "Sign In"}
                    </button>
                </form>

                <div className="auth-footer">
                    <span className="auth-footer-text">
                        Don't have an account?
                        <Link to="/register" className="auth-footer-link">Sign up</Link>
                    </span>
                </div>
            </div>
        </div>
    );
}