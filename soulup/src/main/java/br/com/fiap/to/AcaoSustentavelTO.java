package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AcaoSustentavelTO {
    private Long codigo;
    @NotNull
    private Long codigoCategoria;
    @NotBlank
    @Size(max = 120)
    private String nome;
    @Size(max = 500)
    private String descricao;
    @NotNull
    @Positive
    private Integer pontosEcoa;
    private Boolean ativo;

    public AcaoSustentavelTO() {
    }

    public AcaoSustentavelTO(Long codigo, Long codigoCategoria, String nome, String descricao, Integer pontosEcoa, Boolean ativo) {
        this.codigo = codigo;
        this.codigoCategoria = codigoCategoria;
        this.nome = nome;
        this.descricao = descricao;
        this.pontosEcoa = pontosEcoa;
        this.ativo = ativo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Long codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontosEcoa() {
        return pontosEcoa;
    }

    public void setPontosEcoa(Integer pontosEcoa) {
        this.pontosEcoa = pontosEcoa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
