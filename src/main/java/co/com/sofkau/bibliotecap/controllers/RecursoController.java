package co.com.sofkau.bibliotecap.controllers;

import co.com.sofkau.bibliotecap.dtos.RecursoDTO;
import co.com.sofkau.bibliotecap.services.RecursoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recursos/")
public  class RecursoController {
    Logger logger = LoggerFactory.getLogger(RecursoController.class);

    private RecursoService recursoService;

    @Autowired
    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecursoDTO> findbyId(@PathVariable("id") String id) {
        return new ResponseEntity(recursoService.obtenerPorId(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<RecursoDTO>> findAll() {
        return new ResponseEntity(recursoService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping(value = "crear")
    public ResponseEntity<RecursoDTO> create(@RequestBody RecursoDTO recursoDTO) {
        return new ResponseEntity(recursoService.crear(recursoDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/modificar")
    public ResponseEntity<RecursoDTO> update(@RequestBody RecursoDTO recursoDTO) {
        if (recursoDTO.getId() != null) {
            return new ResponseEntity(recursoService.modificar(recursoDTO), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        try {
            recursoService.borrar(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity disponible(@PathVariable("id") String id){
        return new ResponseEntity(recursoService.verificarDisponibilidad(id), HttpStatus.OK);
    }
    @PutMapping("/prestar/{id}")
    public ResponseEntity lend(@PathVariable("id") String id){
        return  new ResponseEntity(recursoService.prestarRecurso(id), HttpStatus.OK);
    }
    @PutMapping("/devolver/{id}")
    public ResponseEntity returnResource(@PathVariable("id") String id){
        return  new ResponseEntity(recursoService.restornarRecurso(id), HttpStatus.OK);
    }
}
