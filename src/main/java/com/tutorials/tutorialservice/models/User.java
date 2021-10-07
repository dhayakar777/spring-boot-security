package com.tutorials.tutorialservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Auditable {

   @Id
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   @Column(name = "USER_ID", updatable = false, nullable = false)
   private String userId;

   @Column(name = "FIRST_NAME", nullable = false)
   private String firstName;

   @Column(name = "LAST_NAME", nullable = false)
   private String lastName;

   @Column(name = "USER_NAME", nullable = false)
   private String userName;

   @Column(name = "EMAIL", unique = true, nullable = false)
   @Email
   @Size(min = 15, max = 30, message = "Email length shouldn't be more than 30 characters")
   private String email;

   @Column(name = "PASSWORD",  nullable = false)
   @Size(min = 6, max=20, message = "Password length cannot be more than 20 characters")
   private String password;

  /* @Column(name = "LAST_LOGIN", nullable = false)
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonSerialize(using = LocalDateSerializer.class)
   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
   private LocalDateTime lastLogin;*/

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
   /*@JoinTable(name = "USER_ROLES", joinColumns = {@JoinColumn(name = "USER_ID")},
   inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})*/
   @JsonBackReference
   private List<Role> roles ;

   public void addRole(Role role) {
         role.setUser(this);
         this.roles.add(role);
   }


}
