package model.fond;
import java.math.BigDecimal;
import java.sql.Timestamp;


public class RetraitFond {
    private int idRetrait;
    private BigDecimal solde;
    private Timestamp daty;
    private int etat;
    private long idUtilisateur;

    // Getters et Setters
    public int getIdRetrait() { return idRetrait; }
    public void setIdRetrait(int idRetrait) { this.idRetrait = idRetrait; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public Timestamp getDaty() { return daty; }
    public void setDaty(Timestamp daty) { this.daty = daty; }

    public int getEtat() { return etat; }
    public void setEtat(int etat) { this.etat = etat; }

    public long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
}