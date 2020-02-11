/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrateur
 */
public class Connecter {
    
    Connection con;
    public Connecter(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        try{
        con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
        }catch(SQLException e){System.err.println(e);}
        }
    public Connection obtenirconnection() {return con ;}  
}
