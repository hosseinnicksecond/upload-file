package home.train.repository;

import home.train.entity.Doc;
import org.springframework.data.repository.CrudRepository;

public interface DocRepository extends CrudRepository<Doc,Long> {
}
