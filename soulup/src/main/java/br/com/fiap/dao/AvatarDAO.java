package br.com.fiap.dao;

import br.com.fiap.to.AvatarTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvatarDAO {

    public ArrayList<AvatarTO> findAll() {
        ArrayList<AvatarTO> lista = new ArrayList<AvatarTO>();
        String sql = "select * from TB_AVATAR order by ID_AVATAR";
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

    public AvatarTO findByCodigo(Long codigo) {
        String sql = "select * from TB_AVATAR where ID_AVATAR = ?";
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

    public AvatarTO findByUsuario(Long codigoUsuario) {
        String sql = "select * from TB_AVATAR where ID_USUARIO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigoUsuario);
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

    public AvatarTO save(AvatarTO avatar) {
        String sql = "insert into TB_AVATAR (ID_AVATAR, ID_USUARIO, NM_AVATAR, NR_NIVEL, NR_XP, DS_APARENCIA_JSON) values (SEQ_AVATAR.nextval, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_AVATAR"})) {
            ps.setLong(1, avatar.getCodigoUsuario());
            ps.setString(2, avatar.getNome());
            ps.setInt(3, avatar.getNivel());
            ps.setInt(4, avatar.getXp());
            ps.setString(5, avatar.getAparenciaJson());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    avatar.setCodigo(rs.getLong(1));
                }
                return avatar;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public AvatarTO update(AvatarTO avatar) {
        String sql = "update TB_AVATAR set NM_AVATAR = ?, NR_NIVEL = ?, NR_XP = ?, DS_APARENCIA_JSON = ? where ID_AVATAR = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, avatar.getNome());
            ps.setInt(2, avatar.getNivel());
            ps.setInt(3, avatar.getXp());
            ps.setString(4, avatar.getAparenciaJson());
            ps.setLong(5, avatar.getCodigo());
            if (ps.executeUpdate() > 0) {
                return avatar;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_AVATAR where ID_AVATAR = ?";
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

    private AvatarTO montar(ResultSet rs) throws SQLException {
        AvatarTO avatar = new AvatarTO();
        avatar.setCodigo(rs.getLong("id_avatar"));
        avatar.setCodigoUsuario(rs.getLong("id_usuario"));
        avatar.setNome(rs.getString("nm_avatar"));
        avatar.setNivel(rs.getInt("nr_nivel"));
        avatar.setXp(rs.getInt("nr_xp"));
        avatar.setAparenciaJson(rs.getString("ds_aparencia_json"));
        return avatar;
    }
}
