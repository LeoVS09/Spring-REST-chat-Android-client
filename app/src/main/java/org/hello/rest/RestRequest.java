package org.hello.rest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Администратор on 14.08.2016.
 */
public class RestRequest {
    final static String serverUrl = "http://188.114.217.43:9090";
    Map<String, String> vars = new HashMap<String, String>();
    ArrayList<String> keys = new ArrayList<String>();
    RestTemplate restTemplate;
    public RestRequest() throws Exception{
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }
    public <T> T send(String postfix,Object request,Class<T> responseType) throws Exception{
        String url = serverUrl + "/" + postfix + "/{" + keys.get(0) + "}";
        return restTemplate.postForObject(url,request,responseType,vars);
    }
    public RestRequest put(String key, String var){
        vars.put(key,var);
        keys.add(key);
        return this;
    }

}
