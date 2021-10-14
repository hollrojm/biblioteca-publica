package co.com.sofkau.bibliotecap.mappers;


import co.com.sofkau.bibliotecap.collections.Recurso;
import co.com.sofkau.bibliotecap.dtos.RecursoDTO;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class RecursoMapper {

    public Recurso fromDTO(RecursoDTO recursoDto) {
        Recurso recurso = new Recurso();
        recurso.setId(recursoDto.getId());
        recurso.setNombre(recursoDto.getNombre());
        recurso.setFecha(recursoDto.getFecha());
        recurso.setCantidadDisponible(recursoDto.getCantidadDisponible());
        recurso.setCantidadPrestada(recursoDto.getCantidadPrestada());
        recurso.setTipo(recursoDto.getTipo());
        recurso.setTematica(recursoDto.getTematica());
        return recurso;
    }

    public RecursoDTO fromCollection(Recurso collection) {
        RecursoDTO recursoDTO = new RecursoDTO();
        recursoDTO.setId(collection.getId());
        recursoDTO.setNombre(collection.getNombre());
        recursoDTO.setFecha(collection.getFecha());
        recursoDTO.setCantidadDisponible(collection.getCantidadDisponible());
        recursoDTO.setCantidadPrestada(collection.getCantidadPrestada());
        recursoDTO.setTipo(collection.getTipo());
        recursoDTO.setTematica(collection.getTematica());
        return recursoDTO;

    }

    public List<RecursoDTO> fromCollectionList(List<Recurso> collection) {
        if (collection == null) {
            return null;
        }
        List<RecursoDTO> list       = new ArrayList(collection.size());
        Iterator         listTracks = collection.iterator();

        while (listTracks.hasNext()) {
            Recurso recurso = (Recurso) listTracks.next();
            list.add(fromCollection(recurso));
        }
        return list;
    }
}