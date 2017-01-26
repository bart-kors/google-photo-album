package duke.nl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlbumRestService {

    @Autowired
    private AlbumRepository repository;

    @RequestMapping("/allAlbums")
    List<NanoPicture> getAllAlbums() {
        List<NanoPicture> all = new ArrayList<>();
        int index = 1;
        for (Album album : AlbumController.sortAlbums(repository.findAll())) {
            List<Image> images = album.getImages();
            if (!images.isEmpty()) {
                NanoPicture nanoAlbum = new NanoPicture();
                nanoAlbum.setSrct(album.getThumnailUrl());
                nanoAlbum.setTitle(album.getName() + " (" + images.size() + ")");
                int albumId = index++;
                nanoAlbum.setID(albumId);
                nanoAlbum.setKind("album");
                all.add(nanoAlbum);
                for (Image image : images) {
                    NanoPicture pic = new NanoPicture();
                    pic.setSrct(image.getThumnailUrl());
                    pic.setDestURL("open/" + image.getId());
                    pic.setAlbumID(albumId);
                    pic.setID(index++);
                    all.add(pic);
                }
            }
        }
        return all;


    }


}
