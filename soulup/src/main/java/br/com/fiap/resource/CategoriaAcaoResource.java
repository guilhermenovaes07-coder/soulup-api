package br.com.fiap.resource;

import br.com.fiap.bo.CategoriaAcaoBO;
import br.com.fiap.to.CategoriaAcaoTO;
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
@RequestMapping("/soulup/categoria")
public class CategoriaAcaoResource {
    private CategoriaAcaoBO categoriaAcaoBO = new CategoriaAcaoBO();

    @GetMapping
    public ResponseEntity<List<CategoriaAcaoTO>> findAll() {
        List<CategoriaAcaoTO> lista = categoriaAcaoBO.findAll();
        if (lista != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        CategoriaAcaoTO categoria = categoriaAcaoBO.findByCodigo(codigo);
        if (categoria != null) {
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoria de ação não encontrada!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CategoriaAcaoTO categoria) {
        try {
            CategoriaAcaoTO resultado = categoriaAcaoBO.save(categoria);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar categoria de ação: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao salvar categoria de ação");
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid CategoriaAcaoTO categoria) {
        if (categoriaAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoria de ação não encontrada!");
        }
        try {
            categoria.setCodigo(codigo);
            CategoriaAcaoTO resultado = categoriaAcaoBO.update(categoria);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar categoria de ação: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro ao atualizar categoria de ação");
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (categoriaAcaoBO.findByCodigo(codigo) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoria de ação não encontrada!");
        }
        if (categoriaAcaoBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Categoria de ação deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não foi possível excluir categoria de ação: há registros relacionados ou a regra de negócio não permite");
        }
    }
}
