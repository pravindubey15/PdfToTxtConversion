package com.example.PdfToTxtConversion;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/dubey")
public class PdfController {
    @GetMapping("/get")
    public String getAll(){
        return "Hello";
    }

    @PostMapping("/pdftotxt")
    public String pdfToTxt(@RequestParam("file") MultipartFile file) throws IOException{
        UUID uuid=UUID.randomUUID();
        String refId=uuid.toString();

        String name=file.getOriginalFilename();
        String pathx="C:\\Users\\SCOREME-LT-PRAVIN\\Desktop\\S's work\\";
        Path filePath = Paths.get(pathx, name);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }
        // Save the file to the specific directory
        file.transferTo(filePath.toFile());
        PDDocument pd=Loader.loadPDF(new File(String.valueOf(filePath)));
            PDFTextStripper stripper = new PDFTextStripper();
           String text = stripper.getText(pd);
            File outputFile = new File(pathx+refId+".txt");
            FileWriter writer = new FileWriter(outputFile);
            writer.write(text);
            writer.close();

                return "Successfully:-  "+name+" "+ pathx +"converted  "+writer+"\n refId: "+refId;
    }
}
