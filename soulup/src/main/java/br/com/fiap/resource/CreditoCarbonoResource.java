package br.com.fiap.resource;

import br.com.fiap.bo.CreditoCarbonoBO;
import br.com.fiap.to.CreditoCarbonoTO;
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
@RequestMapping("/soulup/credito-carbono")
public class CreditoCarbonoResource {
    private CreditoCarbonoBO creditoCarbonoBO = new CreditoCarbonoBO();

    @GetMapping
    public ResponseEntity<List<CreditoCarbonoTO>> findAll() {
        List<CreditoCarbonoTO> lista = creditoCarbonoBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        CreditoCarbonoTO credito = creditoCarbonoBO.findByCodigo(codigo);
        if (credito != null) {
            return ResponseEntity.status(HttpStatus.OK).body(credito);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Crédito de carbono não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CreditoCarbonoTO credito) {
        try {
            CreditoCarbonoTO resultado = creditoCarbonoBO.save(credito);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar crédito de carbono: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar crédito de carbono");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid CreditoCarbonoTO credito) {
        if (creditoCarbonoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Crédito de carbono não encontrado!");
        }
        try {
            credito.setCodigo(codigo);
            CreditoCarbonoTO resultado = creditoCarbonoBO.update(credito);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar crédito de carbono: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar crédito de carbono");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (creditoCarbonoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Crédito de carbono não encontrado!");
        }
        if (creditoCarbonoBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Crédito de carbono deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir crédito de carbono: há registros relacionados ou a regra de negócio não permite");
        }
    }
}
