package co.com.sofkau.bibliotecap.repositories;

import co.com.sofkau.bibliotecap.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecursoRepository extends MongoRepository<Recurso, String> {

    List<Recurso> encontrarPorTipo(final String tipo);
    List<Recurso> encontrarPorTematica(final String tematica);
}
