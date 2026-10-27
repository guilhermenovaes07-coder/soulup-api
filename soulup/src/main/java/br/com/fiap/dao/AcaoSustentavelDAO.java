package br.com.fiap.dao;

import br.com.fiap.to.AcaoSustentavelTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcaoSustentavelDAO {

    public ArrayList<AcaoSustentavelTO> findAll() {
        ArrayList<AcaoSustentavelTO> lista = new ArrayList<AcaoSustentavelTO>();
        String sql = "select * from TB_ACAO_SUSTENTAVEL order by ID_ACAO";
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

    public AcaoSustentavelTO findByCodigo(Long codigo) {
        String sql = "select * from TB_ACAO_SUSTENTAVEL where ID_ACAO = ?";
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

    public ArrayList<AcaoSustentavelTO> findByCategoria(Long codigoCategoria) {
        ArrayList<AcaoSustentavelTO> lista = new ArrayList<AcaoSustentavelTO>();
        String sql = "select * from TB_ACAO_SUSTENTAVEL where ID_CATEGORIA = ? order by ID_ACAO";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigoCategoria);
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

    public AcaoSustentavelTO save(AcaoSustentavelTO acaoSustentavel) {
        String sql = "insert into TB_ACAO_SUSTENTAVEL (ID_ACAO, ID_CATEGORIA, NM_ACAO, DS_DESCRICAO, NR_PONTOS_ECOA, FL_ATIVO) values (SEQ_ACAO_SUSTENTAVEL.nextval, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_ACAO"})) {
            ps.setLong(1, acaoSustentavel.getCodigoCategoria());
            ps.setString(2, acaoSustentavel.getNome());
            ps.setString(3, acaoSustentavel.getDescricao());
            ps.setInt(4, acaoSustentavel.getPontosEcoa());
            ps.setString(5, acaoSustentavel.getAtivo() ? "S" : "N");
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    acaoSustentavel.setCodigo(rs.getLong(1));
                }
                return acaoSustentavel;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public AcaoSustentavelTO update(AcaoSustentavelTO acaoSustentavel) {
        String sql = "update TB_ACAO_SUSTENTAVEL set ID_CATEGORIA = ?, NM_ACAO = ?, DS_DESCRICAO = ?, NR_PONTOS_ECOA = ?, FL_ATIVO = ? where ID_ACAO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, acaoSustentavel.getCodigoCategoria());
            ps.setString(2, acaoSustentavel.getNome());
            ps.setString(3, acaoSustentavel.getDescricao());
            ps.setInt(4, acaoSustentavel.getPontosEcoa());
            ps.setString(5, acaoSustentavel.getAtivo() ? "S" : "N");
            ps.setLong(6, acaoSustentavel.getCodigo());
            if (ps.executeUpdate() > 0) {
                return acaoSustentavel;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_ACAO_SUSTENTAVEL where ID_ACAO = ?";
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

    private AcaoSustentavelTO montar(ResultSet rs) throws SQLException {
        AcaoSustentavelTO acaoSustentavel = new AcaoSustentavelTO();
        acaoSustentavel.setCodigo(rs.getLong("id_acao"));
        acaoSustentavel.setCodigoCategoria(rs.getLong("id_categoria"));
        acaoSustentavel.setNome(rs.getString("nm_acao"));
        acaoSustentavel.setDescricao(rs.getString("ds_descricao"));
        acaoSustentavel.setPontosEcoa(rs.getInt("nr_pontos_ecoa"));
        acaoSustentavel.setAtivo("S".equals(rs.getString("fl_ativo")));
        return acaoSustentavel;
    }
}
