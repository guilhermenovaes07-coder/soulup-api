package br.com.fiap.bo;

import br.com.fiap.dao.CategoriaAcaoDAO;
import br.com.fiap.to.CategoriaAcaoTO;
import java.util.ArrayList;

public class CategoriaAcaoBO {
    private CategoriaAcaoDAO categoriaAcaoDAO;

    public ArrayList<CategoriaAcaoTO> findAll() {
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        return categoriaAcaoDAO.findAll();
    }

    public CategoriaAcaoTO findByCodigo(Long codigo) {
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        return categoriaAcaoDAO.findByCodigo(codigo);
    }

    public CategoriaAcaoTO save(CategoriaAcaoTO categoria) {
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        categoria.setNome(categoria.getNome().trim());
        CategoriaAcaoTO salvo = categoriaAcaoDAO.save(categoria);
        if (salvo == null) {
            return null;
        }
        return categoriaAcaoDAO.findByCodigo(salvo.getCodigo());
    }

    public CategoriaAcaoTO update(CategoriaAcaoTO categoria) {
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        if (categoriaAcaoDAO.findByCodigo(categoria.getCodigo()) == null) {
            return null;
        }
        categoria.setNome(categoria.getNome().trim());
        if (categoriaAcaoDAO.update(categoria) == null) {
            return null;
        }
        return categoriaAcaoDAO.findByCodigo(categoria.getCodigo());
    }

    public boolean delete(Long codigo) {
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        return categoriaAcaoDAO.delete(codigo);
    }
}
