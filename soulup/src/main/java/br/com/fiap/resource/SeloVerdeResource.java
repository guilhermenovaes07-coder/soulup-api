package br.com.fiap.resource;

import br.com.fiap.bo.SeloVerdeBO;
import br.com.fiap.to.SeloVerdeTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soulup/selo")
public class SeloVerdeResource {
    private SeloVerdeBO seloVerdeBO = new SeloVerdeBO();

    @GetMapping
    public ResponseEntity<List<SeloVerdeTO>> findAll() {
        List<SeloVerdeTO> lista = seloVerdeBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        SeloVerdeTO selo = seloVerdeBO.findByCodigo(codigo);
        if (selo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(selo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Selo verde não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SeloVerdeTO selo) {
        try {
            SeloVerdeTO resultado = seloVerdeBO.save(selo);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar selo verde: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar selo verde");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid SeloVerdeTO selo) {
        if (seloVerdeBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Selo verde não encontrado!");
        }
        try {
            selo.setCodigo(codigo);
            SeloVerdeTO resultado = seloVerdeBO.update(selo);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar selo verde: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar selo verde");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (seloVerdeBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Selo verde não encontrado!");
        }
        if (seloVerdeBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Selo verde deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir selo verde: há registros relacionados ou a regra de negócio não permite");
        }
    }
}
