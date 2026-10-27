package br.com.fiap.dao;

import br.com.fiap.to.CreditoCarbonoTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditoCarbonoDAO {

    public ArrayList<CreditoCarbonoTO> findAll() {
        ArrayList<CreditoCarbonoTO> lista = new ArrayList<CreditoCarbonoTO>();
        String sql = "select * from TB_CREDITO_CARBONO order by ID_CREDITO";
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

    public CreditoCarbonoTO findByCodigo(Long codigo) {
        String sql = "select * from TB_CREDITO_CARBONO where ID_CREDITO = ?";
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

    public ArrayList<CreditoCarbonoTO> findByUsuario(Long codigoUsuario) {
        ArrayList<CreditoCarbonoTO> lista = new ArrayList<CreditoCarbonoTO>();
        String sql = "select * from TB_CREDITO_CARBONO where ID_USUARIO = ? order by ID_CREDITO";
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

    public CreditoCarbonoTO save(CreditoCarbonoTO creditoCarbono) {
        String sql = "insert into TB_CREDITO_CARBONO (ID_CREDITO, ID_USUARIO, NR_QUANTIDADE_KG, DS_ORIGEM, DS_STATUS) values (SEQ_CREDITO_CARBONO.nextval, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql, new String[] {"ID_CREDITO"})) {
            ps.setLong(1, creditoCarbono.getCodigoUsuario());
            ps.setBigDecimal(2, creditoCarbono.getQuantidadeKg());
            ps.setString(3, creditoCarbono.getOrigem());
            ps.setString(4, creditoCarbono.getStatus());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    creditoCarbono.setCodigo(rs.getLong(1));
                }
                return creditoCarbono;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public CreditoCarbonoTO update(CreditoCarbonoTO creditoCarbono) {
        String sql = "update TB_CREDITO_CARBONO set NR_QUANTIDADE_KG = ?, DS_ORIGEM = ?, DS_STATUS = ? where ID_CREDITO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setBigDecimal(1, creditoCarbono.getQuantidadeKg());
            ps.setString(2, creditoCarbono.getOrigem());
            ps.setString(3, creditoCarbono.getStatus());
            ps.setLong(4, creditoCarbono.getCodigo());
            if (ps.executeUpdate() > 0) {
                return creditoCarbono;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from TB_CREDITO_CARBONO where ID_CREDITO = ?";
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

    private CreditoCarbonoTO montar(ResultSet rs) throws SQLException {
        CreditoCarbonoTO creditoCarbono = new CreditoCarbonoTO();
        creditoCarbono.setCodigo(rs.getLong("id_credito"));
        creditoCarbono.setCodigoUsuario(rs.getLong("id_usuario"));
        creditoCarbono.setQuantidadeKg(rs.getBigDecimal("nr_quantidade_kg"));
        creditoCarbono.setOrigem(rs.getString("ds_origem"));
        if (rs.getTimestamp("dt_emissao") != null) {
            creditoCarbono.setDataEmissao(rs.getTimestamp("dt_emissao").toLocalDateTime());
        }
        creditoCarbono.setStatus(rs.getString("ds_status"));
        return creditoCarbono;
    }
}
