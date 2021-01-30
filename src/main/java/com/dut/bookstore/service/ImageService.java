package com.dut.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
   String upload(MultipartFile multipartFile, String type, int id);
   byte[] load(String imageName, String type, int id) throws IOException;
}
