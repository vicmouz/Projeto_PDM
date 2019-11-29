package br.edu.ifpe.tads.pdm.urbano.entidades;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Denuncia {

    private String titulo;
    private String descricao;
    private Usuario criado_por;
    private Date criado_em;
    private Double latitude;
    private Double longitude;
    private int curtidas;
    private HashMap<String, Comentario> comentarios;
    private String status;
    private String foto;
    private String nome;



    public Denuncia (String titulo, String descricao, Double latitude , Double longitude, String foto,Usuario user){
        this.titulo = titulo;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.foto = foto;
        this.criado_por = user;
    }

    public Denuncia(){}


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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(Usuario criado_por) {
        this.criado_por = criado_por;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public HashMap<String, Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(HashMap<String, Comentario> comentarios) {
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

    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}
