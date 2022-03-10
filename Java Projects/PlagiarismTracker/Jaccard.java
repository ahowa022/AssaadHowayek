public class Jaccard implements Similarity{
  public double score(WordMap a, WordMap b){
    String[] tableStringA, tableStringB;
    tableStringA = a.keys();
    tableStringB = b.keys();
    int counter=0;
    for(int i=0; i<a.size();i++){
      for(int j=0; j<b.size(); j++){
        if(tableStringA[i].equals(tableStringB[j])){
          counter= counter+1;;
        }
      }
    }
    double union = a.size()+b.size()-counter;
    double fraction = counter/union;
    return fraction;
  }

}
