package br.com.fiap.bo;

import br.com.fiap.dao.AcaoSustentavelDAO;
import br.com.fiap.dao.UsuarioAcaoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.AcaoSustentavelTO;
import br.com.fiap.to.UsuarioAcaoTO;
import java.util.ArrayList;

public class UsuarioAcaoBO {
    private UsuarioAcaoDAO usuarioAcaoDAO;
    private UsuarioDAO usuarioDAO;
    private AcaoSustentavelDAO acaoSustentavelDAO;

    public ArrayList<UsuarioAcaoTO> findAll() {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        return usuarioAcaoDAO.findAll();
    }

    public UsuarioAcaoTO findByCodigo(Long codigo) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        return usuarioAcaoDAO.findByCodigo(codigo);
    }

    public UsuarioAcaoTO save(UsuarioAcaoTO registro) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        usuarioDAO = new UsuarioDAO();
        acaoSustentavelDAO = new AcaoSustentavelDAO();
        if (usuarioDAO.findByCodigo(registro.getCodigoUsuario()) == null) {
            return null;
        }
        AcaoSustentavelTO acao = acaoSustentavelDAO.findByCodigo(registro.getCodigoAcao());
        if (acao == null || !Boolean.TRUE.equals(acao.getAtivo())) {
            return null;
        }
        registro.setPontosGanhos(acao.getPontosEcoa());
        registro.setStatus("PENDENTE");
        UsuarioAcaoTO salvo = usuarioAcaoDAO.save(registro);
        if (salvo == null) {
            return null;
        }
        return usuarioAcaoDAO.findByCodigo(salvo.getCodigo());
    }

    public UsuarioAcaoTO update(UsuarioAcaoTO registro) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        UsuarioAcaoTO existente = usuarioAcaoDAO.findByCodigo(registro.getCodigo());
        if (existente == null || !"PENDENTE".equals(existente.getStatus())) {
            return null;
        }
        existente.setEvidenciaUrl(registro.getEvidenciaUrl());
        if (usuarioAcaoDAO.update(existente) == null) {
            return null;
        }
        return usuarioAcaoDAO.findByCodigo(registro.getCodigo());
    }

    public boolean delete(Long codigo) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        UsuarioAcaoTO existente = usuarioAcaoDAO.findByCodigo(codigo);
        if (existente == null || "APROVADO".equals(existente.getStatus())) {
            return false;
        }
        return usuarioAcaoDAO.delete(codigo);
    }

    public UsuarioAcaoTO aprovar(Long codigo) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        UsuarioAcaoTO existente = usuarioAcaoDAO.findByCodigo(codigo);
        if (existente == null || !"PENDENTE".equals(existente.getStatus())) {
            return null;
        }
        if (!usuarioAcaoDAO.aprovar(codigo)) {
            return null;
        }
        return usuarioAcaoDAO.findByCodigo(codigo);
    }

    public UsuarioAcaoTO rejeitar(Long codigo) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        UsuarioAcaoTO existente = usuarioAcaoDAO.findByCodigo(codigo);
        if (existente == null || !"PENDENTE".equals(existente.getStatus())) {
            return null;
        }
        if (!usuarioAcaoDAO.rejeitar(codigo)) {
            return null;
        }
        return usuarioAcaoDAO.findByCodigo(codigo);
    }

    public ArrayList<UsuarioAcaoTO> findByUsuario(Long codigoUsuario) {
        usuarioAcaoDAO = new UsuarioAcaoDAO();
        return usuarioAcaoDAO.findByUsuario(codigoUsuario);
    }
}
