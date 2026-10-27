package br.com.fiap.dao;

import br.com.fiap.to.UsuarioSeloTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioSeloDAO {

    public ArrayList<UsuarioSeloTO> findAll() {
        ArrayList<UsuarioSeloTO> lista = new ArrayList<UsuarioSeloTO>();
        String sql = "select * from TB_USUARIO_SELO order by ID_USUARIO_SELO";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(montar(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Erro na consulta: " + ex.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        return lista;
    }

    public UsuarioSeloTO findByCodigo(Long codigo) {
        String sql = "select * from TB_USUARIO_SELO where ID_USUARIO_SELO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return montar(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Erro na consulta: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public ArrayList<UsuarioSeloTO> findByUsuario(Long codigoUsuario) {
        ArrayList<UsuarioSeloTO> lista = new ArrayList<UsuarioSeloTO>();
        String sql = "select * from TB_USUARIO_SELO where ID_USUARIO = ? order by ID_USUARIO_SELO";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigoUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(montar(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Erro na consulta: " + ex.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        return lista;
    }

    public UsuarioSeloTO findByUsuarioAndSelo(Long codigoUsuario, Long codigoSelo) {
        String sql = "select * from TB_USUARIO_SELO where ID_USUARIO = ? and ID_SELO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigoUsuario);
            ps.setLong(2, codigoSelo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return montar(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Erro na consulta: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioSeloTO save(UsuarioSeloTO usuarioSelo) {
        String sql = "insert into TB_USUARIO_SELO (ID_USUARIO_SELO, ID_USUARIO, ID_SELO) values (SEQ_USUARIO_SELO.nextval, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_USUARIO_SELO"})) {
            ps.setLong(1, usuarioSelo.getCodigoUsuario());
            ps.setLong(2, usuarioSelo.getCodigoSelo());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    usuarioSelo.setCodigo(rs.getLong(1));
                }
                return usuarioSelo;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioSeloTO update(UsuarioSeloTO usuarioSelo) {
        String sql = "update TB_USUARIO_SELO set ID_USUARIO = ?, ID_SELO = ? where ID_USUARIO_SELO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, usuarioSelo.getCodigoUsuario());
            ps.setLong(2, usuarioSelo.getCodigoSelo());
            ps.setLong(3, usuarioSelo.getCodigo());
            if (ps.executeUpdate() > 0) {
                return usuarioSelo;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_USUARIO_SELO where ID_USUARIO_SELO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    private UsuarioSeloTO montar(ResultSet rs) throws SQLException {
        UsuarioSeloTO usuarioSelo = new UsuarioSeloTO();
        usuarioSelo.setCodigo(rs.getLong("id_usuario_selo"));
        usuarioSelo.setCodigoUsuario(rs.getLong("id_usuario"));
        usuarioSelo.setCodigoSelo(rs.getLong("id_selo"));
        if (rs.getTimestamp("dt_conquista") != null) {
            usuarioSelo.setDataConquista(rs.getTimestamp("dt_conquista").toLocalDateTime());
        }
        return usuarioSelo;
    }
}
