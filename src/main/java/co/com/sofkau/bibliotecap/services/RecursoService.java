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

@Service
public  class RecursoService {

    private RecursoRepository recursoRepository;
    private RecursoMapper mapper ;

    @Autowired
    public RecursoService(RecursoRepository recursoRepository, RecursoMapper mapper) {
        this.recursoRepository = recursoRepository;
        this.mapper = mapper;
    }

    public List<RecursoDTO> obtenerTodos() {
        List<Recurso> recursos = recursoRepository.findAll();
        return mapper.fromCollectionList(recursos);
    }
    public RecursoDTO obtenerPorId(String id) {
        Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return mapper.fromCollection(recurso);
    }
    public RecursoDTO crear(RecursoDTO recursoDTO) {
        Recurso recurso = mapper.fromDTO(recursoDTO);
        return mapper.fromCollection(recursoRepository.save(recurso));
    }
    public RecursoDTO modificar(RecursoDTO recursoDTO) {
        Recurso recurso = mapper.fromDTO(recursoDTO);
        recursoRepository.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return mapper.fromCollection(recursoRepository.save(recurso));
    }
    public void borrar(String id) {
        recursoRepository.deleteById(id);
    }

    public String verificarDisponibilidad(String id) {
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (diponible(recursoDTO)) {
            return "El recurso " + recursoDTO.getNombre() + " se encuentra disponible y cuenta con "+(recursoDTO.getCantidadDisponible()
            -recursoDTO.getCantidadPrestada())+" unidad(es) disponible(es)";
        }
        return "El recurso " + recursoDTO.getNombre() + " no esta disponible desde la fecha " + recursoDTO.getFecha();
    }
    private boolean diponible(RecursoDTO recursoDTO) {
        return recursoDTO.getCantidadDisponible() > recursoDTO.getCantidadPrestada();
    }
    public String prestarRecurso(String id) {
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (diponible(recursoDTO)) {
            recursoDTO.setCantidadPrestada(recursoDTO.getCantidadPrestada() + 1);
            recursoDTO.setFecha(LocalDate.now());
            modificar(recursoDTO);
            return "El recurso " + recursoDTO.getNombre() + " se ha prestado";
        }
        return "El recurso " + recursoDTO.getNombre() + " no tiene unidades disponibles para prestar";
    }
    public String restornarRecurso(String id) {
        RecursoDTO recursoDTO = obtenerPorId(id);
        if (recursoDTO.getCantidadPrestada() > 0) {
            recursoDTO.setCantidadPrestada(recursoDTO.getCantidadPrestada() - 1);
            modificar(recursoDTO);
            return "El recurso " + recursoDTO.getNombre() + " ha sido devuelto";
        }
        return "El recurso " + recursoDTO.getNombre() + " no aparece como prestado";
    }




}
