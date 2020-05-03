package com.example.service;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.model.ItemImage;

@Service
public class ItemImageService {
  
	  private static final String s3bucket = "cmpe172project";

	
  @Autowired
  private AmazonS3Client s3;

  @Bean
  public ItemImage addImage(MultipartFile multipartFile) throws Exception {
	  
    try{
      File fileToUpload = (File) multipartFile;
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH-mm-ss");
      Date date = new Date();
      String id = dateFormat.format(date);

      s3.putObject(new PutObjectRequest(s3bucket, id, fileToUpload));
      //GeneratePresignedUrlRequest requestURL = new GeneratePresignedUrlRequest(s3bucket, id);
      //requestURL.setMethod(HttpMethod.GET);
     // requestURL.setExpiration(DateTime.now().plusMonths(6).toDate());
      //URL signedUrl = s3.generatePresignedUrl(requestURL);
      return new ItemImage(id, id);
    }
    catch(Exception ex){ 
      throw new Exception("Can't add to s3", ex);
    }
    
  }
  
  @Bean
  public void deleteImage(ItemImage image){
	  
	  s3.deleteObject(new DeleteObjectRequest(s3bucket, image.getImageKey())); 

}
}