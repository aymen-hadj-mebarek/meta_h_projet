package Classes;

public class KpObject {
    
    private int _id, _weight, _value;
    private String _name;
    private static int _total = 0;

    public KpObject(String name, int weight, int value)
    {
        _name = name;
        _id = _total;
        _total ++;
        _weight = weight;
        _value = value;
    }

    public KpObject(int id, int weight, int value)
    {
        _id = id;
        _weight = weight;
        _value = value;
    }
    
    public String get_name() {
        return _name;
    }
    
    public int getId()
    {
        return _id;
    }

    public int getWeight()
    {
        return _weight;
    }


    public int getValue()
    {
        return _value;
    }

    @Override
    public String toString() {
        return String.valueOf(_id)+","+String.valueOf(_name)+","+String.valueOf(_weight)+","+String.valueOf(_value);
    }


}
