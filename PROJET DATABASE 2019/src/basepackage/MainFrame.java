/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basepackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrateur
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    Music chansonGlob=new Music("","","");
    Artist artistGlob=new Artist("","","","","");
    Album albumGlob=new Album("","","","","");
    public MainFrame() 
    {
        initComponents();        
        this.setLocationRelativeTo(null);//pou centrer fenetre la
    }
        Connecter conn = new Connecter();
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model=new DefaultTableModel();
       public void DisplayListMusic () 
       {     
           //NAP RETIRE TEXTE KI KOT IMAJ LA
           lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
           afftitle.setText("");
           affartist.setText("");
           affduration.setText("");
                   
           DefaultTableModel model=new DefaultTableModel();//nou reinicialiser tout kolon yo
           model.addColumn("ID");
           model.addColumn("TITLE");
           model.addColumn("AUTHOR");
           model.addColumn("DURATION");
           model.addColumn("SAVED DATE");
            try
            { 
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from chanson");
           while(Rs.next())
           {
               model.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan"),Rs.getString("duree_chan"),Rs.getString("date_enr_chan")}); 
           }
           Rs.close();
            }
            catch (Exception e){System.err.println(e);} 
            tble.setModel(model);
            
            int c=0;// nap afficher total mmusic ki genyen
        try{
              stm=conn.obtenirconnection().createStatement();
                 Rs=stm.executeQuery("Select count(*) as total from chanson ");
                   if(Rs.next()){
                        c=Rs.getInt("total");
                        Rs.close(); 
        }
               }catch (Exception e){System.err.println(e);}
        jLabel12.setText("Total("+c+")");
        
        RepaintPanel(listMusic);
          
        }
       public void DisplayListArtist(){
      
           lb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
           affNomComplet.setText("");
         affid.setText("");
         affaddress.setText("");
    DefaultTableModel modelA=new DefaultTableModel();        
            int d=0;//pou kontrole nombre de musiciens
    try{
               stm=conn.obtenirconnection().createStatement();
                Rs=stm.executeQuery("Select count(*) as total from musicien ");
               if(Rs.next()){
     d=Rs.getInt("total");
    Rs.close(); 
    }
           }catch (Exception e){System.err.println(e);}
    jLabel35.setText("Total("+d+")");
 
       modelA.addColumn("NIF");
       modelA.addColumn("LAST NAME");
       modelA.addColumn("FIRST NAME");
       modelA.addColumn("ADDRESS");
       modelA.addColumn("PHONE");
        modelA.addColumn("SAVED DATE");
       
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus,adresse_mus, phone_mus,date_enr_mus  from musicien");
           while(Rs.next()){
               modelA.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus"),Rs.getString("adresse_mus"),Rs.getString("phone_mus"),Rs.getString("date_enr_mus")});
           }Rs.close(); 
       }catch (Exception e){System.err.println(e);}
 
       tble1.setModel(modelA);
       RepaintPanel(listArtist);   
}
       public void DisplayListInstrum(){
        
           lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
           affNomComplet1.setText("");
         affid1.setText("");
         affaddress1.setText("");
    DefaultTableModel modelA=new DefaultTableModel();        
            int d=0;//pou kontrole nombre de musiciens
    try{
               stm=conn.obtenirconnection().createStatement();
                Rs=stm.executeQuery("Select count(*) as total from instrument ");
               if(Rs.next()){
     d=Rs.getInt("total");
    Rs.close(); 
    }
           }catch (Exception e){System.err.println(e);}
    jLabel125.setText("Total("+d+")");
 
       modelA.addColumn("ID");
       modelA.addColumn("NAME");
       modelA.addColumn("PROCURATION DATE");
        modelA.addColumn("SAVED DATE");
       
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select * from instrument");
           while(Rs.next()){
               modelA.addRow(new Object[]{Rs.getString("id_inst"),Rs.getString("nom_inst"),
               Rs.getString("date_acqui"),Rs.getString("date_enr_inst")});
           }Rs.close(); 
       }catch (Exception e){System.err.println(e);}
 
       tbleInst.setModel(modelA);
       RepaintPanel(listInstrum);   
}
         public void DisplaySettings(){
        
       RepaintPanel(setting);   
}
          

    //FONCYION KI PEMET OU REFRESH BOARD LA  
    public void RepaintPanel(javax.swing.JPanel ThePanel){
        boardPanel.removeAll();
        boardPanel.repaint();
        boardPanel.revalidate();
        boardPanel.add(ThePanel);
        boardPanel.repaint();
        boardPanel.revalidate();
    }
        //FONCTION POU LEU MOUN KLIKE SOU TABLE LIGNE NAN TOU SELEKSYONE
    public class TableMouseListener extends MouseAdapter{
        private JTable  table;
        public TableMouseListener(JTable table){
            this.table=table;
            
        }public void mousePressed(MouseEvent event){
            Point point=event.getPoint();
            int currentRow = table.rowAtPoint(point);
            table.setRowSelectionInterval(currentRow,currentRow);
            System.out.println("nou selectionner yon ligne");      
        }     
    }
    
     public void DisplayListAlbum() {
  
         //NAP RETIRE TEXTE KI KOT IMAJ LA
           lb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
           afftitle1.setText("");
         affartist1.setText("");
          DefaultTableModel model=new DefaultTableModel();//nou reinicialiser tout kolon yo
            model.addColumn("ID");
            model.addColumn("TITLE");
            model.addColumn("FORMAT");
            model.addColumn("ENTRY DATE");
             model.addColumn("AUTHOR");
             model.addColumn("SAVED DATE");
            try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, id_alb, titre_alb,format_alb, date_lanc, date_enr_alb from album");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_alb"),Rs.getString("titre_alb"),Rs.getString("format_alb"),
               Rs.getString("date_lanc"),Rs.getString("NIF_mus"),Rs.getString("date_enr_alb")});
           }Rs.close();
            }catch (Exception e){System.err.println(e);} 
            tble2.setModel(model); 
            
            int ce=0;// nap afficher total mmusic ki genyen
        try{
              stm=conn.obtenirconnection().createStatement();
                 Rs=stm.executeQuery("Select count(*) as total from album");
                   if(Rs.next()){
                        ce=Rs.getInt("total");
                        Rs.close(); 
        }
               }catch (Exception e){System.err.println(e);}
        jLabel76.setText("Total("+ce+")");
        
        RepaintPanel(listAlbum);
          
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPopupMusic = new javax.swing.JPopupMenu();
        Edit = new javax.swing.JMenuItem();
        Delete = new javax.swing.JMenuItem();
        infomusic = new javax.swing.JMenuItem();
        jPopupArtist = new javax.swing.JPopupMenu();
        editArtist = new javax.swing.JMenuItem();
        deleteartist = new javax.swing.JMenuItem();
        infoartist = new javax.swing.JMenuItem();
        jPopupFtAritist = new javax.swing.JPopupMenu();
        Remove = new javax.swing.JMenuItem();
        jPopupAlbum = new javax.swing.JPopupMenu();
        editAlbum = new javax.swing.JMenuItem();
        deleteAlbum = new javax.swing.JMenuItem();
        infoAlb = new javax.swing.JMenuItem();
        jPopupRemALB = new javax.swing.JPopupMenu();
        RemALb = new javax.swing.JMenuItem();
        jPopupInstrum = new javax.swing.JPopupMenu();
        editinst = new javax.swing.JMenuItem();
        deleteInst = new javax.swing.JMenuItem();
        infoInst = new javax.swing.JMenuItem();
        jPopupRemARTinInst = new javax.swing.JPopupMenu();
        remArt = new javax.swing.JMenuItem();
        BasePanel = new javax.swing.JPanel();
        MenuPanel = new javax.swing.JPanel()

        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(MenuPanel.getX(), MenuPanel.getY(), new Color(15,15,15,200),
                    getWidth(), getHeight(), new Color(0,0,0,200), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(MenuPanel.getX(),MenuPanel.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        boardPanel = new javax.swing.JPanel();
        welcom = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(welcom.getX(), welcom.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(welcom.getX(),welcom.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel10 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel230 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        listMusic = new javax.swing.JPanel()

        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(listMusic.getX(), listMusic.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(listMusic.getX(),listMusic.getY(),getWidth(),getHeight());
            }
        }

        ;
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tble = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;}

        };
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        afftitle = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        affartist = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        affduration = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        EditMusic = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        titreField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        dureeField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        lbl_image = new javax.swing.JLabel();
        AddMusic = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        titreField1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        dureeField1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        listArtist = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(listArtist.getX(), listArtist.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(listArtist.getX(),listArtist.getY(),getWidth(),getHeight());
            }
        }

        ;
        jScrollPane3 = new javax.swing.JScrollPane();
        tble1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;}
        };
        jLabel35 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        lb2 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        affNomComplet = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        affid = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        affaddress = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabell155 = new javax.swing.JLabel();
        AddArtist = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_nom_art = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txt_prenom_art = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        nif1 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txt_address_art = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_phone_art = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        nif2 = new javax.swing.JTextField();
        nif3 = new javax.swing.JTextField();
        nif4 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        EditArtist = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txt_nom_art1 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        txt_prenom_art12 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txt_NIF_1p1 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txt_address_art1 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txt_phone_art1 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txt_NIF_2p1 = new javax.swing.JTextField();
        txt_NIF_3p1 = new javax.swing.JTextField();
        txt_NIF_4p1 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        lbl_image2 = new javax.swing.JLabel();
        path2 = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        AddAlbum = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        titreField2 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        newcomb = new javax.swing.JComboBox();
        listAlbum = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(listAlbum.getX(), listAlbum.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(listAlbum.getX(),listAlbum.getY(),getWidth(),getHeight());
            }
        }
        ;
        jScrollPane4 = new javax.swing.JScrollPane();
        tble2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;}
        };
        jLabel76 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        lb1 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        afftitle1 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        affartist1 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        EditAlbum = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        titreField5 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jLabel99 = new javax.swing.JLabel();
        lbl_image1 = new javax.swing.JLabel();
        path1 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        combobo = new javax.swing.JComboBox();
        listInstrum = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(listArtist.getX(), listArtist.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(listArtist.getX(),listArtist.getY(),getWidth(),getHeight());
            }
        }

        ;
        jScrollPane6 = new javax.swing.JScrollPane();
        tbleInst = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;}
        };
        jLabel125 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        lb3 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        affNomComplet1 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        affid1 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        affaddress1 = new javax.swing.JLabel();
        AddInstrum = new javax.swing.JPanel();
        jLabel135 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        newcomb1 = new javax.swing.JComboBox();
        EditInstrum = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        newcomb2 = new javax.swing.JComboBox();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        dateChooserCombo5 = new datechooser.beans.DateChooserCombo();
        jLabel160 = new javax.swing.JLabel();
        setting = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        infoMusicpanel = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(infoMusicpanel.getX(), infoMusicpanel.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(infoMusicpanel.getX(),infoMusicpanel.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel155 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        infoArtist = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(infoMusicpanel.getX(), infoMusicpanel.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(infoMusicpanel.getX(),infoMusicpanel.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel156 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel191 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        infoInstrum = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(infoMusicpanel.getX(), infoMusicpanel.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(infoMusicpanel.getX(),infoMusicpanel.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel161 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        infoAlbum = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Paint p= new GradientPaint(infoMusicpanel.getX(), infoMusicpanel.getY(), new Color(5,5,5,180),
                    getWidth(), getHeight(), new Color(0,0,0), true);
                Graphics2D g2d=(Graphics2D)g;
                g2d.setPaint(p);
                g2d.fillRect(infoMusicpanel.getX(),infoMusicpanel.getY(),getWidth(),getHeight());
            }
        }
        ;
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel215 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();

        jPopupMusic.setBackground(new java.awt.Color(51, 51, 51));
        jPopupMusic.setForeground(new java.awt.Color(255, 255, 255));

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        jPopupMusic.add(Edit);

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });
        jPopupMusic.add(Delete);

        infomusic.setText("Info");
        infomusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infomusicActionPerformed(evt);
            }
        });
        jPopupMusic.add(infomusic);

        editArtist.setText("Edit");
        editArtist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editArtistActionPerformed(evt);
            }
        });
        jPopupArtist.add(editArtist);

        deleteartist.setText("Delete");
        deleteartist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteartistActionPerformed(evt);
            }
        });
        jPopupArtist.add(deleteartist);

        infoartist.setText("Info");
        infoartist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoartistActionPerformed(evt);
            }
        });
        jPopupArtist.add(infoartist);

        Remove.setText("Remove from list");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        jPopupFtAritist.add(Remove);

        editAlbum.setText("Edit");
        editAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAlbumActionPerformed(evt);
            }
        });
        jPopupAlbum.add(editAlbum);

        deleteAlbum.setText("Delete");
        deleteAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAlbumActionPerformed(evt);
            }
        });
        jPopupAlbum.add(deleteAlbum);

        infoAlb.setText("Info");
        infoAlb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoAlbActionPerformed(evt);
            }
        });
        jPopupAlbum.add(infoAlb);

        RemALb.setText("Remove from album");
        RemALb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemALbActionPerformed(evt);
            }
        });
        jPopupRemALB.add(RemALb);

        editinst.setText("Edit");
        editinst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editinstActionPerformed(evt);
            }
        });
        jPopupInstrum.add(editinst);

        deleteInst.setText("Delete");
        deleteInst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteInstActionPerformed(evt);
            }
        });
        jPopupInstrum.add(deleteInst);

        infoInst.setText("Info");
        infoInst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoInstActionPerformed(evt);
            }
        });
        jPopupInstrum.add(infoInst);

        remArt.setText("Remove from list");
        remArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remArtActionPerformed(evt);
            }
        });
        jPopupRemARTinInst.add(remArt);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Melomane Records");

        BasePanel.setBackground(new java.awt.Color(255, 255, 255));

        MenuPanel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/music.jpg"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/album.jpg"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jLabel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel4MouseMoved(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });
        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel5MouseMoved(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/artist.jpg"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });
        jLabel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel6MouseMoved(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/instru.jpg"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel7MouseMoved(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });
        jLabel8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel8MouseMoved(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/settings.jpg"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        jLabel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel9MouseMoved(evt);
            }
        });

        jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/dis.jpg"))); // NOI18N
        jLabel217.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel217MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel217MouseExited(evt);
            }
        });
        jLabel217.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel217MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, MenuPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 242, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap())
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel217)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel217)
                .addGap(63, 63, 63)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        boardPanel.setBackground(new java.awt.Color(255, 255, 255));
        boardPanel.setLayout(new java.awt.CardLayout());

        welcom.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 255, 0));
        jLabel10.setText("WELCOME");

        jLabel149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m1.jpg"))); // NOI18N
        jLabel149.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel149MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel149MouseExited(evt);
            }
        });
        jLabel149.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel149MouseMoved(evt);
            }
        });

        jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m2.jpg"))); // NOI18N
        jLabel150.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel150MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel150MouseExited(evt);
            }
        });
        jLabel150.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel150MouseMoved(evt);
            }
        });

        jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m3.jpg"))); // NOI18N
        jLabel151.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel151MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel151MouseExited(evt);
            }
        });
        jLabel151.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel151MouseMoved(evt);
            }
        });

        jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m4.jpg"))); // NOI18N
        jLabel152.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel152MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel152MouseExited(evt);
            }
        });
        jLabel152.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel152MouseMoved(evt);
            }
        });

        jLabel218.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel218.setForeground(new java.awt.Color(255, 255, 255));
        jLabel218.setText("Contact Us");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel219.setForeground(new java.awt.Color(255, 255, 255));
        jLabel219.setText("FDS, Delmas 33");

        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setText("kevindubuche@gmail.com");

        jLabel221.setForeground(new java.awt.Color(255, 255, 255));
        jLabel221.setText("bgtheodore18@gmail.com");

        jLabel222.setForeground(new java.awt.Color(255, 255, 255));
        jLabel222.setText("yvrans.luc.9@gmail.com");

        jLabel223.setForeground(new java.awt.Color(255, 255, 255));
        jLabel223.setText("+509 3687 0473");

        jLabel225.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel225.setForeground(new java.awt.Color(255, 255, 255));
        jLabel225.setText("About Us");

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel226.setForeground(new java.awt.Color(255, 255, 255));
        jLabel226.setText("Melomane Records is that solution");

        jLabel227.setForeground(new java.awt.Color(255, 255, 255));
        jLabel227.setText("every artist has been looking for");

        jLabel228.setForeground(new java.awt.Color(255, 255, 255));
        jLabel228.setText("so they can be known");

        jLabel229.setForeground(new java.awt.Color(255, 255, 255));
        jLabel229.setText("all over the world");

        jLabel230.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel230.setForeground(new java.awt.Color(255, 255, 255));
        jLabel230.setText("Testimonials");

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel231.setForeground(new java.awt.Color(255, 255, 255));
        jLabel231.setText("\"Good job bro ! You killed it !\"");

        jLabel232.setForeground(new java.awt.Color(51, 153, 255));
        jLabel232.setText("By Saintil Theodule");

        jLabel233.setForeground(new java.awt.Color(255, 255, 255));
        jLabel233.setText("You made a professional work");

        jLabel234.setForeground(new java.awt.Color(51, 153, 255));
        jLabel234.setText("By unknown");

        javax.swing.GroupLayout welcomLayout = new javax.swing.GroupLayout(welcom);
        welcom.setLayout(welcomLayout);
        welcomLayout.setHorizontalGroup(
            welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(welcomLayout.createSequentialGroup()
                        .addGap(0, 141, Short.MAX_VALUE)
                        .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(288, 288, 288))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomLayout.createSequentialGroup()
                                .addComponent(jLabel149)
                                .addGap(112, 112, 112)
                                .addComponent(jLabel150)
                                .addGap(112, 112, 112)
                                .addComponent(jLabel151)
                                .addGap(112, 112, 112)
                                .addComponent(jLabel152)
                                .addGap(108, 108, 108))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomLayout.createSequentialGroup()
                        .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel230)
                            .addComponent(jLabel231)
                            .addComponent(jLabel232)
                            .addComponent(jLabel233)
                            .addComponent(jLabel234))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel225)
                            .addComponent(jLabel226)
                            .addComponent(jLabel227)
                            .addComponent(jLabel228)
                            .addComponent(jLabel229))
                        .addGap(246, 246, 246)
                        .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel218)
                            .addComponent(jLabel219)
                            .addComponent(jLabel220)
                            .addComponent(jLabel221)
                            .addComponent(jLabel222)
                            .addComponent(jLabel223))
                        .addGap(18, 18, 18))))
        );
        welcomLayout.setVerticalGroup(
            welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel10)
                .addGap(92, 92, 92)
                .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel149)
                    .addComponent(jLabel150)
                    .addComponent(jLabel151)
                    .addComponent(jLabel152))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(welcomLayout.createSequentialGroup()
                        .addGroup(welcomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(welcomLayout.createSequentialGroup()
                                .addComponent(jLabel218)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel219)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel220)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel221)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel222))
                            .addGroup(welcomLayout.createSequentialGroup()
                                .addComponent(jLabel225)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel226)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel227)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel228)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel229)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel223))
                    .addGroup(welcomLayout.createSequentialGroup()
                        .addComponent(jLabel230)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel231)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel232)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel233)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel234)
                        .addGap(20, 20, 20)))
                .addContainerGap())
        );

        boardPanel.add(welcom, "card2");

        listMusic.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("M U S I C");

        tble.addMouseListener(new TableMouseListener(tble));
        tble.setBackground(new java.awt.Color(51, 51, 51));
        tble.setForeground(new java.awt.Color(255, 255, 255));
        JTableHeader Theader =tble.getTableHeader();
        tble.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble.setRowHeight(35);
        tble.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tble.setSelectionForeground(new java.awt.Color(51, 255, 51));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupMusic, org.jdesktop.beansbinding.ObjectProperty.create(), tble, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        tble.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tble);

        int c=0;
        try{
            stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select count(*) as total from chanson ");
            if(Rs.next()){
                c=Rs.getInt("total");
                Rs.close();
            }
        }catch (Exception e){System.err.println(e);}
        jLabel12.setForeground(new java.awt.Color(0, 255, 0));
        jLabel12.setText("Total ("+c+")");

        jTextField2.setText("Search");
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        afftitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        afftitle.setForeground(new java.awt.Color(255, 255, 255));
        afftitle.setText("  ");

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Played by the artist named :");

        affartist.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        affartist.setForeground(new java.awt.Color(255, 255, 255));
        affartist.setText("Played by the artist named :");

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Enjoy with ");

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Melomande Reecords");

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Duration :");

        affduration.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        affduration.setForeground(new java.awt.Color(255, 255, 255));
        affduration.setText("Duration :");

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel86.setText("Sort by :");

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 255, 51));
        jLabel100.setText("ID");
        jLabel100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel100MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel100MouseExited(evt);
            }
        });
        jLabel100.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel100MouseMoved(evt);
            }
        });

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(0, 255, 51));
        jLabel101.setText("Title");
        jLabel101.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel101MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel101MouseExited(evt);
            }
        });
        jLabel101.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel101MouseMoved(evt);
            }
        });

        jLabel102.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(0, 255, 51));
        jLabel102.setText("Author");
        jLabel102.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel102MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel102MouseExited(evt);
            }
        });
        jLabel102.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel102MouseMoved(evt);
            }
        });

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(0, 255, 51));
        jLabel103.setText("Duration");
        jLabel103.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel103MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel103MouseExited(evt);
            }
        });
        jLabel103.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel103MouseMoved(evt);
            }
        });

        jLabel154.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(0, 255, 51));
        jLabel154.setText("Saved date");
        jLabel154.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel154MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel154MouseExited(evt);
            }
        });
        jLabel154.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel154MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout listMusicLayout = new javax.swing.GroupLayout(listMusic);
        listMusic.setLayout(listMusicLayout);
        listMusicLayout.setHorizontalGroup(
            listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(listMusicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listMusicLayout.createSequentialGroup()
                        .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listMusicLayout.createSequentialGroup()
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(affartist))
                            .addGroup(listMusicLayout.createSequentialGroup()
                                .addComponent(jLabel83)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel84)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(affduration)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(listMusicLayout.createSequentialGroup()
                        .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listMusicLayout.createSequentialGroup()
                                .addComponent(jLabel86)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel100)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel101)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel102)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel103)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel154)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(listMusicLayout.createSequentialGroup()
                                .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(afftitle, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        listMusicLayout.setVerticalGroup(
            listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listMusicLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listMusicLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(afftitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listMusicLayout.createSequentialGroup()
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel83)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel85)
                                    .addComponent(affduration)))
                            .addComponent(affartist))
                        .addGap(18, 18, 18)
                        .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel100)
                            .addComponent(jLabel101)
                            .addComponent(jLabel102)
                            .addComponent(jLabel103)
                            .addComponent(jLabel154)))
                    .addGroup(listMusicLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(listMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
        );

        boardPanel.add(listMusic, "card3");

        EditMusic.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel13.setText("Edit music");

        jLabel14.setText("Title");

        titreField.setText("Enter the title");

        jLabel15.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel15.setText("This will be the music's title");

        jLabel16.setText("Author");

        jLabel17.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel17.setText("Select an artist. NOTICE : A music can have one or many authors");

        jLabel18.setText("Duration");

        dureeField.setText("Enter the duration");
        dureeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dureeFieldActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel19.setText("Make sure the format inserted is 00:00:00");

        jLabel20.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel20.setText("Add the featuring artists by editing the music");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg"))); // NOI18N
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel21MouseExited(evt);
            }
        });
        jLabel21.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel21MouseMoved(evt);
            }
        });

        jTable1.addMouseListener(new TableMouseListener(jTable1));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupFtAritist, org.jdesktop.beansbinding.ObjectProperty.create(), jTable1, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTable1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setText("Add featuring artists");

        jLabel23.setText("Other artists on that music");

        jLabel24.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel24.setText("Save the new artist");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel24MouseExited(evt);
            }
        });
        jLabel24.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel24MouseMoved(evt);
            }
        });

        jLabel81.setText("Load cover image");
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });

        jLabel82.setText("Apply");
        jLabel82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel82MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel82MouseExited(evt);
            }
        });
        jLabel82.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel82MouseMoved(evt);
            }
        });

        lbl_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg"))); // NOI18N

        javax.swing.GroupLayout EditMusicLayout = new javax.swing.GroupLayout(EditMusic);
        EditMusic.setLayout(EditMusicLayout);
        EditMusicLayout.setHorizontalGroup(
            EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditMusicLayout.createSequentialGroup()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(EditMusicLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(titreField, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21)
                                .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dureeField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EditMusicLayout.createSequentialGroup()
                                        .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(18, 18, 18)
                        .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel23)
                                .addComponent(jLabel22)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24))
                        .addContainerGap(170, Short.MAX_VALUE))
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_image)
                            .addComponent(jLabel81)
                            .addComponent(jLabel82)
                            .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))))
        );
        EditMusicLayout.setVerticalGroup(
            EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditMusicLayout.createSequentialGroup()
                .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel13)
                        .addGap(120, 120, 120)
                        .addComponent(jLabel14))
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EditMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dureeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel21))
                    .addGroup(EditMusicLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        boardPanel.add(EditMusic, "card4");

        AddMusic.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel25.setText("Add music");

        jLabel26.setText("Title");

        titreField1.setText("Enter the title");

        jLabel27.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel27.setText("This will be the music's title");

        jLabel28.setText("Author");

        jLabel29.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel29.setText("Select an artist. NOTICE : A music can have one or many authors");

        jLabel30.setText("Duration");

        dureeField1.setText("Enter the duration");
        dureeField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dureeField1ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel31.setText("Make sure the format inserted is 00:00:00");

        jLabel32.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel32.setText("Add the featuring artists by editing the music");

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addmus.jpg"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
        });
        jLabel33.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel33MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout AddMusicLayout = new javax.swing.GroupLayout(AddMusic);
        AddMusic.setLayout(AddMusicLayout);
        AddMusicLayout.setHorizontalGroup(
            AddMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddMusicLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addGap(0, 816, Short.MAX_VALUE))
            .addGroup(AddMusicLayout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addGroup(AddMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titreField1)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dureeField1)
                    .addGroup(AddMusicLayout.createSequentialGroup()
                        .addGroup(AddMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel32)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(493, Short.MAX_VALUE))
        );
        AddMusicLayout.setVerticalGroup(
            AddMusicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddMusicLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titreField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addGap(49, 49, 49)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addGap(50, 50, 50)
                .addComponent(jLabel30)
                .addGap(12, 12, 12)
                .addComponent(dureeField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addGap(36, 36, 36)
                .addComponent(jLabel33)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        boardPanel.add(AddMusic, "card4");

        listArtist.setBackground(new java.awt.Color(255, 255, 255));

        tble1.addMouseListener(new TableMouseListener(tble1));
        tble1.setBackground(new java.awt.Color(51, 51, 51));
        tble1.setForeground(new java.awt.Color(255, 255, 255));
        tble1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble1.setRowHeight(35);
        tble1.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tble1.setSelectionForeground(new java.awt.Color(51, 255, 51));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupArtist, org.jdesktop.beansbinding.ObjectProperty.create(), tble1, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        tble1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tble1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tble1);

        int d=0;
        try{
            stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select count(*) as total from musicien ");
            if(Rs.next()){
                d=Rs.getInt("total");
                Rs.close();
            }
        }catch (Exception e){System.err.println(e);}
        jLabel35.setForeground(new java.awt.Color(0, 255, 0));
        jLabel35.setText("Total ("+d+")");

        jTextField3.setText("Search");
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("A R T I S T");

        affNomComplet.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        affNomComplet.setForeground(new java.awt.Color(255, 255, 255));
        affNomComplet.setText("  ");

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setText("With the ID :");

        affid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        affid.setForeground(new java.awt.Color(255, 255, 255));
        affid.setText("Played by the artist named :");

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText("Can be found at :");

        jLabel119.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel119.setText("Sort by :");

        jLabel120.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(0, 255, 51));
        jLabel120.setText("NIF");
        jLabel120.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel120MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel120MouseExited(evt);
            }
        });
        jLabel120.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel120MouseMoved(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(0, 255, 51));
        jLabel121.setText("Last name");
        jLabel121.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel121MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel121MouseExited(evt);
            }
        });
        jLabel121.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel121MouseMoved(evt);
            }
        });

        jLabel122.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(0, 255, 51));
        jLabel122.setText("First name");
        jLabel122.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel122MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel122MouseExited(evt);
            }
        });
        jLabel122.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel122MouseMoved(evt);
            }
        });

        jLabel123.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(0, 255, 51));
        jLabel123.setText("Address");
        jLabel123.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel123MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel123MouseExited(evt);
            }
        });
        jLabel123.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel123MouseMoved(evt);
            }
        });

        affaddress.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        affaddress.setForeground(new java.awt.Color(255, 255, 255));
        affaddress.setText("Duration :");

        jLabel124.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(0, 255, 51));
        jLabel124.setText("Phone");
        jLabel124.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel124MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel124MouseExited(evt);
            }
        });
        jLabel124.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel124MouseMoved(evt);
            }
        });

        jLabell155.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabell155.setForeground(new java.awt.Color(0, 255, 51));
        jLabell155.setText("Saved date");
        jLabell155.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabell155MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabell155MouseExited(evt);
            }
        });
        jLabell155.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabell155MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout listArtistLayout = new javax.swing.GroupLayout(listArtist);
        listArtist.setLayout(listArtistLayout);
        listArtistLayout.setHorizontalGroup(
            listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
            .addGroup(listArtistLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listArtistLayout.createSequentialGroup()
                        .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listArtistLayout.createSequentialGroup()
                                .addComponent(jLabel115)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(affid))
                            .addGroup(listArtistLayout.createSequentialGroup()
                                .addComponent(jLabel116)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(affaddress))
                            .addComponent(jLabel75)
                            .addComponent(affNomComplet, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(listArtistLayout.createSequentialGroup()
                        .addComponent(jLabel119)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel120)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel121)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel122)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel123)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel124)
                        .addGap(18, 18, 18)
                        .addComponent(jLabell155)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        listArtistLayout.setVerticalGroup(
            listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listArtistLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listArtistLayout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(affNomComplet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(listArtistLayout.createSequentialGroup()
                                .addComponent(jLabel115)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel116)
                                    .addComponent(affaddress)))
                            .addComponent(affid))
                        .addGap(10, 10, 10)
                        .addGroup(listArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel120)
                            .addComponent(jLabel121)
                            .addComponent(jLabel122)
                            .addComponent(jLabel123)
                            .addComponent(jLabel35)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel124)
                            .addComponent(jLabell155)))
                    .addComponent(lb2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
        );

        boardPanel.add(listArtist, "card3");

        AddArtist.setBackground(new java.awt.Color(255, 255, 255));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel36.setText("Add artist");

        jLabel37.setText("Last name");

        txt_nom_art.setText("Enter the artist's last name");

        jLabel38.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel38.setText("This will be the artist's last name");

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addartist.jpg"))); // NOI18N
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
        });
        jLabel44.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel44MouseMoved(evt);
            }
        });

        jLabel45.setText("First name");

        txt_prenom_art.setText("Enter the artist's first name");
        txt_prenom_art.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_prenom_artActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel46.setText("This will be the  artist's first name");

        jLabel47.setText("NIF");

        nif1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nif1ActionPerformed(evt);
            }
        });
        nif1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nif1KeyTyped(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel48.setText("This will be the  artist's ID (a 10 digit number)");

        jLabel49.setText("Address");

        txt_address_art.setText("Enter the artist's address");

        jLabel50.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel50.setText("No specification on the address");

        jLabel51.setText("Phone number");

        txt_phone_art.setText("Enter the artist's phone number");

        jLabel52.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel52.setText("No specification on the phone number");

        nif2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nif2ActionPerformed(evt);
            }
        });
        nif2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nif2KeyTyped(evt);
            }
        });

        nif3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nif3ActionPerformed(evt);
            }
        });
        nif3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nif3KeyTyped(evt);
            }
        });

        nif4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nif4ActionPerformed(evt);
            }
        });
        nif4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nif4KeyTyped(evt);
            }
        });

        jLabel39.setText("-");

        jLabel40.setText("-");

        jLabel41.setText("-");

        javax.swing.GroupLayout AddArtistLayout = new javax.swing.GroupLayout(AddArtist);
        AddArtist.setLayout(AddArtistLayout);
        AddArtistLayout.setHorizontalGroup(
            AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddArtistLayout.createSequentialGroup()
                .addComponent(jLabel36)
                .addGap(0, 827, Short.MAX_VALUE))
            .addGroup(AddArtistLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel44)
                        .addGroup(AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_phone_art, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51)))
                    .addComponent(txt_address_art, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(txt_prenom_art, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(txt_nom_art, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel49)
                    .addGroup(AddArtistLayout.createSequentialGroup()
                        .addGroup(AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addGroup(AddArtistLayout.createSequentialGroup()
                                .addComponent(nif1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nif2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nif3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel41)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nif4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddArtistLayout.setVerticalGroup(
            AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddArtistLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nom_art, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_prenom_art, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nif1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(nif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(nif3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(nif4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addGap(18, 18, 18)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_address_art, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_phone_art, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addGap(18, 18, 18)
                .addComponent(jLabel44)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        boardPanel.add(AddArtist, "card4");

        EditArtist.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel42.setText("Edit artist");

        jLabel43.setText("Last name");

        txt_nom_art1.setText("Enter the artist's last name");

        jLabel53.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel53.setText("This will be the artit's last name");

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg"))); // NOI18N
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
        });
        jLabel54.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel54MouseMoved(evt);
            }
        });

        jLabel55.setText("First name");

        txt_prenom_art12.setText("Enter the artist's first name");
        txt_prenom_art12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_prenom_art12ActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel56.setText("This will be the  artist's first name");

        jLabel57.setText("NIF");

        txt_NIF_1p1.setEditable(false);
        txt_NIF_1p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NIF_1p1ActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel58.setText("This will be the  artist's ID (a 10 digit number)");

        jLabel59.setText("Address");

        txt_address_art1.setText("Enter the artist's address");

        jLabel60.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel60.setText("No specification on the address");

        jLabel61.setText("Phone number");

        txt_phone_art1.setText("Enter the artist's phone number");

        jLabel62.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel62.setText("No specification on the phone number");

        txt_NIF_2p1.setEditable(false);
        txt_NIF_2p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NIF_2p1ActionPerformed(evt);
            }
        });

        txt_NIF_3p1.setEditable(false);
        txt_NIF_3p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NIF_3p1ActionPerformed(evt);
            }
        });

        txt_NIF_4p1.setEditable(false);
        txt_NIF_4p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NIF_4p1ActionPerformed(evt);
            }
        });

        jLabel63.setText("-");

        jLabel64.setText("-");

        jLabel65.setText("-");

        jLabel117.setText("Load image");
        jLabel117.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel117MouseClicked(evt);
            }
        });

        jLabel118.setText("Apply");
        jLabel118.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel118MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel118MouseExited(evt);
            }
        });
        jLabel118.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel118MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout EditArtistLayout = new javax.swing.GroupLayout(EditArtist);
        EditArtist.setLayout(EditArtistLayout);
        EditArtistLayout.setHorizontalGroup(
            EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditArtistLayout.createSequentialGroup()
                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addGroup(EditArtistLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(txt_prenom_art12, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel54)
                                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_phone_art1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel61)))
                            .addComponent(jLabel56)
                            .addComponent(txt_address_art1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60)
                            .addComponent(txt_nom_art1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53)
                            .addComponent(jLabel59)
                            .addComponent(jLabel43)
                            .addGroup(EditArtistLayout.createSequentialGroup()
                                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addGroup(EditArtistLayout.createSequentialGroup()
                                        .addComponent(txt_NIF_1p1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_NIF_2p1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel64)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_NIF_3p1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel65)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_NIF_4p1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel57))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_image2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel117, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(path2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(37, 37, 37))
        );
        EditArtistLayout.setVerticalGroup(
            EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditArtistLayout.createSequentialGroup()
                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditArtistLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel42)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nom_art1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel53)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prenom_art12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel56))
                    .addGroup(EditArtistLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lbl_image2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel117)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(path2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel118)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EditArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_NIF_1p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63)
                    .addComponent(txt_NIF_2p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(txt_NIF_3p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(txt_NIF_4p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_address_art1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60)
                .addGap(18, 18, 18)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_phone_art1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addGap(18, 18, 18)
                .addComponent(jLabel54)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        boardPanel.add(EditArtist, "card4");

        AddAlbum.setBackground(new java.awt.Color(255, 255, 255));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel66.setText("Add album");

        jLabel67.setText("Title");

        titreField2.setText("Enter the title");

        jLabel68.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel68.setText("This will be the album's title");

        jLabel69.setText("Author");

        jLabel70.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel70.setText("Select an artist. NOTICE : A music can have one or many authors");

        jLabel71.setText("Date");

        jLabel72.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel72.setText("Choose a date");

        jLabel73.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel73.setText("Add the featuring artists by editing the music");

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addalbum.jpg"))); // NOI18N
        jLabel74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel74MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel74MouseExited(evt);
            }
        });
        jLabel74.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel74MouseMoved(evt);
            }
        });

        dateChooserCombo1.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateChooserCombo1.setLocale(new java.util.Locale("en", "ZA", ""));

    jLabel77.setText("Format");

    jLabel78.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel78.setText("Choose a format");

    newcomb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vinyle", "CD", "Cassette", "MP3" }));

    javax.swing.GroupLayout AddAlbumLayout = new javax.swing.GroupLayout(AddAlbum);
    AddAlbum.setLayout(AddAlbumLayout);
    AddAlbumLayout.setHorizontalGroup(
        AddAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(AddAlbumLayout.createSequentialGroup()
            .addComponent(jLabel66)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(AddAlbumLayout.createSequentialGroup()
            .addGap(180, 180, 180)
            .addGroup(AddAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jComboBox4, 0, 335, Short.MAX_VALUE)
                .addComponent(jLabel78)
                .addComponent(jLabel72)
                .addComponent(jLabel74, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel70)
                .addComponent(jLabel67)
                .addComponent(titreField2)
                .addComponent(jLabel68)
                .addComponent(jLabel69)
                .addComponent(jLabel73)
                .addComponent(jLabel77)
                .addComponent(jLabel71)
                .addComponent(dateChooserCombo1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addComponent(newcomb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(465, Short.MAX_VALUE))
    );
    AddAlbumLayout.setVerticalGroup(
        AddAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(AddAlbumLayout.createSequentialGroup()
            .addGap(38, 38, 38)
            .addComponent(jLabel66)
            .addGap(28, 28, 28)
            .addComponent(jLabel67)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(titreField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel68)
            .addGap(18, 18, 18)
            .addComponent(jLabel69)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel70)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel73)
            .addGap(18, 18, 18)
            .addComponent(jLabel77)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(newcomb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel78)
            .addGap(18, 18, 18)
            .addComponent(jLabel71)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel72)
            .addGap(18, 18, 18)
            .addComponent(jLabel74)
            .addContainerGap(127, Short.MAX_VALUE))
    );

    boardPanel.add(AddAlbum, "card4");

    listAlbum.setBackground(new java.awt.Color(255, 255, 255));

    tble2.setBackground(new java.awt.Color(51, 51, 51));
    tble2.setForeground(new java.awt.Color(255, 255, 255));
    tble2.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    tble2.setRowHeight(35);
    tble2.setSelectionBackground(new java.awt.Color(102, 102, 102));
    tble2.setSelectionForeground(new java.awt.Color(51, 255, 51));

    binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupAlbum, org.jdesktop.beansbinding.ObjectProperty.create(), tble2, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
    bindingGroup.addBinding(binding);

    tble2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tble2MouseClicked(evt);
        }
    });
    jScrollPane4.setViewportView(tble2);

    int cd=0;
    try{
        stm=conn.obtenirconnection().createStatement();
        Rs=stm.executeQuery("Select count(*) as total from album ");
        if(Rs.next()){
            c=Rs.getInt("total");
            Rs.close();
        }
    }catch (Exception e){System.err.println(e);}
    jLabel76.setForeground(new java.awt.Color(0, 255, 0));
    jLabel76.setText("Total ("+cd+")");

    jTextField4.setText("Search");
    jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField4KeyReleased(evt);
        }
    });

    jLabel104.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel104.setForeground(new java.awt.Color(255, 255, 255));
    jLabel104.setText("A L B U M");

    afftitle1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    afftitle1.setForeground(new java.awt.Color(255, 255, 255));
    afftitle1.setText("  ");

    jLabel105.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel105.setForeground(new java.awt.Color(255, 255, 255));
    jLabel105.setText("Played by the artist named :");

    affartist1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    affartist1.setForeground(new java.awt.Color(255, 255, 255));
    affartist1.setText("Played by the artist named :");

    jLabel106.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    jLabel106.setForeground(new java.awt.Color(255, 255, 255));
    jLabel106.setText("Enjoy with ");

    jLabel107.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    jLabel107.setForeground(new java.awt.Color(255, 255, 255));
    jLabel107.setText("Melomande Reecords");

    jLabel109.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel109.setText("Sort by :");

    jLabel110.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel110.setForeground(new java.awt.Color(0, 255, 51));
    jLabel110.setText("ID");
    jLabel110.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel110MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel110MouseExited(evt);
        }
    });
    jLabel110.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel110MouseMoved(evt);
        }
    });

    jLabel111.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel111.setForeground(new java.awt.Color(0, 255, 51));
    jLabel111.setText("Title");
    jLabel111.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel111MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel111MouseExited(evt);
        }
    });
    jLabel111.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel111MouseMoved(evt);
        }
    });

    jLabel112.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel112.setForeground(new java.awt.Color(0, 255, 51));
    jLabel112.setText("Author");
    jLabel112.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel112MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel112MouseExited(evt);
        }
    });
    jLabel112.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel112MouseMoved(evt);
        }
    });

    jLabel113.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel113.setForeground(new java.awt.Color(0, 255, 51));
    jLabel113.setText("Duration");
    jLabel113.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel113MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel113MouseExited(evt);
        }
    });
    jLabel113.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel113MouseMoved(evt);
        }
    });

    jLabel134.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel134.setForeground(new java.awt.Color(0, 255, 51));
    jLabel134.setText("Saved date");
    jLabel134.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel134MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel134MouseExited(evt);
        }
    });
    jLabel134.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel134MouseMoved(evt);
        }
    });

    javax.swing.GroupLayout listAlbumLayout = new javax.swing.GroupLayout(listAlbum);
    listAlbum.setLayout(listAlbumLayout);
    listAlbumLayout.setHorizontalGroup(
        listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane4)
        .addGroup(listAlbumLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listAlbumLayout.createSequentialGroup()
                    .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(listAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel105)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(affartist1))
                        .addGroup(listAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel106)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel107))
                        .addComponent(jLabel104))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(listAlbumLayout.createSequentialGroup()
                    .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, listAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel109)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel110)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel111)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel112)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel113)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel134)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel76))
                        .addComponent(afftitle1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    listAlbumLayout.setVerticalGroup(
        listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(listAlbumLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listAlbumLayout.createSequentialGroup()
                    .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(listAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel104)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(afftitle1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(listAlbumLayout.createSequentialGroup()
                                    .addComponent(jLabel105)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel106)
                                        .addComponent(jLabel107)))
                                .addComponent(affartist1))
                            .addGap(12, 12, 12)
                            .addGroup(listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel110)
                                .addComponent(jLabel111)
                                .addComponent(jLabel112)
                                .addComponent(jLabel113)
                                .addComponent(jLabel134)))
                        .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
    );

    boardPanel.add(listAlbum, "card3");

    EditAlbum.setBackground(new java.awt.Color(255, 255, 255));

    jLabel79.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    jLabel79.setText("Edit album");

    jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg"))); // NOI18N
    jLabel87.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel87MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel87MouseExited(evt);
        }
    });
    jLabel87.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel87MouseMoved(evt);
        }
    });

    jTable2.setAutoCreateRowSorter(true);
    jTable2.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));

    binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupRemALB, org.jdesktop.beansbinding.ObjectProperty.create(), jTable2, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
    bindingGroup.addBinding(binding);

    jScrollPane5.setViewportView(jTable2);

    jLabel88.setText("Add new music to the album");

    jLabel89.setText("All the musics on that album");

    jLabel90.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel90.setText("Add the music to the album");
    jLabel90.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel90MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel90MouseExited(evt);
        }
    });
    jLabel90.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel90MouseMoved(evt);
        }
    });

    jLabel91.setText("Title");

    titreField5.setText("Enter the title");

    jLabel92.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel92.setText("Thid will be the album title");

    jLabel93.setText("Author");

    jLabel94.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel94.setText("Select an artist. NOTICE :A music can have one or many othors");

    jLabel95.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel95.setText("Add the featuring artists by modifying the music");

    jLabel96.setText("Format");

    jLabel97.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel97.setText("Choose a format");

    jLabel98.setText("Date");

    dateChooserCombo2.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo2.setWeekStyle(datechooser.view.WeekDaysStyle.SHORT);
dateChooserCombo2.setLocale(new java.util.Locale("en", "ZA", ""));

jLabel99.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
jLabel99.setText("Choose a date");

jLabel108.setText("Apply");
jLabel108.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        jLabel108MouseClicked(evt);
    }
    public void mouseExited(java.awt.event.MouseEvent evt) {
        jLabel108MouseExited(evt);
    }
    });
    jLabel108.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel108MouseMoved(evt);
        }
    });

    jLabel114.setText("Load cover image");
    jLabel114.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel114MouseClicked(evt);
        }
    });

    combobo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vinyle", "CD", "Cassette", "MP3" }));

    javax.swing.GroupLayout EditAlbumLayout = new javax.swing.GroupLayout(EditAlbum);
    EditAlbum.setLayout(EditAlbumLayout);
    EditAlbumLayout.setHorizontalGroup(
        EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditAlbumLayout.createSequentialGroup()
            .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EditAlbumLayout.createSequentialGroup()
                    .addComponent(jLabel79)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditAlbumLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel87)
                        .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel91, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(titreField5, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel92, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateChooserCombo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combobo, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)
                    .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel89)
                            .addComponent(jLabel88)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel90))
                    .addGap(34, 34, 34)))
            .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(path1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel108)
                .addComponent(jLabel114)
                .addComponent(lbl_image1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(22, 22, 22))
    );
    EditAlbumLayout.setVerticalGroup(
        EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(EditAlbumLayout.createSequentialGroup()
            .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EditAlbumLayout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addComponent(lbl_image1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel114)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(path1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jLabel108))
                .addGroup(EditAlbumLayout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jLabel79)
                    .addGap(33, 33, 33)
                    .addGroup(EditAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(EditAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel91)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(titreField5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel92)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel93)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel94)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel95)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel96)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(combobo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel97)
                            .addGap(23, 23, 23)
                            .addComponent(jLabel98)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dateChooserCombo2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel99)
                            .addGap(16, 16, 16)
                            .addComponent(jLabel87))
                        .addGroup(EditAlbumLayout.createSequentialGroup()
                            .addComponent(jLabel89)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel88)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel90)))))
            .addContainerGap(118, Short.MAX_VALUE))
    );

    boardPanel.add(EditAlbum, "card4");

    listInstrum.setBackground(new java.awt.Color(255, 255, 255));

    tbleInst.setBackground(new java.awt.Color(51, 51, 51));
    tbleInst.setForeground(new java.awt.Color(255, 255, 255));
    tbleInst.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    tbleInst.setRowHeight(35);
    tbleInst.setSelectionBackground(new java.awt.Color(102, 102, 102));
    tbleInst.setSelectionForeground(new java.awt.Color(51, 255, 51));

    binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupInstrum, org.jdesktop.beansbinding.ObjectProperty.create(), tbleInst, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
    bindingGroup.addBinding(binding);

    tbleInst.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tbleInstMouseClicked(evt);
        }
    });
    jScrollPane6.setViewportView(tbleInst);

    int da=0;
    try{
        stm=conn.obtenirconnection().createStatement();
        Rs=stm.executeQuery("Select count(*) as total from instrument ");
        if(Rs.next()){
            da=Rs.getInt("total");
            Rs.close();
        }
    }catch (Exception e){System.err.println(e);}
    jLabel125.setForeground(new java.awt.Color(0, 255, 0));
    jLabel125.setText("Total ("+da+")");

    jTextField5.setText("Search");
    jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField5KeyReleased(evt);
        }
    });

    jLabel126.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel126.setForeground(new java.awt.Color(255, 255, 255));
    jLabel126.setText("I N S T R U M E N T");

    affNomComplet1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    affNomComplet1.setForeground(new java.awt.Color(255, 255, 255));
    affNomComplet1.setText("  ");

    jLabel127.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel127.setForeground(new java.awt.Color(255, 255, 255));
    jLabel127.setText("With the ID :");

    affid1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    affid1.setForeground(new java.awt.Color(255, 255, 255));
    affid1.setText("Played by the artist named :");

    jLabel128.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    jLabel128.setForeground(new java.awt.Color(255, 255, 255));
    jLabel128.setText("Received on");

    jLabel129.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel129.setText("Sort by :");

    jLabel130.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel130.setForeground(new java.awt.Color(0, 255, 51));
    jLabel130.setText("ID");
    jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel130MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel130MouseExited(evt);
        }
    });
    jLabel130.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel130MouseMoved(evt);
        }
    });

    jLabel131.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel131.setForeground(new java.awt.Color(0, 255, 51));
    jLabel131.setText("Name");
    jLabel131.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel131MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel131MouseExited(evt);
        }
    });
    jLabel131.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel131MouseMoved(evt);
        }
    });

    jLabel132.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel132.setForeground(new java.awt.Color(0, 255, 51));
    jLabel132.setText("Procuration date");
    jLabel132.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel132MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel132MouseExited(evt);
        }
    });
    jLabel132.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel132MouseMoved(evt);
        }
    });

    jLabel133.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel133.setForeground(new java.awt.Color(0, 255, 51));
    jLabel133.setText("Saved date");
    jLabel133.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel133MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel133MouseExited(evt);
        }
    });
    jLabel133.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel133MouseMoved(evt);
        }
    });

    affaddress1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    affaddress1.setForeground(new java.awt.Color(255, 255, 255));
    affaddress1.setText("Duration :");

    javax.swing.GroupLayout listInstrumLayout = new javax.swing.GroupLayout(listInstrum);
    listInstrum.setLayout(listInstrumLayout);
    listInstrumLayout.setHorizontalGroup(
        listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        .addGroup(listInstrumLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listInstrumLayout.createSequentialGroup()
                    .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(listInstrumLayout.createSequentialGroup()
                            .addComponent(jLabel127)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(affid1))
                        .addGroup(listInstrumLayout.createSequentialGroup()
                            .addComponent(jLabel128)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(affaddress1))
                        .addComponent(jLabel126)
                        .addComponent(affNomComplet1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
                .addGroup(listInstrumLayout.createSequentialGroup()
                    .addComponent(jLabel129)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel130)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel131)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel132)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel133)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel125)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
    );
    listInstrumLayout.setVerticalGroup(
        listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(listInstrumLayout.createSequentialGroup()
            .addGap(19, 19, 19)
            .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listInstrumLayout.createSequentialGroup()
                    .addComponent(jLabel126)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(affNomComplet1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(listInstrumLayout.createSequentialGroup()
                            .addComponent(jLabel127)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel128)
                                .addComponent(affaddress1)))
                        .addComponent(affid1))
                    .addGap(10, 10, 10)
                    .addGroup(listInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel130)
                        .addComponent(jLabel131)
                        .addComponent(jLabel132)
                        .addComponent(jLabel133)
                        .addComponent(jLabel125)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
    );

    boardPanel.add(listInstrum, "card3");

    AddInstrum.setBackground(new java.awt.Color(255, 255, 255));

    jLabel135.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    jLabel135.setText("Add instrument");

    jLabel140.setText("Procuration date");

    jLabel141.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel141.setText("Choose a date");

    jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addinst.jpg"))); // NOI18N
    jLabel143.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel143MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel143MouseExited(evt);
        }
    });
    jLabel143.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel143MouseMoved(evt);
        }
    });

    dateChooserCombo3.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo3.setLocale(new java.util.Locale("en", "ZA", ""));

jLabel144.setText("Name");

jLabel145.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
jLabel145.setText("Choose an instrument");

newcomb1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guitare", "Banjo", "Assòtò", "Synthé", "Piano", "Flute" }));

javax.swing.GroupLayout AddInstrumLayout = new javax.swing.GroupLayout(AddInstrum);
AddInstrum.setLayout(AddInstrumLayout);
AddInstrumLayout.setHorizontalGroup(
    AddInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(AddInstrumLayout.createSequentialGroup()
        .addComponent(jLabel135)
        .addGap(0, 737, Short.MAX_VALUE))
    .addGroup(AddInstrumLayout.createSequentialGroup()
        .addGap(181, 181, 181)
        .addGroup(AddInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addComponent(jLabel145)
            .addComponent(jLabel141)
            .addComponent(jLabel143, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel144)
            .addComponent(jLabel140)
            .addComponent(dateChooserCombo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(newcomb1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    AddInstrumLayout.setVerticalGroup(
        AddInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(AddInstrumLayout.createSequentialGroup()
            .addGap(38, 38, 38)
            .addComponent(jLabel135)
            .addGap(64, 64, 64)
            .addComponent(jLabel144)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(newcomb1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel145)
            .addGap(18, 18, 18)
            .addComponent(jLabel140)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dateChooserCombo3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel141)
            .addGap(18, 18, 18)
            .addComponent(jLabel143)
            .addGap(271, 271, 271))
    );

    boardPanel.add(AddInstrum, "card4");

    EditInstrum.setBackground(new java.awt.Color(255, 255, 255));

    jLabel136.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    jLabel136.setText("Edit instrument");

    jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg"))); // NOI18N
    jLabel137.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel137MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel137MouseExited(evt);
        }
    });
    jLabel137.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel137MouseMoved(evt);
        }
    });

    jTable3.setAutoCreateRowSorter(true);
    jTable3.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));

    binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupRemARTinInst, org.jdesktop.beansbinding.ObjectProperty.create(), jTable3, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
    bindingGroup.addBinding(binding);

    jScrollPane7.setViewportView(jTable3);

    jLabel138.setText("Add a new artist who uses this instrument");

    jLabel139.setText("All the artists who use this instrument");

    jLabel142.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel142.setText("Add this artist to the list");
    jLabel142.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel142MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel142MouseExited(evt);
        }
    });
    jLabel142.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel142MouseMoved(evt);
        }
    });

    jLabel157.setText("Name");

    newcomb2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guitare", "Banjo", "Assòtò", "Synthé", "Piano", "Flute" }));
    newcomb2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            newcomb2ActionPerformed(evt);
        }
    });

    jLabel158.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
    jLabel158.setText("Choose an instrument");

    jLabel159.setText("Procuration date");

    dateChooserCombo5.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
        new datechooser.view.appearance.ViewAppearance("custom",
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                true,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 255),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.ButtonPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(128, 128, 128),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(0, 0, 255),
                false,
                true,
                new datechooser.view.appearance.swing.LabelPainter()),
            new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 0, 0),
                false,
                false,
                new datechooser.view.appearance.swing.ButtonPainter()),
            (datechooser.view.BackRenderer)null,
            false,
            true)));
dateChooserCombo5.setLocale(new java.util.Locale("en", "ZA", ""));

jLabel160.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
jLabel160.setText("Choose a date");

javax.swing.GroupLayout EditInstrumLayout = new javax.swing.GroupLayout(EditInstrum);
EditInstrum.setLayout(EditInstrumLayout);
EditInstrumLayout.setHorizontalGroup(
    EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(EditInstrumLayout.createSequentialGroup()
        .addGap(181, 181, 181)
        .addGroup(EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel137)
            .addGroup(EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel158)
                .addComponent(jLabel160)
                .addComponent(jLabel157)
                .addComponent(jLabel159)
                .addComponent(dateChooserCombo5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newcomb2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addGap(28, 28, 28)
        .addGroup(EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel139)
                .addComponent(jLabel138)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel142))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    .addGroup(EditInstrumLayout.createSequentialGroup()
        .addComponent(jLabel136)
        .addGap(0, 739, Short.MAX_VALUE))
    );
    EditInstrumLayout.setVerticalGroup(
        EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(EditInstrumLayout.createSequentialGroup()
            .addGap(38, 38, 38)
            .addGroup(EditInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(EditInstrumLayout.createSequentialGroup()
                    .addComponent(jLabel139)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel138)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel142))
                .addGroup(EditInstrumLayout.createSequentialGroup()
                    .addComponent(jLabel136)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel157)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(newcomb2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel158)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel159)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateChooserCombo5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel160)
                    .addGap(199, 199, 199)
                    .addComponent(jLabel137)))
            .addContainerGap(137, Short.MAX_VALUE))
    );

    boardPanel.add(EditInstrum, "card4");

    setting.setBackground(new java.awt.Color(0, 0, 0));

    jLabel34.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    jLabel34.setForeground(new java.awt.Color(255, 255, 255));
    jLabel34.setText("Settings");

    jLabel146.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel146.setForeground(new java.awt.Color(255, 255, 255));
    jLabel146.setText("About");

    jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/rights.jpg"))); // NOI18N

    jLabel148.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel148.setForeground(new java.awt.Color(255, 255, 255));
    jLabel148.setText("Administrator Actions");

    jLabel153.setForeground(new java.awt.Color(51, 255, 0));
    jLabel153.setText("Reset database");
    jLabel153.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel153MouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel153MouseExited(evt);
        }
    });
    jLabel153.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            jLabel153MouseMoved(evt);
        }
    });

    javax.swing.GroupLayout settingLayout = new javax.swing.GroupLayout(setting);
    setting.setLayout(settingLayout);
    settingLayout.setHorizontalGroup(
        settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(settingLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel146)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel147))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
            .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel148)
                .addComponent(jLabel153))
            .addGap(89, 89, 89))
    );
    settingLayout.setVerticalGroup(
        settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(settingLayout.createSequentialGroup()
            .addGap(55, 55, 55)
            .addComponent(jLabel34)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel146)
                .addComponent(jLabel148))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel147)
                .addComponent(jLabel153))
            .addContainerGap(328, Short.MAX_VALUE))
    );

    boardPanel.add(setting, "card15");

    jLabel155.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
    jLabel155.setForeground(new java.awt.Color(255, 255, 255));
    jLabel155.setText("Info music");

    jLabel162.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel162.setForeground(new java.awt.Color(255, 255, 255));
    jLabel162.setText("ID :");

    jLabel163.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel163.setForeground(new java.awt.Color(255, 255, 255));
    jLabel163.setText("Title :");

    jLabel164.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel164.setForeground(new java.awt.Color(255, 255, 255));
    jLabel164.setText("Author :");

    jLabel165.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel165.setForeground(new java.awt.Color(255, 255, 255));
    jLabel165.setText("Duration :");

    jLabel166.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel166.setForeground(new java.awt.Color(255, 255, 255));
    jLabel166.setText("Saved date :");

    jLabel167.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel167.setForeground(new java.awt.Color(255, 255, 255));
    jLabel167.setText("Album :");

    jLabel168.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N

    jLabel169.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel169.setForeground(new java.awt.Color(255, 255, 255));
    jLabel169.setText("Title :");

    jLabel170.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel170.setForeground(new java.awt.Color(255, 255, 255));
    jLabel170.setText("Author :");

    jLabel171.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel171.setForeground(new java.awt.Color(255, 255, 255));
    jLabel171.setText("Duration :");

    jLabel172.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel172.setForeground(new java.awt.Color(255, 255, 255));
    jLabel172.setText("Saved date :");

    jLabel173.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel173.setForeground(new java.awt.Color(255, 255, 255));
    jLabel173.setText("Album :");

    jScrollPane8.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane8.setForeground(new java.awt.Color(51, 51, 51));

    jTable4.setBackground(new java.awt.Color(51, 51, 51));
    jTable4.setForeground(new java.awt.Color(255, 255, 255));
    jTable4.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable4.setCellSelectionEnabled(true);
    jTable4.setRowHeight(30);
    jTable4.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable4.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane8.setViewportView(jTable4);

    jLabel174.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel174.setForeground(new java.awt.Color(255, 255, 255));
    jLabel174.setText("Featuring artists on that song");

    jLabel175.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel175.setForeground(new java.awt.Color(255, 255, 255));
    jLabel175.setText("ID :");

    javax.swing.GroupLayout infoMusicpanelLayout = new javax.swing.GroupLayout(infoMusicpanel);
    infoMusicpanel.setLayout(infoMusicpanelLayout);
    infoMusicpanelLayout.setHorizontalGroup(
        infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoMusicpanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel168)
            .addGap(94, 94, 94)
            .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoMusicpanelLayout.createSequentialGroup()
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel174)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(infoMusicpanelLayout.createSequentialGroup()
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel163)
                        .addComponent(jLabel164)
                        .addComponent(jLabel162)
                        .addComponent(jLabel166)
                        .addComponent(jLabel167)
                        .addComponent(jLabel165))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 453, Short.MAX_VALUE)
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel169)
                        .addComponent(jLabel170)
                        .addComponent(jLabel171)
                        .addComponent(jLabel172)
                        .addComponent(jLabel173)
                        .addComponent(jLabel175))
                    .addGap(191, 191, 191))))
        .addGroup(infoMusicpanelLayout.createSequentialGroup()
            .addGap(280, 280, 280)
            .addComponent(jLabel155)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    infoMusicpanelLayout.setVerticalGroup(
        infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoMusicpanelLayout.createSequentialGroup()
            .addGap(59, 59, 59)
            .addComponent(jLabel155)
            .addGap(18, 18, 18)
            .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel168)
                .addGroup(infoMusicpanelLayout.createSequentialGroup()
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel162)
                        .addComponent(jLabel175))
                    .addGap(18, 18, 18)
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel163)
                        .addComponent(jLabel169))
                    .addGap(18, 18, 18)
                    .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel164)
                        .addComponent(jLabel170))))
            .addGap(18, 18, 18)
            .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel165)
                .addComponent(jLabel171))
            .addGap(18, 18, 18)
            .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel166)
                .addComponent(jLabel172))
            .addGap(18, 18, 18)
            .addGroup(infoMusicpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel167)
                .addComponent(jLabel173))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel174)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    boardPanel.add(infoMusicpanel, "card2");

    jLabel156.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
    jLabel156.setForeground(new java.awt.Color(255, 255, 255));
    jLabel156.setText("Info artist");

    jLabel176.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel176.setForeground(new java.awt.Color(255, 255, 255));
    jLabel176.setText("NIF :");

    jLabel177.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel177.setForeground(new java.awt.Color(255, 255, 255));
    jLabel177.setText("Last name :");

    jLabel178.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel178.setForeground(new java.awt.Color(255, 255, 255));
    jLabel178.setText("First name :");

    jLabel179.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel179.setForeground(new java.awt.Color(255, 255, 255));
    jLabel179.setText("Address :");

    jLabel180.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel180.setForeground(new java.awt.Color(255, 255, 255));
    jLabel180.setText("Phone :");

    jLabel181.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel181.setForeground(new java.awt.Color(255, 255, 255));
    jLabel181.setText("Saved date : :");

    jLabel182.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N

    jLabel183.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel183.setForeground(new java.awt.Color(255, 255, 255));
    jLabel183.setText("unknown");

    jLabel184.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel184.setForeground(new java.awt.Color(255, 255, 255));
    jLabel184.setText("unknown :");

    jLabel185.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel185.setForeground(new java.awt.Color(255, 255, 255));
    jLabel185.setText("unknown :");

    jLabel186.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel186.setForeground(new java.awt.Color(255, 255, 255));
    jLabel186.setText("unknown :");

    jLabel187.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel187.setForeground(new java.awt.Color(255, 255, 255));
    jLabel187.setText("Album :");

    jScrollPane9.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane9.setForeground(new java.awt.Color(51, 51, 51));

    jTable5.setBackground(new java.awt.Color(51, 51, 51));
    jTable5.setForeground(new java.awt.Color(255, 255, 255));
    jTable5.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable5.setCellSelectionEnabled(true);
    jTable5.setRowHeight(30);
    jTable5.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable5.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane9.setViewportView(jTable5);

    jLabel188.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel188.setForeground(new java.awt.Color(255, 255, 255));
    jLabel188.setText("All musics played by this artist");

    jLabel189.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel189.setForeground(new java.awt.Color(255, 255, 255));
    jLabel189.setText("unknown:");

    jLabel190.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel190.setForeground(new java.awt.Color(255, 255, 255));
    jLabel190.setText("All the albums he/she has");

    jScrollPane10.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane10.setForeground(new java.awt.Color(51, 51, 51));

    jTable6.setBackground(new java.awt.Color(51, 51, 51));
    jTable6.setForeground(new java.awt.Color(255, 255, 255));
    jTable6.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable6.setCellSelectionEnabled(true);
    jTable6.setRowHeight(30);
    jTable6.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable6.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane10.setViewportView(jTable6);

    jLabel191.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel191.setForeground(new java.awt.Color(255, 255, 255));
    jLabel191.setText("All the instruments he/she uses");

    jScrollPane11.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane11.setForeground(new java.awt.Color(51, 51, 51));

    jTable7.setBackground(new java.awt.Color(51, 51, 51));
    jTable7.setForeground(new java.awt.Color(255, 255, 255));
    jTable7.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable7.setCellSelectionEnabled(true);
    jTable7.setRowHeight(30);
    jTable7.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable7.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane11.setViewportView(jTable7);

    javax.swing.GroupLayout infoArtistLayout = new javax.swing.GroupLayout(infoArtist);
    infoArtist.setLayout(infoArtistLayout);
    infoArtistLayout.setHorizontalGroup(
        infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoArtistLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel182)
            .addGap(94, 94, 94)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel177)
                        .addComponent(jLabel178)
                        .addComponent(jLabel176)
                        .addComponent(jLabel180)
                        .addComponent(jLabel181)
                        .addComponent(jLabel179))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel183)
                        .addComponent(jLabel184)
                        .addComponent(jLabel185)
                        .addComponent(jLabel186)
                        .addComponent(jLabel187)
                        .addComponent(jLabel189))
                    .addGap(191, 191, 191))
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel188)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel190)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel191)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))))
        .addGroup(infoArtistLayout.createSequentialGroup()
            .addGap(280, 280, 280)
            .addComponent(jLabel156)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    infoArtistLayout.setVerticalGroup(
        infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoArtistLayout.createSequentialGroup()
            .addGap(59, 59, 59)
            .addComponent(jLabel156)
            .addGap(18, 18, 18)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel182)
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel176)
                        .addComponent(jLabel189))
                    .addGap(18, 18, 18)
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel177)
                        .addComponent(jLabel183))
                    .addGap(18, 18, 18)
                    .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel178)
                        .addComponent(jLabel184))))
            .addGap(18, 18, 18)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel179)
                .addComponent(jLabel185))
            .addGap(18, 18, 18)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel180)
                .addComponent(jLabel186))
            .addGap(18, 18, 18)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel181)
                .addComponent(jLabel187))
            .addGap(18, 18, 18)
            .addGroup(infoArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addComponent(jLabel188)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addComponent(jLabel190)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(infoArtistLayout.createSequentialGroup()
                    .addComponent(jLabel191)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(44, Short.MAX_VALUE))
    );

    boardPanel.add(infoArtist, "card2");

    jLabel161.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
    jLabel161.setForeground(new java.awt.Color(255, 255, 255));
    jLabel161.setText("Info instrument");

    jLabel192.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel192.setForeground(new java.awt.Color(255, 255, 255));
    jLabel192.setText("ID :");

    jLabel193.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel193.setForeground(new java.awt.Color(255, 255, 255));
    jLabel193.setText("Name :");

    jLabel194.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel194.setForeground(new java.awt.Color(255, 255, 255));
    jLabel194.setText("Procuration date :");

    jLabel195.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel195.setForeground(new java.awt.Color(255, 255, 255));
    jLabel195.setText("Saved date :");

    jLabel198.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N

    jLabel199.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel199.setForeground(new java.awt.Color(255, 255, 255));
    jLabel199.setText("unknown");

    jLabel200.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel200.setForeground(new java.awt.Color(255, 255, 255));
    jLabel200.setText("unknown :");

    jLabel201.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel201.setForeground(new java.awt.Color(255, 255, 255));
    jLabel201.setText("unknown :");

    jLabel205.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel205.setForeground(new java.awt.Color(255, 255, 255));
    jLabel205.setText("unknown:");

    jLabel207.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel207.setForeground(new java.awt.Color(255, 255, 255));
    jLabel207.setText("All the artists who use this instrument");

    jScrollPane14.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane14.setForeground(new java.awt.Color(51, 51, 51));

    jTable10.setBackground(new java.awt.Color(51, 51, 51));
    jTable10.setForeground(new java.awt.Color(255, 255, 255));
    jTable10.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable10.setCellSelectionEnabled(true);
    jTable10.setRowHeight(30);
    jTable10.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable10.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane14.setViewportView(jTable10);

    javax.swing.GroupLayout infoInstrumLayout = new javax.swing.GroupLayout(infoInstrum);
    infoInstrum.setLayout(infoInstrumLayout);
    infoInstrumLayout.setHorizontalGroup(
        infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoInstrumLayout.createSequentialGroup()
            .addGap(280, 280, 280)
            .addComponent(jLabel161)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(infoInstrumLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel198)
            .addGap(94, 94, 94)
            .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoInstrumLayout.createSequentialGroup()
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel207)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(infoInstrumLayout.createSequentialGroup()
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel193)
                        .addComponent(jLabel194)
                        .addComponent(jLabel192)
                        .addComponent(jLabel195))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 447, Short.MAX_VALUE)
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel199)
                        .addComponent(jLabel200)
                        .addComponent(jLabel201)
                        .addComponent(jLabel205))
                    .addGap(191, 191, 191))))
    );
    infoInstrumLayout.setVerticalGroup(
        infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoInstrumLayout.createSequentialGroup()
            .addGap(59, 59, 59)
            .addComponent(jLabel161)
            .addGap(18, 18, 18)
            .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel198)
                .addGroup(infoInstrumLayout.createSequentialGroup()
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel192)
                        .addComponent(jLabel205))
                    .addGap(18, 18, 18)
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel193)
                        .addComponent(jLabel199))
                    .addGap(18, 18, 18)
                    .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel194)
                        .addComponent(jLabel200))))
            .addGap(18, 18, 18)
            .addGroup(infoInstrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel195)
                .addComponent(jLabel201))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
            .addComponent(jLabel207)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(71, 71, 71))
    );

    boardPanel.add(infoInstrum, "card2");

    jLabel196.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
    jLabel196.setForeground(new java.awt.Color(255, 255, 255));
    jLabel196.setText("Info album");

    jLabel197.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel197.setForeground(new java.awt.Color(255, 255, 255));
    jLabel197.setText("ID :");

    jLabel202.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel202.setForeground(new java.awt.Color(255, 255, 255));
    jLabel202.setText("Title :");

    jLabel203.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel203.setForeground(new java.awt.Color(255, 255, 255));
    jLabel203.setText("Format :");

    jLabel204.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel204.setForeground(new java.awt.Color(255, 255, 255));
    jLabel204.setText("Entry date :");

    jLabel206.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel206.setForeground(new java.awt.Color(255, 255, 255));
    jLabel206.setText("Author :");

    jLabel208.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel208.setForeground(new java.awt.Color(255, 255, 255));
    jLabel208.setText("Saved date : :");

    jLabel209.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N

    jLabel210.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel210.setForeground(new java.awt.Color(255, 255, 255));
    jLabel210.setText("unknown");

    jLabel211.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel211.setForeground(new java.awt.Color(255, 255, 255));
    jLabel211.setText("unknown :");

    jLabel212.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel212.setForeground(new java.awt.Color(255, 255, 255));
    jLabel212.setText("unknown :");

    jLabel213.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel213.setForeground(new java.awt.Color(255, 255, 255));
    jLabel213.setText("unknown :");

    jLabel214.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
    jLabel214.setForeground(new java.awt.Color(255, 255, 255));
    jLabel214.setText("Album :");

    jScrollPane12.setBackground(new java.awt.Color(51, 51, 51));
    jScrollPane12.setForeground(new java.awt.Color(51, 51, 51));

    jTable8.setBackground(new java.awt.Color(51, 51, 51));
    jTable8.setForeground(new java.awt.Color(255, 255, 255));
    jTable8.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ));
    jTable8.setCellSelectionEnabled(true);
    jTable8.setRowHeight(30);
    jTable8.setSelectionBackground(new java.awt.Color(102, 102, 102));
    jTable8.setSelectionForeground(new java.awt.Color(51, 255, 51));
    jScrollPane12.setViewportView(jTable8);

    jLabel215.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
    jLabel215.setForeground(new java.awt.Color(255, 255, 255));
    jLabel215.setText("All musics on by this album");

    jLabel216.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel216.setForeground(new java.awt.Color(255, 255, 255));
    jLabel216.setText("unknown:");

    javax.swing.GroupLayout infoAlbumLayout = new javax.swing.GroupLayout(infoAlbum);
    infoAlbum.setLayout(infoAlbumLayout);
    infoAlbumLayout.setHorizontalGroup(
        infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoAlbumLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel209)
            .addGap(94, 94, 94)
            .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoAlbumLayout.createSequentialGroup()
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel202)
                        .addComponent(jLabel203)
                        .addComponent(jLabel197)
                        .addComponent(jLabel206)
                        .addComponent(jLabel208)
                        .addComponent(jLabel204))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 484, Short.MAX_VALUE)
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel210)
                        .addComponent(jLabel211)
                        .addComponent(jLabel212)
                        .addComponent(jLabel213)
                        .addComponent(jLabel214)
                        .addComponent(jLabel216))
                    .addGap(191, 191, 191))
                .addGroup(infoAlbumLayout.createSequentialGroup()
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel215)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))))
        .addGroup(infoAlbumLayout.createSequentialGroup()
            .addGap(280, 280, 280)
            .addComponent(jLabel196)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    infoAlbumLayout.setVerticalGroup(
        infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoAlbumLayout.createSequentialGroup()
            .addGap(59, 59, 59)
            .addComponent(jLabel196)
            .addGap(18, 18, 18)
            .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel209)
                .addGroup(infoAlbumLayout.createSequentialGroup()
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel197)
                        .addComponent(jLabel216))
                    .addGap(18, 18, 18)
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel202)
                        .addComponent(jLabel210))
                    .addGap(18, 18, 18)
                    .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel203)
                        .addComponent(jLabel211))))
            .addGap(18, 18, 18)
            .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel204)
                .addComponent(jLabel212))
            .addGap(18, 18, 18)
            .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel206)
                .addComponent(jLabel213))
            .addGap(18, 18, 18)
            .addGroup(infoAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel208)
                .addComponent(jLabel214))
            .addGap(18, 18, 18)
            .addComponent(jLabel215)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(44, Short.MAX_VALUE))
    );

    boardPanel.add(infoAlbum, "card2");

    javax.swing.GroupLayout BasePanelLayout = new javax.swing.GroupLayout(BasePanel);
    BasePanel.setLayout(BasePanelLayout);
    BasePanelLayout.setHorizontalGroup(
        BasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(BasePanelLayout.createSequentialGroup()
            .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    BasePanelLayout.setVerticalGroup(
        BasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(MenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(BasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(BasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    bindingGroup.bind();

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        // TODO add your handling code here:
         jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/musicon.jpg")));
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jLabel4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseMoved
        // TODO add your handling code here:
         jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/albumon.jpg")));
    }//GEN-LAST:event_jLabel4MouseMoved

    private void jLabel6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseMoved
        // TODO add your handling code here:
         jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/artiston.jpg")));
    }//GEN-LAST:event_jLabel6MouseMoved

    private void jLabel7MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseMoved
        // TODO add your handling code here:
         jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/instruon.jpg")));
    }//GEN-LAST:event_jLabel7MouseMoved

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
         jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/music.jpg")));
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
         jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/album.jpg")));
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
         jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/artist.jpg")));
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
         jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/instru.jpg")));
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        // TODO add your handling code here:
         jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+on.jpg")));
    }//GEN-LAST:event_jLabel2MouseMoved

    private void jLabel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseMoved
        // TODO add your handling code here:
          jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+on.jpg")));
    }//GEN-LAST:event_jLabel5MouseMoved

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
          jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg")));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // TODO add your handling code here:
          jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg")));
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
               DisplayListMusic();
        
    }//GEN-LAST:event_jLabel1MouseClicked

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
           int index = tble.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble.getModel();
        
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        chansonGlob.setID_chan(model.getValueAt(index, 0).toString());
           chansonGlob.setTitre_chan(model.getValueAt(index, 1).toString()); 
           chansonGlob.setAuteur_chan(model.getValueAt(index,2).toString()) ;
           chansonGlob.setDuree_chan(model.getValueAt(index,3).toString());
        
         
        titreField.setText(chansonGlob.getTitre_chan());
        dureeField.setText(chansonGlob.getDuree_chan());
        //.....nap recuepere tout nom yo epi afiche sal te klike a an premye
         try
        {
            jComboBox1.removeAllItems();jComboBox2.removeAllItems();
          // stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (select right (concat('0000000000',NIF_mus),10) as NIF_mus from  musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play ) order by nom_mus asc ");
            jComboBox1.addItem(chansonGlob.getAuteur_chan());
            
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox1.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);               
           } Rs.close();
           //NAP RETIRE AUTEUR PRINCIPALE LA.
           jComboBox1.getSelectedItem();
           String id_musicien_principal=jComboBox1.getSelectedItem().toString();
        System.out.println(id_musicien_principal);
        
           Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (Select NIF_mus from musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play )");
           //jComboBox2.addItem(" ");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox2.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);
        }
     //..........TABLE POUR ARTIST SECONDAIRES........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("NIF");
       model2.addColumn("Last name");
       model2.addColumn("First name");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from  musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable1.setModel(model2);
        
       
       //NAP AFICHE PHOTO A
        boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_chan from chanson where id_chan='"+chansonGlob.getId_chan()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_chan");
                
                 if(img==null) 
                     tem=false;
                 
                else {
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lbl_image.getWidth(),lbl_image.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lbl_image.setIcon(newImage);}
            }
         
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
       
             if (tem==false)
       lbl_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
        RepaintPanel(EditMusic);
        
        }
    }//GEN-LAST:event_EditActionPerformed

    private void dureeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dureeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dureeFieldActionPerformed

    private void jLabel21MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseMoved
        // TODO add your handling code here:
         jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChangeson.jpg")));
    }//GEN-LAST:event_jLabel21MouseMoved

    private void jLabel21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseExited
        // TODO add your handling code here:
           jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg")));
    }//GEN-LAST:event_jLabel21MouseExited

    private void dureeField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dureeField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dureeField1ActionPerformed

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        // TODO add your handling code here:
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addmus.jpg")));
    }//GEN-LAST:event_jLabel33MouseExited

    private void jLabel33MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseMoved
        // TODO add your handling code here:
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addmuson.jpg")));
    }//GEN-LAST:event_jLabel33MouseMoved

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        Fillcombo();
        RepaintPanel(AddMusic);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
      int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this music","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          Music my_chanson = new Music("","","");
          try{
          my_chanson.setTitre_chan(titreField1.getText());
          my_chanson.setAuteur_chan(jComboBox3.getSelectedItem().toString());
        my_chanson.setDuree_chan(dureeField1.getText());
         
        String requete="insert into chanson(titre_chan, auteur_chan, duree_chan)VALUES('"+my_chanson.getTitre_chan()+"','"+my_chanson.getAuteur_chan()+"','"+my_chanson.getDuree_chan()+"')";
        
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe music has been added sucessfully !");
        }catch(Exception ex){ JOptionPane.showMessageDialog(null,"\nThe music has NOT been saved ! Make sure all fields are correctly filled !");} 
      }
      else if(reponse==JOptionPane.NO_OPTION)
      {
          JOptionPane.showMessageDialog(null,"\nThe music has NOT been saved !");
      }
      else if (reponse==JOptionPane.CLOSED_OPTION)
      {
           JOptionPane.showMessageDialog(null,"\nThe music has NOT been saved !");
      }
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        DisplayListArtist();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        // TODO add your handling code here:
              
    if(txt_nom_art.getText().trim().isEmpty() || txt_prenom_art.getText().trim().isEmpty()
            || nif1.getText().trim().isEmpty() || nif2.getText().trim().isEmpty()
            || nif3.getText().trim().isEmpty() || nif4.getText().trim().isEmpty() ||
            
             nif1.getText().length()<3 || nif2.getText().length()<3
            || nif3.getText().length()<3 || nif4.getText().length()<1
            )
     JOptionPane.showMessageDialog(null,"\nThe artist cannot be saved ! Make sure all fields are correctly filled !");
     else{  
        int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this artist","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    
         if(reponse==JOptionPane.YES_OPTION)
      {
          Artist my_musicien =new Artist("", "", "", "","");
          
        my_musicien.setNom(txt_nom_art.getText());
        my_musicien.setPreNom(txt_prenom_art.getText());
        my_musicien.setAdresse(txt_address_art.getText());
        my_musicien.setPhone(txt_phone_art.getText());
        String NIF1p_art=nif1.getText();
        String NIF2p_art=nif2.getText();
        String NIF3p_art=nif3.getText();
        String NIF4p_art=nif4.getText();
        my_musicien.setNIF(NIF1p_art + NIF2p_art + NIF3p_art + NIF4p_art);
        try{my_musicien.setNom(txt_nom_art.getText());
        my_musicien.setPreNom(txt_prenom_art.getText());
            String requete="insert into musicien(NIF_mus, nom_mus, prenom_mus, adresse_mus, phone_mus)VALUES('"+my_musicien.getNIF()+"','"+my_musicien.getNom()+"','"+my_musicien.getPreNom()+"','"+my_musicien.getAdresse()+"','"+my_musicien.getPhone()+"')";  
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe artist has been added sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
      else if(reponse==JOptionPane.NO_OPTION)
      {
          JOptionPane.showMessageDialog(null,"\nThe artist has NOT been saved !");
      }
      else if (reponse==JOptionPane.CLOSED_OPTION)
      {
           JOptionPane.showMessageDialog(null,"\nThe artist has NOT been saved !");
      }
     }         
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited
        // TODO add your handling code here:
         jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addartist.jpg")));
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel44MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseMoved
        // TODO add your handling code here:
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addartiston.jpg")));
    }//GEN-LAST:event_jLabel44MouseMoved

    private void nif1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nif1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nif1ActionPerformed

    private void txt_prenom_artActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_prenom_artActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_prenom_artActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here
        RepaintPanel(AddArtist);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void nif2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nif2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nif2ActionPerformed

    private void nif3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nif3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nif3ActionPerformed

    private void nif4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nif4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nif4ActionPerformed

    private void nif1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nif1KeyTyped
        // TODO add your handling code here:
        if(nif1.getText().length()>=3){
            evt.consume();
            nif2.requestFocus();
        }
    }//GEN-LAST:event_nif1KeyTyped

    private void nif2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nif2KeyTyped
        // TODO add your handling code here:
         if(nif2.getText().length()>=3){
            evt.consume();
            nif3.requestFocus();
        }
    }//GEN-LAST:event_nif2KeyTyped

    private void nif3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nif3KeyTyped
        // TODO add your handling code here:
         if(nif3.getText().length()>=3){
            evt.consume();
              nif4.requestFocus();
        }
    }//GEN-LAST:event_nif3KeyTyped

    private void nif4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nif4KeyTyped
        // TODO add your handling code here:
         if(nif4.getText().length()>=1){
            evt.consume();
              txt_address_art.requestFocus();
        }
    }//GEN-LAST:event_nif4KeyTyped

    private void editArtistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editArtistActionPerformed
        // TODO add your handling code here:
          int index = tble1.getSelectedRow();
       
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel modelA= tble1.getModel();
        
           artistGlob.setNIF(modelA.getValueAt(index, 0).toString());
           artistGlob.setNom(modelA.getValueAt(index, 1).toString()); 
           artistGlob.setPreNom(modelA.getValueAt(index,2).toString()) ;
           artistGlob.setAdresse(modelA.getValueAt(index,3).toString());
           artistGlob.setPhone(modelA.getValueAt(index,4).toString());
           
           String NIFp1=artistGlob.getNIF().substring(0,3);
           String NIFp2=artistGlob.getNIF().substring(3,6);
           String NIFp3=artistGlob.getNIF().substring(6,9);
           String NIFp4=artistGlob.getNIF().substring(9,10);
      
        txt_nom_art1.setText(artistGlob.getNom());
        txt_prenom_art12.setText(artistGlob.getPreNom());
        txt_address_art1.setText(artistGlob.getAdresse());
        txt_phone_art1.setText(artistGlob.getPhone());
        txt_NIF_1p1.setText(NIFp1);
        txt_NIF_2p1.setText(NIFp2);
        txt_NIF_3p1.setText(NIFp3);
        txt_NIF_4p1.setText(NIFp4);
       
         
        
           //NAP AFICHE PHOTO A
        boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_mus from musicien where NIF_mus='"+artistGlob.getNIF()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_mus");
                
                 if(img==null) 
                     tem=false;
                 
                else{
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lbl_image2.getWidth(),lbl_image2.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lbl_image2.setIcon(newImage);}
            }
              if (tem==false)
       lbl_image2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        
          RepaintPanel(EditArtist); 
     }   
    }//GEN-LAST:event_editArtistActionPerformed

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        // TODO add your handling code here:
            int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save the changes","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          Artist my_musicien =new Artist("", "", "", "","");
          
        my_musicien.setNom(txt_nom_art1.getText());
        my_musicien.setPreNom(txt_prenom_art12.getText());
        my_musicien.setAdresse(txt_address_art1.getText());
        my_musicien.setPhone(txt_phone_art1.getText());
        String NIF1p_art=txt_NIF_1p1.getText();
        String NIF2p_art=txt_NIF_2p1.getText();
        String NIF3p_art=txt_NIF_3p1.getText();
        String NIF4p_art=txt_NIF_4p1.getText();
        my_musicien.setNIF(NIF1p_art + NIF2p_art + NIF3p_art + NIF4p_art);
        //NOU PRAL UPDATE KOTE NIF LA = NIF KI TE NAN LIST LA POU SI MOUN NAN TE MODIFYE L
        TableModel modelA= tble1.getModel();
         int index = tble1.getSelectedRow();
        String NIF_Autentif=modelA.getValueAt(index, 0).toString();
        //NAP RETIRE KEY YO ??
        
        //NOU PRAL CHANJE NIF LA NAN PLAY DABOR KOXZ SE YON KLE ETRANGERE
   
        String requete=" UPDATE  musicien SET NIF_mus='"+my_musicien.getNIF()+"',nom_mus='"+my_musicien.getNom()+"',prenom_mus='"+my_musicien.getPreNom()+"',adresse_mus='"+my_musicien.getAdresse()+"',phone_mus='"+my_musicien.getPhone()+"'WHERE NIF_mus="+NIF_Autentif+"";   
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe artist has been edited sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
      }      
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
        // TODO add your handling code here:
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg")));
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel54MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseMoved
        // TODO add your handling code here:
         jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChangeson.jpg")));
    }//GEN-LAST:event_jLabel54MouseMoved

    private void txt_prenom_art12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_prenom_art12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_prenom_art12ActionPerformed

    private void txt_NIF_1p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NIF_1p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NIF_1p1ActionPerformed

    private void txt_NIF_2p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NIF_2p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NIF_2p1ActionPerformed

    private void txt_NIF_3p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NIF_3p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NIF_3p1ActionPerformed

    private void txt_NIF_4p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NIF_4p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NIF_4p1ActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        // TODO add your handling code here:
            int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to delete this music","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tble.getSelectedRow();
        TableModel model= tble.getModel();
        String ID = model.getValueAt(index, 0).toString();
 
        String requete1="DELETE FROM play WHERE id_chan_play="+ID+" ";
        try{
        stm.executeUpdate(requete1);
       System.out.println("\nThe music has been delete sucessfully FRIOM PLAY !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
         String requete="DELETE FROM chanson WHERE id_chan="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe music has been delete sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
  
        DisplayListMusic();    
         
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void deleteartistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteartistActionPerformed
        // TODO add your handling code here:
        int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to delete this artist","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tble1.getSelectedRow();
        TableModel model= tble1.getModel();
        String ID = model.getValueAt(index, 0).toString();
                 
     
        String requete1="DELETE FROM play WHERE NIF_mus_play="+ID+" ";
        try{
        stm.executeUpdate(requete1);
       System.out.println("\nThe artist has been delete sucessfully FRIOM PLAY !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
         String requete="DELETE FROM musicien WHERE NIF_mus="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe artist has been delete sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
        DisplayListArtist();
      }  
    }//GEN-LAST:event_deleteartistActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        // TODO add your handling code here:
       int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to remove this artist from the list","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = jTable1.getSelectedRow();
        TableModel modelA= jTable1.getModel();
        String NIF = modelA.getValueAt(index, 0).toString();
               
       String requete="DELETE FROM play WHERE NIF_mus_play ="+NIF+" and id_chan_play ="+chansonGlob.getId_chan()+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe music has been removed from the list sucessfully !");

        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
   //   REFRESH TI TABLO A
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("NIF");
       model2.addColumn("Last name");
       model2.addColumn("First name");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from  musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable1.setModel(model2);
        
       
       //   REFRESH TI COMBOX FT A
        try{
            stm=conn.obtenirconnection().createStatement();
       Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (Select NIF_mus from musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play )");
           jComboBox2.removeAllItems();
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox2.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
       
        RepaintPanel(EditMusic);
      
      }
    }//GEN-LAST:event_RemoveActionPerformed

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:        
        
        //RANJE SA : LEU COMBO BOX LA VID LI BAY ERROR NAN CONSOLE LA
          if(jComboBox1.getSelectedItem().toString().substring(jComboBox1.getSelectedItem().toString().length()-10).equals(jComboBox2.getSelectedItem().toString().substring(jComboBox2.getSelectedItem().toString().length()-10))){
          JOptionPane.showMessageDialog(null,"\nThis artist is already the author of that song !");
          System.out.println(jComboBox1.getSelectedItem().toString()+"...."+jComboBox2.getSelectedItem().toString());
      }
      
      //SI PA GEN PROBLLEM
      else{
          System.out.println(jComboBox1.getSelectedItem().toString()+"...."+jComboBox2.getSelectedItem().toString());
          
              
         int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this artist","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
            int index = tble.getSelectedRow();
        Music my_chanson=new Music("","","");
        TableModel model= tble.getModel();
        DefaultTableModel modelA=new DefaultTableModel();  
          modelA.addColumn("NIF");
       modelA.addColumn("Last name");
       modelA.addColumn("First name");
       
       
           my_chanson.setID_chan(model.getValueAt(index, 0).toString());
         String ID2=jComboBox2.getSelectedItem().toString().substring(jComboBox2.getSelectedItem().toString().length()-10);
        String requete="insert into play (NIF_mus_play, id_chan_play)VALUES('"+ID2+"','"+my_chanson.getId_chan()+"')";
        try{
        stm.executeUpdate(requete);
        //NAP TOU UPDATE TI TABLO A
        try{
           modelA.setRowCount(0);
           stm=conn.obtenirconnection().createStatement();
           
            Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien ,Play where ID_chan_play='"+my_chanson.getId_chan()+"' and NIF_mus=NIF_mus_play");
           while(Rs.next()){
               modelA.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}
       jTable1.setModel(modelA);
        JOptionPane.showMessageDialog(null,"\nThe featuring artist has been added succesfully !");
        
        //NAP UPDATE COMBO FEATURING NAN TOU
        jComboBox2.removeAllItems();
        try{
        Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (Select NIF_mus from musicien ,Play where ID_chan_play='"+my_chanson.getId_chan()+"' and NIF_mus=NIF_mus_play )");
           jComboBox2.addItem(" ");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox2.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
 
           //NAP UPDATE COMBO PRINCIPALE LA TOU POU ATIS KI SOT FEAT LA PA KA PRINCIPAL ANKO
        String recup=jComboBox1.getSelectedItem().toString();
        jComboBox1.removeAllItems();
        
        try{
        Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (select right (concat('0000000000',NIF_mus),10) as NIF_mus from  musicien ,Play where ID_chan_play='"+my_chanson.getId_chan()+"' and NIF_mus=NIF_mus_play ) order by nom_mus asc");
           jComboBox1.addItem(recup);
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox1.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
 
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
      
      }
      
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseExited
        // TODO add your handling code here:
        jLabel24.setFont(new Font("Tahoma", Font.PLAIN,11));
    }//GEN-LAST:event_jLabel24MouseExited

    private void jLabel24MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseMoved
        // TODO add your handling code here:
        jLabel24.setFont(new Font("Tahoma", Font.BOLD,11));
    }//GEN-LAST:event_jLabel24MouseMoved

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
                // TODO add your handling code here:
      int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this music","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tble.getSelectedRow();
        TableModel model= tble.getModel();
        String ID = model.getValueAt(index, 0).toString();
                 
          Music my_chanson = new Music("","","");
          my_chanson.setTitre_chan(titreField.getText());
        my_chanson.setAuteur_chan(jComboBox1.getSelectedItem().toString());
        my_chanson.setDuree_chan(dureeField.getText());
        String requete=" UPDATE  chanson SET titre_chan='"+my_chanson.getTitre_chan()+"',auteur_chan='"+my_chanson.getAuteur_chan()+"',duree_chan='"+my_chanson.getDuree_chan()+"'WHERE id_chan="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe music has been edited sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
     
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseClicked
        // TODO add your handling code here:
      int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this album","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          Album my_album = new Album("","","","","");
          try{
          my_album.setTitre_alb(titreField2.getText());
         my_album.setFormat_alb(newcomb.getSelectedItem().toString()); 
          my_album.setMusicien_alb(jComboBox4.getSelectedItem().toString().substring(jComboBox4.getSelectedItem().toString().length()-10));
        my_album.setDate_Lanc_alb(dateChooserCombo1.getText());
        String requete="insert into album (titre_alb, format_alb, date_lanc,NIF_mus)VALUES('"+my_album.getTitre_alb()+"','"+my_album.getFormat_alb()+"','"+my_album.getDatee_lanc_alb()+"','"+my_album.getNIF_musicien_alb()+"')";
        
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe album has been added sucessfully !");
        }catch(Exception ex){ JOptionPane.showMessageDialog(null,"\nThe album has NOT been saved ! Make sure all fields are correctly filled !");} 
      }
      
      else if (reponse==JOptionPane.CLOSED_OPTION)
      {
           JOptionPane.showMessageDialog(null,"\nThe album has NOT been saved !");
      }

    }//GEN-LAST:event_jLabel74MouseClicked

    private void jLabel74MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseExited
        // TODO add your handling code here:
          jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addalbum.jpg")));
    }//GEN-LAST:event_jLabel74MouseExited

    private void jLabel74MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseMoved
        // TODO add your handling code here:
         jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addalbumon.jpg")));
    }//GEN-LAST:event_jLabel74MouseMoved

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved
        // TODO add your handling code here:
         jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+on.jpg")));
    }//GEN-LAST:event_jLabel3MouseMoved

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
             jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg")));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        FillcomboAlbum();
        RepaintPanel(AddAlbum);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        DisplayListAlbum();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel87MouseClicked
        // TODO add your handling code here:
              int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save the changes","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          Album my_album =new Album("", "", "", "","");
          
        my_album.setTitre_alb(titreField5.getText());
        my_album.setFormat_alb(combobo.getSelectedItem().toString());
        my_album.setDate_Lanc_alb(dateChooserCombo2.getText());
        my_album.setMusicien_alb(jComboBox7.getSelectedItem().toString());
        //NOU PRAL UPDATE KOTE NIF LA = NIF KI TE NAN LIST LA POU SI MOUN NAN TE MODIFYE L
        TableModel modelA= tble2.getModel();
         int index = tble2.getSelectedRow();
        my_album.setID_alb(modelA.getValueAt(index, 0).toString());
        //NAP RETIRE KEY YO ??
        
        //NOU PRAL CHANJE NIF LA NAN PLAY DABOR KOXZ SE YON KLE ETRANGERE
        System.out.println(my_album.getDatee_lanc_alb()+".....");
        String requete=" UPDATE  album SET titre_alb='"+my_album.getTitre_alb()+"',format_alb='"+my_album.getFormat_alb()+"',date_lanc='"+my_album.getDatee_lanc_alb()+"',NIF_mus='"+my_album.getNIF_musicien_alb().substring(my_album.getNIF_musicien_alb().length()-10)+"'WHERE id_alb="+my_album.getID_Album()+"";   
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe album has been edited sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
      }     
    }//GEN-LAST:event_jLabel87MouseClicked

    private void jLabel87MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel87MouseExited
        // TODO add your handling code here:
         jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg")));
    }//GEN-LAST:event_jLabel87MouseExited

    private void jLabel87MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel87MouseMoved
        // TODO add your handling code here:
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChangeson.jpg")));
    }//GEN-LAST:event_jLabel87MouseMoved

    private void jLabel90MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel90MouseClicked
        // TODO add your handling code here:           
         int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to add this music to the album","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
            int index = tble2.getSelectedRow();
        Album my_album=new Album("","","","","");
        TableModel model= tble2.getModel();
        my_album.setID_alb(model.getValueAt(index, 0).toString());
        Music my_music=new Music("","","");
        my_music.setID_chan(jComboBox6.getSelectedItem().toString().substring(jComboBox6.getSelectedItem().toString().length()-4));

        String requete="UPDATE  chanson SET id_alb='"+my_album.getID_Album()+"'WHERE id_chan="+my_music.getId_chan()+"";
        try{
        stm.executeUpdate(requete); 
        JOptionPane.showMessageDialog(null,"\nThe featuring artist has been added succesfully !");  
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
      
        
        try{
          jComboBox6.removeAllItems();
           Rs=stm.executeQuery(" select titre_chan,id_chan from chanson where ID_alb is null");
           while(Rs.next()){
               String titre_chan=Rs.getString("titre_chan");
               String id_chan=Rs.getString("id_chan");
               jComboBox6.addItem(titre_chan+"    "+id_chan);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
        
          //..........TABLE POUR ALBUM ........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("ID");
       model2.addColumn("Title");
       model2.addColumn("Author");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select id_chan, titre_chan, auteur_chan from  chanson where ID_alb='"+albumGlob.getID_Album()+"' ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable2.setModel(model2);
  
        RepaintPanel(EditAlbum);
        
      }  
      
    }//GEN-LAST:event_jLabel90MouseClicked

    private void jLabel90MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel90MouseExited
        // TODO add your handling code here:
         jLabel90.setFont(new Font("Tahoma", Font.PLAIN,11));
    }//GEN-LAST:event_jLabel90MouseExited

    private void jLabel90MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel90MouseMoved
        // TODO add your handling code here:
        jLabel90.setFont(new Font("Tahoma", Font.BOLD,11));
    }//GEN-LAST:event_jLabel90MouseMoved

    private void editAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAlbumActionPerformed
        // TODO add your handling code here:
                  int index = tble2.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble2.getModel();
        
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        albumGlob.setID_alb(model.getValueAt(index, 0).toString());
           albumGlob.setTitre_alb(model.getValueAt(index, 1).toString()); 
           albumGlob.setFormat_alb(model.getValueAt(index,2).toString()) ;
           albumGlob.setDate_Lanc_alb(model.getValueAt(index,3).toString());
           albumGlob.setMusicien_alb(model.getValueAt(index,4).toString());
             
        
         
        titreField5.setText(albumGlob.getTitre_alb());
        combobo.addItem(albumGlob.getFormat_alb());
        dateChooserCombo2.setText(albumGlob.getDatee_lanc_alb());
        //ANN AFICHE NOM ET PRENOM PROMOTEUR ALBUM NAN
        
        
        //.....nap recuepere tout nom yo epi afiche sal te klike a an premye
         try
        {
            jComboBox7.removeAllItems();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus='"+albumGlob.getNIF_musicien_alb()+"'");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox7.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);               
           } Rs.close();
            
           //NAP RANPLI PREMYE COMBOBOX LA
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien order by nom_mus asc ");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox7.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);               
           } Rs.close();
          
        jComboBox6.removeAllItems();
           Rs=stm.executeQuery(" select titre_chan,id_chan from chanson where ID_alb is null");
           while(Rs.next()){
               String titre_chan=Rs.getString("titre_chan");
               String id_chan=Rs.getString("id_chan");
               jComboBox6.addItem(titre_chan+"    "+id_chan);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);
        }
     //..........TABLE POUR MUSIC ........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("ID");
       model2.addColumn("Title");
       model2.addColumn("Author");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select id_chan, titre_chan, auteur_chan from  chanson where ID_alb='"+albumGlob.getID_Album()+"' ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable2.setModel(model2);
  
       
        //NAP AFICHE PHOTO A
        boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_alb from album where ID_alb='"+albumGlob.getID_Album()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_alb");
                
                 if(img==null) 
                     tem=false;
                 
                else if(img!=null){
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lbl_image1.getWidth(),lbl_image1.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lbl_image1.setIcon(newImage);}
            }
              if (tem==false)
       lbl_image1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
       
        RepaintPanel(EditAlbum);
        
        }
    }//GEN-LAST:event_editAlbumActionPerformed

    private void RemALbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemALbActionPerformed
        // TODO add your handling code here:
           int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to remove this music from the album","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = jTable2.getSelectedRow();
        TableModel modelA= jTable2.getModel();
        String IDMusic = modelA.getValueAt(index, 0).toString();
               
       String requete= "UPDATE chanson SET id_alb=null WHERE id_chan ="+IDMusic+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe music has been removed from the album sucessfully !");

        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
   //..........TABLE POUR MUSIC ........   
   Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("ID");
       model2.addColumn("Title");
       model2.addColumn("Author");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select id_chan, titre_chan, auteur_chan from  chanson where ID_alb='"+albumGlob.getID_Album()+"' ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable2.setModel(model2);
        
              try{
        jComboBox6.removeAllItems();
                   stm=conn.obtenirconnection().createStatement();

           Rs=stm.executeQuery(" select titre_chan,id_chan from chanson where ID_alb is null");
           while(Rs.next()){
               String titre_chan=Rs.getString("titre_chan");
               String id_chan=Rs.getString("id_chan");
               jComboBox6.addItem(titre_chan+"    "+id_chan);  
           }Rs.close();
                      
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
       
        RepaintPanel(EditAlbum);
      
      }
        
    }//GEN-LAST:event_RemALbActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
         DefaultTableModel model=new DefaultTableModel();
         model.setRowCount(0);
         model.addColumn("ID");
         model.addColumn("TITLE");
            model.addColumn("AUTHOR");
            model.addColumn("DURATION");
              model.addColumn("SAVED DATE");
            try{       
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from chanson where titre_chan like '"+jTextField2.getText()+'%'+"'  order by titre_chan asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan"),Rs.getString("duree_chan"),Rs.getString("date_enr_chan")});
           }
       }catch (Exception e){System.err.println(e);} 
       tble.setModel(model);
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jLabel81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseClicked
        // TODO add your handling code here:
        try{
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);  
        File f=chooser.getSelectedFile();
        
        String filename=f.getAbsolutePath();
    path.setText(filename);
       if(path.getText().toString().substring(path.getText().toString().length()-3).compareToIgnoreCase("png")==0 || path.getText().toString().substring(path.getText().toString().length()-3).compareToIgnoreCase("jpg")==0 
                || path.getText().toString().substring(path.getText().toString().length()-3).compareToIgnoreCase("ico")==0  )
       {
           ImageIcon icon= new ImageIcon(filename);
       
         Image image= icon.getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
         ImageIcon newImage= new ImageIcon(image);
         lbl_image.setIcon(newImage);
       }
     
        }catch (Exception e){ }
        
       
    }//GEN-LAST:event_jLabel81MouseClicked

    private void jLabel82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseClicked
        // TODO add your handling code here:  
        int index = tble.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble.getModel();
        chansonGlob.setID_chan(model.getValueAt(index, 0).toString());
        }

        String s=path.getText();
        if(s.toString().substring(s.toString().length()-3).compareToIgnoreCase("png")==0 || s.toString().substring(s.toString().length()-3).compareToIgnoreCase("jpg")==0 
                || s.toString().substring(s.toString().length()-3).compareToIgnoreCase("ico")==0  )
        {
            try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            PreparedStatement ps= con.prepareStatement("update chanson set image_chan=? where id_chan='"+chansonGlob.getId_chan()+"'");
            InputStream is=new FileInputStream(new File(s));
            ps.setBlob(1,is);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Done");
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        
        }
    }//GEN-LAST:event_jLabel82MouseClicked

    private void tbleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleMouseClicked
        // TODO add your handling code here:     
        //NAP RECUPERER DONNE SOU LIGNE LI KLIKE A POUN KA AFICHE FOTO A
          //NAP AFICHE TEXT KOT FOTO A
         int index = tble.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
            //nap afiche buton info a
            
           
        TableModel model= tble.getModel();
        
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        chansonGlob.setID_chan(model.getValueAt(index, 0).toString());
           chansonGlob.setTitre_chan(model.getValueAt(index, 1).toString()); 
           chansonGlob.setAuteur_chan(model.getValueAt(index,2).toString()) ;
           chansonGlob.setDuree_chan(model.getValueAt(index,3).toString());
        
         
        afftitle.setText(chansonGlob.getTitre_chan());
         affartist.setText(chansonGlob.getAuteur_chan());
         affduration.setText(chansonGlob.getDuree_chan());
        }
        boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_chan from chanson where id_chan='"+chansonGlob.getId_chan()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_chan");
                
                 if(img==null) 
                     tem=false;
                 
                else {
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lb.getWidth(),lb.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lb.setIcon(newImage);}
            }
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        
   if (tem==false)
       lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
     
    }//GEN-LAST:event_tbleMouseClicked

    private void jLabel100MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseClicked
        // TODO add your handling code here:
       OrderMusicBy("id_chan");
    }//GEN-LAST:event_jLabel100MouseClicked

    private void jLabel100MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseMoved
        // TODO add your handling code here:
          jLabel100.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel100MouseMoved

    private void jLabel100MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseExited
        // TODO add your handling code here:
        jLabel100.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel100MouseExited

    private void jLabel101MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseClicked
        // TODO add your handling code here:
        OrderMusicBy("titre_chan");
    }//GEN-LAST:event_jLabel101MouseClicked

    private void jLabel101MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseExited
        // TODO add your handling code here:
         jLabel101.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel101MouseExited

    private void jLabel101MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel101MouseMoved
        // TODO add your handling code here:
         jLabel101.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel101MouseMoved

    private void jLabel102MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseClicked
        // TODO add your handling code here:
        OrderMusicBy("auteur_chan");
    }//GEN-LAST:event_jLabel102MouseClicked

    private void jLabel102MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseExited
        // TODO add your handling code here:
          jLabel102.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel102MouseExited

    private void jLabel102MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel102MouseMoved
        // TODO add your handling code here:
         jLabel102.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel102MouseMoved

    private void jLabel103MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel103MouseClicked
        // TODO add your handling code here:
        OrderMusicBy("duree_chan");
    }//GEN-LAST:event_jLabel103MouseClicked

    private void jLabel103MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel103MouseExited
        // TODO add your handling code here:
        jLabel103.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel103MouseExited

    private void jLabel103MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel103MouseMoved
        // TODO add your handling code here:
         jLabel103.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel103MouseMoved

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
            DefaultTableModel model=new DefaultTableModel();
         model.setRowCount(0);
         model.addColumn("NIF");
         model.addColumn("LAST NAME");
            model.addColumn("FIRST NAME");
            model.addColumn("ADDRESS");
            model.addColumn("PHONE");
            model.addColumn("SAVED DATE");
            try{       
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus,adresse_mus, phone_mus,date_enr_mus from musicien where nom_mus like '"+jTextField3.getText()+'%'+"'  order by nom_mus asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("Prenom_mus"),Rs.getString("adresse_mus"),Rs.getString("phone_mus"),Rs.getString("date_enr_mus")});
           }
       }catch (Exception e){System.err.println(e);}
       tble1.setModel(model);
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
           DefaultTableModel model=new DefaultTableModel();
         model.setRowCount(0);
         model.addColumn("ID");
         model.addColumn("TITLE");
            model.addColumn("FORMAT");
            model.addColumn("ENTRY DATE");
            model.addColumn("AUTHOR");
            model.addColumn("SAVED DATE");
            try{       
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from album where titre_alb like '"+jTextField4.getText()+'%'+"'  order by titre_alb asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_alb"),Rs.getString("titre_alb"),
               Rs.getString("format_alb"),Rs.getString("date_lanc"),Rs.getString("NIF_mus"),Rs.getString("date_enr_alb")});
           }
       }catch (Exception e){System.err.println(e);} 
       tble2.setModel(model);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jLabel110MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel110MouseClicked
        // TODO add your handling code here:
        OrderAlbumBy("id_alb");
    }//GEN-LAST:event_jLabel110MouseClicked

    private void jLabel110MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel110MouseExited
        // TODO add your handling code here:
         jLabel110.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel110MouseExited

    private void jLabel110MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel110MouseMoved
        // TODO add your handling code here:
          jLabel110.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel110MouseMoved

    private void jLabel111MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel111MouseClicked
        // TODO add your handling code here:
        OrderAlbumBy("titre_alb");
    }//GEN-LAST:event_jLabel111MouseClicked

    private void jLabel111MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel111MouseExited
        // TODO add your handling code here:
          jLabel111.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel111MouseExited

    private void jLabel111MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel111MouseMoved
        // TODO add your handling code here:
         jLabel111.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel111MouseMoved

    private void jLabel112MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel112MouseClicked
        // TODO add your handling code here:
        OrderAlbumBy("format_alb");
    }//GEN-LAST:event_jLabel112MouseClicked

    private void jLabel112MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel112MouseExited
        // TODO add your handling code here:
          jLabel112.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel112MouseExited

    private void jLabel112MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel112MouseMoved
        // TODO add your handling code here:
         jLabel112.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel112MouseMoved

    private void jLabel113MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel113MouseClicked
        // TODO add your handling code here:
        OrderAlbumBy("date_lanc");
    }//GEN-LAST:event_jLabel113MouseClicked

    private void jLabel113MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel113MouseExited
        // TODO add your handling code here:
          jLabel113.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel113MouseExited

    private void jLabel113MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel113MouseMoved
        // TODO add your handling code here:
         jLabel113.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel113MouseMoved

    private void tble2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble2MouseClicked
        // TODO add your handling code here:
          //NAP RECUPERER DONNE SOU LIGNE LI KLIKE A POUN KA AFICHE FOTO A
          //NAP AFICHE TEXT KOT FOTO A
         int index = tble2.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble2.getModel();
        
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        albumGlob.setID_alb(model.getValueAt(index, 0).toString());
           albumGlob.setTitre_alb(model.getValueAt(index, 1).toString()); 
           albumGlob.setFormat_alb(model.getValueAt(index,2).toString()) ;
           albumGlob.setDate_Lanc_alb(model.getValueAt(index,3).toString());
           albumGlob.setMusicien_alb(model.getValueAt(index,4).toString());
        
         
        afftitle1.setText(albumGlob.getTitre_alb());
         affartist1.setText(albumGlob.getNIF_musicien_alb());
        }
        boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_alb from album where id_alb='"+albumGlob.getID_Album()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_alb");
                
                 if(img==null) 
                     tem=false;
                
                 
                else{
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lb1.getWidth(),lb1.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lb1.setIcon(newImage);}
            }
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        
   if (tem==false)
       lb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
     
        
    }//GEN-LAST:event_tble2MouseClicked

    private void jLabel108MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel108MouseClicked
        // TODO add your handling code here:
        int index = tble2.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble2.getModel();
        albumGlob.setID_alb(model.getValueAt(index, 0).toString());
        }

        String s=path1.getText();
         if(s.substring(s.length()-3).compareToIgnoreCase("png")==0 || s.substring(s.length()-3).compareToIgnoreCase("jpg")==0 
                || s.substring(s.length()-3).compareToIgnoreCase("ico")==0  )
        {
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            PreparedStatement ps= con.prepareStatement("update album set image_alb=? where id_alb='"+albumGlob.getID_Album()+"'");
            InputStream is=new FileInputStream(new File(s));
            ps.setBlob(1,is);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Done");
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        }
    }//GEN-LAST:event_jLabel108MouseClicked

    private void jLabel114MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel114MouseClicked
        // TODO add your handling code here:
        try{
           JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);  
        File f=chooser.getSelectedFile();
        String filename=f.getAbsolutePath();
    path1.setText(filename);
    if(path1.getText().substring(path1.getText().length()-3).compareToIgnoreCase("png")==0 || path1.getText().substring(path1.getText().length()-3).compareToIgnoreCase("jpg")==0 
                || path1.getText().substring(path1.getText().length()-3).compareToIgnoreCase("ico")==0  )
       {
         ImageIcon icon= new ImageIcon(filename);
         Image image= icon.getImage().getScaledInstance(lbl_image1.getWidth(), lbl_image1.getHeight(), Image.SCALE_SMOOTH);
         ImageIcon newImage= new ImageIcon(image);
         lbl_image1.setIcon(newImage);
       }
        }catch (Exception e){ }
        
    }//GEN-LAST:event_jLabel114MouseClicked

    private void jLabel120MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel120MouseClicked
        // TODO add your handling code here:
        OrderArtistBy("NIF_mus");
    }//GEN-LAST:event_jLabel120MouseClicked

    private void jLabel120MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel120MouseExited
        // TODO add your handling code here:
        jLabel120.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel120MouseExited

    private void jLabel120MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel120MouseMoved
        // TODO add your handling code here:
          jLabel120.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel120MouseMoved

    private void jLabel121MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel121MouseClicked
        // TODO add your handling code here:
        OrderArtistBy("nom_mus");
    }//GEN-LAST:event_jLabel121MouseClicked

    private void jLabel121MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel121MouseExited
        // TODO add your handling code here:
         jLabel121.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel121MouseExited

    private void jLabel121MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel121MouseMoved
        // TODO add your handling code here:
        jLabel121.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel121MouseMoved

    private void jLabel122MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel122MouseClicked
        // TODO add your handling code here:
        OrderArtistBy("prenom_mus");
    }//GEN-LAST:event_jLabel122MouseClicked

    private void jLabel122MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel122MouseExited
        // TODO add your handling code here:
         jLabel122.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel122MouseExited

    private void jLabel122MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel122MouseMoved
        // TODO add your handling code here:
        jLabel122.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel122MouseMoved

    private void jLabel123MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel123MouseClicked
        // TODO add your handling code here:
        OrderArtistBy("adresse_mus ");
    }//GEN-LAST:event_jLabel123MouseClicked

    private void jLabel123MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel123MouseExited
        // TODO add your handling code here:
         jLabel123.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel123MouseExited

    private void jLabel123MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel123MouseMoved
        // TODO add your handling code here:
        jLabel123.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel123MouseMoved

    private void tble1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble1MouseClicked
        // TODO add your handling code here:
         //NAP RECUPERER DONNE SOU LIGNE LI KLIKE A POUN KA AFICHE FOTO A
          //NAP AFICHE TEXT KOT FOTO A
         int index = tble1.getSelectedRow();
        if (tble1.getSelectedRow() !=-1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel modelq= tble1.getModel();
        Artist artistGlobq=new Artist("","","","","");
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        artistGlobq.setNIF(modelq.getValueAt(index, 0).toString());
           artistGlobq.setNom(modelq.getValueAt(index, 1).toString()); 
           artistGlobq.setPreNom(modelq.getValueAt(index,2).toString()) ;
           artistGlobq.setAdresse(modelq.getValueAt(index,3).toString());
        
         
        affNomComplet.setText(artistGlobq.getNom()+" "+artistGlobq.getPreNom());
         affid.setText(artistGlobq.getNIF());
         affaddress.setText(artistGlobq.getAdresse());
         
          boolean tem =true;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            Statement st= con.createStatement();
            ResultSet rs= st.executeQuery("Select image_mus from musicien where NIF_mus='"+artistGlobq.getNIF()+"' ");
            if(rs.next())
            {
                
                byte[] img=rs.getBytes("image_mus");
                
                 if(img==null) 
                     tem=false;
       
                else {
                ImageIcon image =new ImageIcon(img);
                Image im =image.getImage();
                Image myImg = im.getScaledInstance(lb2.getWidth(),lb2.getHeight(),Image.SCALE_SMOOTH);
                ImageIcon newImage= new ImageIcon(myImg);
                lb2.setIcon(newImage);}
            }
            
          
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        
   if (tem==false)
       lb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/logo.jpg")));
        }
       
     
    }//GEN-LAST:event_tble1MouseClicked

    private void jLabel124MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel124MouseClicked
        // TODO add your handling code here:
        OrderArtistBy("phone_mus");
    }//GEN-LAST:event_jLabel124MouseClicked

    private void jLabel124MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel124MouseExited
        // TODO add your handling code here:
         jLabel124.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel124MouseExited

    private void jLabel124MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel124MouseMoved
        // TODO add your handling code here:
        jLabel124.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel124MouseMoved

    private void jLabel117MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel117MouseClicked
        // TODO add your handling code here:
        try{    
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);  
        File f=chooser.getSelectedFile();
        String filename=f.getAbsolutePath();
    path2.setText(filename);
     if(path2.getText().toString().substring(path2.getText().toString().length()-3).compareToIgnoreCase("png")==0 || path2.getText().toString().substring(path2.getText().toString().length()-3).compareToIgnoreCase("jpg")==0 
                || path2.getText().toString().substring(path2.getText().toString().length()-3).compareToIgnoreCase("ico")==0  )
       {
         ImageIcon icon= new ImageIcon(filename);
         Image image= icon.getImage().getScaledInstance(lbl_image2.getWidth(), lbl_image2.getHeight(), Image.SCALE_SMOOTH);
         ImageIcon newImage= new ImageIcon(image);
         lbl_image2.setIcon(newImage);
       }
        }catch(Exception e){}
        
    }//GEN-LAST:event_jLabel117MouseClicked

    private void jLabel118MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel118MouseClicked
        // TODO add your handling code here:
                int index = tble1.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tble1.getModel();
        artistGlob.setNIF(model.getValueAt(index, 0).toString());
        }

        String s=path2.getText();
         if(s.toString().substring(s.toString().length()-3).compareToIgnoreCase("png")==0 || s.toString().substring(s.toString().length()-3).compareToIgnoreCase("jpg")==0 
                || s.toString().substring(s.toString().length()-3).compareToIgnoreCase("ico")==0  )
        {
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/MelomaneRecordz_DLT_VERSION_BETA","root","");
            PreparedStatement ps= con.prepareStatement("update musicien set image_mus=? where NIF_mus='"+artistGlob.getNIF()+"'");
            InputStream is=new FileInputStream(new File(s));
            ps.setBlob(1,is);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Done");
        }catch (Exception e){ 
           JOptionPane.showMessageDialog(null,e);}
        }
    }//GEN-LAST:event_jLabel118MouseClicked

    private void tbleInstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleInstMouseClicked
        // TODO add your handling code here:
                //NAP AFICHE TEXT KOT FOTO A
         int index = tbleInst.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tbleInst.getModel();
        Instrument my_ins=new Instrument("","","");
        
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        my_ins.setId_inst(model.getValueAt(index, 0).toString());
           my_ins.setNom_inst(model.getValueAt(index, 1).toString()); 
           my_ins.setDate_acqui(model.getValueAt(index,2).toString()) ;
 
        
         affNomComplet1.setText(my_ins.getNom_inst());
        affid1.setText(my_ins.getId_inst());
         affaddress1.setText(my_ins.getDate_acqui());
         switch(my_ins.getNom_inst())
         {
             case "Assòtò" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/asoto.jpg")));
                 break;
             case "Banjo" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/banjo.jpg")));
                 break;
             case "Flute" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/flute.jpg")));
                 break;
             case "Guitare" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/guitare.jpg")));
                 break;
             case "Piano" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/piano.jpg")));
                 break;
             case "Synthé" :
                 lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/synthel.jpg")));
                 break;
         }
          
        }
    }//GEN-LAST:event_tbleInstMouseClicked

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:
         DefaultTableModel model=new DefaultTableModel();
         model.setRowCount(0);
         model.addColumn("ID");
         model.addColumn("NAME");
            model.addColumn("PROCURATION DATE");
            model.addColumn("SAVED DATE");
            try{       
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from instrument where nom_inst like '"+jTextField5.getText()+'%'+"'  order by nom_inst asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_inst"),Rs.getString("nom_inst"),
               Rs.getString("date_acqui"),Rs.getString("date_enr_inst")});
           }
       }catch (Exception e){System.err.println(e);}
       tbleInst.setModel(model);
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jLabel130MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseClicked
        // TODO add your handling code here:
        OrderInstumentBy("id_inst");
    }//GEN-LAST:event_jLabel130MouseClicked

    private void jLabel130MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseExited
        // TODO add your handling code here:
          jLabel130.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel130MouseExited

    private void jLabel130MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseMoved
        // TODO add your handling code here:
         jLabel130.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel130MouseMoved

    private void jLabel131MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel131MouseClicked
        // TODO add your handling code here:
         OrderInstumentBy("nom_inst");
    }//GEN-LAST:event_jLabel131MouseClicked

    private void jLabel131MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel131MouseExited
        // TODO add your handling code here:
          jLabel131.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel131MouseExited

    private void jLabel131MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel131MouseMoved
        // TODO add your handling code here:
          jLabel131.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel131MouseMoved

    private void jLabel132MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel132MouseClicked
        // TODO add your handling code here:
        OrderInstumentBy("date_acqui");
    }//GEN-LAST:event_jLabel132MouseClicked

    private void jLabel132MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel132MouseExited
        // TODO add your handling code here:
          jLabel132.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel132MouseExited

    private void jLabel132MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel132MouseMoved
        // TODO add your handling code here:
          jLabel132.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel132MouseMoved

    private void jLabel133MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel133MouseClicked
        // TODO add your handling code here:
        OrderInstumentBy("date_enr_inst");
    }//GEN-LAST:event_jLabel133MouseClicked

    private void jLabel133MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel133MouseExited
        // TODO add your handling code here:
          jLabel133.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel133MouseExited

    private void jLabel133MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel133MouseMoved
        // TODO add your handling code here:
          jLabel133.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel133MouseMoved

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        DisplayListInstrum();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel143MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel143MouseClicked
        // TODO add your handling code here:
            int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this instrument","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
             Instrument my_int = new Instrument("","","");
          my_int.setNom_inst(newcomb1.getSelectedItem().toString());
         my_int.setDate_acqui(dateChooserCombo3.getText()); 
        String requete="insert into instrument (nom_inst , date_acqui )VALUES('"+my_int.getNom_inst()+"','"+my_int.getDate_acqui()+"')";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe instrument has been added sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
    }//GEN-LAST:event_jLabel143MouseClicked

    private void jLabel143MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel143MouseExited
        // TODO add your handling code here:
         jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addinst.jpg")));
    }//GEN-LAST:event_jLabel143MouseExited

    private void jLabel143MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel143MouseMoved
        // TODO add your handling code here:
         jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/addinston.jpg")));
    }//GEN-LAST:event_jLabel143MouseMoved

    private void jLabel8MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseMoved
        // TODO add your handling code here:
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+on.jpg")));
    }//GEN-LAST:event_jLabel8MouseMoved

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // TODO add your handling code here:
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/+.jpg")));
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
         RepaintPanel(AddInstrum);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void deleteAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAlbumActionPerformed
        // TODO add your handling code here:
             int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to delete this album","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tble2.getSelectedRow();
        TableModel model= tble2.getModel();
        String ID = model.getValueAt(index, 0).toString();
                 
     
        String requete1= "UPDATE  chanson SET id_alb=null WHERE id_alb="+ID+"";
       
        try{
        stm.executeUpdate(requete1);
       System.out.println("\nThe artist has been delete sucessfully FRIOM PLAY !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
         String requete="DELETE FROM album WHERE id_alb ="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe album has been delete sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
        DisplayListAlbum();
      }
    }//GEN-LAST:event_deleteAlbumActionPerformed

    private void jLabel137MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel137MouseClicked
        // TODO add your handling code here:
              int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this instrument","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tbleInst.getSelectedRow();
        TableModel model= tbleInst.getModel();
        String ID = model.getValueAt(index, 0).toString();
                 
          Instrument my_inst = new Instrument("","","");
          my_inst.setNom_inst(newcomb2.getSelectedItem().toString());
        my_inst.setDate_acqui(dateChooserCombo5.getText());
        String requete=" UPDATE  instrument SET nom_inst ='"+my_inst.getNom_inst()+"',date_acqui ='"+my_inst.getDate_acqui()+"' WHERE id_inst="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe instrument has been edited sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
    }//GEN-LAST:event_jLabel137MouseClicked

    private void jLabel137MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel137MouseExited
        // TODO add your handling code here:
        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChanges.jpg")));
    }//GEN-LAST:event_jLabel137MouseExited

    private void jLabel137MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel137MouseMoved
        // TODO add your handling code here:
         jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/saveChangeson.jpg")));
    }//GEN-LAST:event_jLabel137MouseMoved

    private void jLabel142MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel142MouseClicked
        // TODO add your handling code here:
             if(jComboBox8.getSelectedItem()!=null){ 
         int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to save this artist in the list","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
            int index = tbleInst.getSelectedRow();
        Instrument my_inst=new Instrument("","","");
        TableModel model= tbleInst.getModel();
        DefaultTableModel modelA=new DefaultTableModel();  
          modelA.addColumn("NIF");
       modelA.addColumn("Last name");
       modelA.addColumn("First name");
       
       
           my_inst.setId_inst(model.getValueAt(index, 0).toString());
        String ID2=jComboBox8.getSelectedItem().toString().substring(jComboBox8.getSelectedItem().toString().length()-10);
        String requete="insert into jouer (NIF_mus_jouer, id_inst_jouer)VALUES('"+ID2+"','"+my_inst.getId_inst()+"')";
        try{
        stm.executeUpdate(requete);
        //NAP TOU UPDATE TI TABLO A
        try{
           modelA.setRowCount(0);
           stm=conn.obtenirconnection().createStatement();
           
            Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien ,jouer where id_inst_jouer='"+my_inst.getId_inst()+"' and NIF_mus=NIF_mus_jouer");
           while(Rs.next()){
               modelA.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}
       jTable3.setModel(modelA);
        JOptionPane.showMessageDialog(null,"\nThe artist has been added succesfully !");
        
        //NAP UPDATE COMBO FEATURING NAN TOU
        jComboBox8.removeAllItems();
        try{
        Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (Select NIF_mus from musicien ,jouer where id_inst_jouer='"+my_inst.getId_inst()+"' and NIF_mus=NIF_mus_jouer )");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox8.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
 

        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());} 
      }
      
             }
      
    }//GEN-LAST:event_jLabel142MouseClicked

    private void jLabel142MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel142MouseExited
        // TODO add your handling code here:
            jLabel142.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel142MouseExited

    private void jLabel142MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel142MouseMoved
        // TODO add your handling code here:
            jLabel142.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel142MouseMoved

    private void editinstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editinstActionPerformed
        // TODO add your handling code here:
                  int index = tbleInst.getSelectedRow();
                  Instrument my_inst=new Instrument("","","");
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
        TableModel model= tbleInst.getModel();
          jComboBox8.removeAllItems();//nap retire tout sa ki nan comboibox yo
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        my_inst.setId_inst(model.getValueAt(index, 0).toString());
           my_inst.setNom_inst(model.getValueAt(index, 1).toString()); 
           my_inst.setDate_acqui(model.getValueAt(index,2).toString()) ;
       newcomb2.removeAllItems();
        newcomb2.addItem(my_inst.getNom_inst());
        newcomb2.addItem("Guitare"); newcomb2.addItem("Banjo"); newcomb2.addItem("Assòtò"); newcomb2.addItem("Synthé");
          newcomb2.addItem("Piano");newcomb2.addItem("Flute");
        dateChooserCombo5.setText(my_inst.getDate_acqui());
        //ANN AFICHE NOM ET PRENOM PROMOTEUR ALBUM NAN
        
        
//        //.....nap recuepere tout nom yo epi afiche sal te klike a an premye
         try
        {    
           //NAP RANPLI PREMYE COMBOBOX LA
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (select right (concat('0000000000',NIF_mus_jouer),10) as NIF_mus_jouer from Jouer where id_inst_jouer='"+my_inst.getId_inst()+"') order by nom_mus asc ");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox8.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);               
           } Rs.close();
                               
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);
        }
     //..........TABLE POUR Artist ........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("NIF");
       model2.addColumn("Last name");
       model2.addColumn("First name");
           
       try{
           stm=conn.obtenirconnection().createStatement();
          Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus  in (select right (concat('0000000000',NIF_mus_jouer),10) as NIF_mus_jouer from Jouer where id_inst_jouer='"+my_inst.getId_inst()+"') order by nom_mus asc ");
             while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable3.setModel(model2);
  
        RepaintPanel(EditInstrum);
        
        }
    }//GEN-LAST:event_editinstActionPerformed

    private void newcomb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newcomb2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newcomb2ActionPerformed

    private void deleteInstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteInstActionPerformed
        // TODO add your handling code here:
           int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to delete this instrument","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = tbleInst.getSelectedRow();
        TableModel model= tbleInst.getModel();
        String ID = model.getValueAt(index, 0).toString();
 
        String requete1="DELETE FROM jouer WHERE id_inst_jouer="+ID+" ";
        try{
        stm.executeUpdate(requete1);
       System.out.println("\nThe instrument has been delete sucessfully FROM jouer !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
         String requete="DELETE FROM instrument WHERE id_inst="+ID+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe instrument has been delete sucessfully !");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
  
        DisplayListInstrum();    
         
      }
    }//GEN-LAST:event_deleteInstActionPerformed

    private void remArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remArtActionPerformed
        // TODO add your handling code here:
              int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to remove this artist from the list","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {
          int index = jTable3.getSelectedRow();
        TableModel modelA= jTable3.getModel();
        String IDArt = modelA.getValueAt(index, 0).toString();
        
        int index2 = tbleInst.getSelectedRow();
        TableModel modelB= tbleInst.getModel();
        String IDInst = modelB.getValueAt(index2, 0).toString();
               
       String requete= "DELETE FROM jouer WHERE NIF_mus_jouer="+IDArt+" AND id_inst_jouer="+IDInst+" ";
        try{
        stm.executeUpdate(requete);
        JOptionPane.showMessageDialog(null,"\nThe artist has been removed from the list sucessfully !");

        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
   //..........TABLE POUR MUSIC ........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel modelp=new DefaultTableModel();
       
       modelp.addColumn("NIF");
       modelp.addColumn("Last name");
       modelp.addColumn("First name");
           
        try{
           modelp.setRowCount(0);
           stm=conn.obtenirconnection().createStatement();
           
            Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien ,jouer where id_inst_jouer='"+IDInst+"' and NIF_mus=NIF_mus_jouer");
           while(Rs.next()){
               modelp.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}
       jTable3.setModel(modelp);

        
       
       //   REFRESH TI COMBOX FT A
      jComboBox8.removeAllItems();
        try{
             stm=conn.obtenirconnection().createStatement();
        Rs=stm.executeQuery(" select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from musicien where NIF_mus not in (Select NIF_mus from musicien ,jouer where id_inst_jouer='"+IDInst+"' and NIF_mus=NIF_mus_jouer )");
           while(Rs.next()){
               String NIF_mus=Rs.getString("NIF_mus");
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               jComboBox8.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);  
           }Rs.close();
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);}
  
        RepaintPanel(EditInstrum);
      
      }
        
    }//GEN-LAST:event_remArtActionPerformed

    private void jLabel134MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseClicked
        // TODO add your handling code here:
        OrderAlbumBy("date_enr_alb");
    }//GEN-LAST:event_jLabel134MouseClicked

    private void jLabel134MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseExited
        // TODO add your handling code here:
          jLabel134.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel134MouseExited

    private void jLabel134MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel134MouseMoved
        // TODO add your handling code here:
         jLabel134.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel134MouseMoved

    private void jLabel9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseMoved
        // TODO add your handling code here:
         jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/settingsOn.jpg")));
    }//GEN-LAST:event_jLabel9MouseMoved

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
           jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/settings.jpg")));
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        DisplaySettings();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel82MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseMoved
        // TODO add your handling code here:
         jLabel82.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel82MouseMoved

    private void jLabel82MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseExited
        // TODO add your handling code here:
         jLabel82.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel82MouseExited

    private void jLabel154MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel154MouseClicked
        // TODO add your handling code here:
        OrderMusicBy("date_enr_chan");
    }//GEN-LAST:event_jLabel154MouseClicked

    private void jLabel154MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel154MouseExited
        // TODO add your handling code here:
        jLabel154.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel154MouseExited

    private void jLabel154MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel154MouseMoved
        // TODO add your handling code here:
        jLabel154.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel154MouseMoved

    private void jLabell155MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabell155MouseClicked
        // TODO add your handling code here:
         OrderArtistBy("date_enr_mus");
    }//GEN-LAST:event_jLabell155MouseClicked

    private void jLabell155MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabell155MouseExited
        // TODO add your handling code here:
        jLabell155.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabell155MouseExited

    private void jLabell155MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabell155MouseMoved
        // TODO add your handling code here:
        jLabell155.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabell155MouseMoved

    private void jLabel118MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel118MouseMoved
        // TODO add your handling code here:
         jLabel118.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel118MouseMoved

    private void jLabel118MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel118MouseExited
        // TODO add your handling code here:
           jLabel118.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel118MouseExited

    private void infomusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infomusicActionPerformed
        // TODO add your handling code here:
          int index = tble.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
          
            
             //..........TABLE POUR ARTIST SECONDAIRES........   
    Statement stm;
    ResultSet Rs;
    DefaultTableModel model2=new DefaultTableModel();
       
       model2.addColumn("NIF");
       model2.addColumn("Last name");
       model2.addColumn("First name");
           
       try{
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus from  musicien ,Play where ID_chan_play='"+chansonGlob.getId_chan()+"' and NIF_mus=NIF_mus_play ");
           while(Rs.next()){
               model2.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("prenom_mus")});
           }
       }catch (Exception e){System.err.println(e);}       
       jTable4.setModel(model2);
        
    
       //ann al deye titre album nan
      TableModel model= tble.getModel();
               try{                    
       stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select titre_alb   from album  where album.id_alb in (Select chanson.id_alb   from chanson  where chanson.id_chan='"+model.getValueAt(index, 0).toString()+"' )");
         String titre_alb="unknown";
                   while(Rs.next()){
                       titre_alb=Rs.getString("titre_alb");
                       }
                   jLabel173.setText(titre_alb);
               }catch (Exception e){System.err.println(e);} 
           
        //NAP SAVE TOUT DONNEES CHANSON A YO NANN YON VARIABLE GLOB
        chansonGlob.setID_chan(model.getValueAt(index, 0).toString());
           chansonGlob.setTitre_chan(model.getValueAt(index, 1).toString()); 
           chansonGlob.setAuteur_chan(model.getValueAt(index,2).toString()) ;
           chansonGlob.setDuree_chan(model.getValueAt(index,3).toString());
        
         jLabel175.setText(chansonGlob.getId_chan());
        jLabel169.setText(chansonGlob.getTitre_chan());
         jLabel170.setText(chansonGlob.getAuteur_chan());
         jLabel171.setText(chansonGlob.getDuree_chan());
         jLabel172.setText(model.getValueAt(index,4).toString());
         
         RepaintPanel(infoMusicpanel);
        }
         
    }//GEN-LAST:event_infomusicActionPerformed

    private void infoartistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoartistActionPerformed
        // TODO add your handling code here:
          int index = tble1.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
    Statement stm;
    ResultSet Rs;
     //nap affiche TOUT music atist sa genyen
      DefaultTableModel model2=new DefaultTableModel();     
       model2.addColumn("Title");
       TableModel model= tble1.getModel();
       
               try{                    
       stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select titre_chan   from chanson  where chanson.id_chan in (Select Play.id_chan_play  from Play  where Play.NIF_mus_play='"+model.getValueAt(index, 0).toString()+"' )");          
                   while(Rs.next()){
                        model2.addRow(new Object[]{Rs.getString("titre_chan")});
                       }
                   Rs=stm.executeQuery("Select titre_chan   from chanson  where chanson.auteur_chan='"+model.getValueAt(index, 1).toString()+" "+model.getValueAt(index, 2).toString()+"          "+model.getValueAt(index, 0).toString()+"' ");
         while(Rs.next()){      
                        model2.addRow(new Object[]{Rs.getString("titre_chan")});
                       }
               }catch (Exception e){System.err.println(e);} 
               jTable5.setModel(model2);
               
            //nap affiche TOUT album atist sa genyen
      DefaultTableModel model3=new DefaultTableModel();     
       model3.addColumn("Title");
 
       
               try{                    
       stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select titre_alb  from album  where NIF_mus ='"+model.getValueAt(index, 0).toString()+"' ");
         
                   while(Rs.next()){
                        model3.addRow(new Object[]{Rs.getString("titre_alb")});
                       }
               }catch (Exception e){System.err.println(e);} 
               jTable6.setModel(model3);
               
                //nap affiche TOUT instrument atist sa genyen
      DefaultTableModel model4=new DefaultTableModel();     
       model4.addColumn("Name");
       model4.addColumn("ID");
    
               try{                    
       stm=conn.obtenirconnection().createStatement();
              Rs=stm.executeQuery("Select nom_inst, id_inst from Instrument  where Instrument.id_inst in (Select Jouer.id_inst_jouer  from Jouer  where Jouer.NIF_mus_jouer='"+model.getValueAt(index, 0).toString()+"' )");
         
                   while(Rs.next()){
                        model4.addRow(new Object[]{Rs.getString("nom_inst"),Rs.getString("id_inst")});
                       }
               }catch (Exception e){System.err.println(e);} 
               jTable7.setModel(model4);
               
         jLabel189.setText(model.getValueAt(index, 0).toString());
         jLabel183.setText(model.getValueAt(index, 1).toString());
         jLabel184.setText(model.getValueAt(index,2).toString());
         jLabel185.setText(model.getValueAt(index,3).toString());
         jLabel186.setText(model.getValueAt(index,4).toString());
         jLabel187.setText(model.getValueAt(index,5).toString());
         RepaintPanel(infoArtist);
        }    
    }//GEN-LAST:event_infoartistActionPerformed

    private void infoInstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoInstActionPerformed
        // TODO add your handling code here:
            int index = tbleInst.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
    Statement stm;
    ResultSet Rs;
       
       TableModel model= tbleInst.getModel();
               
                //nap affiche TOUT instrument atist sa genyen
      DefaultTableModel model4=new DefaultTableModel();     
       model4.addColumn("Last name");
       model4.addColumn("First name");
    
               try{                    
       stm=conn.obtenirconnection().createStatement();
              Rs=stm.executeQuery("Select nom_mus, prenom_mus  from Musicien where Musicien.NIF_mus in (Select Jouer.NIF_mus_jouer  from Jouer  where Jouer.id_inst_jouer='"+model.getValueAt(index, 0).toString()+"' )");
         
                   while(Rs.next()){
                        model4.addRow(new Object[]{Rs.getString("nom_mus"),Rs.getString("prenom_mus")});
                       }
               }catch (Exception e){System.err.println(e);} 
               jTable10.setModel(model4);
               
         jLabel205.setText(model.getValueAt(index, 0).toString());
         jLabel199.setText(model.getValueAt(index, 1).toString());
         jLabel200.setText(model.getValueAt(index,2).toString());
         jLabel201.setText(model.getValueAt(index,3).toString());
         RepaintPanel(infoInstrum);
        }    
    }//GEN-LAST:event_infoInstActionPerformed

    private void infoAlbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoAlbActionPerformed
        // TODO add your handling code here:
             int index = tble2.getSelectedRow();
        if (index != -1)// si gen ligne ki seleksyone la aji, sinon anyen pap fet
        {
    Statement stm;
    ResultSet Rs;
     //nap affiche TOUT music ki sou album sa
      DefaultTableModel model2=new DefaultTableModel();     
       model2.addColumn("Title");
       TableModel model= tble2.getModel();
       
               try{                    
       stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select titre_chan from chanson  where id_alb ='"+model.getValueAt(index, 0).toString()+"' ");
         
                   while(Rs.next()){
                        model2.addRow(new Object[]{Rs.getString("titre_chan")});
                       }
               }catch (Exception e){System.err.println(e);} 
               jTable8.setModel(model2);
               
            
 
       //nou pral deye nom promoteur album nan la
            try{                    
       stm=conn.obtenirconnection().createStatement();
              Rs=stm.executeQuery("Select nom_mus, prenom_mus  from Musicien where Musicien.NIF_mus ="+model.getValueAt(index, 4).toString());
         
                   while(Rs.next()){
                        String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
                        jLabel213.setText(nom_mus+" "+prenom_mus);
                       }
               }catch (Exception e){System.err.println(e);} 
            
    
               
         jLabel216.setText(model.getValueAt(index, 0).toString());
         jLabel210.setText(model.getValueAt(index, 1).toString());
         jLabel211.setText(model.getValueAt(index,2).toString());
         jLabel212.setText(model.getValueAt(index,3).toString());
          
         jLabel214.setText(model.getValueAt(index,5).toString());
         RepaintPanel(infoAlbum);
        }    
    }//GEN-LAST:event_infoAlbActionPerformed

    private void jLabel153MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel153MouseMoved
        // TODO add your handling code here:
        jLabel153.setFont(new Font("Tahoma", Font.BOLD,12));
    }//GEN-LAST:event_jLabel153MouseMoved

    private void jLabel153MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel153MouseExited
        // TODO add your handling code here:
         jLabel153.setFont(new Font("Tahoma", Font.PLAIN,12));
    }//GEN-LAST:event_jLabel153MouseExited

    private void jLabel153MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel153MouseClicked
        // TODO add your handling code here:
           int reponse=  JOptionPane.showConfirmDialog(this,"Confirm you want to reset the database   ATTENTION !!! All data will be lost","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if(reponse==JOptionPane.YES_OPTION)
      {   
       String requete="DELETE FROM jouer";
       String requete1="DELETE FROM play";
       String requete2="DELETE FROM instrument"; 
       String requete3="DELETE FROM chanson";
       String requete4="DELETE FROM album";
       String requete5="DELETE FROM musicien";
        try{
        stm.executeUpdate(requete);stm.executeUpdate(requete1);
         stm.executeUpdate(requete2);stm.executeUpdate(requete3);
         stm.executeUpdate(requete4);stm.executeUpdate(requete5);
        JOptionPane.showMessageDialog(null,"\nThe data base is now empty!");
        }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        
      }
    }//GEN-LAST:event_jLabel153MouseClicked

    private void jLabel149MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel149MouseMoved
        // TODO add your handling code here:
         jLabel149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m1on.jpg")));
    }//GEN-LAST:event_jLabel149MouseMoved

    private void jLabel150MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel150MouseMoved
        // TODO add your handling code here:
         jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m2on.jpg")));
    }//GEN-LAST:event_jLabel150MouseMoved

    private void jLabel151MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel151MouseMoved
        // TODO add your handling code here:
         jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m3on.jpg")));
    }//GEN-LAST:event_jLabel151MouseMoved

    private void jLabel152MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel152MouseMoved
        // TODO add your handling code here:
         jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m4on.jpg")));
    }//GEN-LAST:event_jLabel152MouseMoved

    private void jLabel152MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel152MouseExited
        // TODO add your handling code here:
        jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m4.jpg")));
    }//GEN-LAST:event_jLabel152MouseExited

    private void jLabel151MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel151MouseExited
        // TODO add your handling code here:
        jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m3.jpg")));
    }//GEN-LAST:event_jLabel151MouseExited

    private void jLabel150MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel150MouseExited
        // TODO add your handling code here:
            jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m2.jpg")));
    }//GEN-LAST:event_jLabel150MouseExited

    private void jLabel149MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel149MouseExited
        // TODO add your handling code here:
            jLabel149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/m1.jpg")));
    }//GEN-LAST:event_jLabel149MouseExited

    private void jLabel149MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel149MouseClicked
        // TODO add your handling code here:
         DisplayListMusic();
    }//GEN-LAST:event_jLabel149MouseClicked

    private void jLabel150MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel150MouseClicked
        // TODO add your handling code here:
          DisplayListAlbum();
    }//GEN-LAST:event_jLabel150MouseClicked

    private void jLabel151MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel151MouseClicked
        // TODO add your handling code here:
        DisplayListArtist();
    }//GEN-LAST:event_jLabel151MouseClicked

    private void jLabel152MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel152MouseClicked
        // TODO add your handling code here:
         DisplayListInstrum();
    }//GEN-LAST:event_jLabel152MouseClicked

    private void jLabel217MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel217MouseMoved
        // TODO add your handling code here:
        jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/dison.jpg")));
    }//GEN-LAST:event_jLabel217MouseMoved

    private void jLabel217MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel217MouseExited
        // TODO add your handling code here:
         jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basepackage/dis.jpg")));
    }//GEN-LAST:event_jLabel217MouseExited

    private void jLabel217MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel217MouseClicked
        // TODO add your handling code here:
        RepaintPanel(welcom);
    }//GEN-LAST:event_jLabel217MouseClicked

    private void jLabel108MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel108MouseMoved
        // TODO add your handling code here:
          jLabel108.setFont(new Font("Tahoma", Font.BOLD,11));
    }//GEN-LAST:event_jLabel108MouseMoved

    private void jLabel108MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel108MouseExited
        // TODO add your handling code here:
        jLabel108.setFont(new Font("Tahoma", Font.PLAIN,11));
    }//GEN-LAST:event_jLabel108MouseExited
public void OrderMusicBy(String truc)
{
     DefaultTableModel model=new DefaultTableModel();
        try{ 
          model.setRowCount(0);
           model.addColumn("ID");
       model.addColumn("TITLE");
       model.addColumn("AUTHOR");
        model.addColumn("DURATION");
         model.addColumn("SAVED DATE");
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from chanson order by "+truc+" asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_chan"),Rs.getString("titre_chan"),
               Rs.getString("auteur_chan"),Rs.getString("duree_chan"),Rs.getString("date_enr_chan")});
           }
       }catch (Exception e){System.err.println(e);}
       tble.setModel(model); 
}
public void OrderAlbumBy(String truc)
{
     DefaultTableModel model=new DefaultTableModel();
        try{ 
           model.setRowCount(0);
           model.addColumn("ID");
           model.addColumn("TITLE");
           model.addColumn("FORMAT");
           model.addColumn("ENTRY DATE");
           model.addColumn("AUTHOR");
           model.addColumn("SAVED DATE");
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from album order by "+truc+" asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_alb"),Rs.getString("titre_alb"),
               Rs.getString("format_alb"),Rs.getString("date_lanc"),Rs.getString("NIF_mus"),Rs.getString("date_enr_alb")});
           }
       }catch (Exception e){System.err.println(e);}
       tble2.setModel(model); 
}
public void OrderInstumentBy(String truc)
{
     DefaultTableModel model=new DefaultTableModel();
        try{ 
           model.setRowCount(0);
           model.addColumn("ID");
           model.addColumn("NAME");
           model.addColumn("PROCURATION DATE");
           model.addColumn("SAVED DATE");
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("Select* from instrument order by "+truc+" asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("id_inst"),Rs.getString("nom_inst"),
               Rs.getString("date_acqui"),Rs.getString("date_enr_inst")});
           }
       }catch (Exception e){System.err.println(e);}
       tbleInst.setModel(model); 
}
public void OrderArtistBy(String truc)
{
    DefaultTableModel model=new DefaultTableModel();
         model.setRowCount(0);
         model.addColumn("NIF");
         model.addColumn("LAST NAME");
            model.addColumn("FIRST NAME");
            model.addColumn("ADDRESS");
            model.addColumn("PHONE");
            model.addColumn("SAVED DATE");
            try{       
           stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus,adresse_mus, phone_mus,date_enr_mus from musicien order by "+truc+" asc");
           while(Rs.next()){
               model.addRow(new Object[]{Rs.getString("NIF_mus"),Rs.getString("nom_mus"),
               Rs.getString("Prenom_mus"),Rs.getString("adresse_mus"),Rs.getString("phone_mus"),Rs.getString("date_enr_mus")});
           }
       }catch (Exception e){System.err.println(e);}
       tble1.setModel(model);   
}
        private void Fillcombo()
    {
        try
        {
           jComboBox3.removeAllItems();
            stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus,adresse_mus, phone_mus from musicien");
           while(Rs.next()){
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               String NIF_mus=Rs.getString("NIF_mus");
               jComboBox3.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);
                
           }   
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);
        }
    }
                private void FillcomboAlbum()
    {
        try
        {
           jComboBox4.removeAllItems();
            stm=conn.obtenirconnection().createStatement();
            Rs=stm.executeQuery("select right (concat('0000000000',NIF_mus),10) as NIF_mus, nom_mus, prenom_mus,adresse_mus, phone_mus from musicien");
           while(Rs.next()){
               String nom_mus=Rs.getString("nom_mus");
               String prenom_mus=Rs.getString("prenom_mus");
               String NIF_mus=Rs.getString("NIF_mus");
               jComboBox4.addItem(nom_mus+" "+prenom_mus+"          "+NIF_mus);
                
           }   
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null,e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddAlbum;
    private javax.swing.JPanel AddArtist;
    private javax.swing.JPanel AddInstrum;
    private javax.swing.JPanel AddMusic;
    private javax.swing.JPanel BasePanel;
    private javax.swing.JMenuItem Delete;
    private javax.swing.JMenuItem Edit;
    private javax.swing.JPanel EditAlbum;
    private javax.swing.JPanel EditArtist;
    private javax.swing.JPanel EditInstrum;
    private javax.swing.JPanel EditMusic;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JMenuItem RemALb;
    private javax.swing.JMenuItem Remove;
    private javax.swing.JLabel affNomComplet;
    private javax.swing.JLabel affNomComplet1;
    private javax.swing.JLabel affaddress;
    private javax.swing.JLabel affaddress1;
    private javax.swing.JLabel affartist;
    private javax.swing.JLabel affartist1;
    private javax.swing.JLabel affduration;
    private javax.swing.JLabel affid;
    private javax.swing.JLabel affid1;
    private javax.swing.JLabel afftitle;
    private javax.swing.JLabel afftitle1;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JComboBox combobo;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private datechooser.beans.DateChooserCombo dateChooserCombo5;
    private javax.swing.JMenuItem deleteAlbum;
    private javax.swing.JMenuItem deleteInst;
    private javax.swing.JMenuItem deleteartist;
    private javax.swing.JTextField dureeField;
    private javax.swing.JTextField dureeField1;
    private javax.swing.JMenuItem editAlbum;
    private javax.swing.JMenuItem editArtist;
    private javax.swing.JMenuItem editinst;
    private javax.swing.JMenuItem infoAlb;
    private javax.swing.JPanel infoAlbum;
    private javax.swing.JPanel infoArtist;
    private javax.swing.JMenuItem infoInst;
    private javax.swing.JPanel infoInstrum;
    private javax.swing.JPanel infoMusicpanel;
    private javax.swing.JMenuItem infoartist;
    private javax.swing.JMenuItem infomusic;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabell155;
    private javax.swing.JPopupMenu jPopupAlbum;
    private javax.swing.JPopupMenu jPopupArtist;
    private javax.swing.JPopupMenu jPopupFtAritist;
    private javax.swing.JPopupMenu jPopupInstrum;
    private javax.swing.JPopupMenu jPopupMusic;
    private javax.swing.JPopupMenu jPopupRemALB;
    private javax.swing.JPopupMenu jPopupRemARTinInst;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lb1;
    private javax.swing.JLabel lb2;
    private javax.swing.JLabel lb3;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JLabel lbl_image1;
    private javax.swing.JLabel lbl_image2;
    private javax.swing.JPanel listAlbum;
    private javax.swing.JPanel listArtist;
    private javax.swing.JPanel listInstrum;
    private javax.swing.JPanel listMusic;
    private javax.swing.JComboBox newcomb;
    private javax.swing.JComboBox newcomb1;
    private javax.swing.JComboBox newcomb2;
    private javax.swing.JTextField nif1;
    private javax.swing.JTextField nif2;
    private javax.swing.JTextField nif3;
    private javax.swing.JTextField nif4;
    private javax.swing.JTextField path;
    private javax.swing.JTextField path1;
    private javax.swing.JTextField path2;
    private javax.swing.JMenuItem remArt;
    private javax.swing.JPanel setting;
    private javax.swing.JTable tble;
    private javax.swing.JTable tble1;
    private javax.swing.JTable tble2;
    private javax.swing.JTable tbleInst;
    private javax.swing.JTextField titreField;
    private javax.swing.JTextField titreField1;
    private javax.swing.JTextField titreField2;
    private javax.swing.JTextField titreField5;
    private javax.swing.JTextField txt_NIF_1p1;
    private javax.swing.JTextField txt_NIF_2p1;
    private javax.swing.JTextField txt_NIF_3p1;
    private javax.swing.JTextField txt_NIF_4p1;
    private javax.swing.JTextField txt_address_art;
    private javax.swing.JTextField txt_address_art1;
    private javax.swing.JTextField txt_nom_art;
    private javax.swing.JTextField txt_nom_art1;
    private javax.swing.JTextField txt_phone_art;
    private javax.swing.JTextField txt_phone_art1;
    private javax.swing.JTextField txt_prenom_art;
    private javax.swing.JTextField txt_prenom_art12;
    private javax.swing.JPanel welcom;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables


}
