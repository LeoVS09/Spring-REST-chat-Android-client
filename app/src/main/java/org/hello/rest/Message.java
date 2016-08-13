package org.hello.rest;

public class Message {
    private String name;
    private String text;
    private String thumbnail;
    private long time;

    public Message(){}
    public Message(String name,String text, long time){
        this.name = name;
        this.text = text;
        this.time = time;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setTime(long time){
        this.time = time;
    }

    public String getName(){
        return name;
    }
    public String getText(){
        return text;
    }
    public long getTime(){
        return time;
    }
}