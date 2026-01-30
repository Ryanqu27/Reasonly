import { useState } from 'react';
import { useAuth } from './AuthContext';
import { useNavigate, Link } from 'react-router-dom';

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
        <form onSubmit={handleSubmit}>
            <h1 style={{ textAlign: 'center', fontSize: '1.5rem', fontWeight: 600, color: 'var(--primary)' }}>Welcome back! Please login to continue</h1>
            <div>
                <input type="email" placeholder='Email' value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>

            <div>
                <input type="password" placeholder='Password' value={password} onChange={(e) => setPassword(e.target.value)} required />
            </div>

            {error && <div style={{ color: "red" }}>{error}</div>}

            <button style={{ background: 'var(--primary)', color: 'var(--text-main)', fontSize: '0.875rem', padding: '0.5rem 1rem', borderRadius: '5px', border: 'none', cursor: 'pointer' }} type="submit" disabled={loading}>
                {loading ? "Loggin in... " : "Log in"}
            </button>

            <div>
                Don't have an account? <Link to="/register" style={{ color: 'var(--primary)' }}>Register</Link>
            </div>
        </form>
    );
}