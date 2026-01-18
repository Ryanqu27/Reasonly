import React, { useState } from 'react';
import { useAuth } from './AuthContext';
import { useNavigate, Link } from 'react-router-dom';

export default function RegisterForm() {
    const { register } = useAuth();
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirm, setConfirm] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        if (!email || !password || !confirm) {
            setError("Please fill all fields");
            return;
        }
        if (password !== confirm) {
            setError("Passwords do not match");
            return;
        }
        setLoading(true);
        try {
            await register(email, password);
            navigate("/login");
        }
        catch (err) {
            setError(err?.message || err?.error || "Registration failed");
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

            <div>
                <label>Confirm Password</label>
                <input type="password" value={confirm} onChange={(e) => setConfirm(e.target.value)} required />
            </div>

            {error && <div stye={{ color: "red" }}>{error}</div>}

            <button type="submit" disabled={loading}>
                {loading ? "Registering..." : "Register"}
            </button>
                
            <div>
                Already have an account? <Link to="/login">Login</Link>
            </div>
        </form>
    );
}