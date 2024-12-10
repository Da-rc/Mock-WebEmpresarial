package com.darc.daveilguard.davback.controller;

import com.darc.daveilguard.davback.dto.EmpleadoDto;
import com.darc.daveilguard.davback.model.Empleado;
import com.darc.daveilguard.davback.services.EmpleadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoDto>> findAll(){
        List<Empleado> empleados = service.findAll();
        List<EmpleadoDto> empleadosDto = empleados.stream().map(Empleado ->
                mapper.map(Empleado, EmpleadoDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(empleadosDto);
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
       Optional<Empleado> empleadoOpt = service.findById(id);
       if(empleadoOpt.isPresent()){
           EmpleadoDto empleadoDto = mapper.map(empleadoOpt.get(), EmpleadoDto.class);
           return ResponseEntity.ok().body(empleadoDto);
       }
       return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/{texto}")
    public ResponseEntity<List<EmpleadoDto>> search(@PathVariable String texto){
        List<Empleado> empleados = service.search(texto);
        List<EmpleadoDto> empleadoDtos = empleados.stream().map(Empleado ->
                mapper.map(Empleado, EmpleadoDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(empleadoDtos);
    }

    @GetMapping("/provincia/{provincia}")
    public ResponseEntity<List<EmpleadoDto>> findByProvincia(@PathVariable String provincia){
        List<Empleado> empleados = service.findByProvincia(provincia);
        List<EmpleadoDto> empleadoDtos = empleados.stream().map(Empleado ->
                mapper.map(Empleado, EmpleadoDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(empleadoDtos);
    }

    @PostMapping("/crearEmpleado")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado){
        Optional<Empleado> empleadoOpt = service.crearEmpleado(empleado);
        if(empleadoOpt.isPresent()){
            return ResponseEntity.ok().body(empleadoOpt.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un empleado con ese DNI");
    }

    @PutMapping("/updateEmpleado")
    public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado){
        Optional<Empleado> empleadoOpt = service.updateEmpleado(empleado);
        if(empleadoOpt.isPresent()){
            return ResponseEntity.ok().body(empleadoOpt.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un empleado con ese DNI");
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> deleteEmpleado(@RequestParam int id){
        Boolean borrado = service.deleteEmpleado(id);
        if(borrado){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}
