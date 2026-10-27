package br.com.fiap.to;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreditoCarbonoTO {
    private Long codigo;
    @NotNull
    private Long codigoUsuario;
    @NotNull
    @Positive
    private BigDecimal quantidadeKg;
    @NotBlank
    @Size(max = 120)
    private String origem;
    private LocalDateTime dataEmissao;
    @Pattern(regexp = "ATIVO|UTILIZADO|EXPIRADO")
    private String status;

    public CreditoCarbonoTO() {
    }

    public CreditoCarbonoTO(Long codigo, Long codigoUsuario, BigDecimal quantidadeKg, String origem, LocalDateTime dataEmissao, String status) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.quantidadeKg = quantidadeKg;
        this.origem = origem;
        this.dataEmissao = dataEmissao;
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

    public BigDecimal getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(BigDecimal quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
