package com.sagar.controller;

import com.sagar.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable String id) throws IOException {
        GridFsResource resource = imageService.getImage(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
