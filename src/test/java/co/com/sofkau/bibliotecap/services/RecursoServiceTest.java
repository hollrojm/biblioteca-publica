package co.com.sofkau.bibliotecap.services;

import co.com.sofkau.bibliotecap.collections.Recurso;
import co.com.sofkau.bibliotecap.repositories.RecursoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecursoServiceTest {

    @MockBean
    private RecursoRepository recursoRepository;


    @Autowired
    private RecursoService recursoService;




}