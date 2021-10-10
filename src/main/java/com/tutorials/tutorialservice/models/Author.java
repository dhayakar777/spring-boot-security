package com.tutorials.tutorialservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AUTHORS")
public class Author {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "AUTHOR_ID")
    private String authorId;

    @Column(name = "AUTHR_NAME")
    private String name;

    @Column(name = "AUTHR_EMAIL")
    private String email;

    @Column(name = "AUTHR_DESIGNATION")
    private String designation;

    @Column(name = "GENDER")
    private String gender;

    @OneToOne
    @JoinColumn(name = "AUTHR_TUTORIAL_Id", referencedColumnName = "TUTORIAL_ID")
    private Tutorial tutorial;
}
