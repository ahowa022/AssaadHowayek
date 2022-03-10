import java.util.Random;

public class GameModel {

 // Your code here

//static
 private static Random generator = new Random();
 //instance
 private Solution current;
 private boolean[][] table;
 private int incrementer = 0;

 public GameModel(int width, int height){
   table =  new boolean[height][width];
   incrementer=0;
   setSolution();
 }

 public int getHeight(){
   return table.length;
 }
 public int getWidth(){
   return table[0].length;
 }

 public void click(int i, int j){
   if(i < 0 || i > getHeight() - 1 || j < 0 || j > getWidth() - 1){
     throw new ArrayIndexOutOfBoundsException("The parameters entered are not present in the GameModel for the fct click!");
   }

   clickHelper(i,j);

   if(j-1>=0){
     clickHelper(i,j-1);
   }

   if(j+1<getWidth()){
     clickHelper(i,j+1);
   }

   if(i+1<getHeight()){
     clickHelper(i+1,j);
   }

   if(i-1>=0){
     clickHelper(i-1,j);
   }

   incrementer++;
   setSolution();

 }

 private void clickHelper(int i, int j){// helperfunction
   if(isON(i,j)){
     set(j,i,false);
   } else {
     set(j,i,true);
   }
 }

 public int getNumberOfSteps(){
   return incrementer;
 }

 public boolean isFinished(){
   for(int i=0; i<getHeight(); i++){
     for (int j =0; j<getWidth();j++){
       if(table[i][j]==false){
         return false;
       }
     }
   }
   return true;
 }

 public void randomize(){
   boolean solvable = false;
   while(!solvable){
     for(int i=0; i<getHeight(); i++){
       for (int j =0; j<getWidth();j++){
         table[i][j]=generator.nextBoolean();
         }
       }
       if(LightsOut.solve(this).size()!=0 && !isFinished()){
         solvable = true;
       }
   }
   setSolution();
   incrementer=0;

   }



 public boolean isON(int i, int j){
   if(i < 0 || i > getHeight() - 1 || j < 0 || j > getWidth() - 1){
     throw new ArrayIndexOutOfBoundsException("The parameters entered are not present in the GameModel for the fct isON!");
   }
   return table[i][j];
 }

 public void reset(){
   for (int i=0; i<table.length;i++){
     for(int j=0; j<table[0].length;j++){
       table[i][j] = false;
     }
   }
   setSolution();
   incrementer=0;
 }

 public void setSolution(){
   current =  LightsOut.solveShortest(this);
 }

 public boolean solutionSelects(int i, int j){
   if (current != null && current.get(j,i)){
       return true;
     } else {
       return false;
   }
 }

 public boolean getSolutionBoolean(int row, int column){ //return value of solution at specific index
   return current.get(column,row); //extra function
 }


 public void set(int i, int j, boolean value){
   //check if i and j are valid
   if(j < 0 || j > getHeight() - 1 || i < 0 || i > getWidth() - 1){
     throw new ArrayIndexOutOfBoundsException("The parameters entered are not present in the GameModel for the fct set!");
   }
   table[j][i] = value;
 }

 public String toString(){
   String message="";
   for (int i=0; i<table.length; i++){
     message+="[";
     for (int j=0; j<table[0].length;j++){
       message += table[i][j];
       if (j<table[0].length-1){
         message+=", ";
       }
     }
     message +="]";
     if(i<table.length-1){
       message+=", ";
     }
   }
   return "["+message+"]";
 }

}
