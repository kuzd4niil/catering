package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.ApplicationUserRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);

        return list;
    }

    public List<User> getUsers(
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

        return userRepository.findAll(pageable).getContent();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        User tempUser = userRepository.findById(userId).get();
        if (tempUser == null) {
            return userRepository.save(user);
        } else {
            tempUser.setUsername(user.getUsername());
            tempUser.setEmail(user.getEmail());
            return userRepository.save(tempUser);
        }
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    // UserDetailService interface implementation
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getUserRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(ApplicationUserRole role) {
        return role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
    }
}