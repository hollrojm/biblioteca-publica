package co.com.sofkau.bibliotecap.repositories;

import co.com.sofkau.bibliotecap.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecursoRepository extends MongoRepository<Recurso, String> {
}
