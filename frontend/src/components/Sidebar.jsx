import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../UserAuth/AuthContext.jsx';
import './Sidebar.css';

export default function Sidebar() {
    const location = useLocation();
    const navigate = useNavigate();
    const { logout } = useAuth();

    const navItems = [
        {
            id: 'questions',
            label: 'Questions',
            icon: '📝',
            path: '/'
        },
        {
            id: 'profile',
            label: 'Profile',
            icon: '👤',
            path: '/profile'
        },
        {
            id: 'settings',
            label: 'Settings',
            icon: '⚙️',
            path: '/settings'
        }
    ];

    const isActive = (path) => {
        return location.pathname === path;
    };

    return (
        <aside className="sidebar">
            <div className="sidebar-header">
                <h1 className="sidebar-logo">Reasonly</h1>
            </div>

            <nav className="sidebar-nav">
                {navItems.map((item) => (
                    <button
                        key={item.id}
                        className={`nav-item ${isActive(item.path) ? 'active' : ''}`}
                        onClick={() => navigate(item.path)}
                    >
                        <span className="nav-icon">{item.icon}</span>
                        <span className="nav-label">{item.label}</span>
                    </button>
                ))}
            </nav>

            <div className="sidebar-footer">
                <button className="logout-button" onClick={logout}>
                    <span className="nav-icon">🚪</span>
                    <span className="nav-label">Logout</span>
                </button>
            </div>
        </aside>
    );
}
