package br.com.fiap.bo;

import br.com.fiap.dao.AvatarDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.AvatarTO;
import java.util.ArrayList;

public class AvatarBO {
    private AvatarDAO avatarDAO;
    private UsuarioDAO usuarioDAO;

    public ArrayList<AvatarTO> findAll() {
        avatarDAO = new AvatarDAO();
        return avatarDAO.findAll();
    }

    public AvatarTO findByCodigo(Long codigo) {
        avatarDAO = new AvatarDAO();
        return avatarDAO.findByCodigo(codigo);
    }

    public AvatarTO save(AvatarTO avatar) {
        avatarDAO = new AvatarDAO();
        usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.findByCodigo(avatar.getCodigoUsuario()) == null) {
            return null;
        }
        if (avatarDAO.findByUsuario(avatar.getCodigoUsuario()) != null) {
            return null;
        }
        if (avatar.getNivel() == null) {
            avatar.setNivel(1);
        }
        if (avatar.getXp() == null) {
            avatar.setXp(0);
        }
        AvatarTO salvo = avatarDAO.save(avatar);
        if (salvo == null) {
            return null;
        }
        return avatarDAO.findByCodigo(salvo.getCodigo());
    }

    public AvatarTO update(AvatarTO avatar) {
        avatarDAO = new AvatarDAO();
        AvatarTO existente = avatarDAO.findByCodigo(avatar.getCodigo());
        if (existente == null) {
            return null;
        }
        avatar.setCodigoUsuario(existente.getCodigoUsuario());
        if (avatar.getNivel() == null) {
            avatar.setNivel(existente.getNivel());
        }
        if (avatar.getXp() == null) {
            avatar.setXp(existente.getXp());
        }
        if (avatarDAO.update(avatar) == null) {
            return null;
        }
        return avatarDAO.findByCodigo(avatar.getCodigo());
    }

    public boolean delete(Long codigo) {
        avatarDAO = new AvatarDAO();
        return avatarDAO.delete(codigo);
    }

    public AvatarTO findByUsuario(Long codigoUsuario) {
        avatarDAO = new AvatarDAO();
        return avatarDAO.findByUsuario(codigoUsuario);
    }
}
