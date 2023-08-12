package com.example.pdf_demo2222;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdf_demo2222.models.Pdf;
import com.example.pdf_demo2222.models.PdfRepository;


@RestController
@RequestMapping("/api/v1")
public class PdfController {

    @Autowired
    private PdfRepository pdfRepository;
    
    @PostMapping("/insert")
    public ResponseEntity<Integer> insertPdf(@RequestParam MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] fileByteArray = inputStream.readAllBytes();

            var newPdf = new Pdf(file.getOriginalFilename(), fileByteArray);

            Pdf save = pdfRepository.save(newPdf);
            return ResponseEntity.ok(save.getId()); 

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/get")
    public ResponseEntity<String> getPdf(@RequestParam int id) {

        Optional<Pdf> findById = pdfRepository.findById(id);

        if (findById.isEmpty()) {
            return null;
        }

        Pdf pdf = findById.get();

        // return ResponseEntity.ok(pdf);

        try {
            File pdfFile = new File(System.getProperty("user.dir") +"/fileoutput/"+ pdf.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);

            fileOutputStream.write(pdf.getFileByteArray());

            String pdfUrl = pdfFile.getAbsolutePath();

            fileOutputStream.close();
            return ResponseEntity.ok(pdfUrl);
            
        } catch (IOException exception) {
            exception.printStackTrace();
        }
            
        return ResponseEntity.ok(pdf.getFileName());
    }
}
