import javax.swing.*;

import Classes.Node;
import Classes.AStarNode;
import Classes.KnapSack;
import Classes.KpObject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;
import java.util.Stack;
import java.util.List;

public class MainWindow extends JFrame {

    static Random rn = new Random();

    public static int[][] initMatrix(int nbrBackPack, int nbrObject) {
        int[][] matrix = new int[nbrBackPack][nbrObject];

        for (int i = 0; i < nbrBackPack; i++) {
            for (int j = 0; j < nbrObject; j++)
                matrix[i][j] = 0;
        }

        return matrix;
    }

    public static Node initNode(ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject) {
        return new Node(initMatrix(listSack.size(), listObject.size()), 0, 0);
    }

    public static int getMax(ArrayList<Integer> listValue) {
        int max = listValue.get(0);

        for (int i = 1; i < listValue.size(); i++)
            if (listValue.get(i) > max)
                max = listValue.get(i);

        return max;
    }

    public static AStarNode initState(int nbrBackPack, int nbrObjet) {
        int matrix[][] = new int[nbrBackPack][nbrObjet];
        return new AStarNode(initMatrix(nbrBackPack, nbrObjet), 0, 0, 0);
    }

    public static int getMaxCapacity(ArrayList<KnapSack> listSack) {
        int max = listSack.get(0)._capacity;

        for (int i = 1; i < listSack.size(); i++)
            if (listSack.get(i)._capacity > max)
                max = listSack.get(i)._capacity;

        return max;
    }

    public static Node bfs(Node initialState, ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject) {
        Node state = new Node(initialState._matrix, -1, initialState._vTotal);
        int var = 0;
        Node N;
        LinkedList<Node> L1 = new LinkedList<Node>();
        LinkedList<Node> L2 = new LinkedList<Node>();

        L1.add(state);
        while (var < listObject.size()) {
            System.out.println(var);
            while (!L1.isEmpty()) {
                N = L1.pollFirst();
                if (N._idO > var) {
                    var++;
                }

                for (int i = 0; i < listSack.size(); i++) {

                    Node M = N.add(var, i, listSack, listObject);
                    if (M.available(listSack, listObject)) {
                        L2.addLast(M);
                    } else {
                    }
                }
                L2.addLast(new Node(copyMatrix(N._matrix), var, N.calcValue(listSack, listObject)));
            }
            L1.addAll(L2);
            L2.clear();
            var++;
        }

        Collections.sort(L1, Comparator.comparingInt(node -> node._vTotal));
        return L1.getLast();
    }

    public static Node dfs(ArrayList<KnapSack> listSack, ArrayList<KpObject> listObject)
    {
        Stack <Node> ouvert = new Stack<>();
        ArrayList <Integer> listValue = new ArrayList<>();
        Node initialState = initNode(listSack, listObject);
        Node sol = initNode(listSack, listObject);
        Node state = initNode(listSack, listObject);
        Node newState;
        KpObject o;

        ouvert.push(initialState);
        while(!ouvert.isEmpty())
        {
            state = ouvert.pop();
            if(state._vTotal > sol._vTotal)
            {
                sol._idO = state._idO;
                sol._vTotal = state._vTotal;
                sol._matrix = copyMatrix(state._matrix);
            }
            if(state._idO > initialState._matrix[0].length - 1)
            {
                listValue.add(state._vTotal);
                continue;
            }
            else     
            {
                o = listObject.get(state._idO);
                for(KnapSack sack : listSack)
                {
                    if(o.getWeight() <= sack.getKnapSackCapacity())
                    {
                        newState = new Node(copyMatrix(state._matrix), state._idO + 1, state._vTotal + o.getValue());
                        newState._matrix[sack.getId()][state._idO] = 1;
                        ouvert.push(newState);
                        sack._capacity -= o.getWeight();
                    }
                }
                ouvert.push(new Node(copyMatrix(state._matrix), (state._idO + 1), state._vTotal));
             
            }
        }

        if(state._vTotal > sol._vTotal)
        {
            sol._idO = state._idO;
            sol._vTotal = state._vTotal;
            sol._matrix = copyMatrix(state._matrix);
        }
        
        printMatrix(sol._matrix);
        
        return sol;
     }

    public static AStarNode a_star(ArrayList <KnapSack> listSack, ArrayList <KpObject> listObject)
    {
        ArrayList <AStarNode> ouvert = new ArrayList<>();
        AStarNode initialState = initState(listSack.size(), listObject.size());
        AStarNode state, newState;
        state = initState(listSack.size(), listObject.size());
        KpObject o;
        int maxCapacity;
        
        ouvert.add(initialState);
        while(!ouvert.isEmpty())
        {
            state = ouvert.get(0);
            ouvert.remove(ouvert.get(0));
        //                                     
            if(state._idO > initialState._matrix[0].length - 1)
            {                
                return state;
            }
            else 
            {
                o = listObject.get(state._idO);
                maxCapacity = getMaxCapacity(listSack);                
                for(KnapSack sack : listSack)
                {
                    if(o.getWeight() <= sack.getKnapSackCapacity())
                    {
                        newState = new AStarNode(copyMatrix(state._matrix), (state._idO + 1), o.getValue(), (sack.getKnapSackCapacity() - o.getWeight()));
                        newState._matrix[sack.getId()][state._idO] = 1;
                        ouvert.add(newState);
                        sack._capacity -= o.getWeight();
                    }                                        
                }
                
                ouvert.add(new AStarNode(copyMatrix(state._matrix), (state._idO + 1), 0, maxCapacity));
                Collections.sort(ouvert, Comparator.comparingInt(AStarNode::getF));                                
            }
         
        }

        return state;
        
    }

    private static int[][] copyMatrix(int[][] originalMatrix) {
        int[][] newMatrix = new int[originalMatrix.length][];
        for (int i = 0; i < originalMatrix.length; i++) {
            newMatrix[i] = originalMatrix[i].clone();
        }
        return newMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row)
                System.out.print("[" + element + "]");

            System.out.print("\n");
        }
        System.out.print("\n");
    }

    JLabel Titre;
    JButton MenuSacs;
    JButton MenuObjets;
    JButton MenuAfficher;
    JButton SolBFS;
    JButton SolDFS;
    JButton SolA;

    MainWindow(ArrayList<KpObject> listObjects, ArrayList<KnapSack> listSacks) {
        Font Title_font = new Font("Cosmos", Font.BOLD, 40);
        Font Text_Font = new Font("Cosmos", Font.PLAIN, 20);

        this.setTitle("Probleme de Sac a Dos");
        this.setSize(500, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        // titre :
        Titre = new JLabel();
        Titre.setText("Probleme de Sac a Dos");
        Titre.setBounds(0, 25, 500, 100);
        Titre.setFont(Title_font);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setVerticalAlignment(JLabel.CENTER);

        // boutton de films :
        MenuSacs = new JButton();
        MenuSacs.setText("AJOUTER UN SAC");
        MenuSacs.setBounds(100, 150, 300, 100);
        MenuSacs.setFont(Text_Font);
        MenuSacs.setHorizontalAlignment(JLabel.CENTER);
        MenuSacs.setVerticalAlignment(JLabel.CENTER);
        MenuSacs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WindowSac(listSacks, KnapSack._total);
            }
        });
        MenuSacs.setFocusable(false);

        // boutton de salles :
        MenuObjets = new JButton();
        MenuObjets.setText("AJOUTER UN OBJET");
        MenuObjets.setBounds(100, 300, 300, 100);
        MenuObjets.setFont(Text_Font);
        MenuObjets.setHorizontalAlignment(JLabel.CENTER);
        MenuObjets.setVerticalAlignment(JLabel.CENTER);
        MenuObjets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WindowObjects(listObjects);
            }

        });
        MenuObjets.setFocusable(false);

        MenuAfficher = new JButton();
        MenuAfficher.setText("MENU DES DONNEES");
        MenuAfficher.setBounds(100, 450, 300, 100);
        MenuAfficher.setFont(Text_Font);
        MenuAfficher.setHorizontalAlignment(JLabel.CENTER);
        MenuAfficher.setVerticalAlignment(JLabel.CENTER);
        MenuAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WindowAffich(listObjects, listSacks);
            }

        });
        MenuAfficher.setFocusable(false);

        // boutton de diffusions :
        SolBFS = new JButton();
        SolBFS.setText("BFS");
        SolBFS.setBounds(75, 600, 100, 100);
        SolBFS.setFont(Text_Font);
        SolBFS.setHorizontalAlignment(JLabel.CENTER);
        SolBFS.setVerticalAlignment(JLabel.CENTER);
        SolBFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[][] matrix = initMatrix(listSacks.size(), listObjects.size());
                Node initialState = new Node(matrix, 0, 0);
                long startTime = System.currentTimeMillis();

                Node Solution = bfs(initialState, listSacks, listObjects);

                long endTime = System.currentTimeMillis();

                double executionTimeSeconds = (endTime - startTime) / 1000.0;

                new WindowResult(Solution, listObjects, listSacks, executionTimeSeconds, "BFS");
            }

        });
        SolBFS.setFocusable(false);

        // boutton de billets :
        SolDFS = new JButton();
        SolDFS.setText("DFS");
        SolDFS.setBounds(200, 600, 100, 100);
        SolDFS.setFont(Text_Font);
        SolDFS.setHorizontalAlignment(JLabel.CENTER);
        SolDFS.setVerticalAlignment(JLabel.CENTER);
        SolDFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[][] matrix = initMatrix(listSacks.size(), listObjects.size());
                Node initialState = new Node(matrix, 0, 0);
                long startTime = System.currentTimeMillis();

                Node Solution = dfs(listSacks, listObjects);

                long endTime = System.currentTimeMillis();
                double executionTimeSeconds = (endTime - startTime) / 1000.0;

                new WindowResult(Solution, listObjects, listSacks, executionTimeSeconds, "DFS");
            }

        });
        SolDFS.setFocusable(false);

        SolA = new JButton();
        SolA.setText("A*");
        SolA.setBounds(325, 600, 100, 100);
        SolA.setFont(Text_Font);
        SolA.setHorizontalAlignment(JLabel.CENTER);
        SolA.setVerticalAlignment(JLabel.CENTER);
        SolA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] matrix = initMatrix(listSacks.size(), listObjects.size());
                Node initialState = new Node(matrix, 0, 0);
                long startTime = System.currentTimeMillis();

                AStarNode Solution = a_star(listSacks, listObjects);
                Node S = new Node(Solution._matrix, 0, 0);
                long endTime = System.currentTimeMillis();
                double executionTimeSeconds = (endTime - startTime) / 1000.0;

                new WindowResult(S, listObjects, listSacks, executionTimeSeconds, "A*");
            }

        });
        SolA.setFocusable(false);

        // ajout des elements :
        this.add(Titre);
        this.add(MenuSacs);
        this.add(MenuObjets);
        this.add(MenuAfficher);
        this.add(SolBFS);
        this.add(SolDFS);
        this.add(SolA);
        this.setVisible(true);
    }

}
