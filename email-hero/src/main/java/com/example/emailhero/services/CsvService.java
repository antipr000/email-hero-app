package com.example.emailhero.services;

import com.example.emailhero.decorators.CsvColumn;
import com.example.emailhero.domain.GrantStages;
import com.example.emailhero.domain.GrantTypes;
import com.example.emailhero.exceptions.CsvReadException;
import com.example.emailhero.exceptions.FileUploadException;
import com.example.emailhero.models.GrantRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Service
public class CsvService {
    private final Logger logger = LoggerFactory.getLogger(CsvService.class);
    public static GrantTypes getGrantType(String value) {
        return switch (value) {
            case "OPERATING_GRANT" -> GrantTypes.OPERATING_GRANT;
            case "PROJECT_GRANT" -> GrantTypes.PROJECT_GRANT;
            default -> GrantTypes.UNKNOWN;
        };
    }

    public static GrantStages getGrantStage(String value) {
        if ("Awarded".equals(value)) {
            return GrantStages.AWARDED;
        }

        return GrantStages.UNKNOWN;
    }

    public List<GrantRecord> getGrantRecords(String filePath) throws CsvReadException {
        try {
            List<GrantRecord> allRecords = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // Remove first line as it contains headers
            lines.removeFirst();
            for (String line: lines) {
                Scanner sc = new Scanner(line);
                sc.useDelimiter(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                GrantRecord record = new GrantRecord();
                List<String> columns = new ArrayList<>();
                while(sc.hasNext()) {
                    columns.add(sc.next());
                }
                for (Field field: GrantRecord.class.getDeclaredFields()) {
                    if (field.isAnnotationPresent(CsvColumn.class)) {
                        CsvColumn annotation = field.getAnnotation(CsvColumn.class);
                        int index = annotation.index();
                        String value = columns.get(index);

                        if (field.getType() == int.class) {
                            field.setInt(record, Integer.parseInt(value));
                        } else if (field.getType() == GrantTypes.class) {
                            field.set(record, getGrantType(value));
                        } else if (field.getType() == GrantStages.class) {
                            field.set(record, getGrantStage(value));
                        } else if (field.getType() == List.class) {
                            List<String> tags = List.of(value.split(" "));
                            field.set(record, tags);
                        } else {
                            // Remove irrelevant double quotes (present in amount fields)
                            field.set(record, value.replaceAll("^\"|\"$", ""));
                        }
                    }
                }
                allRecords.add(record);
            }
            return allRecords;
        } catch (Exception e) {
            logger.error("Failed to import csv file {}", e.getLocalizedMessage());
            throw new CsvReadException("Failed to read csv file: " + filePath);
        }
    }

    public String uploadCsvFile(MultipartFile file) throws FileUploadException {
        try {
            String fileName = UUID.randomUUID().toString() + ".csv";
            File tempFile = File.createTempFile(fileName, "");
            Path path = Paths.get(tempFile.getAbsolutePath());
            Files.write(path, file.getBytes());
            logger.info("Successfully uploaded file to {}", tempFile.getAbsolutePath());
            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            logger.error("Failed to upload csv file {}", e.getLocalizedMessage());
            throw new FileUploadException("Failed to upload csv file.");
        }
    }
}
