package br.edu.infnet.domain.controller;

import br.edu.infnet.domain.model.User;
import br.edu.infnet.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/users"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all-users")
    public ResponseEntity listUsers() {
        List<User> list = (List<User>) userRepository.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity getById(@PathVariable Integer id) {

        ResponseEntity response = ResponseEntity.notFound().build();
        User user = this.findById(id);
        if (user != null) {
            response = ResponseEntity.ok().body(user);
        }

        return response;
    }

    private User findById(Integer id) {

        User response = null;
        try {
            response = userRepository.findById(id).get();
        } catch (Exception e) {
        }

        return response;
    }

    @GetMapping(path = {"/email/{email}"})
    public ResponseEntity getByEmail(@PathVariable String email) {

        ResponseEntity response = ResponseEntity.notFound().build();
        try {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                response = ResponseEntity.ok().body(user);
            }
        } catch (Exception e) {
        }

        return response;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {

        ResponseEntity response = ResponseEntity.badRequest().build();
        if (user != null && user.getId() == null) {
            User registered = userRepository.save(user);
            response = ResponseEntity.status(HttpStatus.CREATED).body(registered);
        }

        return response;
    }

    @PutMapping
    public ResponseEntity editUser(@RequestBody User user) {

        ResponseEntity response = ResponseEntity.badRequest().build();
        if (user != null && user.getId() == null) {
            User registered = this.findById(user.getId());

            if (registered != null) {
                try {
                    registered = userRepository.save(user);
                    response = ResponseEntity.ok().body(registered);
                } catch (Exception e) {
                }
            }
        }

        return response;
    }
}
