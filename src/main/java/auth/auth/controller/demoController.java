package auth.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/demo")
public class demoController {
    @GetMapping
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("OK!");
    }
}
