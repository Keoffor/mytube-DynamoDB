package com.auth.ken.authjwt.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;


@Service
public class S3Service implements FileService{

    public static final String BUCKET_NAME = "utube-buckets";
    private final AmazonS3Client amazonS3Client;



    public S3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public String uploadFile(MultipartFile file){
         //upload file to AWS S3
        // prepare a key
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = UUID.randomUUID() + "." + filenameExtension;
        var metadata = new ObjectMetadata();
         metadata.setContentLength(file.getSize());
         metadata.setContentType(file.getContentType());
         try {
             amazonS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
         }catch (IOException io){
             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                     "An exception occurred while uploading the file");
         }

         amazonS3Client.setObjectAcl("utube-buckets",key, CannedAccessControlList.PublicRead);
         return  amazonS3Client.getResourceUrl("utube-buckets", key);
    }
}
