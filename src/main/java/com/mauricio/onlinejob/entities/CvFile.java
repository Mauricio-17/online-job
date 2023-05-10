package com.mauricio.onlinejob.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class CvFile { // this is for mediumblob datatype to store the pdf files

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob
    @Column(length = 10000000)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CvFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;

    }
}
