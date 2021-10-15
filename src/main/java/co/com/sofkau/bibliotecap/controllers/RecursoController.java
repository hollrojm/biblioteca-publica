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
@RequestMapping("api/recursos")
public  class RecursoController {
    Logger logger = LoggerFactory.getLogger(RecursoController.class);

    @Autowired
    private RecursoService recursoService;

    @GetMapping()
    public List<RecursoDTO> listResources(){
        return recursoService.lista();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RecursoDTO> findbyId(@PathVariable() String id) {
        return recursoService.getById(id)
                .map(recursoDTO -> new ResponseEntity<>(recursoDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "crear")
    public ResponseEntity<RecursoDTO> create(@RequestBody RecursoDTO recursoDTO) {
        return new ResponseEntity(recursoService.crear(recursoDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){

        if(recursoService.eliminar(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/modificar")
    public ResponseEntity<RecursoDTO> modificar(@RequestBody RecursoDTO recursoDTO) {
                    return new ResponseEntity<>(recursoService.modificar(recursoDTO),HttpStatus.OK);
        }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity disponible(@PathVariable() RecursoDTO id){
        return new ResponseEntity<>(recursoService.verificarRecursoDisponible(id),HttpStatus.OK);
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity<String> recursoPrestado(@PathVariable() String id){
        return new ResponseEntity<>(recursoService.recursoPrestado(id),HttpStatus.OK);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity recursoDevuelto(@PathVariable("id") String id){
        return  new ResponseEntity(recursoService.recursoDevuelto(id), HttpStatus.OK);
    }
    @GetMapping("/recomendar/tipo/{tipo}")
    public ResponseEntity<List<RecursoDTO>> recomendarPorTipo(@PathVariable() String tipo){
        return new ResponseEntity<>(recursoService.recomendarRecursoPorTipo(tipo),HttpStatus.OK);
    }

    @GetMapping("/recomendar/tematica/{tematica}")
    public ResponseEntity<List<RecursoDTO>> recomendarPorTematica(@PathVariable() String tematica){
        return new ResponseEntity<>(recursoService.recomendadoPorTema(tematica),HttpStatus.OK);
    }
}
