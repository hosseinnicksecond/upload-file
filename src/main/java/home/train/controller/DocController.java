package home.train.controller;

import home.train.entity.Doc;
import home.train.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
public class DocController {

    private final DocService service;

    public DocController(DocService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showPage(Model model) {
        model.addAttribute("docs", service.getDocList());
        return "docs";
    }

    @PostMapping("/doForm")
    public String processForm(@RequestParam MultipartFile[] files) {
        log.info("from form method ********");
        for (MultipartFile file:files)
        service.SaveFile(file);
        return "redirect:/";
    }

    @GetMapping("/downloadFiles/{fileId}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable("fileId") String id) {
        Doc doc = service.getById(Long.valueOf(id));

        assert doc.getStoredFile() != null;
        byte[] loadedFile = new byte[doc.getStoredFile().length];
        int i = 0;
        for (byte b : doc.getStoredFile()) {
            loadedFile[i++] = b;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:fileName=\" " + doc.getFileName()
                        + "\"").body(new ByteArrayResource(loadedFile));

    }
}
