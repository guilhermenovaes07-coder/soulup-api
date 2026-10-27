package br.com.fiap.bo;

import br.com.fiap.dao.AcaoSustentavelDAO;
import br.com.fiap.dao.CategoriaAcaoDAO;
import br.com.fiap.to.AcaoSustentavelTO;
import java.util.ArrayList;

public class AcaoSustentavelBO {
    private AcaoSustentavelDAO acaoSustentavelDAO;
    private CategoriaAcaoDAO categoriaAcaoDAO;

    public ArrayList<AcaoSustentavelTO> findAll() {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        return acaoSustentavelDAO.findAll();
    }

    public AcaoSustentavelTO findByCodigo(Long codigo) {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        return acaoSustentavelDAO.findByCodigo(codigo);
    }

    public AcaoSustentavelTO save(AcaoSustentavelTO acao) {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        if (categoriaAcaoDAO.findByCodigo(acao.getCodigoCategoria()) == null) {
            return null;
        }
        if (acao.getAtivo() == null) {
            acao.setAtivo(true);
        }
        AcaoSustentavelTO salva = acaoSustentavelDAO.save(acao);
        if (salva == null) {
            return null;
        }
        return acaoSustentavelDAO.findByCodigo(salva.getCodigo());
    }

    public AcaoSustentavelTO update(AcaoSustentavelTO acao) {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        categoriaAcaoDAO = new CategoriaAcaoDAO();
        AcaoSustentavelTO existente = acaoSustentavelDAO.findByCodigo(acao.getCodigo());
        if (existente == null) {
            return null;
        }
        if (categoriaAcaoDAO.findByCodigo(acao.getCodigoCategoria()) == null) {
            return null;
        }
        if (acao.getAtivo() == null) {
            acao.setAtivo(existente.getAtivo());
        }
        if (acaoSustentavelDAO.update(acao) == null) {
            return null;
        }
        return acaoSustentavelDAO.findByCodigo(acao.getCodigo());
    }

    public boolean delete(Long codigo) {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        return acaoSustentavelDAO.delete(codigo);
    }

    public ArrayList<AcaoSustentavelTO> findByCategoria(Long codigoCategoria) {
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        return acaoSustentavelDAO.findByCategoria(codigoCategoria);
    }
}
