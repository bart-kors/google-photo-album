package duke.nl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class Album {

    private final static Logger LOG = LoggerFactory.getLogger(Album.class);

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy dd", Locale.ENGLISH);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @URL
    @NotEmpty
    private String googleUrl;

    private String name;

    private String thumnailUrl;

    private boolean downloaded;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();


    protected Album() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumnailUrl(String thumnailUrl) {
        this.thumnailUrl = thumnailUrl;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public String getName() {
        return name;
    }

    public String getThumnailUrl() {
        return thumnailUrl;
    }

    public List<Image> getImages() {
        return images;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateFromName() {
        if (StringUtils.isNotBlank(name)) {
            try {
                return LocalDate.parse(name + " 01", formatter);
            } catch (DateTimeParseException e)    {
                LOG.warn("{}",e.getMessage());
                //ignore
            }

        }
        return null;
    }
}
