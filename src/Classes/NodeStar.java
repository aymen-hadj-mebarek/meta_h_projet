package Classes;

public class NodeStar {

    public int [] [] _matrix;
    public int _h, _g, _f;
    public NodeStar(int [] [] matrix, int h, int g)
    {
        _matrix = matrix;
        _h = h;
        _g = g;
        _f = h + g;
    }

    public int [] [] getNode()
    {
        return _matrix;
    }
    public int getH()
    {
        return _h;
    }

    public int getG()
    {
        return _g;
    }
    
    public int getF()
    {
        return _f;
    }

}