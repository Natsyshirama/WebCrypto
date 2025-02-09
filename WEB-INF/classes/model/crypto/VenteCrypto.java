package model.crypto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VenteCrypto {
    private int idVente;
    private BigDecimal nbCrypto;
    private BigDecimal cours;
    private Timestamp daty;
    private int idCryptomonnaie;
    private long idUtilisateur;


    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
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
