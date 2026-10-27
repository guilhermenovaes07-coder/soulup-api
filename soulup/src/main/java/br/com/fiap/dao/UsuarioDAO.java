package br.com.fiap.dao;

import br.com.fiap.to.UsuarioTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class UsuarioDAO {

    public ArrayList<UsuarioTO> findAll() {
        ArrayList<UsuarioTO> lista = new ArrayList<UsuarioTO>();
        String sql = "select * from TB_USUARIO order by ID_USUARIO";
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

    public UsuarioTO findByCodigo(Long codigo) {
        String sql = "select * from TB_USUARIO where ID_USUARIO = ?";
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

    public UsuarioTO findByEmail(String email) {
        String sql = "select * from TB_USUARIO where lower(DS_EMAIL) = lower(?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
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

    public UsuarioTO save(UsuarioTO usuario) {
        String sql = "insert into TB_USUARIO (ID_USUARIO, NM_USUARIO, DS_EMAIL, DS_SENHA_HASH, DT_NASCIMENTO) values (SEQ_USUARIO.nextval, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_USUARIO"})) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            if (usuario.getDataNascimento() != null) {
                ps.setDate(4, Date.valueOf(usuario.getDataNascimento()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setCodigo(rs.getLong(1));
                }
                return usuario;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioTO update(UsuarioTO usuario) {
        String sql = "update TB_USUARIO set NM_USUARIO = ?, DS_EMAIL = ?, DS_SENHA_HASH = ?, DT_NASCIMENTO = ? where ID_USUARIO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            if (usuario.getDataNascimento() != null) {
                ps.setDate(4, Date.valueOf(usuario.getDataNascimento()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setLong(5, usuario.getCodigo());
            if (ps.executeUpdate() > 0) {
                return usuario;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_USUARIO where ID_USUARIO = ?";
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

    private UsuarioTO montar(ResultSet rs) throws SQLException {
        UsuarioTO usuario = new UsuarioTO();
        usuario.setCodigo(rs.getLong("id_usuario"));
        usuario.setNome(rs.getString("nm_usuario"));
        usuario.setEmail(rs.getString("ds_email"));
        if (rs.getDate("dt_nascimento") != null) {
            usuario.setDataNascimento(rs.getDate("dt_nascimento").toLocalDate());
        }
        if (rs.getTimestamp("dt_cadastro") != null) {
            usuario.setDataCadastro(rs.getTimestamp("dt_cadastro").toLocalDateTime());
        }
        usuario.setSaldoEcoaPoints(rs.getInt("nr_saldo_ecoa_points"));
        return usuario;
    }
}
