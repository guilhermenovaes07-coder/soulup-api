package br.com.fiap.dao;

import br.com.fiap.to.UsuarioAcaoTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioAcaoDAO {

    public ArrayList<UsuarioAcaoTO> findAll() {
        ArrayList<UsuarioAcaoTO> lista = new ArrayList<UsuarioAcaoTO>();
        String sql = "select * from TB_USUARIO_ACAO order by ID_REGISTRO";
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

    public UsuarioAcaoTO findByCodigo(Long codigo) {
        String sql = "select * from TB_USUARIO_ACAO where ID_REGISTRO = ?";
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

    public ArrayList<UsuarioAcaoTO> findByUsuario(Long codigoUsuario) {
        ArrayList<UsuarioAcaoTO> lista = new ArrayList<UsuarioAcaoTO>();
        String sql = "select * from TB_USUARIO_ACAO where ID_USUARIO = ? order by ID_REGISTRO";
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

    public UsuarioAcaoTO save(UsuarioAcaoTO usuarioAcao) {
        String sql = "insert into TB_USUARIO_ACAO (ID_REGISTRO, ID_USUARIO, ID_ACAO, NR_PONTOS_GANHOS, DS_EVIDENCIA_URL, DS_STATUS) values (SEQ_USUARIO_ACAO.nextval, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_REGISTRO"})) {
            ps.setLong(1, usuarioAcao.getCodigoUsuario());
            ps.setLong(2, usuarioAcao.getCodigoAcao());
            ps.setInt(3, usuarioAcao.getPontosGanhos());
            ps.setString(4, usuarioAcao.getEvidenciaUrl());
            ps.setString(5, usuarioAcao.getStatus());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    usuarioAcao.setCodigo(rs.getLong(1));
                }
                return usuarioAcao;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioAcaoTO update(UsuarioAcaoTO usuarioAcao) {
        String sql = "update TB_USUARIO_ACAO set DS_EVIDENCIA_URL = ? where ID_REGISTRO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuarioAcao.getEvidenciaUrl());
            ps.setLong(2, usuarioAcao.getCodigo());
            if (ps.executeUpdate() > 0) {
                return usuarioAcao;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_USUARIO_ACAO where ID_REGISTRO = ?";
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

    public boolean aprovar(Long codigo) {
        UsuarioAcaoTO registro = findByCodigo(codigo);
        if (registro == null) {
            return false;
        }
        try {
            ConnectionFactory.getConnection().setAutoCommit(false);
            String sqlStatus = "update TB_USUARIO_ACAO set DS_STATUS = 'APROVADO' where ID_REGISTRO = ? and DS_STATUS = 'PENDENTE'";
            String sqlSaldo = "update TB_USUARIO set NR_SALDO_ECOA_POINTS = NR_SALDO_ECOA_POINTS + ? where ID_USUARIO = ?";
            String sqlXp = "update TB_AVATAR set NR_XP = NR_XP + ?, NR_NIVEL = GREATEST(NR_NIVEL, FLOOR((NR_XP + ?) / 100) + 1) where ID_USUARIO = ?";
            try (PreparedStatement ps1 = ConnectionFactory.getConnection().prepareStatement(sqlStatus);
                 PreparedStatement ps2 = ConnectionFactory.getConnection().prepareStatement(sqlSaldo);
                 PreparedStatement ps3 = ConnectionFactory.getConnection().prepareStatement(sqlXp)) {
                ps1.setLong(1, codigo);
                if (ps1.executeUpdate() == 0) {
                    ConnectionFactory.getConnection().rollback();
                    return false;
                }
                ps2.setInt(1, registro.getPontosGanhos());
                ps2.setLong(2, registro.getCodigoUsuario());
                ps2.executeUpdate();
                ps3.setInt(1, registro.getPontosGanhos());
                ps3.setInt(2, registro.getPontosGanhos());
                ps3.setLong(3, registro.getCodigoUsuario());
                ps3.executeUpdate();
                ConnectionFactory.getConnection().commit();
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao aprovar: " + ex.getMessage());
            try {
                ConnectionFactory.getConnection().rollback();
            } catch (SQLException ex2) {
                System.out.println("Erro no rollback: " + ex2.getMessage());
            }
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public boolean rejeitar(Long codigo) {
        String sql = "update TB_USUARIO_ACAO set DS_STATUS = 'REJEITADO' where ID_REGISTRO = ? and DS_STATUS = 'PENDENTE'";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erro ao rejeitar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    private UsuarioAcaoTO montar(ResultSet rs) throws SQLException {
        UsuarioAcaoTO usuarioAcao = new UsuarioAcaoTO();
        usuarioAcao.setCodigo(rs.getLong("id_registro"));
        usuarioAcao.setCodigoUsuario(rs.getLong("id_usuario"));
        usuarioAcao.setCodigoAcao(rs.getLong("id_acao"));
        if (rs.getTimestamp("dt_realizacao") != null) {
            usuarioAcao.setDataRealizacao(rs.getTimestamp("dt_realizacao").toLocalDateTime());
        }
        usuarioAcao.setPontosGanhos(rs.getInt("nr_pontos_ganhos"));
        usuarioAcao.setEvidenciaUrl(rs.getString("ds_evidencia_url"));
        usuarioAcao.setStatus(rs.getString("ds_status"));
        return usuarioAcao;
    }
}
