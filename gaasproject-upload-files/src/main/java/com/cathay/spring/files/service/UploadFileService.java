package com.cathay.spring.files.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cathay.spring.files.entity.MyEntity;
import com.cathay.spring.files.repository.MyRepository;

@Service
public class UploadFileService {
	@Autowired
	MyRepository repository;

	public void save(MultipartFile file) {
		try {
			List<MyEntity> entities = UploadFileHelper.excelOrTxtToEntities(file.getInputStream(),
					file.getContentType());
			repository.saveAll(entities);
		} catch (IOException e) {
			throw new RuntimeException("fail to store data: " + e.getMessage());
		}
	}

	public List<MyEntity> getAllTutorials() {
		return repository.findAll();
	}
}
