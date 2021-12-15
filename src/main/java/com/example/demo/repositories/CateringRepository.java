package com.example.demo.repositories;

import com.example.demo.models.Catering;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateringRepository extends PagingAndSortingRepository<Catering, Long> {

    Catering findByEmail(String email);
}