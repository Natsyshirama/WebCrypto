package model.crypto;
import java.math.BigDecimal;

public class Cryptoko {
    private int idCrypto;
    private String nom;
    private BigDecimal quantite;
    private BigDecimal coursActuel;

    // Constructeurs
    public Cryptoko() {}

    public Cryptoko(int idCrypto, String nom, BigDecimal quantite, BigDecimal coursActuel) {
        this.idCrypto = idCrypto;
        this.nom = nom;
        this.quantite = quantite;
        this.coursActuel = coursActuel;
    }

    // Getters
    public int getIdCrypto() {
        return idCrypto;
    }

    public String getNom() {
        return nom;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public BigDecimal getCoursActuel() {
        return coursActuel;
    }

    // Setters
    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public void setCoursActuel(BigDecimal coursActuel) {
        this.coursActuel = coursActuel;
    }

}