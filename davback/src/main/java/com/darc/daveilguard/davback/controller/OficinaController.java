package com.darc.daveilguard.davback.controller;

import com.darc.daveilguard.davback.dto.OficinaDto;
import com.darc.daveilguard.davback.model.Oficina;
import com.darc.daveilguard.davback.services.OficinaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OficinaController {

    @Autowired
    private OficinaService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/oficinas")
    public ResponseEntity<List<OficinaDto>> findAll(){
        List<Oficina> oficinas = service.findAll();
        List<OficinaDto> oficinasDto = oficinas.stream().map(Oficina -> mapper.map(Oficina, OficinaDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(oficinasDto);
    }
}
