package com.api.rest.repository;

import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    private Empleado empleado;
    @BeforeEach
    private void setup(){
        this.empleado = Empleado.builder().nombre("Martin").apellido("Schipani").email("mss@gmail.com").build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    public void testGuardarEmpleado(){
        //********************* BDD ************************
        //given - dado o condicion previa o configuracion
        Empleado empleado1 = Empleado.builder().nombre("Pepe").apellido("Lopez#").email("p12@gmail.com").build();
        //when - accion o el comportamiento que vamos  probar
        Empleado empleadoGuardado = this.empleadoRepository.save(empleado1);
        //then - verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    public void testListarEmpleados(){
        // given
        Empleado empleado1 = Empleado.builder().nombre("Julen").apellido("Oliva").email("j2@gmail.com").build();
        this.empleadoRepository.save(empleado1);
        this.empleadoRepository.save(this.empleado);
        //when
        List<Empleado> listaEmpleados = this.empleadoRepository.findAll();
        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por id")
    @Test
    public void testParaObtenerEmpleadoPorId(){
        // given
        this.empleadoRepository.save(this.empleado);
        // when
        Empleado empleadoEncontrado = this.empleadoRepository.findById(this.empleado.getId()).get();
        // then
        assertThat(empleadoEncontrado).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    public void testActualizarEmpleado(){
        // given
        this.empleadoRepository.save(this.empleado);
        // when
        Empleado empleadoGuardado = this.empleadoRepository.findById(this.empleado.getId()).get();
        empleadoGuardado.setEmail("c232@gmail.com");
        empleadoGuardado.setNombre("Cristian");
        empleadoGuardado.setApellido("Ramirez");
        Empleado empleadoActualizado = this.empleadoRepository.save(empleadoGuardado);
        // then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("c232@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Cristian");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Ramirez");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    public void testEliminarEmpleado(){
        // given
        this.empleadoRepository.save(this.empleado);
        // when
        this.empleadoRepository.deleteById(this.empleado.getId());
        Optional<Empleado> empleadoOptional = this.empleadoRepository.findById(empleado.getId());
        // then
        assertThat(empleadoOptional).isEmpty();
    }
}
