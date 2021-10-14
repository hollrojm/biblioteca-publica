package co.com.sofkau.bibliotecap.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecursoDTO {

    private String id;
    private String nombre;
    private LocalDate fecha;
    private Integer cantidadDisponible;
    private Integer cantidadPrestada;
    private String tipo;
    private String tematica;
}
