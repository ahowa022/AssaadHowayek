//**********************************************************
// Nom: Assaad Howayek
// Date: 2018
//**********************************************************
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class ContactListUI extends JFrame implements ActionListener {
   
   


   /////////////////////////////////////////////
   GridLayout unGrid1 = new GridLayout(5,1); //layout dont j'avais besoin pour mon gui
   ////////////////////////////////////////////

   
   
   

   JButton ajouteElt = new JButton("Ajoute");// bouton utiliser pour ajouter element a la liste
   JButton rechercheNom = new JButton("Recherche Nom"); //bouton utiliser pour rechercher le nom
   JButton rechercheVille = new JButton("Recherche Ville"); //bouton utiliser pour rechercher la ville
   JButton rechercheTel= new JButton("Recherche Tel."); //bouton utilser pour rechercher numero de telephomne
   JButton supprimeNom = new JButton("Supprime"); //bouton utiliser pour supprimer un element
   JButton afficheList = new JButton("Affiche Liste");
   

   JLabel nomPourAjouter = new JLabel("nom:"); //nom de la personne a inscrire
   JLabel numeroTelephoneAjouter = new JLabel("# de tel."); // mum de telephone a inscrire
   JLabel villeAjouter = new JLabel("ville"); //ville a inscrire
   JLabel message = new JLabel("Bienvenue a votre liste de contact"); // utiliser pour indiquer l'utilisateur divers messages lors du pgm
   JLabel elementRecherche = new JLabel("Recherche Elt.:");// label qui indique que la personne recherche qqc
   JLabel nomSupprimer= new JLabel("nom:"); // nom a supprimer
   

   JTextField nomInscrire     = new JTextField ("", 8); // text field pour inscrire nom
   JTextField numTelInscrire     = new JTextField ("", 10); //txt field pour inscrire # tel
   JTextField villeInscrire     = new JTextField ("", 8); //txt field pour inscrire ville
   JTextField rechercheElt    = new JTextField ("", 8); //txt field pour rechercher element
   JTextField supprimeElt     = new JTextField ("", 8); //txt field pour supprimer element
   
   
   
   // Panels necessaire pour mon interface graphique
   JPanel  panno1         = new JPanel();
   JPanel  panno2         = new JPanel();
   JPanel  panno3         = new JPanel();
   JPanel  panno4          = new JPanel();
   JPanel  panno5       = new JPanel();

   public ContactListUI () {
      super ("INSCRIPTION");
      setSize (700,450);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(unGrid1);
      

      ExAvecList ex1 = new ExAvecList();
      

      
      //fait en sorte que mon pgm realise qu'une boutton est utilisee
      ajouteElt.addActionListener(this);
      rechercheNom.addActionListener(this);
      rechercheVille.addActionListener(this);
      rechercheTel.addActionListener(this);
      supprimeNom.addActionListener(this);
      afficheList.addActionListener(this);
      
      //ajoute les labels, text field et boutton a chaque panno dans un certain ordre pour rendre mon IUG fonctionnel 
      panno1.add(nomPourAjouter);
      panno1.add(nomInscrire);
      panno1.add(numeroTelephoneAjouter);
      panno1.add(numTelInscrire);
      panno1.add(villeAjouter);
      panno1.add(villeInscrire);
      panno1.add(ajouteElt);
      add(panno1);
      
      panno2.add(elementRecherche);
      panno2.add(rechercheElt);
      panno2.add(rechercheNom);
      panno2.add(rechercheVille);
      panno2.add(rechercheTel);
      add(panno2);
      
      panno3.add(nomSupprimer); 
      panno3.add(supprimeElt);
      panno3.add(supprimeNom);
      add(panno3);
      
      panno4.add(afficheList);
      add(panno4);
      
      panno5.add(message);
      add(panno5);
      
      setVisible(true);
   }  // fin constructeur
   
   public void actionPerformed( ActionEvent actionEvent ) {
      String command = actionEvent.getActionCommand();
      
      if (command=="Ajoute"){
        Debug("boutton Ajoute fonctionne"); //debug pour assurer fonctionnement du boutton
        AjouterElement(); // methode qui ajoute element
      }
      
      if (command=="Recherche Nom"){ //MeME COMMENTAIRE QUE PREMIER IF POUR LES PRCHAINS IF MAIS ILS FONT JUSTE APPELE a DIFFeRENTE MeTHODE
        Debug("boutton pour rechercher nom fonctionne");
        RechercheNom();
      }
      
      if (command=="Recherche Ville"){
        Debug("boutton rechercher ville fonctionne");
        RechercheVille();
      }
      
      if (command=="Recherche Tel."){
        Debug("boutton rechercher # tel fonctionne");
        RechercheTelephone();
      }
      
      if (command=="Supprime"){
        Debug("boutton pour supprimer fonctionne");
        SupprimeElt();
      }
      
      if (command=="Affiche Liste"){
        Debug("boutton affiche liste fonctionne");
        AfficheList();
      }
 
      
   }
   
  public static void Debug(String debug) {                                        //methode de debogue
    System.out.println(debug);  //imprime arguments envoyer par la methode qui l'appelle
  }
  
  public void AjouterElement(){
    String nom, ville, telNum, messageAuLecteur; //string pour nom inscrit, ville inscrit, telnum inscrit et message a indiquer au lecteur
    
    nom = nomInscrire.getText().trim(); //obtient le nom de jtextfield
    ville = villeInscrire.getText().trim(); //obtient le ville de jtextfield
    telNum = numTelInscrire.getText().trim(); //obtient le numtel de jtextfield
    if ((nom.equals(""))&&(ville.equals(""))&&(telNum.equals(""))){ //si le contact est vide j'affiche au lecteur qu'il doit avoir au moins 1 element
      message.setText("Il faut inscrire au moins un eLeMENT!");
    }else {
    InfoEx ex = new InfoEx(); //creer un nouveau element pour l'ajouter a la liste
    ex.SetNom(nom); //set le nom inscrit a l'element
    ex.SetVille(ville); // set le ville inscrit a l'element
    ex.SetTel(telNum); // set le # tel a l'element
    messageAuLecteur = ex.ToString(); //convertit message a l'utilisateur en string
    message.setText("Vous avez ajouter: "+ messageAuLecteur +" a votre liste de contacte"); //affiche au lecteur ce qu'il a ajoute
    numTelInscrire.setText(""); //reset les textfield
    villeInscrire.setText(""); //reset les textfield
    nomInscrire.setText("");   //reset les textfield
    Debug(ex.ToString()); //debug l'element que j'ai ajoute pour etre sur que je l'ai ajoute
    ExAvecList.exDeList.add(ex); //ajoute l'element a la liste
    ExAvecList.SortNames(); //J'envoie l'element a ma methode pour le mettre en ordre alphabetique 
    }
  }
  
  
  public void RechercheNom(){
    int index=-1, elementTrouver=0; //index utiliser pour afficher positon de l'element rechercher et element trouver est la variable int qui aide afficher au lecteur que son element est trouvee
    String str = rechercheElt.getText().trim(); //prend l'element que l'utilisateur recherche et le met dans un string 
    
    for(int i=0; i<ExAvecList.exDeList.size(); i++){ //boucle qui arrete apres passer a travers chaque element de la liste 
       if(ExAvecList.exDeList.get(i).GetNom().equals(str)){ //si un element de la liste est equivalent a l'element rechercher on entre dans cette if
        index=i;//index est egale a l'incrementation
        Debug(str + " se trouve a la position " + index); //indique dans ma console l'element qui se trouve a quelle position
        message.setText("L'element " + ExAvecList.exDeList.get(index).ToString()+" se trouve a l'index " +index); //affiche dans mon gui la contact de l'element recherche
        rechercheElt.setText(""); // vide mon Jtextfield
        elementTrouver=-9; //changer la valeur pour ne pas afficher que l'element rechercher n'existe pas puisque ca existe
       } 
    }
    if (elementTrouver==0){
      message.setText("L'element tu veux rechercher n'existe pas"); //si l'element rechercher n'est pas dans la liste je l'indique sur le gui pour l'utilisateur
      Debug("L'element tu veux rechercher n'existe pas");
    }
  }
  
  public void RechercheTelephone(){ //MeME COMMENTAIRE QUE RECHERCHE NOM MAIS AU LIEU DE GetNom() dans le if c'est GetTelNum() pour le numero de telephone
    int index=-1, elementTrouver=0;
    String str = rechercheElt.getText().trim();
    
    for(int i=0; i<ExAvecList.exDeList.size(); i++){
       if(ExAvecList.exDeList.get(i).GetTelNum().equals(str)){
        index=i;
        Debug(str + " se trouve a la position " + index); 
        message.setText("L'element " + ExAvecList.exDeList.get(index).ToString()+" se trouve a l'index " +index);
        rechercheElt.setText("");
        elementTrouver=-9;
       } 
    }
    if (elementTrouver==0){
      message.setText("L'element tu veux rechercher n'existe pas");
      Debug("L'element tu veux rechercher n'existe pas");
    }
  }
  
  public void RechercheVille(){  //MeME COMMENTAIRE QUE RECHERCHE NOM MAIS AU LIEU DE GetNom() dans le if c'est GetVille() pour la ville 
    int index=-1, elementTrouver=0;
    String str = rechercheElt.getText().trim();
    
    for(int i=0; i<ExAvecList.exDeList.size(); i++){
       if(ExAvecList.exDeList.get(i).GetVille().equals(str)){
        index=i;
        Debug(str + " se trouve a la position " + index); 
        message.setText("L'element " + ExAvecList.exDeList.get(index).ToString()+" se trouve a l'index " +index);
        rechercheElt.setText("");
        elementTrouver=-9;
       } 
    }
    if (elementTrouver==0){
      message.setText("L'element tu veux rechercher n'existe pas");
      Debug("L'element tu veux rechercher n'existe pas");
    }
  }
  
  public void AfficheList(){
    String affiche; //utiliser pour afficher la liste 
    for(int i=0; i<ExAvecList.exDeList.size(); i++){ // passe a travaers chaque element de la liste 
      affiche = ExAvecList.exDeList.get(i).ToString(); //prend l'element a un certain index et le met en string
      Debug("a la position: "+ i + " - " +affiche); // affiche l'element dans la console ainsi que son index 
      message.setText("La liste est affichee dans la console"); //indiquer au utilisateur dans le GUI que la liste est affichee dans la console
    }
  }
  
  public void SupprimeElt() {
    String str = supprimeElt.getText().trim(); //get l'element que l'utilisateur veut supprimer
    int index=-1, elementTrouver=0;  //index utiliser pour afficher position de l'element supprimer et pour afficher au lecteur que l'element est supprimee  et element trouver est la variable int qui aide afficher au lecteur que son element est trouvee
    
    for(int i=0; i<ExAvecList.exDeList.size(); i++){ //boucle qui passe a travers chaque element
       if(ExAvecList.exDeList.get(i).GetNom().equals(str)){ // si l'element rechercher est retrouver dans la liste je va dans cette if
        index=i; //index est egale a l'incrementation
        Debug(str + " qui se trouve a la position " + index +" est supprimee de la liste"); //affiche dans la console que l'element est supprimer 
        message.setText("L'element " + ExAvecList.exDeList.get(index).ToString()+" qui se trouvait a l'index " +index+ " c'est fait supprimer"); //indiquer a l'utilisateur ce qui a supprime
        ExAvecList.exDeList.remove(index); //supprimer l'element de la liste
        supprimeElt.setText(""); //reset text de l'element supprimer
        elementTrouver=-9;//changer la valeur pour ne pas afficher que l'element a supprimer n'existe pas puisque ca existe
       } 
     }
    
    if (elementTrouver==0){
      message.setText("L'element tu veux supprimer n'existe pas"); //affiche a l'utilisateur que l'element a supprimer n'existe pas 
      Debug("L'element tu veux supprimer n'existe pas"); //debug pour afficher a l'utilisateur que l'element qu'il veut supprimer n'existe pas 
    }
  }
  
  
   

   public static void main (String[] arguments) {

      
   } // fin de main
}  // fin class