package Classes;

import java.util.ArrayList;

public class Node {

    public int _matrix[][];
    public int _idO;
    public int _vTotal;
    
    public Node(int [] [] matrix, int idO, int vTotal)
    {
        _matrix = matrix;
        _idO = idO;
        _vTotal = vTotal;
    }

    public Node(int [] [] matrix, ArrayList<KpObject> Object)
        {
            _matrix = matrix;
            _idO = 0;
            _vTotal = calc_value2(Object);
        }

    public int calcValue(ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject){
        int S = 0;
        for (int i = 0; i < _matrix.length; i++) {
            for (int j = 0; j < _matrix[i].length; j++) {
                if(_matrix[i][j] == 1){
                    S += listObject.get(j).getValue();
                }
            }
        }
        return S;
    }

    public Node add(int obj, int sac,ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject){
        Node N  = new Node(copyMatrix(this._matrix), this._idO, this._vTotal);
        N._matrix[sac][obj] = 1;
        N._vTotal = N.calcValue(listSack, listObject);
        return N;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "id : "+String.valueOf(_idO) + " value : " + String.valueOf(_vTotal);
    }

    private static int[][] copyMatrix(int[][] originalMatrix) {
        int[][] newMatrix = new int[originalMatrix.length][];
        for (int i = 0; i < originalMatrix.length; i++) {
            newMatrix[i] = originalMatrix[i].clone();
        }
        return newMatrix;
    }

    public boolean available(ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject){
        boolean b = true;
        int S;
        for (int i = 0; i < _matrix.length; i++) {
            S = 0;
            for (int j = 0; j < _matrix[i].length; j++) {
                if (_matrix[i][j] == 1) {
                    S += listObject.get(j).getWeight();
                }
                if (listSack.get(i).getKnapSackCapacity() < S) {
                    return false;
                }
            }
        }


        return b;
    
    }

    public int calc_value2( ArrayList<KpObject> Object){
        int S = 0;
        for (int i = 0; i < this._matrix.length; i++) {
            for (int j = 0; j < this._matrix[i].length; j++) {
                if(this._matrix[i][j] == 1){
                    S += Object.get(j).getValue();
                }
            }
        }
        return S;
    }
}
