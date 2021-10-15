package co.com.sofkau.bibliotecap.services;

import co.com.sofkau.bibliotecap.dtos.RecursoDTO;

import java.util.List;
import java.util.Optional;

public interface IRecursoService {
    List<RecursoDTO> lista();
    RecursoDTO crear(RecursoDTO recursoDTO);
    boolean eliminar(String id);
    Optional<RecursoDTO> getById(String id);
    RecursoDTO modificar(RecursoDTO recursoDTO);
    boolean verificarRecursoDisponible(RecursoDTO recursoDTO);
    String recursoPrestado(String id);
    String recursoDevuelto(String id);
    List<RecursoDTO> recomendarRecursoPorTipo(String tipo);
    List<RecursoDTO> recomendadoPorTema(String tematica);
    List<RecursoDTO> recomendadoPorTemayTipo(String tipo, String tematica);

}


