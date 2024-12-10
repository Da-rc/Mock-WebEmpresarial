package com.darc.daveilguard.davback.services;

import com.darc.daveilguard.davback.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> findAll();
    Optional<Empleado> findById(int id);
    List<Empleado> search(String texto);
    List<Empleado> findByProvincia(String provincia);
    Optional<Empleado> crearEmpleado(Empleado empleado);
    Optional<Empleado> updateEmpleado(Empleado empleado);
    Boolean deleteEmpleado(int id);
}
