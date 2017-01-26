package duke.nl;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {

    List<Album> findByDownloaded(boolean downloaded);

}
