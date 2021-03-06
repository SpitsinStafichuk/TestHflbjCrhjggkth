
package com.github.spitsinstafichuk.vkazam.vos;

public class SongUrl {

    private final long id;

    private String url;

    private String artist;

    private String title;

    private int urlType;

    public SongUrl(long id, String url, String artist, String title, int urlType) {
        super();
        this.id = id;
        this.url = url;
        this.artist = artist;
        this.title = title;
        this.urlType = urlType;
    }

    public SongUrl(String url, String artist, String title, int urlType) {
        super();
        id = -1;
        this.url = url;
        this.artist = artist;
        this.title = title;
        this.urlType = urlType;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

}
