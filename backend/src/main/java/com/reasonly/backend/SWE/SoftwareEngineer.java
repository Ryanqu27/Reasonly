package com.reasonly.backend.SWE;

import com.reasonly.backend.QuestionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SoftwareEngineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private QuestionType questionType;
    private String name;
    private String techStack;

    public SoftwareEngineer(Integer id, String name, String techStack, QuestionType questionType) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
        this.questionType = questionType;
    }

    public SoftwareEngineer() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTechStack() {
        return techStack;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
    
    public void setId(Integer newId) {
        id = newId;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setTechStack(String newTechStack) {
        techStack = newTechStack;
    }

    public void setQuestionType(QuestionType newQuestionType) {
        questionType = newQuestionType;
    }

}
