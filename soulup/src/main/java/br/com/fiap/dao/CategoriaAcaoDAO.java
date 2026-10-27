package br.com.fiap.dao;

import br.com.fiap.to.CategoriaAcaoTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaAcaoDAO {

    public ArrayList<CategoriaAcaoTO> findAll() {
        ArrayList<CategoriaAcaoTO> lista = new ArrayList<CategoriaAcaoTO>();
        String sql = "select * from TB_CATEGORIA_ACAO order by ID_CATEGORIA";
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

    public CategoriaAcaoTO findByCodigo(Long codigo) {
        String sql = "select * from TB_CATEGORIA_ACAO where ID_CATEGORIA = ?";
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

    public CategoriaAcaoTO save(CategoriaAcaoTO categoriaAcao) {
        String sql = "insert into TB_CATEGORIA_ACAO (ID_CATEGORIA, NM_CATEGORIA, DS_DESCRICAO) values (SEQ_CATEGORIA_ACAO.nextval, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_CATEGORIA"})) {
            ps.setString(1, categoriaAcao.getNome());
            ps.setString(2, categoriaAcao.getDescricao());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    categoriaAcao.setCodigo(rs.getLong(1));
                }
                return categoriaAcao;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public CategoriaAcaoTO update(CategoriaAcaoTO categoriaAcao) {
        String sql = "update TB_CATEGORIA_ACAO set NM_CATEGORIA = ?, DS_DESCRICAO = ? where ID_CATEGORIA = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, categoriaAcao.getNome());
            ps.setString(2, categoriaAcao.getDescricao());
            ps.setLong(3, categoriaAcao.getCodigo());
            if (ps.executeUpdate() > 0) {
                return categoriaAcao;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_CATEGORIA_ACAO where ID_CATEGORIA = ?";
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

    private CategoriaAcaoTO montar(ResultSet rs) throws SQLException {
        CategoriaAcaoTO categoriaAcao = new CategoriaAcaoTO();
        categoriaAcao.setCodigo(rs.getLong("id_categoria"));
        categoriaAcao.setNome(rs.getString("nm_categoria"));
        categoriaAcao.setDescricao(rs.getString("ds_descricao"));
        return categoriaAcao;
    }
}
