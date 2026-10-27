package br.com.fiap.bo;

import br.com.fiap.dao.CreditoCarbonoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.CreditoCarbonoTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CreditoCarbonoBO {
    private CreditoCarbonoDAO creditoCarbonoDAO;
    private UsuarioDAO usuarioDAO;

    public ArrayList<CreditoCarbonoTO> findAll() {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        return creditoCarbonoDAO.findAll();
    }

    public CreditoCarbonoTO findByCodigo(Long codigo) {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        return creditoCarbonoDAO.findByCodigo(codigo);
    }

    public CreditoCarbonoTO save(CreditoCarbonoTO credito) {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.findByCodigo(credito.getCodigoUsuario()) == null) {
            return null;
        }
        credito.setQuantidadeKg(credito.getQuantidadeKg().setScale(2, RoundingMode.HALF_UP));
        if (credito.getQuantidadeKg().compareTo(BigDecimal.ZERO) <= 0 || credito.getQuantidadeKg().compareTo(new BigDecimal("99999999.99")) > 0) {
            return null;
        }
        if (credito.getStatus() == null) {
            credito.setStatus("ATIVO");
        }
        CreditoCarbonoTO salvo = creditoCarbonoDAO.save(credito);
        if (salvo == null) {
            return null;
        }
        return creditoCarbonoDAO.findByCodigo(salvo.getCodigo());
    }

    public CreditoCarbonoTO update(CreditoCarbonoTO credito) {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        CreditoCarbonoTO existente = creditoCarbonoDAO.findByCodigo(credito.getCodigo());
        if (existente == null) {
            return null;
        }
        credito.setCodigoUsuario(existente.getCodigoUsuario());
        credito.setQuantidadeKg(credito.getQuantidadeKg().setScale(2, RoundingMode.HALF_UP));
        if (credito.getQuantidadeKg().compareTo(BigDecimal.ZERO) <= 0 || credito.getQuantidadeKg().compareTo(new BigDecimal("99999999.99")) > 0) {
            return null;
        }
        if (credito.getStatus() == null) {
            credito.setStatus(existente.getStatus());
        }
        if (creditoCarbonoDAO.update(credito) == null) {
            return null;
        }
        return creditoCarbonoDAO.findByCodigo(credito.getCodigo());
    }

    public boolean delete(Long codigo) {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        return creditoCarbonoDAO.delete(codigo);
    }

    public ArrayList<CreditoCarbonoTO> findByUsuario(Long codigoUsuario) {
        creditoCarbonoDAO = new CreditoCarbonoDAO();
        return creditoCarbonoDAO.findByUsuario(codigoUsuario);
    }
}
