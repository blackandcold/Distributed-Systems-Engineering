package dsess12gruppe13.repository;

import dsess12gruppe13.domain.Person;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Person.class)
public interface PersonRepository {

    List<dsess12gruppe13.domain.Person> findAll();
}
