package com.mauricio.onlinejob.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @NotNull(message = "Nombre requerido!")
    @Column(nullable = false)
    private String name;

    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthday;

    @Size(max = 50, message = "Nombre muy largo, debe ser menor a 50 carácteres")
    @NotNull(message = "Username requerido!")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "Contraseña requerida!")
    @Column(nullable = false)
    private String password;

    // this is for mediumblob datatype to store the pdf files
    @Lob
    @Column(name = "cv_data", length = 10000000)
    private byte[] cvData;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private UserType userType;
}
