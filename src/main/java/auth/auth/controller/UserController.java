package auth.auth.controller;

import auth.auth.model.Role;
import auth.auth.model.User;
import auth.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {
    public final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable int id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        repo.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        User update = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        update.setUsername(user.getUsername());
        //if(update.getPassword().equals(user.getPassword()))//same encoded password
        //{
            //update.setPassword(user.getPassword());
        //}
        //else {
        update.setPassword(passwordEncoder.encode(user.getPassword()));
        //}
        //update.setPassword(user.getPassword());
        repo.save(update);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        User delete = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (delete.getRole()!=Role.ADMIN) {
            repo.delete(delete);
            return ResponseEntity.badRequest().build();
        }
        else
            return ResponseEntity.noContent().build();
    }
}
