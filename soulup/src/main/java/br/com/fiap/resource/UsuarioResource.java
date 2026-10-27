package br.com.fiap.resource;

import br.com.fiap.bo.AvatarBO;
import br.com.fiap.bo.CreditoCarbonoBO;
import br.com.fiap.bo.UsuarioAcaoBO;
import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.bo.UsuarioSeloBO;
import br.com.fiap.to.AvatarTO;
import br.com.fiap.to.CreditoCarbonoTO;
import br.com.fiap.to.UsuarioAcaoTO;
import br.com.fiap.to.UsuarioSeloTO;
import br.com.fiap.to.UsuarioTO;
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
@RequestMapping("/soulup/usuario")
public class UsuarioResource {
    private UsuarioBO usuarioBO = new UsuarioBO();
    private AvatarBO avatarBO = new AvatarBO();
    private UsuarioAcaoBO usuarioAcaoBO = new UsuarioAcaoBO();
    private UsuarioSeloBO usuarioSeloBO = new UsuarioSeloBO();
    private CreditoCarbonoBO creditoCarbonoBO = new CreditoCarbonoBO();

    @GetMapping
    public ResponseEntity<List<UsuarioTO>> findAll() {
        List<UsuarioTO> lista = usuarioBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        UsuarioTO usuario = usuarioBO.findByCodigo(codigo);
        if (usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioTO usuario) {
        try {
            UsuarioTO resultado = usuarioBO.save(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar usuário");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid UsuarioTO usuario) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }
        try {
            usuario.setCodigo(codigo);
            UsuarioTO resultado = usuarioBO.update(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar usuário");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }
        if (usuarioBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir usuário: há registros relacionados ou a regra de negócio não permite");
        }
    }

    @GetMapping("/{codigo}/avatar")
    public ResponseEntity<?> findAvatar(@PathVariable Long codigo) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
        AvatarTO avatar = avatarBO.findByUsuario(codigo);
        if (avatar != null) {
            return ResponseEntity.status(HttpStatus.OK).body(avatar);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar não encontrado!");
        }
    }

    @GetMapping("/{codigo}/acoes")
    public ResponseEntity<?> findAcoes(@PathVariable Long codigo) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
        List<UsuarioAcaoTO> lista = usuarioAcaoBO.findByUsuario(codigo);
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}/selos")
    public ResponseEntity<?> findSelos(@PathVariable Long codigo) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
        List<UsuarioSeloTO> lista = usuarioSeloBO.findByUsuario(codigo);
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}/creditos")
    public ResponseEntity<?> findCreditos(@PathVariable Long codigo) {
        if (usuarioBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
        List<CreditoCarbonoTO> lista = creditoCarbonoBO.findByUsuario(codigo);
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }
}
