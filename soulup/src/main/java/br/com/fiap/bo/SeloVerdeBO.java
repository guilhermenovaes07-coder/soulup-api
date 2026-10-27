package br.com.fiap.bo;

import br.com.fiap.dao.SeloVerdeDAO;
import br.com.fiap.to.SeloVerdeTO;
import java.util.ArrayList;

public class SeloVerdeBO {
    private SeloVerdeDAO seloVerdeDAO;

    public ArrayList<SeloVerdeTO> findAll() {
        seloVerdeDAO = new SeloVerdeDAO();
        return seloVerdeDAO.findAll();
    }

    public SeloVerdeTO findByCodigo(Long codigo) {
        seloVerdeDAO = new SeloVerdeDAO();
        return seloVerdeDAO.findByCodigo(codigo);
    }

    public SeloVerdeTO save(SeloVerdeTO selo) {
        seloVerdeDAO = new SeloVerdeDAO();
        selo.setNome(selo.getNome().trim());
        SeloVerdeTO salvo = seloVerdeDAO.save(selo);
        if (salvo == null) {
            return null;
        }
        return seloVerdeDAO.findByCodigo(salvo.getCodigo());
    }

    public SeloVerdeTO update(SeloVerdeTO selo) {
        seloVerdeDAO = new SeloVerdeDAO();
        if (seloVerdeDAO.findByCodigo(selo.getCodigo()) == null) {
            return null;
        }
        selo.setNome(selo.getNome().trim());
        if (seloVerdeDAO.update(selo) == null) {
            return null;
        }
        return seloVerdeDAO.findByCodigo(selo.getCodigo());
    }

    public boolean delete(Long codigo) {
        seloVerdeDAO = new SeloVerdeDAO();
        return seloVerdeDAO.delete(codigo);
    }
}
