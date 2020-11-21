package home.train.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Doc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private Byte[] storedFile;

    public Doc(String fileName, String fileType, Byte[] storedFile) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.storedFile = storedFile;
    }
}
