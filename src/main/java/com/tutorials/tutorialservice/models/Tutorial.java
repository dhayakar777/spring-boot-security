package com.tutorials.tutorialservice.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TUTORIALS")
public class Tutorial extends Auditable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "TUTORIAL_ID")
    private String id;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "DESCP")
    private String description;

    @Column(name = "IS_PUBLISHED")
    private boolean published;

    @Column(name="YEAR_OF_PUBLISH")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "02-10-2021")
    private String publishedYear;

    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Author author;

}
