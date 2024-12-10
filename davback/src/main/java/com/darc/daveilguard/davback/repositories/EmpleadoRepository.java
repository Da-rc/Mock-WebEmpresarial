package com.darc.daveilguard.davback.repositories;

import com.darc.daveilguard.davback.model.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
    @Query("SELECT e FROM Empleado e WHERE " +
            "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.dni) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.area) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Empleado> busquedaPorTermino(String busqueda);
    List<Empleado> findByOficinaProvinciaIgnoreCase(String provincia);
    Optional<Empleado> findByDni(String dni);
}
