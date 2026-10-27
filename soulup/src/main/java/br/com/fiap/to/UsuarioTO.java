package br.com.fiap.to;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class UsuarioTO {
    private Long codigo;
    @NotBlank
    @Size(max = 120)
    private String nome;
    @NotBlank
    @Email
    @Size(max = 150)
    private String email;
    @NotBlank
    @Size(min = 6, max = 72)
    private String senha;
    @Past
    private LocalDate dataNascimento;
    private LocalDateTime dataCadastro;
    private Integer saldoEcoaPoints;

    public UsuarioTO() {
    }

    public UsuarioTO(Long codigo, String nome, String email, String senha, LocalDate dataNascimento, LocalDateTime dataCadastro, Integer saldoEcoaPoints) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
        this.saldoEcoaPoints = saldoEcoaPoints;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getSaldoEcoaPoints() {
        return saldoEcoaPoints;
    }

    public void setSaldoEcoaPoints(Integer saldoEcoaPoints) {
        this.saldoEcoaPoints = saldoEcoaPoints;
    }
}
