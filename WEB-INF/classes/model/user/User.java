package model.user;

public class User {
    private long id;
    private String nom;
    private String email;
    private String password;

    // Getters/Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
     public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}