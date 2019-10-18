package br.edu.ifpe.tads.pdm.urbano.entidades;

import java.util.Date;

public class Usuario {

        private String nome;
        private String cpf;
      //  private Date data_nascimento;
       // private Byte[] foto;
        private String email;

        public Usuario(String nome, String email, String cpf ){
            this.nome = nome;
            this.email = email;
            this.cpf = cpf;
        }

    public String getNome() {
        return nome;
    }

    /*public void setNome(String nome) {
        this.nome = nome;
    }*/

    public String getCpf() {
        return cpf;
    }

    /*public void setCpf(String cpf) {
        this.cpf = cpf;
    }*/

   /* public Date getData_nascimento() {
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
    }*/

    public String getEmail() {
        return email;
    }

   /* public void setEmail(String email) {
        this.email = email;
    }*/
}
