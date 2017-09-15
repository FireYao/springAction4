package com.fireyao.bean;

/**
 * Created by lly on 2017/8/31
 */
public class ExpressiveBean {

    private String title;
    private String artist;

    public ExpressiveBean(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public ExpressiveBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "ExpressiveBean{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
