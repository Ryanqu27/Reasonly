import { useState, useEffect } from 'react';
import Editor from '@monaco-editor/react';
import './QuestionFormats.css';

const SyntaxHighlightedOutput = ({ text, theme }) => {
    if (!text) return null;
    const isDark = theme === 'vs-dark';
    const themeClass = isDark ? 'theme-dark' : 'theme-light';
    
    return (
        <pre className={`code-syntax-output ${themeClass}`}>
            {text.split('\n').map((line, i) => {
                let lineClass = `output-line-default ${themeClass}`;
                
                if (line.startsWith('Expected:')) {
                    lineClass = `output-line-expected ${themeClass}`;
                } else if (line.startsWith('Got:')) {
                    lineClass = `output-line-got ${themeClass}`;
                } else if (line.includes('Error') || line.includes('Exception') || line.startsWith('Test case')) {
                    lineClass = `output-line-error ${themeClass}`;
                } else if (line.trim().startsWith('at ') || line.includes('.java:') || line.includes('line ') || line.includes('File "')) {
                    lineClass = `output-line-path ${themeClass}`;
                } else if (line.includes('✓')) {
                    lineClass = `output-line-success ${themeClass}`;
                } else if (line.includes('✗')) {
                    lineClass = `output-line-error ${themeClass}`;
                }

                return (
                    <div key={i} className={lineClass}>
                        {line}
                    </div>
                );
            })}
        </pre>
    );
};

export default function CodeWritingQuestion({ question, onAnswer, selectedAnswer, 
    showFeedback, isSubmitting, runCode, preferredLanguage, editorFontSize,
    editorTheme, editorTabSize }) {
    const [language, setLanguage] = useState(preferredLanguage);
    const [fontSize] = useState(editorFontSize);
    const [localRunResult, setLocalRunResult] = useState(null);
    const [isRunning, setIsRunning] = useState(false);
    const [showResetConfirm, setShowResetConfirm] = useState(false);
    const [showSubmitConfirm, setShowSubmitConfirm] = useState(false);

    // Maps Backend Enum -> Monaco Editor language string
    const LANGUAGE_MAP = {
        "JAVA": "java",
        "PYTHON": "python",
        "JAVASCRIPT": "javascript",
        "C_PLUS_PLUS": "cpp",
        "C_SHARP": "csharp",
        "GO": "go"
    };

    const LANGUAGE_LABELS = {
        "JAVA": "Java",
        "PYTHON": "Python",
        "JAVASCRIPT": "JavaScript",
        "C_PLUS_PLUS": "C++",
        "C_SHARP": "C#",
        "GO": "Go"
    };

    const getBoilerplate = (lang, method, tabSize = 4) => {
        const m = method || "methodName";
        const indent = " ".repeat(tabSize);
        if (lang === "JAVA") {
            return `class Solution {\n${indent}// Write your ${m} method here\n${indent}\n}`;
        } else if (lang === "PYTHON") {
            return `def ${m}(...):\n${indent}# Write your code here\n${indent}pass`;
        } else if (lang === "JAVASCRIPT") {
            return `function ${m}(...) {\n${indent}// Write your code here\n${indent}\n}\n\n// Required for execution\nmodule.exports = { ${m} };`;
        } else if (lang === "C_PLUS_PLUS") {
            return `#include <iostream>\n#include <string>\n#include <vector>\nusing namespace std;\n\n// Write your ${m} function here\n`;
        } else if (lang === "C_SHARP") {
            return `using System;\nusing System.Collections.Generic;\n\npublic class Solution {\n${indent}// Write your ${m} method here\n${indent}\n}`;
        } else if (lang === "GO") {
            return `package main\n\n\n// Write your ${m} function here\n`;
        }
        return "";
    };

    // Separate editor state per language
    const [codeByLang, setCodeByLang] = useState(() => {
        const stored = localStorage.getItem(`code-draft-${question.id}`);
        if (stored) {
            try {
                return JSON.parse(stored);
            } catch (e) {
                console.error("Failed to parse stored code", e);
            }
        }
        return {
            JAVA: getBoilerplate("JAVA", question.methodName, editorTabSize),
            PYTHON: getBoilerplate("PYTHON", question.methodName, editorTabSize),
            JAVASCRIPT: getBoilerplate("JAVASCRIPT", question.methodName, editorTabSize),
            C_PLUS_PLUS: getBoilerplate("C_PLUS_PLUS", question.methodName, editorTabSize),
            C_SHARP: getBoilerplate("C_SHARP", question.methodName, editorTabSize),
            GO: getBoilerplate("GO", question.methodName, editorTabSize),
        };
    });

    useEffect(() => {
        localStorage.setItem(`code-draft-${question.id}`, JSON.stringify(codeByLang));
    }, [codeByLang, question.id]);

    useEffect(() => {
        if (selectedAnswer) {
            const lang = selectedAnswer[1] || "JAVA";
            setLanguage(lang);
            setCodeByLang(prev => ({ ...prev, [lang]: selectedAnswer[0] }));
        }
    }, [selectedAnswer, question.methodName]);

    const handleCodeChange = (value) => {
        setCodeByLang(prev => ({ ...prev, [language]: value }));
    };

    const handleResetCode = () => {
        setShowResetConfirm(true);
    };

    const confirmReset = () => {
        const boilerplate = getBoilerplate(language, question.methodName, editorTabSize);
        setCodeByLang(prev => ({ ...prev, [language]: boilerplate }));
        setShowResetConfirm(false);
    };

    const cancelReset = () => {
        setShowResetConfirm(false);
    };

    const confirmSubmit = () => {
        setShowSubmitConfirm(false);
        onAnswer([currentCode.trim(), language]);
    };

    const cancelSubmit = () => {
        setShowSubmitConfirm(false);
    };

    const currentCode = codeByLang[language] || "";

    const onRunClick = async () => {
        setIsRunning(true);
        setLocalRunResult(null);
        try {
            const result = await runCode([currentCode.trim(), language]);
            setLocalRunResult(result.data);
        } catch {
            setLocalRunResult({ success: false, errorMessage: "Failed to connect to execution server." });
        } finally {
            setIsRunning(false);
        }
    };

    return (
        <div className="question-card">
            <h3 className="format-question-text">
                <span className="format-subtitle">
                    Write the code for the following method:
                </span>
                {question.question}
            </h3>

            <div className="code-language-bar">
                {['JAVA', 'PYTHON', 'JAVASCRIPT', 'C_PLUS_PLUS', 'C_SHARP', 'GO'].map((lang) => (
                    <button
                        key={lang}
                        onClick={() => setLanguage(lang)}
                        className={`code-language-btn ${language === lang ? 'active' : ''}`}
                    >
                        {LANGUAGE_LABELS[lang]}
                    </button>
                ))}
            </div>

            <div className="code-editor-wrapper">
                <code>
                    <Editor
                        height="400px"
                        language={LANGUAGE_MAP[language]}
                        theme={editorTheme || "vs-dark"}
                        value={currentCode}
                        onChange={handleCodeChange}
                        options={{ 
                            fontSize: fontSize,
                            tabSize: editorTabSize,
                            detectIndentation: false
                        }}
                    />
                </code>
            </div>

            <div className="code-action-row" style={{ display: 'flex', gap: '10px' }}>
                <button
                    onClick={handleResetCode}
                    className="code-reset-btn"
                    disabled={isSubmitting || isRunning}
                >
                    Reset Code
                </button>
                <button
                    onClick={onRunClick}
                    className="code-run-btn"
                    disabled={!currentCode.trim() || isSubmitting || isRunning}
                >
                    {isRunning ? "Running..." : "Run Code"}
                </button>
            </div>

            {localRunResult && (
                <div className="code-run-result">
                    <h4 className={localRunResult.success ? 'success' : 'failure'}>
                        {localRunResult.success ? "✓ All Sample Test Cases Passed" : "✗ Execution Issue"}
                    </h4>
                    {localRunResult.errorMessage && (
                        <pre className="error">
                            {localRunResult.errorMessage}
                        </pre>
                    )}
                    {localRunResult.consoleOutput && !localRunResult.success && !localRunResult.errorMessage && (
                        <pre className="output">
                            {localRunResult.consoleOutput}
                        </pre>
                    )}
                </div>
            )}
            {!showFeedback && (
                <div className="format-submit-block">
                    <button
                        onClick={() => setShowSubmitConfirm(true)}
                        className="format-submit-btn"
                        disabled={!currentCode.trim() || isSubmitting}
                    >
                        {isSubmitting ? (
                            <span className="format-spinner-container">
                                <svg
                                    className="format-spinner"
                                    width="16" height="16" viewBox="0 0 24 24" fill="none"
                                    stroke="currentColor" strokeWidth="3" strokeLinecap="round"
                                >
                                    <path d="M12 2v4m0 12v4M4.93 4.93l2.83 2.83m8.48 8.48l2.83 2.83M2 12h4m12 0h4M4.93 19.07l2.83-2.83m8.48-8.48l2.83-2.83" />
                                </svg>
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

            {showResetConfirm && (
                <div className="code-modal-overlay">
                    <div className={`code-modal-content ${editorTheme === 'vs-dark' ? 'theme-dark' : 'theme-light'}`}>
                        <h3 className="code-modal-title">Reset Code?</h3>
                        <p className="code-modal-desc">
                            Are you sure you want to reset your code to the default boilerplate? This will erase your current work for <strong>{LANGUAGE_LABELS[language]}</strong>.
                        </p>
                        <div className="code-modal-actions">
                            <button
                                onClick={cancelReset}
                                className={`code-modal-btn code-modal-btn-cancel ${editorTheme === 'vs-dark' ? 'theme-dark' : 'theme-light'}`}
                            >
                                Cancel
                            </button>
                            <button
                                onClick={confirmReset}
                                className="code-modal-btn code-modal-btn-danger"
                            >
                                Yes, Reset
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {showSubmitConfirm && (
                <div className="code-modal-overlay">
                    <div className={`code-modal-content ${editorTheme === 'vs-dark' ? 'theme-dark' : 'theme-light'}`}>
                        <h3 className="code-modal-title">Submit Code?</h3>
                        <p className="code-modal-desc">
                            {(!localRunResult || !localRunResult.success) 
                                ? "You haven't successfully passed the local test cases yet. Are you sure you want to submit?" 
                                : "Are you ready to submit your solution? Your rating will be updated based on the result."}
                        </p>
                        <div className="code-modal-actions">
                            <button
                                onClick={cancelSubmit}
                                className={`code-modal-btn code-modal-btn-cancel ${editorTheme === 'vs-dark' ? 'theme-dark' : 'theme-light'}`}
                            >
                                Cancel
                            </button>
                            <button
                                onClick={confirmSubmit}
                                className="code-modal-btn code-modal-btn-primary"
                            >
                                Yes, Submit
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    )
}