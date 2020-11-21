package home.train.service;

import home.train.entity.Doc;
import home.train.repository.DocRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class DocService {

    private final DocRepository repository;

    public DocService(DocRepository repository) {
        this.repository = repository;
    }

    public Set<Doc> getDocList(){
        Set<Doc> docs= new HashSet<>();
        repository.findAll().forEach(docs::add);
        return docs;
    }

    public Doc getById(Long id){
        Optional<Doc> doc=repository.findById(id);
        if(doc.isPresent()){
            return doc.get();
        }
        return null;
    }

    public Doc SaveFile(MultipartFile file) {

        log.info("from saving method service");
        try {

            if (file != null) {
                Byte[] fileByte = new Byte[file.getBytes().length];
                int i = 0;
                for (byte b : file.getBytes()) {
                    fileByte[i++] = b;
                }

                Doc doc = new Doc(file.getOriginalFilename(), file.getContentType(), fileByte);

                Doc savedDoc = repository.save(doc);
                return savedDoc;
            }
        }catch (IOException e){
            System.out.println("from service *********"+e.getCause());
            e.printStackTrace();
        }
        return null;

    }

}
