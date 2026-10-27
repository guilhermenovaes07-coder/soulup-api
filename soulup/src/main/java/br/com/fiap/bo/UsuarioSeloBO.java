package br.com.fiap.bo;

import br.com.fiap.dao.SeloVerdeDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dao.UsuarioSeloDAO;
import br.com.fiap.to.UsuarioSeloTO;
import java.util.ArrayList;

public class UsuarioSeloBO {
    private UsuarioSeloDAO usuarioSeloDAO;
    private UsuarioDAO usuarioDAO;
    private SeloVerdeDAO seloVerdeDAO;

    public ArrayList<UsuarioSeloTO> findAll() {
        usuarioSeloDAO = new UsuarioSeloDAO();
        return usuarioSeloDAO.findAll();
    }

    public UsuarioSeloTO findByCodigo(Long codigo) {
        usuarioSeloDAO = new UsuarioSeloDAO();
        return usuarioSeloDAO.findByCodigo(codigo);
    }

    public UsuarioSeloTO save(UsuarioSeloTO conquista) {
        usuarioSeloDAO = new UsuarioSeloDAO();
        usuarioDAO = new UsuarioDAO();
        seloVerdeDAO = new SeloVerdeDAO();
        if (usuarioDAO.findByCodigo(conquista.getCodigoUsuario()) == null) {
            return null;
        }
        if (seloVerdeDAO.findByCodigo(conquista.getCodigoSelo()) == null) {
            return null;
        }
        if (usuarioSeloDAO.findByUsuarioAndSelo(conquista.getCodigoUsuario(), conquista.getCodigoSelo()) != null) {
            return null;
        }
        UsuarioSeloTO salva = usuarioSeloDAO.save(conquista);
        if (salva == null) {
            return null;
        }
        return usuarioSeloDAO.findByCodigo(salva.getCodigo());
    }

    public UsuarioSeloTO update(UsuarioSeloTO conquista) {
        usuarioSeloDAO = new UsuarioSeloDAO();
        usuarioDAO = new UsuarioDAO();
        seloVerdeDAO = new SeloVerdeDAO();
        if (usuarioSeloDAO.findByCodigo(conquista.getCodigo()) == null) {
            return null;
        }
        if (usuarioDAO.findByCodigo(conquista.getCodigoUsuario()) == null) {
            return null;
        }
        if (seloVerdeDAO.findByCodigo(conquista.getCodigoSelo()) == null) {
            return null;
        }
        UsuarioSeloTO outra = usuarioSeloDAO.findByUsuarioAndSelo(conquista.getCodigoUsuario(), conquista.getCodigoSelo());
        if (outra != null && !outra.getCodigo().equals(conquista.getCodigo())) {
            return null;
        }
        if (usuarioSeloDAO.update(conquista) == null) {
            return null;
        }
        return usuarioSeloDAO.findByCodigo(conquista.getCodigo());
    }

    public boolean delete(Long codigo) {
        usuarioSeloDAO = new UsuarioSeloDAO();
        return usuarioSeloDAO.delete(codigo);
    }

    public ArrayList<UsuarioSeloTO> findByUsuario(Long codigoUsuario) {
        usuarioSeloDAO = new UsuarioSeloDAO();
        return usuarioSeloDAO.findByUsuario(codigoUsuario);
    }
}
