package br.edu.ifpe.tads.pdm.urbano.entidades;

public class Comentario {


private String comentario;
private Usuario usuario;


    public Comentario(){}

    public Comentario(String comentario){
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
