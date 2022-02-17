package com.example.demo.services;

import com.example.demo.exception.UserWithThisEmailExists;
import com.example.demo.exception.UserWithThisUsernameExists;
import com.example.demo.models.Catering;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UserWithThisUsernameExists("A user with this username exists");
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new UserWithThisEmailExists("A user with this email exists");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    // @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getUserRole()));
    }

    static public Collection<? extends GrantedAuthority> mapRolesToAuthorities(ApplicationUserRole role) {
        return role.getPermissions().stream().map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toList());
    }

    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UserWithThisUsernameExists("A user with this username exists");
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new UserWithThisEmailExists("A user with this email exists");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(ApplicationUserRole.USER);
        user.setCaterings(new ArrayList<Catering>());
        return userRepository.save(user);
    }
}