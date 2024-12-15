package com.darc.daveilguard.davback.controller;

import com.darc.daveilguard.davback.dto.EmpleadoDto;
import com.darc.daveilguard.davback.model.Empleado;
import com.darc.daveilguard.davback.services.EmpleadoService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FirebaseAuth firebaseAuth;

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
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado, HttpServletRequest request){
        try{
            validateToken(request);

            Optional<Empleado> empleadoOpt = service.crearEmpleado(empleado);
            if(empleadoOpt.isPresent()){
                return ResponseEntity.ok().body(empleadoOpt.get());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un empleado con ese DNI");
        }catch (Exception e){
            return ResponseEntity.status(401).body("Acceso denegado, token inválido");
        }

    }

    @PutMapping("/updateEmpleado")
    public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado, HttpServletRequest request){
        try {
            validateToken(request);

            Optional<Empleado> empleadoOpt = service.updateEmpleado(empleado);
            if(empleadoOpt.isPresent()){
                return ResponseEntity.ok().body(empleadoOpt.get());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un empleado con ese DNI");

        }catch (Exception e){
            return ResponseEntity.status(401).body("Acceso denegado, token inválido");
        }
    }

    @DeleteMapping("/deleteEmpleado/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable int id, HttpServletRequest request){
        try {
            validateToken(request);

            Boolean borrado = service.deleteEmpleado(id);
            if(borrado){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(401).body("Acceso denegado, token inválido");
        }
    }

    private FirebaseToken validateToken(HttpServletRequest request) throws IOException{
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null &&  authHeader.startsWith("Bearer ")){
            String idToken = authHeader.substring(7);
            try{
                return firebaseAuth.verifyIdToken(idToken);
            }catch (Exception e) {
                throw new IOException("Token no válido", e);
            }
        } else {
            throw new IOException("Token no proporcionado o erroneo");
        }
    }

}
