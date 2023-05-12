package com.api.rest.service.impl;

import com.api.rest.Exceptions.ResourcesNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repository.EmpleadoRepository;
import com.api.rest.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImp implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = this.empleadoRepository.findByEmail(empleado.getEmail());
        if(empleadoGuardado.isPresent()){
            throw new ResourcesNotFoundException("El empleado con ese email ya existe: " + empleado.getEmail());
        }
        return this.empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return this.empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(long id) {
        return this.empleadoRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return this.empleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(long id) {
        this.empleadoRepository.deleteById(id);
    }
}
