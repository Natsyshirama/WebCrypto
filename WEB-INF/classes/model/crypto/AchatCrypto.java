package model.crypto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AchatCrypto {
    private int idAchat;
    private BigDecimal nbCrypto;
    private BigDecimal cours;
    private Timestamp daty;
    private int idCryptomonnaie;
    private long idUtilisateur;

    // Getters et Setters

    public int getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(int idAchat) {
        this.idAchat = idAchat;
    }

    public BigDecimal getNbCrypto() {
        return nbCrypto;
    }

    public void setNbCrypto(BigDecimal nbCrypto) {
        this.nbCrypto = nbCrypto;
    }

    public BigDecimal getCours() {
        return cours;
    }

    public void setCours(BigDecimal cours) {
        this.cours = cours;
    }

    public Timestamp getDaty() {
        return daty;
    }

    public void setDaty(Timestamp daty) {
        this.daty = daty;
    }

    public int getIdCryptomonnaie() {
        return idCryptomonnaie;
    }

    public void setIdCryptomonnaie(int idCryptomonnaie) {
        this.idCryptomonnaie = idCryptomonnaie;
    }

    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
