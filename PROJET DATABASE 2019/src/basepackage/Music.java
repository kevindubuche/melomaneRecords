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
public class Music {
      
    public Music (String titre_chan, String auteur_chan, String duree_chan)
    {
        
        this.titre_chan=titre_chan;
        this.auteur_chan=auteur_chan;
        this.duree_chan=duree_chan;
    }
    public void setID_chan(String id_chan)
    {
       this.id_chan=id_chan; 
    }
    public void setTitre_chan(String titre_chan)
    {
       this.titre_chan=titre_chan; 
    }
     public void setAuteur_chan(String auteur_chan)
    {
       this.auteur_chan=auteur_chan; 
    }
      public void setDuree_chan(String duree_chan)
    {
       this.duree_chan=duree_chan; 
    }
       public void setId_alb(String id_alb)
    {
       this.id_alb=id_alb; 
    }
         public String getId_alb()
    {
      return id_alb; 
    }
    public String getId_chan()
    {
       return id_chan; 
    }
    public String getTitre_chan()
    {
       return titre_chan; 
    }
    public String getAuteur_chan()
    {
       return auteur_chan; 
    }
    public String getDuree_chan()
    {
       return duree_chan; 
    }
          
    private String id_chan, titre_chan, auteur_chan, duree_chan,id_alb;
}
