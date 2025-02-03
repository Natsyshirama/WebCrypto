package model.crypto;

import java.math.BigDecimal;

public class Crypto {
    private int id;
    private String nom;
    private BigDecimal cours;
    private BigDecimal nbCrypto;   // anciennement nb_crypto
    private BigDecimal totalCrypto; // nouvel attribut

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getCours() {
        return cours;
    }

    public void setCours(BigDecimal cours) {
        this.cours = cours;
    }

    public BigDecimal getNbCrypto() {
        return nbCrypto;
    }

    public void setNbCrypto(BigDecimal nbCrypto) {
        this.nbCrypto = nbCrypto;
    }

    public BigDecimal getTotalCrypto() {
        return totalCrypto;
    }

    public void setTotalCrypto(BigDecimal totalCrypto) {
        this.totalCrypto = totalCrypto;
    }
}
