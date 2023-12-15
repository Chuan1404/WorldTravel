package com.worldstory.travel.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDateTime;

@Service
public class AmazonS3Service {
    @Value("${aws.s3.bucket.name}")
    private String bucket;

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(File file) {
        // bucket/folder/file
        String path = String.format("images/%s", file.getName());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, file);
        amazonS3.putObject(putObjectRequest);

        URL s3Url = amazonS3.getUrl(bucket, path);
        file.delete();

        return s3Url.toString();
    }

    public String uploadFile(MultipartFile multipartFile) {
        File file = multipartToFile(multipartFile);
        if(file == null) return null;
        return uploadFile(file);
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    private File multipartToFile(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename() : LocalDateTime.now().toString();
        File file = new File(filename);

        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
            return file;
        } catch (Exception err) {
            System.out.println("file error");
        }
        return null;
    }

}
