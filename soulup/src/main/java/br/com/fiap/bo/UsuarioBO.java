package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.UsuarioTO;
import java.util.ArrayList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO;

    public ArrayList<UsuarioTO> findAll() {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findAll();
    }

    public UsuarioTO findByCodigo(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findByCodigo(codigo);
    }

    public UsuarioTO save(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
            return null;
        }
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        UsuarioTO salvo = usuarioDAO.save(usuario);
        if (salvo == null) {
            return null;
        }
        return usuarioDAO.findByCodigo(salvo.getCodigo());
    }

    public UsuarioTO update(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.findByCodigo(usuario.getCodigo()) == null) {
            return null;
        }
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        UsuarioTO outro = usuarioDAO.findByEmail(usuario.getEmail());
        if (outro != null && !outro.getCodigo().equals(usuario.getCodigo())) {
            return null;
        }
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        if (usuarioDAO.update(usuario) == null) {
            return null;
        }
        return usuarioDAO.findByCodigo(usuario.getCodigo());
    }

    public boolean delete(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.delete(codigo);
    }
}
