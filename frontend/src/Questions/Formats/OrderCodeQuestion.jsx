import { useState, useRef, useEffect } from 'react';
import './QuestionFormats.css';

export default function OrderCodeQuestion({ question, onAnswer, selectedAnswer, showFeedback, isSubmitting }) {
    const [blocks, setBlocks] = useState([]);
    
    useEffect(() => {
        if (selectedAnswer && selectedAnswer.length > 0) {
            setBlocks(selectedAnswer);
        } else if (question && question.answers) {
            setBlocks([...question.answers]);
        }
    }, [question, selectedAnswer]);

    const dragItem = useRef();
    const dragOverItem = useRef();

    const onDragStart = (e, index) => {
        dragItem.current = index;
        setTimeout(() => {
            e.target.classList.add('dragging');
        }, 0);
    }

    const onDragEnter = (e, index) => {
        dragOverItem.current = index;
    }

    const onDragOver = (e) => {
        e.preventDefault();
    }

    const onDragEnd = (e) => {
        e.target.classList.remove('dragging');
        
        if (dragItem.current === undefined || dragOverItem.current === undefined) return;

        const newBlocks = [...blocks];
        
        const draggedItemContent = newBlocks.splice(dragItem.current, 1)[0];
        
        newBlocks.splice(dragOverItem.current, 0, draggedItemContent);

        dragItem.current = undefined;
        dragOverItem.current = undefined;

        setBlocks(newBlocks);
    }

    const handleSubmit = () => {
        onAnswer(blocks);
    }

    const getBlockClass = (block, index) => {
        let cls = 'format-draggable-block format-code-snippet';
        
        if (showFeedback) {
            const isCorrectPosition = question.correctAnswer[index] === block;
            cls += isCorrectPosition ? ' correct-position' : ' incorrect-position';
        }
        
        return cls;
    }

    return (
      <div className='question-card'>
        <h3 className="format-question-text">
            {question.question}
            <span className="format-subtitle">
                Drag and drop the code blocks into the correct sequence.
            </span>
        </h3>

        <div className='format-answers-grid' style={{ gap: '0.5rem', marginBottom: '1.5rem' }}>
            {blocks.map((block, index) => (
                <div
                    key={index}
                    className={getBlockClass(block, index)}
                    draggable={!showFeedback}
                    onDragStart={(e) => onDragStart(e, index)}
                    onDragEnter={(e) => onDragEnter(e, index)}
                    onDragOver={onDragOver}
                    onDragEnd={onDragEnd}
                >
                    <div className="drag-handle">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                            <line x1="8" y1="6" x2="21" y2="6"></line>
                            <line x1="8" y1="12" x2="21" y2="12"></line>
                            <line x1="8" y1="18" x2="21" y2="18"></line>
                            <circle cx="3" cy="6" r="1"></circle>
                            <circle cx="3" cy="12" r="1"></circle>
                            <circle cx="3" cy="18" r="1"></circle>
                        </svg>
                    </div>
                    <code>{block}</code>
                </div>
            ))}
        </div>

        {!showFeedback && (
            <div className="format-submit-block">
                <button
                    onClick={handleSubmit}
                    className="format-submit-btn"
                    disabled={isSubmitting}
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
                        'Submit Order'
                    )}
                </button>
            </div>
        )}

        <div className="format-footer">
            <span>{question.topic}</span>
            <span className="format-difficulty">{question.difficulty}</span>
        </div>
      </div>  
    );
}