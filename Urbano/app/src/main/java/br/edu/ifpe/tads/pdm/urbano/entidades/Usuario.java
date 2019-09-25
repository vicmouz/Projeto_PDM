package br.edu.ifpe.tads.pdm.urbano.entidades;

import java.util.Date;

public class Usuario {

        private String nome;
        private String cpf;
        private Date data_nascimento;
        private Byte[] foto;

        public Usuario(){

        }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }
}
