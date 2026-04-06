package com.reasonly.backend.User.UserSettings;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reasonly.backend.User.User;
import com.reasonly.backend.Question.QuestionTopic;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private UserExperience experience;

    @Enumerated(EnumType.STRING)
    private UserMotivation motivation;

    @Enumerated(EnumType.STRING)
    private UserLanguage preferredLanguage;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<QuestionTopic> interests;

    private boolean isDarkMode = true;
    private int editorFontSize = 14;
}
