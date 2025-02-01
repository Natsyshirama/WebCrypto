package model.fond;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DepotFond{
    private int idDepot;
    private BigDecimal solde;
    private Timestamp daty;
    private int etat;
    private long idUtilisateur;


public int getIdDepot(){
    return idDepot;
}
public void setIdDepot(int idDepot){this.idDepot = idDepot;}
     public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public Timestamp getDaty() { return daty; }
    public void setDaty(Timestamp daty) { this.daty = daty; }

    public int getEtat() { return etat; }
    public void setEtat(int etat) { this.etat = etat; }

    public long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(long idUtilisateur) { this.idUtilisateur = idUtilisateur; }
}