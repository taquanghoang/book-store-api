package com.dut.bookstore.service.Impl;

import com.dut.bookstore.constant.DefaultParam;
import com.dut.bookstore.constant.DefaultPath;
import com.dut.bookstore.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String upload(MultipartFile multipartFile, String type, int id) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path storageDirectory = Paths.get(DefaultPath.ROOT_FOLDER);
        String imageDir = "";
        Path imageDirPath = null;

        if(!Files.exists(storageDirectory)) {
            try {
                Files.createDirectories(storageDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            switch(type) {
                case DefaultParam.AVATAR:
                    imageDir = DefaultPath.AVATAR_FOLDER + File.separator + id;
                    break;
                case DefaultParam.BOOK:
                    imageDir = DefaultPath.BOOK_FOLDER + File.separator + id;
                    break;
            }

            imageDirPath = Paths.get(imageDir);

            if(!Files.exists(imageDirPath)) {
                try {
                    Files.createDirectories(imageDirPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Path destination = Paths.get(imageDirPath.toString() + "\\" + fileName);

        try {
            Files.copy(multipartFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return type + "/" + id + "/" + fileName;
    }

    @Override
    public byte[] load(String imageName, String type, int id) throws IOException {
        Path destination = null;
        String imageDir = "";

        switch (type) {
            case DefaultParam.AVATAR:
                imageDir = DefaultPath.AVATAR_FOLDER + File.separator + id;
                break;
            case DefaultParam.BOOK:
                imageDir = DefaultPath.BOOK_FOLDER + File.separator + id;
                break;
        }

        destination = Paths.get(imageDir+"\\"+imageName);

        return IOUtils.toByteArray(destination.toUri());
    }
}
