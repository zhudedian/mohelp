package com.edong.mohelp.skill;

public class Skill {

    private String title;
    private String content1;
    private String content2;
    public Skill(String title,String content1,String content2){
        this.title = title;
        this.content1 = content1;
        this.content2 = content2;
    }

    public String getContent1() {
        return content1;
    }

    public String getContent2() {
        return content2;
    }

    public String getTitle() {
        return title;
    }
}
