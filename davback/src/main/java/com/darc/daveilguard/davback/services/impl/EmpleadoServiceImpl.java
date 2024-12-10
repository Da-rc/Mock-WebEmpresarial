package com.darc.daveilguard.davback.services.impl;

import com.darc.daveilguard.davback.model.Empleado;
import com.darc.daveilguard.davback.repositories.EmpleadoRepository;
import com.darc.daveilguard.davback.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        List<Empleado> empleados = (List<Empleado>) repository.findAll();
        System.out.println(empleados);
        return empleados;

    }

    @Override
    public Optional<Empleado> findById(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> search(String texto) {
        return repository.busquedaPorTermino(texto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findByProvincia(String provincia) {
        return repository.findByOficinaProvinciaIgnoreCase(provincia);
    }

    @Override
    @Transactional
    public Optional<Empleado> crearEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoOpt = repository.findByDni(empleado.getDni());
        if(empleadoOpt.isPresent()){
            return Optional.empty();
        }
        Empleado empleadoCreado = repository.save(empleado);
        return Optional.of(empleadoCreado);
    }

    @Override
    @Transactional
    public Optional<Empleado> updateEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoOpt = repository.findById(empleado.getId());
        if(empleadoOpt.isPresent()){
            Empleado empleadoCreado = repository.save(empleado);
            return Optional.of(empleadoCreado);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Boolean deleteEmpleado(int id) {
        Optional<Empleado> empleadoOpt = repository.findById(id);
        if(empleadoOpt.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
