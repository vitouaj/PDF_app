package com.example.pdf_demo2222.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pdf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileByteArray;

    public Pdf(String filename, byte[] fileByteArray) {
        this.fileName = filename;
        this.fileByteArray = fileByteArray;
    }


}
