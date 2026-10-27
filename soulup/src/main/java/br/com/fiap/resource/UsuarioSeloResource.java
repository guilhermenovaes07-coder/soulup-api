package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioSeloBO;
import br.com.fiap.to.UsuarioSeloTO;
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
@RequestMapping("/soulup/conquista")
public class UsuarioSeloResource {
    private UsuarioSeloBO usuarioSeloBO = new UsuarioSeloBO();

    @GetMapping
    public ResponseEntity<List<UsuarioSeloTO>> findAll() {
        List<UsuarioSeloTO> lista = usuarioSeloBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        UsuarioSeloTO conquista = usuarioSeloBO.findByCodigo(codigo);
        if (conquista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(conquista);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Conquista de selo não encontrada!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioSeloTO conquista) {
        try {
            UsuarioSeloTO resultado = usuarioSeloBO.save(conquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar conquista de selo: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar conquista de selo");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid UsuarioSeloTO conquista) {
        if (usuarioSeloBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Conquista de selo não encontrada!");
        }
        try {
            conquista.setCodigo(codigo);
            UsuarioSeloTO resultado = usuarioSeloBO.update(conquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar conquista de selo: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar conquista de selo");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (usuarioSeloBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Conquista de selo não encontrada!");
        }
        if (usuarioSeloBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Conquista de selo deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir conquista de selo: há registros relacionados ou a regra de negócio não permite");
        }
    }
}
