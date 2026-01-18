import React, { useState } from 'react';
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
            <div>
                <label>Email</label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>

            <div>
                <label>Password</label>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
            </div>

            {error && <div style={{ color: "red" }}>{error}</div>}

            <button type="submit" disabled={loading}>
                {loading ? "Loggin in... " : "Log in"}
            </button>

            <div>
                Don't have an account? <Link to="/register">Register</Link>
            </div>
        </form>
    );
}