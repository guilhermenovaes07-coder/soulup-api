package br.com.fiap.resource;

import br.com.fiap.bo.AcaoSustentavelBO;
import br.com.fiap.to.AcaoSustentavelTO;
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
@RequestMapping("/soulup/acao")
public class AcaoSustentavelResource {
    private AcaoSustentavelBO acaoSustentavelBO = new AcaoSustentavelBO();

    @GetMapping
    public ResponseEntity<List<AcaoSustentavelTO>> findAll() {
        List<AcaoSustentavelTO> lista = acaoSustentavelBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        AcaoSustentavelTO acao = acaoSustentavelBO.findByCodigo(codigo);
        if (acao != null) {
            return ResponseEntity.status(HttpStatus.OK).body(acao);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ação sustentável não encontrada!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AcaoSustentavelTO acao) {
        try {
            AcaoSustentavelTO resultado = acaoSustentavelBO.save(acao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar ação sustentável: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar ação sustentável");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid AcaoSustentavelTO acao) {
        if (acaoSustentavelBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ação sustentável não encontrada!");
        }
        try {
            acao.setCodigo(codigo);
            AcaoSustentavelTO resultado = acaoSustentavelBO.update(acao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar ação sustentável: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar ação sustentável");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (acaoSustentavelBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ação sustentável não encontrada!");
        }
        if (acaoSustentavelBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Ação sustentável deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir ação sustentável: há registros relacionados ou a regra de negócio não permite");
        }
    }

    @GetMapping("/categoria/{codigoCategoria}")
    public ResponseEntity<List<AcaoSustentavelTO>> findByCategoria(@PathVariable Long codigoCategoria) {
        List<AcaoSustentavelTO> lista = acaoSustentavelBO.findByCategoria(codigoCategoria);
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }
}
