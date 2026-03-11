import { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function FillInTheBlankQuestion({ question, onAnswer, selectedAnswer, showFeedback }) {
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
        if (showFeedback) {
            const isCorrect = question.correctAnswer.includes(input.trim());
            cls += isCorrect ? ' correct' : ' incorrect';
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

            {showFeedback && (
                <div className={`format-bug-feedback ${question.correctAnswer.includes(input.trim()) ? 'correct' : 'incorrect'}`}>
                    {question.correctAnswer.includes(input.trim())
                        ? ''
                        : `Incorrect — the answer was: ${question.correctAnswer?.join(', ')}`}
                </div>
            )}

            {!showFeedback && (
                <div className="format-submit-block">
                    <button
                        onClick={() => onAnswer([input.trim()])}
                        className="format-submit-btn"
                        disabled={!input.trim()}
                    >
                        Submit
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
