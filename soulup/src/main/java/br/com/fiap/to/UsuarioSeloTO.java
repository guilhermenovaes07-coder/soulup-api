package br.com.fiap.to;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class UsuarioSeloTO {
    private Long codigo;
    @NotNull
    private Long codigoUsuario;
    @NotNull
    private Long codigoSelo;
    private LocalDateTime dataConquista;

    public UsuarioSeloTO() {
    }

    public UsuarioSeloTO(Long codigo, Long codigoUsuario, Long codigoSelo, LocalDateTime dataConquista) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoSelo = codigoSelo;
        this.dataConquista = dataConquista;
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

    public Long getCodigoSelo() {
        return codigoSelo;
    }

    public void setCodigoSelo(Long codigoSelo) {
        this.codigoSelo = codigoSelo;
    }

    public LocalDateTime getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDateTime dataConquista) {
        this.dataConquista = dataConquista;
    }
}
