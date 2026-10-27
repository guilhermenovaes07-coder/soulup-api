package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SeloVerdeTO {
    private Long codigo;
    @NotBlank
    @Size(max = 80)
    private String nome;
    @Size(max = 500)
    private String descricao;
    @Size(max = 500)
    private String criterio;
    @Size(max = 500)
    private String iconeUrl;

    public SeloVerdeTO() {
    }

    public SeloVerdeTO(Long codigo, String nome, String descricao, String criterio, String iconeUrl) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.criterio = criterio;
        this.iconeUrl = iconeUrl;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getIconeUrl() {
        return iconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }
}
