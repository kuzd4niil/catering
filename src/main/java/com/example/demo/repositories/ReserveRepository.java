package com.example.demo.repositories;

import com.example.demo.models.Reserve;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReserveRepository extends PagingAndSortingRepository<Reserve, Long> {
}
