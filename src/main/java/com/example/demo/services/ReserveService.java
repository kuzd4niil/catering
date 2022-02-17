package com.example.demo.services;

import com.example.demo.models.Reserve;
import com.example.demo.repositories.ReserveRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;

    public ReserveService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    public List<Reserve> getAll() {
        List<Reserve> list = new ArrayList<>();
        reserveRepository.findAll().forEach(list::add);

        return list;
    }
}
