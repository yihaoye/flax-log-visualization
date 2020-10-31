package com.example.flvb.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class S3FileClient {

    private AmazonS3 s3Client;

    private Regions clientRegion;

    // @Value("${amazonProperties.endpointUrl}")
    // private String endpointUrl;
    // @Value("${amazonProperties.bucketName}")
    // private String bucketName;
    // @Value("${amazonProperties.accessKey}")
    // private String accessKey;
    // @Value("${amazonProperties.secretKey}")
    // private String secretKey;

    @PostConstruct
    private void init() {
        this.clientRegion = Regions.DEFAULT_REGION;
        // This code expects that you have AWS credentials set up per:
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
        // this.s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        this.s3Client = AmazonS3Client.builder()
            .withCredentials(new ProfileCredentialsProvider())
            .withRegion(this.clientRegion)
            .build();
    }

    public boolean uploadFile(String string_obj_key_name, String file_obj_key_name, String bucket_name, String file_name) throws Exception {
        try {
            // Upload a text string as a new object.
            this.s3Client.putObject(bucket_name, string_obj_key_name, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucket_name, file_obj_key_name, new File(file_name));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            this.s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            throw new Exception(e.getErrorMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            throw new Exception(e.getErrorMessage());
        }

        return true;
    }

    public File getFile(String key_name, String bucket_name) throws Exception {
        System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);
        File resFile = new File(new File(key_name));
        try {
            S3Object o = this.s3Client.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(resFile);
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new Exception(e.getErrorMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new Exception(e.getMessage());
        }

        return resFile;
    }

    public void putFile(String key_name, String bucket_name, String file_path) throws Exception {
        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
        try {
            this.s3Client.putObject(bucket_name, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new Exception(e.getErrorMessage());
        }
        System.out.println("Done!");
    }
}