package model.crypto;
import java.math.BigDecimal;

public class UserAnalise {
    private long userId;
    private String nom;
    private String email;
    private BigDecimal totalAchat;
    private BigDecimal totalVente;
    private BigDecimal solde;

    public UserAnalise(long userId, String nom, String email, BigDecimal totalAchat, BigDecimal totalVente, BigDecimal solde) {
        this.userId = userId;
        this.nom = nom;
        this.email = email;
        this.totalAchat = totalAchat;
        this.totalVente = totalVente;
        this.solde = solde;
    }

    public long getUserId() { return userId; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public BigDecimal getTotalAchat() { return totalAchat; }
    public BigDecimal getTotalVente() { return totalVente; }
    public BigDecimal getSolde() { return solde; }
}
