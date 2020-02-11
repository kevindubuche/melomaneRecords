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
public class Album {
    
    public Album (String id_alb, String titre_alb,String format_alb, String date_lanc_alb, String NIF_musicien_alb)
    {
        
        this.titre_alb=titre_alb;
        this.NIF_musicien_alb=NIF_musicien_alb;
        this.date_lanc_alb=date_lanc_alb;
        this.format_alb=format_alb;
        this.id_alb=id_alb;
    }
    public void setID_alb(String id_alb)
    {
       this.id_alb=id_alb; 
    }
    public void setTitre_alb(String titre_alb)
    {
       this.titre_alb=titre_alb; 
    }
     public void setMusicien_alb(String NIF_musicien_alb)
    {
       this.NIF_musicien_alb=NIF_musicien_alb; 
    }
      public void setFormat_alb(String format_alb)
    {
       this.format_alb=format_alb; 
    }
      public void setDate_Lanc_alb(String date_lanc_alb)
    {
       this.date_lanc_alb=date_lanc_alb; 
    }
     
    public String getID_Album()
    {
       return id_alb; 
    }
    public String getTitre_alb()
    {
       return titre_alb; 
    }
      public String getFormat_alb()
    {
       return format_alb; 
    }
    public String getNIF_musicien_alb()
    {
       return NIF_musicien_alb; 
    }
    public String getDatee_lanc_alb()
    {
       return date_lanc_alb; 
    }
          
    private String id_alb, titre_alb, format_alb, date_lanc_alb,NIF_musicien_alb;
}
