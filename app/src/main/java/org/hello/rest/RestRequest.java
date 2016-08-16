package org.hello.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Администратор on 14.08.2016.
 */
public class RestRequest {
    final static String serverUrl = "http://188.114.216.112:9090/mobile";
    String prefix;
    String user;
    RestTemplate restTemplate;
    public RestRequest() throws Exception{
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }
    public <T> T send(Object request,Class<T> responseType) throws Exception{
        String url = serverUrl + "/" + prefix ;

        return restTemplate.postForObject(url,request,responseType);
    }
    public RestRequest in(String prefix, String user){
        this.prefix = prefix;
        this.user = user;
        return this;
    }
    public String headStatus(Object request){
        String url = serverUrl +  "/" + prefix ;
        String text;
        try {
            text = restTemplate.postForEntity(url, request, Void.class).getStatusCode().toString();
        } catch(Exception e){
            text = e.getMessage();
        }
        return text;
    }
    public <T> ResponseEntity headEntity(Object request,Class<T> responseType)throws Exception{
        String url = serverUrl +  "/" + prefix ;
        ResponseEntity entity;

            entity = restTemplate.postForEntity(url, request, responseType);

        return entity;
    }

}
