package com.mauricio.onlinejob.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 30)
    private String name;
    private String description;

}
