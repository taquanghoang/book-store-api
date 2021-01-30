package com.dut.bookstore.controller;

import com.dut.bookstore.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("api/images")
public class ImageController {

    public final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(
            value = "{type}/{id}/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName,
                                 @PathVariable(name = "type") String type,
                                 @PathVariable(name = "id") int id) throws IOException {
        return this.imageService.load(fileName, type, id);
    }
}
