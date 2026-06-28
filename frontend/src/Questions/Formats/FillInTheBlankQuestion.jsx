import { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function FillInTheBlankQuestion({ question, onAnswer, selectedAnswer, showFeedback, isSubmitting, attemptResult }) {
    const [input, setInput] = useState('');

    useEffect(() => {
        if (selectedAnswer && selectedAnswer.length > 0) {
            setInput(selectedAnswer[0]);
        } else {
            setInput('');
        }
    }, [selectedAnswer, question.id]);

    // Separate the prompt text from the code block
    const backtickParts = question.question.split('```');
    const promptText = backtickParts[0]?.trim();

    let codeContent = '';
    if (backtickParts.length > 1) {
        const codeLines = backtickParts[1].split('\n');
        codeLines.shift(); // remove the language identifier line ("java")
        codeContent = codeLines.join('\n').trim();
    }

    // Split the code on ___ to place the input inline
    const codeParts = codeContent.split('___');

    const getInputClass = () => {
        let cls = 'format-inline-input';
        if (showFeedback && attemptResult) {
            cls += attemptResult.correct ? ' correct' : ' incorrect';
        }
        return cls;
    };

    return (
        <div className='question-card'>
            <h3 className="format-question-text">
                {promptText}
                <span className="format-subtitle">
                    Fill in the blank.
                </span>
            </h3>

            <div className="format-code-snippet">
                <code>
                    {codeParts[0]}
                    <input
                        type="text"
                        className={getInputClass()}
                        value={input}
                        onChange={(e) => setInput(e.target.value)}
                        disabled={showFeedback}
                        placeholder="___"
                        onKeyDown={(e) => {
                            if (e.key === 'Enter' && !showFeedback && input.trim()) {
                                onAnswer([input.trim()]);
                            }
                        }}
                    />
                    {codeParts[1]}
                </code>
            </div>

            {!showFeedback && (
                <div className="format-submit-block">
                    <button
                        onClick={() => onAnswer([input.trim()])}
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
                                Submitting...
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
