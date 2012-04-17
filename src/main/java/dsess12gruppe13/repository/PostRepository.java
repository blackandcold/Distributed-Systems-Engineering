package dsess12gruppe13.repository;

import dsess12gruppe13.domain.Post;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Post.class)
public interface PostRepository {

    List<dsess12gruppe13.domain.Post> findAll();
}
