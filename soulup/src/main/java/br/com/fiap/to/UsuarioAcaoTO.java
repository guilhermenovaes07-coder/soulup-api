package br.com.fiap.to;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioAcaoTO {
    private Long codigo;
    @NotNull
    private Long codigoUsuario;
    @NotNull
    private Long codigoAcao;
    private LocalDateTime dataRealizacao;
    private Integer pontosGanhos;
    @Size(max = 500)
    private String evidenciaUrl;
    private String status;

    public UsuarioAcaoTO() {
    }

    public UsuarioAcaoTO(Long codigo, Long codigoUsuario, Long codigoAcao, LocalDateTime dataRealizacao, Integer pontosGanhos, String evidenciaUrl, String status) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoAcao = codigoAcao;
        this.dataRealizacao = dataRealizacao;
        this.pontosGanhos = pontosGanhos;
        this.evidenciaUrl = evidenciaUrl;
        this.status = status;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Long getCodigoAcao() {
        return codigoAcao;
    }

    public void setCodigoAcao(Long codigoAcao) {
        this.codigoAcao = codigoAcao;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public Integer getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(Integer pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public String getEvidenciaUrl() {
        return evidenciaUrl;
    }

    public void setEvidenciaUrl(String evidenciaUrl) {
        this.evidenciaUrl = evidenciaUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
