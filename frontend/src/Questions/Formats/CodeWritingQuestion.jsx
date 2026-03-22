import { useState, useEffect } from 'react';
import Editor from '@monaco-editor/react';

export default function CodeWritingQuestion({ question, onAnswer, selectedAnswer, showFeedback, isSubmitting }) {
    const [input, setInput] = useState("");
    const [language, setLanguage] = useState("JAVA");
    
    // Maps Backend Enum -> Monaco Editor language string
    const LANGUAGE_MAP = {
        "JAVA": "java",
        "PYTHON": "python",
        "JAVASCRIPT": "javascript"
    };

    const getBoilerplate = (lang, method) => {
        const m = method || "methodName";
        if (lang === "JAVA") {
            return `class Solution {\n    // Write your ${m} method here\n    \n}`;
        } else if (lang === "PYTHON") {
            return `def ${m}(...):\n    # Write your code here\n    pass`;
        } else if (lang === "JAVASCRIPT") {
            return `function ${m}(...) {\n    // Write your code here\n    \n}\n\n// Required for execution\nmodule.exports = { ${m} };`;
        }
        return "";
    };

    useEffect(() => {
        if (selectedAnswer) {
            setInput(selectedAnswer[0]);
            if (selectedAnswer[1]) {
                setLanguage(selectedAnswer[1]);
            }
        } else {
            setInput(getBoilerplate(language, question.methodName));
        }
    }, [selectedAnswer, question.methodName]);

    const handleLanguageChange = (newLang) => {
        // Only swap boilerplate if the user hasn't heavily modified the existing one
        const currentBoilerplate = getBoilerplate(language, question.methodName);
        if (input === "" || input === currentBoilerplate) {
            setInput(getBoilerplate(newLang, question.methodName));
        }
        setLanguage(newLang);
    };

    return (
        <div className="question-card">
            <h3 className="format-question-text">
                <span className="format-subtitle">
                    Write the code for the following method:
                </span>
                {question.question}
            </h3>
            
            <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '1rem' }}>
                {['JAVA', 'PYTHON', 'JAVASCRIPT'].map((lang) => (
                    <button
                        key={lang}
                        onClick={() => handleLanguageChange(lang)}
                        style={{
                            padding: '0.5rem 1rem',
                            border: 'none',
                            borderRadius: '4px',
                            background: language === lang ? 'var(--primary)' : '#2d2d2d',
                            color: language === lang ? '#fff' : '#aaa',
                            cursor: 'pointer',
                            fontWeight: language === lang ? 'bold' : 'normal',
                            fontSize: '0.8rem'
                        }}
                    >
                        {lang}
                    </button>
                ))}
            </div>

            <div style={{ border: '1px solid #444', borderRadius: '4px', overflow: 'hidden' }}>
                <code>
                    <Editor
                        height="400px"
                        language={LANGUAGE_MAP[language]}
                        theme="vs-dark"
                        value={input}
                        onChange={(value) => setInput(value)}
                    />
                </code>
            </div>

            {!showFeedback && (
                <div className="format-submit-block">
                    <button
                        onClick={() => onAnswer([input.trim(), language])}
                        className="format-submit-btn"
                        disabled={!input.trim() || isSubmitting}
                    >
                        {isSubmitting ? (
                            <span style={{ display: 'inline-flex', alignItems: 'center', gap: '8px' }}>
                                <svg
                                    style={{ animation: 'spin 1s linear infinite' }}
                                    width="16" height="16" viewBox="0 0 24 24" fill="none"
                                    stroke="currentColor" strokeWidth="3" strokeLinecap="round"
                                >
                                    <path d="M12 2v4m0 12v4M4.93 4.93l2.83 2.83m8.48 8.48l2.83 2.83M2 12h4m12 0h4M4.93 19.07l2.83-2.83m8.48-8.48l2.83-2.83" />
                                </svg>
                                <style>{`@keyframes spin { 100% { transform: rotate(360deg); } }`}</style>
                                Running Code...
                            </span>
                        ) : (
                            'Submit'
                        )}
                    </button>
                </div>
            )}

            <div className="format-footer">
                <span>{question.topic}</span>
                <span className="format-difficulty">{question.difficulty}</span>
            </div>
        </div>
    )
}