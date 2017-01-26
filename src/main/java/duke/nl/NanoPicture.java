package duke.nl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NanoPicture {

    private String srct;
    private String title;
    private Integer ID;
    private String kind;
    private String destURL;
    private Integer albumID;

    public String getSrct() {
        return srct;
    }

    public void setSrct(String srct) {
        this.srct = srct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("ID")
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDestURL() {
        return destURL;
    }

    public void setDestURL(String destURL) {
        this.destURL = destURL;
    }


    public Integer getAlbumID() {
        return albumID;
    }

    public void setAlbumID(Integer albumID) {
        this.albumID = albumID;
    }
}
