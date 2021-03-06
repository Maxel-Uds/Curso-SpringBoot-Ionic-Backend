package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cidade;
import com.maxel.cursomc.domain.Estado;
import com.maxel.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderByNome();
    }
}
