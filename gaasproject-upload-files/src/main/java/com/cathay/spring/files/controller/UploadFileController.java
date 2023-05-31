package com.cathay.spring.files.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cathay.spring.files.entity.MyEntity;
import com.cathay.spring.files.entity.ResponseMessage;
import com.cathay.spring.files.service.UploadFileHelper;
import com.cathay.spring.files.service.UploadFileService;

@Controller
public class UploadFileController {

    @Autowired
    UploadFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (UploadFileHelper.isFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) {
        try {
            List<MyEntity> list = fileService.getAll();
            model.addAttribute("list", list);
        } catch (Exception e) {
            model.addAttribute("list", new ArrayList<>());
        }
        return "uploadPage";
    }

    @GetMapping("/list")
    public ResponseEntity<List<MyEntity>> getAllList() {
        try {
            List<MyEntity> list = fileService.getAll();

            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
