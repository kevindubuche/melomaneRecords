/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basepackage;

/**
 *
 * @author Administrateur
 */
public class Artist {
    
    public Artist (String NIF, String nom, String prenom,  String adresse, String pnone)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.NIF=NIF;
        this.adresse=adresse;
    }
    public void setNom(String nom)
    {
       this.nom=nom; 
    }
     public void setPreNom(String prenom)
    {
       this.prenom=prenom; 
    }
      public void setAdresse(String adresse)
    {
       this.adresse=adresse; 
    }
        public void setNIF(String NIF)
    {
       this.NIF=NIF; 
    }
           public void setPhone(String phone)
    {
       this.phone=phone; 
    }
    public String getNom()
    {
       return nom; 
    }
    public String getPreNom()
    {
       return prenom; 
    }
    public String getAdresse()
    {
       return adresse; 
    }
    public String getNIF()
    {
       return NIF; 
    }
    public String getPhone()
    {
       return phone; 
    }
    
    
    
            
    private String nom, prenom, NIF, adresse, phone;
    
}
