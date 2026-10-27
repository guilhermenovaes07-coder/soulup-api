package br.com.fiap.dao;

import br.com.fiap.to.SeloVerdeTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeloVerdeDAO {

    public ArrayList<SeloVerdeTO> findAll() {
        ArrayList<SeloVerdeTO> lista = new ArrayList<SeloVerdeTO>();
        String sql = "select * from TB_SELO_VERDE order by ID_SELO";
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

    public SeloVerdeTO findByCodigo(Long codigo) {
        String sql = "select * from TB_SELO_VERDE where ID_SELO = ?";
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

    public SeloVerdeTO save(SeloVerdeTO seloVerde) {
        String sql = "insert into TB_SELO_VERDE (ID_SELO, NM_SELO, DS_DESCRICAO, DS_CRITERIO, DS_ICONE_URL) values (SEQ_SELO_VERDE.nextval, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_SELO"})) {
            ps.setString(1, seloVerde.getNome());
            ps.setString(2, seloVerde.getDescricao());
            ps.setString(3, seloVerde.getCriterio());
            ps.setString(4, seloVerde.getIconeUrl());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    seloVerde.setCodigo(rs.getLong(1));
                }
                return seloVerde;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public SeloVerdeTO update(SeloVerdeTO seloVerde) {
        String sql = "update TB_SELO_VERDE set NM_SELO = ?, DS_DESCRICAO = ?, DS_CRITERIO = ?, DS_ICONE_URL = ? where ID_SELO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, seloVerde.getNome());
            ps.setString(2, seloVerde.getDescricao());
            ps.setString(3, seloVerde.getCriterio());
            ps.setString(4, seloVerde.getIconeUrl());
            ps.setLong(5, seloVerde.getCodigo());
            if (ps.executeUpdate() > 0) {
                return seloVerde;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_SELO_VERDE where ID_SELO = ?";
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

    private SeloVerdeTO montar(ResultSet rs) throws SQLException {
        SeloVerdeTO seloVerde = new SeloVerdeTO();
        seloVerde.setCodigo(rs.getLong("id_selo"));
        seloVerde.setNome(rs.getString("nm_selo"));
        seloVerde.setDescricao(rs.getString("ds_descricao"));
        seloVerde.setCriterio(rs.getString("ds_criterio"));
        seloVerde.setIconeUrl(rs.getString("ds_icone_url"));
        return seloVerde;
    }
}
