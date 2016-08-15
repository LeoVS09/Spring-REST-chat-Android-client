package org.hello.rest;

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
    final static String serverUrl = "http://188.114.216.217:9090/mobile";
    String prefix;
    String user;
    RestTemplate restTemplate;
    public RestRequest() throws Exception{
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }
    public <T> T send(Object request,Class<T> responseType) throws Exception{
        String url = serverUrl + "/" + prefix + "/{userLogin}";
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("userLogin",user);
        return restTemplate.postForObject(url,request,responseType,vars);
    }
    public RestRequest in(String prefix, String user){
        this.prefix = prefix;
        this.user = user;
        return this;
    }
    public String headStatus(String login) throws Exception{
        String url = serverUrl + "/{userLogin}" + "/" + prefix ;
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("userLogin",user);
        User us = new User(login,"lol");
        String text = restTemplate.postForEntity(url,us,Void.class,vars).getStatusCode().toString();
        return text;
    }
}
