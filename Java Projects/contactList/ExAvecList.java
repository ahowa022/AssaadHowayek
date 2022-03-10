import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.lang.Object;
import java.util.Collections;

public class ExAvecList {

   // variables d'instance.
   public static List<InfoEx> exDeList;

   //constructeur
   public ExAvecList () {
      Debug("Dans constructeur de ExAvecList");
      //cr�er ma liste
      exDeList = new ArrayList();
   }

   // m�thode de debug qui print l'argument inscrit par l'utilisateur
   private static void Debug (String str) {
      System.out.println(str);
   }

   
   public static void SortNames(){
    Collections.sort(exDeList, new Comparator(){ //d�clare mon comparator pour ma liste de contact
      public int compare(Object nom1, Object nom2){  //d�clare les objets dont je veux comparer 
        int resultat = (((InfoEx)nom1).GetNom().toLowerCase()).compareTo(((InfoEx)nom2).GetNom().toLowerCase()); // obtient mes �l�ments � comparer (nom des contacts), je les compare et je les mets en ordre alphab�tique
        return resultat; //variable int qui retourne valeur n�gatif lorsque le premier objet est au dessus du deuxi�me vice vers
      
      }
    });
  }
   
   public static void main (String[] argsv) throws IOException{
      ContactListUI iug = new ContactListUI();
      String name, city, telNumber; //d�clare les �l�ments d'un contact pour les ajouter tous dans un objet 
       
      
      try {
         File fichier = null; //cr�er un nouvel fichier
         JFileChooser fileChooser = 
                       new JFileChooser(System.getProperty("user.dir")); //Permet l'utilisateur choisir le fichier 
         int valRetour= fileChooser.showOpenDialog(null); //ouvre la fenpetre pour que l'utilisateur choisit le fichier
         if (valRetour== 0) {
            fichier = fileChooser.getSelectedFile(); //indique que l'utilisateur � choisit le fichier 
         } 
         Scanner scan = new Scanner( fichier ); // Scanner utiliser pour obtenir chaque �l�ment du fichier 
         
         while( scan.hasNext() ) {
           InfoEx ex = new InfoEx(); //cr�er un objet pour ajouter � ma liste
           
           name =  scan.nextLine(); //scan la next ligne qui est le nom
           ex.SetNom(name);// set le nom dans mon objet � l'�l�ment que j'ai scan
           
           telNumber=scan.nextLine(); //scan la next ligne qui est le # de tel.
           ex.SetTel(telNumber); //set le # de t�l�phone de mon objet � l'�l�ment que j'ai scan
           
           city= scan.nextLine(); //scan la next ligne qui est la ville
           ex.SetVille(city); //set la ville dans mon objet � l'�l�ment que j'ai scan

           exDeList.add(ex); //ajoute l'objet � ma liste
           SortNames(); //fait appele � une m�thode qui arrnage les objets en ordre alphab�tique des noms
         } // fin while
      } // fin de try
      catch (FileNotFoundException ex  ){
            System.out.println ("Oops - le fichier ne peut pas �tre trouvrer"); //si le fichier n'existe pas j'affiche ce message 
      }// fin catch
   }
}
