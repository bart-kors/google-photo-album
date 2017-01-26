/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package duke.nl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/")
public class AlbumController {

    @Autowired
    private DownloadImages downloadImages;

    @Autowired
    private AlbumRepository repository;

    @GetMapping("/albums")
    public ModelAndView list() {
        Iterable<Album> albums = repository.findAll();
        return new ModelAndView("album/list", "albums", sortAlbums(albums));
    }

    public static List<Album> sortAlbums(Iterable<Album> albums) {
        return StreamSupport.stream(albums.spliterator(), false).sorted((o1, o2) -> {
                LocalDate o1Date = o1.getDateFromName();
                LocalDate o2Date = o2.getDateFromName();
                if (o1Date != null && o2Date != null) {
                    return o2Date.compareTo(o1Date);
                } else if (o1Date == null) {
                    return 1;
                } else {
                    return -1;
                }

            }).collect(Collectors.toList());
    }

    @PostMapping
    public ModelAndView create(@Valid Album album, BindingResult result,
                               RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("album/form", "formErrors", result.getAllErrors());
        }
        repository.save(album);
        downloadImages.download();
        redirect.addFlashAttribute("globalMessage", "Successfully created a album download will be scheduled");
        return new ModelAndView("redirect:/albums");
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        repository.delete(id);
        Iterable<Album> albums = repository.findAll();
        return new ModelAndView("album/list", "albums", sortAlbums(albums));
    }

    @GetMapping
    public ModelAndView showAlbums() {
        Iterable<Album> albums = repository.findAll();
        return new ModelAndView("album/view", "albums", sortAlbums(albums));
    }

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Album album) {
        return "album/form";
    }

}