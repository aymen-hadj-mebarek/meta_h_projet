package Classes;

public class KnapSack {
    public int  _id, _capacity;
    public static int _total = 0;
    public KnapSack(int capacity)
    {   
        _id = _total;
        _total ++;
        _capacity = capacity;
    }

    public int getId()
    {
        return _id;
    }

    public int getKnapSackCapacity()
    {
        return _capacity;
    }

    @Override
    public String toString() {
        return String.valueOf(_id)+","+String.valueOf(_capacity);
    }


    
}
