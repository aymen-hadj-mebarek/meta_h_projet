import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Classes.KnapSack;
import Classes.KpObject;

public class App {

    static Random rn = new Random();
    
    public static ArrayList<KpObject> initObjectList(int nbrObject)
    {
        ArrayList <KpObject> listObject = new ArrayList<>();

        for(int i = 0; i < nbrObject; i++)
            listObject.add(new KpObject("object"+String.valueOf(i), 2+rn.nextInt(8), 5+rn.nextInt(10)));
        
        return listObject;
    }

    public static ArrayList<KnapSack> initSackList(int nbrSack)
    {
        ArrayList <KnapSack> listSack = new ArrayList<>();

        for(int i = 0; i < nbrSack; i++)
        {
            listSack.add(new KnapSack( 50+rn.nextInt(150)));
        }
        return listSack;
    }
    public static void main(String[] args) throws Exception {
        ArrayList<KpObject> listObjects = new ArrayList<KpObject>(Arrays.asList(
                new KpObject("phone", 5, 2),
                new KpObject("ring", 1, 10),
                new KpObject("laptop", 10, 7)));
                
        listObjects.add(new KpObject("Object1", 5, 2)); 
        listObjects.add(new KpObject("Object2", 1, 10));
        listObjects.add(new KpObject("Object3", 10, 7));
        listObjects.add(new KpObject("Object4", 15, 22));
        listObjects.add(new KpObject("Object5", 8, 4));
        listObjects.add(new KpObject("Object6", 3, 11));
        ArrayList<KnapSack> listSack = new ArrayList<KnapSack>(Arrays.asList(
                new KnapSack( 500),
                new KnapSack( 10)));


        new MainWindow(listObjects, listSack);
    }
}
