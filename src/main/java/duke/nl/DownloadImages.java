package duke.nl;

import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class DownloadImages {

    private final static Logger LOG = LoggerFactory.getLogger(DownloadImages.class);


    @Autowired
    private AlbumRepository albumRepository;

    private boolean initDone;

    public void setup() throws IOException {
        if(!initDone) {
            String phantomJs = Files.createTempDirectory("PhantomJs-" + new Random().nextInt()).toFile().getAbsolutePath();
            System.setProperty("wdm.targetPath", phantomJs);
            PhantomJsDriverManager.getInstance().setup("2.1");
            initDone = true;
        }
    }


    //private static final String webSiteURLs[] = new String[]{"https://goo.gl/photos/nhUV4zNdqtGcSdHd9", "https://goo.gl/photos/GqZbhMpeBj9c2WPF8", "https://goo.gl/photos/MhyqU8ZLV1xF38uS8"};

    @Async
    public void download() throws IOException {

        List<Album> albumList = albumRepository.findByDownloaded(false);
        if (!albumList.isEmpty()) {

            setup();

            PhantomJSDriver driver = new PhantomJSDriver();
            driver.manage().window().setSize(new Dimension(1920, 10000));


            for (Album album : albumList) {
                getAlbum(driver, album);
            }

            driver.close();
            LOG.info("done downloading album");
        }


    }

    private void getAlbum(PhantomJSDriver driver, Album album) {
        try {
            driver.get(album.getGoogleUrl());

//

            await().atMost(10, SECONDS).until(imagesArePresent(driver));

            List<WebElement> elements = getImageElements(driver);
            List<Image> images = elements.stream().map(this::getImages).collect(Collectors.toList());


            Optional<String> title = driver.findElements(By.className("cL5NYb")).stream().map(WebElement::getText).findFirst();
            album.setName(title.isPresent() ? title.get() : "unknown");
            album.setDownloaded(true);
            album.setImages(images);
            album.setThumnailUrl(images.get(0).getThumnailUrl());
            albumRepository.save(album);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            LOG.error("File : {}", srcFile);
        }

    }

    private List<WebElement> getImageElements(PhantomJSDriver driver) {
        return driver.findElements(By.className("p137Zd"));
    }

    private Callable<Boolean> imagesArePresent(PhantomJSDriver driver) {
        return () -> !getImageElements(driver).isEmpty();
    }

    private Image getImages(WebElement webElement) {

        String url = webElement.getAttribute("href");
        await().atMost(20, SECONDS).until(nameIsPresent(webElement));
        Image image = new Image(getThumbnailUrl(webElement), url);
        LOG.info("downloaded image");
        return image;

    }

    private Callable<Boolean> nameIsPresent(WebElement imageElement) {
        return () -> !getName(imageElement).isEmpty();
    }

    private String getName(WebElement webElement) {

        String src = getThumbnailUrl(webElement);

        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }
        indexname = src.lastIndexOf("/");
        return src.substring(indexname + 1, src.length());
    }

    private String getThumbnailUrl(WebElement webElement) {
        WebElement imageElement = webElement.findElement(By.className("RY3tic"));
        return imageElement.getAttribute("data-latest-bg");
    }
}