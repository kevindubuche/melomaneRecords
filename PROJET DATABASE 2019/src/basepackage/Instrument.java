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
public class Instrument {
         
    public Instrument (String id_inst , String nom_inst , String date_acqui )
    {
        
        this.id_inst=id_inst;
        this.nom_inst=nom_inst;
        this.date_acqui=date_acqui;
    }
    public void setId_inst(String id_inst)
    {
       this.id_inst=id_inst; 
    }
 
     public void setNom_inst(String nom_inst)
    {
       this.nom_inst=nom_inst; 
    }
      public void setDate_acqui(String date_acqui)
    {
       this.date_acqui=date_acqui; 
    }
    
  
    public String getId_inst()
    {
       return id_inst; 
    }
    public String getNom_inst()
    {
       return nom_inst; 
    }
    public String getDate_acqui()
    {
       return date_acqui; 
    }
    
    private String id_inst ,nom_inst ,date_acqui;
}
