package br.com.fiap.resource;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {

    @GetMapping({"/", "/soulup"})
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("aplicacao", "SoulUp API", "status", "UP"));
    }
}
