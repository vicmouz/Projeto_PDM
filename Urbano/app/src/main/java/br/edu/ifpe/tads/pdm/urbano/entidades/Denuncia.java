package br.edu.ifpe.tads.pdm.urbano.entidades;

import java.util.Date;

public class Denuncia {

    private int id;
    private String titulo;
    private String descricao;
    //private Usuario criado_por;
    private Date criado_em;
    private Long latitude;
    private Long longitude;
    private int curtidas;
    private Comentario[] comentarios;
    private String status;
    private Byte[] foto;
    private String nome;


    /*public Denuncia (String titulo, String descricao, Comentario comentario){
        this.titulo = titulo;
        this.descricao = descricao;
        this.criado_em = new Date();
        this.comentarios[0] = comentario;
    }*/

    public Denuncia (String titulo, String descricao){
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Denuncia(){}

    /*public Denuncia(int curtidas, String titulo, String descricao, String nome, String status){
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = "Aguardando Análise";
        this.nome = nome;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(Date criado_em) {
        this.criado_em = criado_em;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    /*public Usuario getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(Usuario criado_por) {
        this.criado_por = criado_por;
    }*/

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public Comentario[] getComentarios() {
        return comentarios;
    }

    public void setComentarios(Comentario[] comentarios) {
        this.comentarios = comentarios;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
