package com.edong.mohelp.net;

public class Event {

    private Header header;


    public Event(String namespace ,String name){
        this.header = new Header(namespace,name,"");
    }

    public Event(String namespace ,String name,String message){
        this.header = new Header(namespace,name,message);
    }

    public class Header{
        String namespace;
        String name ;
        String message;
        public Header(String namespace ,String name,String message){
            this.namespace = namespace;
            this.name = name;
            this.message = message;
        }
    }
}
