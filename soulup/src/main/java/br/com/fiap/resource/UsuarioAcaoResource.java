package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioAcaoBO;
import br.com.fiap.to.UsuarioAcaoTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soulup/registro-acao")
public class UsuarioAcaoResource {
    private UsuarioAcaoBO usuarioAcaoBO = new UsuarioAcaoBO();

    @GetMapping
    public ResponseEntity<List<UsuarioAcaoTO>> findAll() {
        List<UsuarioAcaoTO> lista = usuarioAcaoBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        UsuarioAcaoTO registro = usuarioAcaoBO.findByCodigo(codigo);
        if (registro != null) {
            return ResponseEntity.status(HttpStatus.OK).body(registro);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro de ação não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioAcaoTO registro) {
        try {
            UsuarioAcaoTO resultado = usuarioAcaoBO.save(registro);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar registro de ação: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar registro de ação");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid UsuarioAcaoTO registro) {
        if (usuarioAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro de ação não encontrado!");
        }
        try {
            registro.setCodigo(codigo);
            UsuarioAcaoTO resultado = usuarioAcaoBO.update(registro);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar registro de ação: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar registro de ação");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (usuarioAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro de ação não encontrado!");
        }
        if (usuarioAcaoBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Registro de ação deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir registro de ação: há registros relacionados ou a regra de negócio não permite");
        }
    }

    @PatchMapping("/{codigo}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable Long codigo) {
        if (usuarioAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de ação não encontrado!");
        }
        UsuarioAcaoTO resultado = usuarioAcaoBO.aprovar(codigo);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Somente registros pendentes podem ser aprovados");
        }
    }

    @PatchMapping("/{codigo}/rejeitar")
    public ResponseEntity<?> rejeitar(@PathVariable Long codigo) {
        if (usuarioAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de ação não encontrado!");
        }
        UsuarioAcaoTO resultado = usuarioAcaoBO.rejeitar(codigo);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Somente registros pendentes podem ser rejeitados");
        }
    }
}
