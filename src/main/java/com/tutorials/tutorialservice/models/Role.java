package com.tutorials.tutorialservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Column(name = "ROLE_ID", updatable = false, nullable = false)
    private String roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @ManyToOne()
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ID")
    @JsonManagedReference
    private User user;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    public Role(String roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

}
