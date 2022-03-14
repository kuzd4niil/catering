package com.example.demo.services;

import com.example.demo.models.Catering;
import com.example.demo.repositories.CateringRepository;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileUploaderService {

    CateringRepository cateringRepository;
    UserRepository userRepository;

    public FileUploaderService(CateringRepository cateringRepository, UserRepository userRepository) {
        this.cateringRepository = cateringRepository;
        this.userRepository = userRepository;
    }

    public List<Catering> uploadFile(MultipartFile file, Principal principal) throws IOException {

        User user = userRepository.findByUsername(principal.getName());

        String currentPath = new java.io.File(".").getCanonicalPath();

        File theDir = new File(currentPath + "/files/");
        if (!theDir.exists()) {
            theDir.mkdir();
        }

        file.transferTo(Paths.get(currentPath + "/files/", file.getOriginalFilename()));

        File fileOfList = new File(currentPath + "/files/" + file.getOriginalFilename());

        FileInputStream excelFile = new FileInputStream(fileOfList);

        Workbook workbook = new XSSFWorkbook(excelFile);

        Sheet sheet = workbook.getSheetAt(0);

        Catering catering;
        List<Catering> list = new ArrayList<>();
        for (Row row : sheet) {
            int i = 0;
            catering = new Catering();
            for (Cell cell : row) {
                switch (i) {
                    case 0 -> catering.setName(cell.getRichStringCellValue().getString());
                    case 1 -> catering.setAddress(cell.getRichStringCellValue().getString());
                    case 2 -> catering.setEmail(cell.getRichStringCellValue().getString());
                }
                ++i;
            }
            catering.setOwner(user);
            list.add(catering);
            cateringRepository.save(catering);
        }

        excelFile.close();
        fileOfList.delete();

        return list;
    }
}
