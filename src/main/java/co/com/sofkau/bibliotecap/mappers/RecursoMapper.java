package co.com.sofkau.bibliotecap.mappers;


import co.com.sofkau.bibliotecap.collections.Recurso;
import co.com.sofkau.bibliotecap.dtos.RecursoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public  interface RecursoMapper {

    RecursoDTO toRecursoDto(Recurso recurso);
    List<RecursoDTO> toRecursosDto(List<Recurso> recursos);

    @InheritInverseConfiguration
    Recurso toRecurso(RecursoDTO recursoDTO);
}