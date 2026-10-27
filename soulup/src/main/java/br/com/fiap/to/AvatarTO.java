package br.com.fiap.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AvatarTO {
    private Long codigo;
    @NotNull
    private Long codigoUsuario;
    @NotBlank
    @Size(max = 60)
    private String nome;
    @Min(1)
    private Integer nivel;
    @PositiveOrZero
    private Integer xp;
    @Size(max = 2000)
    private String aparenciaJson;

    public AvatarTO() {
    }

    public AvatarTO(Long codigo, Long codigoUsuario, String nome, Integer nivel, Integer xp, String aparenciaJson) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.nome = nome;
        this.nivel = nivel;
        this.xp = xp;
        this.aparenciaJson = aparenciaJson;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public String getAparenciaJson() {
        return aparenciaJson;
    }

    public void setAparenciaJson(String aparenciaJson) {
        this.aparenciaJson = aparenciaJson;
    }
}
