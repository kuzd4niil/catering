package com.example.demo.services;

import com.example.demo.exception.CateringAlreadyExist;
import com.example.demo.models.Catering;
import com.example.demo.models.Reserve;
import com.example.demo.models.User;
import com.example.demo.repositories.CateringRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CateringService {

    private final CateringRepository cateringRepository;
    private final UserRepository userRepository;

    public CateringService(CateringRepository cateringRepository, UserRepository userRepository) {
        this.cateringRepository = cateringRepository;
        this.userRepository = userRepository;
    }

    public List<Catering> getCaterings(
            Integer numberPage,
            Short pageSize,
            Short sortDirection,
            String sortBy
    ) {
        Sort sort = Sort.by(
                (sortDirection == 1) ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortBy
        );
        Pageable pageable = PageRequest.of(
                numberPage,
                pageSize,
                sort
        );

        return cateringRepository.findAll(pageable).getContent();
    }

    public Catering addCatering(Catering catering, Principal principal) {
        String username, email;
        email = catering.getEmail();
        Catering existUser = cateringRepository.findByEmail(email);
        if (existUser != null) {
            throw new CateringAlreadyExist("Catering with this email already exist");
        }

        username = principal.getName();
        User user = userRepository.findByUsername(username);
        catering.setOwner(user);
        return cateringRepository.save(catering);
    }

    public Reserve reserve(Long cateringId, Principal principal) {
        Reserve reserve = new Reserve();
        //...
        return reserve;
    }

    public void removeCatering(Long id) {
        cateringRepository.deleteById(id);
    }

    public Catering updateCatering(Long cateringId, Catering catering) {
        Catering tempCatering = cateringRepository.findById(cateringId).get();
        if (tempCatering == null) {
            return cateringRepository.save(catering);
        } else {
            tempCatering.setName(catering.getName());
            tempCatering.setAddress(catering.getAddress());
            tempCatering.setEmail(catering.getEmail());
            return cateringRepository.save(tempCatering);
        }
    }

    public Catering getCatering(Long id) {
        return cateringRepository.findById(id).get();
    }

    public List<Catering> getAll() {
        List<Catering> list = new ArrayList<>();
        cateringRepository.findAll().forEach(list::add);

        return list;
    }
}
