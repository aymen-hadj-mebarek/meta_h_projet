package Classes;
import java.util.ArrayList;

public class KnapSack2 {
    public int  _id, _capacity;
    public ArrayList <KpObject> _listObjectOnSac;

    public KnapSack2(int id, int capacity)
    {
        _id = id;
        _capacity = capacity;
        _listObjectOnSac = new ArrayList<>();
    }

    public int getId()
    {
        return _id;
    }

    public int getKnapSackCapacity()
    {
        return _capacity;
    }

    public void printObjectOnSac()
    {
        System.out.print("S"+ (this.getId() + 1));
        for(int i = 0; i < this._listObjectOnSac.size(); i++)
        {
            System.out.print("[O"+ (this._listObjectOnSac.get(i).getId()+ 1) +"]");
        }

        System.out.print("\n");
           
    }



    
}
