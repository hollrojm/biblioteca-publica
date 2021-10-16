package co.com.sofkau.bibliotecap.services;

import co.com.sofkau.bibliotecap.collections.Recurso;
import co.com.sofkau.bibliotecap.dtos.RecursoDTO;
import co.com.sofkau.bibliotecap.mappers.RecursoMapper;
import co.com.sofkau.bibliotecap.repositories.RecursoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RecursoServiceTest {

    @MockBean
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private RecursoMapper recursoMapper;

    private List<Recurso> recursos() {

        var recurso = new Recurso();
        recurso.setId("000111");
        recurso.setNombre("Cien años de soledad");
        recurso.setFecha(LocalDate.parse("2021-09-27"));
        recurso.setCantidadDisponible(30);
        recurso.setCantidadPrestada(0);
        recurso.setTipo("Ficcion");
        recurso.setTematica("Ranativa");


        var recurso2 = new Recurso();
        recurso2.setId("000112");
        recurso2.setNombre("Satanas");
        recurso2.setFecha(LocalDate.parse("2020-10-27"));
        recurso2.setCantidadDisponible(10);
        recurso2.setCantidadPrestada(0);
        recurso2.setTipo("Novela");
        recurso2.setTematica("Periodistica");


        var recurso3 = new Recurso();
        recurso3.setId("000113");
        recurso3.setNombre("El señor de los anillos");
        recurso3.setFecha(LocalDate.parse("2018-05-10"));
        recurso3.setCantidadDisponible(3);
        recurso3.setCantidadPrestada(0);
        recurso3.setTipo("Ficcion");
        recurso3.setTematica("Aventura");


        var recursos = new ArrayList<Recurso>();
        recursos.add(recurso);
        recursos.add(recurso2);
        recursos.add(recurso3);

        return recursos;
    }

    @Test
    @DisplayName("Test guardar recursos ok")
    void create() {

        var recurso = new Recurso();
        recurso.setId("000140");
        recurso.setNombre("Lady Masacre");
        recurso.setFecha(LocalDate.parse("2018-11-15"));
        recurso.setCantidadDisponible(50);
        recurso.setCantidadPrestada(0);
        recurso.setTipo("Narrativa");
        recurso.setTematica("Aventura");


        var recursoDTO = new RecursoDTO();
        recursoDTO.setNombre("Lady Masacre");
        recursoDTO.setFecha(LocalDate.parse("2018-11-15"));
        recursoDTO.setCantidadDisponible(50);
        recursoDTO.setCantidadPrestada(0);
        recursoDTO.setTipo("Narrativa");
        recursoDTO.setTematica("Aventura");

        Mockito.when(recursoRepository.save(any())).thenReturn(recurso);

        var result = recursoService.crear(recursoDTO);

        Assertions.assertNotNull(result, "El valor guardado no debe ser nulo");

        Assertions.assertEquals(recurso.getNombre(), result.getNombre(), "el nombre no corresponde");
        Assertions.assertEquals(recurso.getFecha(), result.getFecha(), "La fecha no corresponde");
        Assertions.assertEquals(recurso.getCantidadDisponible(), result.getCantidadDisponible(), "La cantidad no corresponde");
        Assertions.assertEquals(recurso.getCantidadPrestada(), result.getCantidadPrestada(), "La cantidad no corresponde");
        Assertions.assertEquals(recurso.getTipo(), result.getTipo(), "el tipo no corresponde");
        Assertions.assertEquals(recurso.getTematica(), result.getTematica(), "La tematica no corresponde");

    }

    @Test
    @DisplayName("Test get list resources success")
    void getListResources(){

        Mockito.when(recursoRepository.findAll()).thenReturn(recursos());

        var result = recursoService.lista();

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Cien años de soledad", result.get(0).getNombre());
        Assertions.assertEquals("Satanas", result.get(1).getNombre());
        Assertions.assertEquals("El señor de los anillos", result.get(2).getNombre());

    }

    @Test
    @DisplayName("Test get resource by id")
    void getResourceById(){

        Mockito.when(recursoRepository.findById(Mockito.any())).thenReturn(recursos().stream().findFirst());

        var result = recursoService.getById(recursos().get(0).getId());

        Assertions.assertEquals(recursos().get(0).getId(), result.get().getId(), "El id no corresponde");

        Assertions.assertEquals("Cien años de soledad", result.get().getNombre(), "El nombre debe corresponder");
        Assertions.assertEquals(30, result.get().getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(LocalDate.parse("2021-09-27"), result.get().getFecha(), "La fecha de cuando se presto debe estar nula");
        Assertions.assertEquals(0, result.get().getCantidadPrestada(), "La cantidad prestada debe ser cero");
        Assertions.assertEquals("Ficcion", result.get().getTipo(), "El tipo debe coincidir ");
        Assertions.assertEquals("Ranativa", result.get().getTematica(), "La tematica debe coincidir");

    }


    @Test
    @DisplayName("test to edit a resource succes")
    void updateResource() {
        var recurso = new RecursoDTO();
        recurso.setId("1111");
        recurso.setNombre("100 años de Soledad Gabriel Garcia");
        recurso.setFecha(LocalDate.parse("2021-11-13"));
        recurso.setCantidadDisponible(30);
        recurso.setCantidadPrestada(0);
        recurso.setTipo("Novela");
        recurso.setTematica("Historia");


        Mockito.when(recursoRepository.save(Mockito.any())).thenReturn(recursoMapper.toRecurso(recurso));
        Mockito.when(recursoRepository.findById(recurso.getId())).thenReturn(recursos().stream().findFirst());
        var result = recursoService.modificar(recurso);

        Assertions.assertNotNull(result, "El dato guardado no debe ser vacio");

        Assertions.assertEquals("1111", result.getId(), "el id debe corresponder");
        Assertions.assertEquals("100 años de Soledad Gabriel Garcia", result.getNombre(), "El nombre debe corresponder");

        Assertions.assertEquals(30, result.getCantidadDisponible(), "la cantidad disponible debe ser igual");
        Assertions.assertEquals(LocalDate.parse("2021-11-13"), result.getFecha(), "la fecha de cuando se presto debe estar nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "la cantidad prestada debe ser cero");
        Assertions.assertEquals("Novela", result.getTipo(), "el tipo debe coincidir ");
        Assertions.assertEquals("Historia", result.getTematica(), "la tematica debe coincidir");
    }

    @Test
    @DisplayName("test prestar recurso ok")
    void LendResourceSuccessfully() {
        var recursoDTO = new RecursoDTO();
        recursoDTO.setId("000111");
        recursoDTO.setNombre("Cien años de soledad");
        recursoDTO.setFecha(LocalDate.parse("2021-09-27"));
        recursoDTO.setCantidadDisponible(30);
        recursoDTO.setCantidadPrestada(0);
        recursoDTO.setTipo("Ficcion");
        recursoDTO.setTematica("Ranativa");

        Mockito.when(recursoRepository.findById(recursoDTO.getId())).thenReturn(recursos().stream().findFirst());
        Mockito.when(recursoRepository.save(Mockito.any())).thenReturn(recursoMapper.toRecurso(recursoDTO));
        var result = recursoService.recursoPrestado(recursoDTO.getId());

        Assertions.assertEquals("EL prestamo del recurso fue exitoso", result);

    }

}