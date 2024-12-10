package com.darc.daveilguard.davback.services.impl;

import com.darc.daveilguard.davback.model.Oficina;
import com.darc.daveilguard.davback.repositories.OficinaRepository;
import com.darc.daveilguard.davback.services.OficinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OficinaServiceImpl implements OficinaService {

   @Autowired
    private OficinaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Oficina> findAll() {
        return (List<Oficina>) repository.findAll();
    }
}
