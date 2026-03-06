import { useState, useEffect } from 'react';
import './QuestionFormats.css';

export default function FindTheBugQuestion({ question, onAnswer, selectedAnswer, showFeedback }) {
    const [input, setInput] = useState('');

    useEffect(() => {
        if (selectedAnswer && selectedAnswer.length > 0) {
            setInput(selectedAnswer[0]);
        } else {
            setInput('');
        }
    }, [selectedAnswer, question.id]);

    // Split the question string to separate the prompt text from the code block.
    // The DataInitializer stores it as: "Identify the line...\n\n```java\n1: code...\n```"
    const parts = question.question.split('```');
    const promptText = parts[0]?.trim();

    let codeBlock = '';
    if (parts.length > 1) {
        // parts[1] looks like "java\n1: public class...\n2: ..."
        // Remove the language identifier on the first line
        const lines = parts[1].split('\n');
        lines.shift(); // removes "java"
        codeBlock = lines.join('\n').trim();
    }

    const getInputClass = () => {
        let cls = 'format-number-input';
        if (showFeedback) {
            const isCorrect = question.correctAnswer.includes(input.trim());
            cls += isCorrect ? ' correct' : ' incorrect';
        }
        return cls;
    };

    return (
        <div className='question-card'>
            <h3 className='format-question-text'>
                {promptText}
                <span className='format-subtitle'>
                    Enter the line number of the bug.
                </span>
            </h3>

            {codeBlock && (
                <div className="format-code-snippet">
                    <code>{codeBlock}</code>
                </div>
            )}

            <div className="format-number-input-container">
                <label className="format-number-input-label">Line Number</label>
                <input
                    type="number"
                    min="1"
                    className={getInputClass()}
                    placeholder="e.g. 4"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    disabled={showFeedback}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' && !showFeedback && input.trim() !== '') {
                            onAnswer([input.trim()]);
                        }
                    }}
                />
            </div>

            {showFeedback && (
                <div className={`format-bug-feedback ${question.correctAnswer.includes(input.trim()) ? 'correct' : 'incorrect'}`}>
                    {question.correctAnswer.includes(input.trim())
                        ? '✓ Correct! You found the bug.'
                        : `✗ Incorrect — the bug was on line ${question.correctAnswer?.join(', ')}`}
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