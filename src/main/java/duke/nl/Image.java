package duke.nl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String thumnailUrl;

    private String url;

    protected Image() {
    }

    public Image(String thumnailUrl, String url) {
        this.thumnailUrl = thumnailUrl;
        this.url = url;
    }

    public String getThumnailUrl() {
        return thumnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public Long getId() {
        return id;
    }

}