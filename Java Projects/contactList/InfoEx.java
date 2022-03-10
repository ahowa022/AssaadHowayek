import java.lang.Object;
import java.util.Collections;
import java.util.*;


public class InfoEx {
   
   //d�clare mes variables d'instances
   private String nom;
   private String ville;
   private String numTel;

   // le constructeur
   public InfoEx () {
      nom="PasDeNom"; //donner une valeur � mes variables d'instances
      ville = "PasDeVille";
      numTel="PasDeNumTel";
   }

   // Les mutateurs
   public void SetNom(String nm) {
      this.nom = nm;   //ajoute le nom � l'objet
   }

   public void SetVille(String vL) {
      this.ville = vL; //ajoute la ville � l'objet
   }

   public void SetTel(String numm) {
      this.numTel = numm;// ajoute le num�ro de t�l�phone � l'objet
   }

   
   public InfoEx GetInfo () {
      return this; //retourne toute l'information d'un objet � un certain index de la liste
   }

   public String GetNom() {
      return this.nom; //retourne nom dans un objet � un certain index de la liste
   }

   public String GetVille() {
      return this.ville;  //retourne ville dans un objet � un certain index de la liste
   }

   public String GetTelNum() {
      return this.numTel; //retourne # de telephone dans un objet � un certain index de la liste
   }

     
   public String ToString(){
      return "nom="+this.nom+"  ville="+this.ville+"  # de tel="+this.numTel; //convertit tout les �l�ments d'un objet � un string organis�e pour faire en sorte que l'utilisateur comprend
   }

}