package Classes;

public class AStarNode {

    public int [] [] _matrix;
    public int _idO, _g, _h, _f;

    public AStarNode(int [] [] matrix, int idObject, int g, int h)
    {
        _matrix = matrix;
        _idO = idObject;
        _g = g;
        _h = h;
        _f = g + h;

    }

    public int getF()
    {
        return _f;
    }

}