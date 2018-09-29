package com.edong.mohelp.music;

import com.edong.mohelp.net.Event;

import java.util.List;

public class MusicEvent extends Event{



    private int position;

    private long offsetInMilliseconds;

    private Data data;

    public MusicEvent(String name){
        super("MusicPlayer",name);
    }

    public void setData(List<Music> dbdata){
        this.data = new Data(dbdata);
    }

    public void setPosition(int position) {
        this.position = position;
    }



    public class Data{
        private List<Music> dbdata;

        public Data(List<Music> dbdata){
            this.dbdata = dbdata;
        }
    }

}
