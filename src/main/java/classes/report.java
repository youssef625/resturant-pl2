package classes;

import java.sql.Timestamp;

public class report {
    private Timestamp timeCreated;
    private String about;
    private String content;



    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public String getContent() {
        return content;
    }

}
