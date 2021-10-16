package co.com.sofkau.bibliotecap.services;

import co.com.sofkau.bibliotecap.collections.Recurso;
import co.com.sofkau.bibliotecap.dtos.RecursoDTO;
import co.com.sofkau.bibliotecap.mappers.RecursoMapper;
import co.com.sofkau.bibliotecap.repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecursoService implements IRecursoService{

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoMapper recursoMapper;


    @Override
    public List<RecursoDTO> lista() {
        return recursoMapper.toRecursosDto(recursoRepository.findAll());
    }

    @Override
    public RecursoDTO crear(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.toRecurso(recursoDTO);
        return recursoMapper.toRecursoDto(recursoRepository.save(recurso));
    }

    @Override
    public boolean eliminar(String id) {
        return getById(id).map(recursoDTO -> {
            recursoRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<RecursoDTO> getById(String id) {
        return recursoRepository.findById(id)
                .map(recurso -> recursoMapper.toRecursoDto(recurso));
    }

    @Override
    public RecursoDTO modificar(RecursoDTO recursoDTO) {
        Optional<Recurso> recurso = recursoRepository.findById(recursoDTO.getId());

        if(recurso.isPresent()){

            recurso.get().setId(recursoDTO.getId());
            recurso.get().setNombre(recursoDTO.getNombre());
            recurso.get().setFecha(recursoDTO.getFecha());
            recurso.get().setCantidadDisponible(recursoDTO.getCantidadDisponible());
            recurso.get().setCantidadPrestada(recursoDTO.getCantidadPrestada());
            recurso.get().setTipo(recursoDTO.getTipo());
            recurso.get().setTematica(recursoDTO.getTematica());

            return recursoMapper.toRecursoDto(recursoRepository.save(recurso.get()));
        }
        throw new RuntimeException("El recurso indicado no existe");
    }

    @Override
    public boolean verificarRecursoDisponible(RecursoDTO recursoDTO) {
        return recursoDTO.getCantidadDisponible()>recursoDTO.getCantidadPrestada();
    }



    @Override
    public String recursoPrestado(String id) {
        return getById(id).map(recursoDTO -> {
            if(verificarRecursoDisponible(recursoDTO)){
                recursoDTO.setCantidadPrestada(recursoDTO.getCantidadPrestada()+1);
                recursoDTO.setFecha(LocalDate.now());

                Recurso recursoModificado = recursoMapper.toRecurso(recursoDTO);
                recursoRepository.save(recursoModificado);

                return "EL prestamo del recurso fue exitoso";
            }
            return "Recurso no disponible en el momento";
        }).orElseThrow(()->new RuntimeException("El recurso a prestar no existe"));
    }

    @Override
    public String recursoDevuelto(String id) {
        return getById(id).map(recursoDTO -> {

            if(recursoDTO.getCantidadPrestada()>0){
                recursoDTO.setCantidadPrestada(recursoDTO.getCantidadPrestada()-1);

                Recurso recursoModificado = recursoMapper.toRecurso(recursoDTO);
                recursoRepository.save(recursoModificado);

                return "Devolucion del recurso con exito";
            }

            return "No hay recursos pendientes por devolver";
        }).orElseThrow(()-> new RuntimeException("Recurso no existe"));
    }

    @Override
    public List<RecursoDTO> recomendarRecursoPorTipo(String tipo) {
        List<RecursoDTO> listarRecursos = new ArrayList<>();
        recursoRepository.encontrarPorTipo(tipo).forEach(recurso -> {
            listarRecursos.add(recursoMapper.toRecursoDto(recurso));
        });
        return listarRecursos;
    }


    @Override
    public List<RecursoDTO> recomendadoPorTema(String tematica) {
        List<RecursoDTO> listarRecursos = new ArrayList<>();
        recursoRepository.encontrarPorTematica(tematica).forEach(recurso -> {
            listarRecursos.add(recursoMapper.toRecursoDto(recurso));
        });
        return listarRecursos;
    }

    @Override
    public List<RecursoDTO> recomendadoPorTemayTipo(String tipo, String tematica) {
        List<RecursoDTO> recursoDTOS = new ArrayList<>();
        List<RecursoDTO> listarRecursos = new ArrayList<>();

        listarRecursos.addAll(recomendadoPorTema(tematica));
        listarRecursos.addAll(recomendarRecursoPorTipo(tipo));

        listarRecursos.stream().distinct().forEach(recursoDTOS::add);

        return recursoDTOS;
    }
}
