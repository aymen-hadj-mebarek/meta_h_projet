import javax.swing.*;

import Classes.Node;
import Classes.AStarNode;
import Classes.KnapSack;
import Classes.KnapSack2;
import Classes.KpObject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static ArrayList<KpObject> initObjectList(int nbrObject, int minWeight, int maxWeight) {
        ArrayList<KpObject> listObject = new ArrayList<>();
    
        for (int i = 0; i < nbrObject; i++)
            listObject.add(new KpObject(i, (minWeight + rn.nextInt(maxWeight - minWeight)), 5 + rn.nextInt(15)));
    
        return listObject;
    }

    public static ArrayList<KnapSack2> initSackList(int nbrSack, int minCapacity, int maxCapacity) {
    ArrayList<KnapSack2> listSack = new ArrayList<>();

    for (int i = 0; i < nbrSack; i++) {
        listSack.add(new KnapSack2(i, (maxCapacity - rn.nextInt(maxCapacity - minCapacity))));
    }
    return listSack;
}
    
    public static Node generateSRef(ArrayList <KnapSack2> listSac, ArrayList <KpObject> listObject)
    {
        Node sref = new Node(initMatrix(listSac.size(), listObject.size()), 0, 0);
        KnapSack2 s;
        KpObject o;
        while(sref._idO < listObject.size())
        {
            s = listSac.get(rn.nextInt(listSac.size()));
            o = listObject.get(sref._idO);
            if(o.getWeight() <= s._capacity)
            {
                sref._matrix[s.getId()][o.getId()] = 1;
                sref._vTotal += o.getValue();
                s._capacity -= o.getWeight();
                s._listObjectOnSac.add(o);
                s.printObjectOnSac();
            }
    
            sref._idO++;
        }

        return sref;
    }

    public static ArrayList<int[]> searchArea(int[] srefRow,int index, int flip, ArrayList<KpObject> listObject, ArrayList<KnapSack> listSac) 
    {
        int h = 0;
        int p;
        int[] s;
        KpObject o;
        KnapSack sac;
        ArrayList<int[]> searchArea = new ArrayList<>();

        while (h < flip) { 
            p = 0;
            s = Arrays.copyOf(srefRow, srefRow.length); 
            do{
                if ((flip * p + h) < s.length) 
                { 
                    o = listObject.get(flip * p + h);
                    sac = listSac.get(index);
                    if (s[flip * p + h] == 0) 
                    {
                        if (o.getWeight() <= sac.getKnapSackCapacity()) 
                        {
                            s[flip * p + h] = 1;
                            sac._capacity -= o.getWeight();

                        }
                    } 
                    else 
                    {
                        s[flip * p + h] = 0;
                        sac._capacity += o.getWeight();
                    }
                }
                p++;
            } while (flip * p + h < s.length);

            searchArea.add(s);
            h++;
        }

        return searchArea;
    }

    public static ArrayList<int[]> searchArea2(int[] srefRow,int index, int flip, ArrayList<KpObject> listObject, ArrayList<KnapSack2> listSac) 
    {
        int h = 0;
        int p;
        int[] s;
        KpObject o;
        KnapSack2 sac;
        ArrayList<int[]> searchArea = new ArrayList<>();

        while (h < flip) { 
            p = 0;
            s = Arrays.copyOf(srefRow, srefRow.length); 
            do{
                if ((flip * p + h) < s.length) 
                { 
                    o = listObject.get(flip * p + h);
                    sac = listSac.get(index);
                    if (s[flip * p + h] == 0) 
                    {
                        if (o.getWeight() <= sac.getKnapSackCapacity()) 
                        {
                            s[flip * p + h] = 1;
                            sac._capacity -= o.getWeight();

                        }
                    } 
                    else 
                    {
                        s[flip * p + h] = 0;
                        sac._capacity += o.getWeight();
                    }
                }
                p++;
            } while (flip * p + h < s.length);

            searchArea.add(s);
            h++;
        }

        return searchArea;
    }

    public static ArrayList<Node> generateSolutions(Node sref, ArrayList<KpObject> listObject, ArrayList<KnapSack> listSac) {
        ArrayList<Node> potentialSolutions = new ArrayList<>();
        ArrayList<int[]> searchPoints;
        int[][] sol;
        for (int i = 0; i < sref._matrix.length; i++) 
        {
            searchPoints = searchArea(sref._matrix[i], i, 2, listObject, listSac); 
            //Construction d'une solution
            for (int[] point : searchPoints) {
                sol = copyMatrix(sref._matrix);
                sol[i] = point;
                
                Node solNode = new Node(sol, 0, calculateNodeValue(sol, listObject, listSac)); 
                potentialSolutions.add(solNode);
            }
        }

        // System.out.println("Abeilles generees");
        // afficherMatricesCoteACote(potentialSolutions);
        return potentialSolutions;
    }

    public static ArrayList<Node> generateSolutions2(Node sref, ArrayList<KpObject> listObject, ArrayList<KnapSack2> listSac) {
        ArrayList<Node> potentialSolutions = new ArrayList<>();
        ArrayList<int[]> searchPoints;
        int[][] sol;
        for (int i = 0; i < sref._matrix.length; i++) 
        {
            searchPoints = searchArea2(sref._matrix[i], i, 2, listObject, listSac); 
            //Construction d'une solution
            for (int[] point : searchPoints) {
                sol = copyMatrix(sref._matrix);
                sol[i] = point;
                
                Node solNode = new Node(sol, 0, calculateNodeValue2(sol, listObject, listSac)); 
                potentialSolutions.add(solNode);
            }
        }

        // System.out.println("Abeilles generees");
        // afficherMatricesCoteACote(potentialSolutions);
        return potentialSolutions;
    }

    public static int calculateNodeValue(int[][] sol, ArrayList<KpObject> listObject, ArrayList<KnapSack> listSac) {
        int vTotal = 0;
        for (int i = 0; i < sol.length; i++) 
        {
            for (int j = 0; j < sol[i].length; j++) 
            {
                if (sol[i][j] == 1) 
                {
                    KpObject object = listObject.get(j);
                    KnapSack sack = listSac.get(i);
                    vTotal += object.getValue();
                }
            }
        }

        return vTotal;
    }
    
    public static int calculateNodeValue2(int[][] sol, ArrayList<KpObject> listObject, ArrayList<KnapSack2> listSac) {
        int vTotal = 0;
        for (int i = 0; i < sol.length; i++) 
        {
            for (int j = 0; j < sol[i].length; j++) 
            {
                if (sol[i][j] == 1) 
                {
                    KpObject object = listObject.get(j);
                    KnapSack2 sack = listSac.get(i);
                    vTotal += object.getValue();
                }
            }
        }

        return vTotal;
    }
    
    public static boolean isObjectNotInOtherSacks(KpObject object, KnapSack2 currentSack, ArrayList<KnapSack2> allSacks) {
        for (KnapSack2 sack : allSacks) {
            if (sack != currentSack && sack._listObjectOnSac.contains(object)) {
                return false; 
            }
        }
        return true; 
    }

    public static ArrayList<Node> localSearch(Node sol, ArrayList<KnapSack2> listSac, ArrayList<KpObject> listObject)
    {

        ArrayList <Node> bees = new ArrayList<>();
        Node newSol;
        KpObject o;
        KnapSack2 s;

        for(int i = 0; i < sol._matrix.length; i++)
        {
            for(int j = 0; j < sol._matrix[0].length; j++)
            {
                o = listObject.get(j); 
                s = listSac.get(i);
                //Copy pour l'instant
                newSol = new Node(copyMatrix(sol._matrix), j, sol._vTotal);
                if(sol._matrix[i][j] == 1)
                {
                    newSol._matrix[i][j] = 0;
                    newSol._vTotal -= o.getValue();
                    bees.add(newSol);
                    s._capacity += o.getWeight();
                }
                else 
                {
                    if(isObjectNotInOtherSacks(o, s, listSac))
                    {
                        if(o.getWeight() <= s._capacity)
                        {
                            newSol._matrix[i][j] = 1;
                            newSol._vTotal += o.getValue();
                            bees.add(newSol);
                            s._capacity -= o.getWeight();
                            
                        }
                    }
                        
                }
               }
            

         }
        //  afficherMatricesCoteACote(bees);

         return bees;
    }

    public static Node getOptimalSolution(ArrayList<Node> bees)
    {
        Node optimalSol = bees.get(0);
        for(int i = 1; i < bees.size(); i++)
            if(bees.get(i)._vTotal > optimalSol._vTotal)
                optimalSol = bees.get(i);


        return optimalSol;
    }
    
    public static Node getBestFromTableDance(ArrayList<Node> danceTable)
    {
        Node best = danceTable.get(0);
        for(int i = 1; i < danceTable.size(); i++)
            if(danceTable.get(i)._vTotal > best._vTotal)
                best = danceTable.get(i);


        return best;
    }

    public static Node getBestInDiversity(ArrayList<Node> danceTable, ArrayList<Node> tabooList) {
        double minDifference = Double.MAX_VALUE;
        Node bestDiverseSolution = null;
        for (Node danceSolution : danceTable) {
            for (Node tabooSolution : tabooList) {
                double difference = Math.abs(danceSolution._vTotal - tabooSolution._vTotal);
                if (difference < minDifference) {
                    minDifference = difference;
                    bestDiverseSolution = danceSolution;
                }
            }
        }
        return bestDiverseSolution;
    }

    public static int fitness(Node sol)
    {
        return sol._vTotal;
    }

    public static Node bso(int flip, int maxIteration, int maxChance, int nbChance,  ArrayList<KnapSack2> listSack, ArrayList<KpObject> listObject) {
        Node sref = generateSRef(listSack, listObject);
        ArrayList<Node> tabooList = new ArrayList<>();
        ArrayList<Node> danceTable = new ArrayList<>();
        Node bestSol;
    
        int i = 0; 
    
        while(i <= maxIteration) 
        {
            tabooList.add(sref);
            ArrayList<Node> bees = generateSolutions2(sref, listObject, listSack);

            for(int j = 0; j < bees.size(); j++) {
                
                bestSol = getOptimalSolution(localSearch(bees.get(j), listSack, listObject));
                if(i == 0) {
                    danceTable.add(bestSol);
                } else {
                    if(danceTable.get(j)._vTotal < bestSol._vTotal) {
                        danceTable.set(j, bestSol);
                    }
                }
            }
    

    
            Node sBest = getBestFromTableDance(danceTable);
    
    
            int delta = fitness(sBest) - fitness(tabooList.get(i));
    
            if(delta > 0) {
                sref = sBest;
                if(nbChance < maxChance) 
                    nbChance = maxChance;
                
            } 
            else 
            {
                nbChance--;
                if(nbChance > 0) {
                    sref = sBest;
                } else {
                    sref = getBestInDiversity(danceTable, tabooList);
                    nbChance = maxChance;
                }
            }
    
            i++;
        }
    
        return sref;
    }
    
    public static int Space_left(Node node, ArrayList<KnapSack2> listSac, ArrayList<KpObject> listObject){
        int S = 0;
        for (int i = 0; i < node._matrix.length; i++) {
            int total = listSac.get(i)._capacity;
            for (int j = 0; j < node._matrix[i].length; j++) {
                if(node._matrix[i][j] == 1){
                    total -= listObject.get(j).getWeight();
                }
            }
            S += total;
        }
        return S;
    }

    public static class NodeComparator implements Comparator<Node> {
        private ArrayList<KnapSack2> listSac;
        private ArrayList<KpObject> listObject;

        public NodeComparator(ArrayList<KnapSack2> listSac, ArrayList<KpObject> listObject) {
            this.listSac = listSac;
            this.listObject = listObject;
        }

        @Override
        public int compare(Node node1, Node node2) {
            int totalValue1 = 4*node1._vTotal + Space_left(node1, listSac, listObject);
            int totalValue2 = 4*node2._vTotal + Space_left(node2, listSac, listObject);
            return Integer.compare(totalValue1, totalValue2);
        }
    }

    public static int[][] generateMatrix(int nbrBackPack, int nbrObject) {
        int[][] matrix = new int[nbrBackPack][nbrObject];

        for (int i = 0; i < nbrBackPack; i++) {
            for (int j = 0; j < nbrObject; j++)
                if (!checkObjectAlreadyExists(matrix, j))
                    matrix[i][j] = Math.round(rn.nextInt(2));
        }

        return matrix;
    }
    
    public static boolean checkObjectAlreadyExists(int[][] m, int col) {
        for (int i = 0; i < m.length; i++) {
            if (m[i][col] == 1) {
                return true;
            }
        }
        return false;
    }

    public static Node crossover(Node parent1, Node parent2, ArrayList<KnapSack2> listSac,
            ArrayList<KpObject> listObject) {
        // Check for compatibility: Ensure matrices have the same dimensions
        if (parent1._matrix.length != parent2._matrix.length
                || parent1._matrix[0].length != parent2._matrix[0].length) {
            throw new IllegalArgumentException("Matrices have different dimensions and cannot be crossed over.");
        }

        // Choose a random crossover point
        Random random = new Random();

        Node offspring = new Node(initMatrix(parent1._matrix.length, parent2._matrix[0].length), 0, 0);
        
        do {
            // System.out.println("Process of Crossover : ");
            offspring = new Node(initMatrix(parent1._matrix.length, parent2._matrix[0].length), 0, 0);
            int crossoverPoint = random.nextInt(parent1._matrix.length);

            // Create a new Node to store the offspring

            // Perform crossover
            int[][] offspringMatrix = new int[parent1._matrix.length][parent1._matrix[0].length];
            for (int i = 0; i < parent1._matrix.length; i++) {
                if (i < crossoverPoint) {
                    offspringMatrix[i] = parent1._matrix[i].clone(); // Copy row from parent 1
                } else {
                    offspringMatrix[i] = parent2._matrix[i].clone(); // Copy row from parent 2
                }
            }

            offspring._matrix = offspringMatrix;
            offspring._vTotal = offspring.calc_value2(listObject);
            // Other attributes of offspring can be set here, like _idO and _vTotal if
            // applicable
        } while (!checkSolution(offspring, listSac, listObject));

        return offspring;
    }

    public static void mutate(Node node, double mutationRate, ArrayList<KpObject> listObject) {
        Random random = new Random();
        
        // Iterate through the matrix and mutate each element with a certain probability
        for (int i = 0; i < node._matrix.length; i++) {
            for (int j = 0; j < node._matrix[i].length; j++) {
                if (random.nextDouble() < mutationRate) {
                    // Flip the value (0 to 1 or 1 to 0)
                    node._matrix[i][j] = (node._matrix[i][j] == 0) ? 1 : 0;
                    node._vTotal = node.calc_value2(listObject);
                }
            }
        }
    }

    public static boolean checkSolution(Node node, ArrayList<KnapSack2> listSac, ArrayList<KpObject> listObject) {
        int S;
        // verification du poids
        for (int i = 0; i < node._matrix.length; i++) {
            S = 0;
            for (int j = 0; j < node._matrix[i].length; j++) {
                if (node._matrix[i][j] == 1) {
                    S += listObject.get(j).getWeight();
                }
            }
            if (S > listSac.get(i)._capacity) {
                return false;
            }
        }
        // verification de repetition : 
        boolean already_put = false;
        for (int j = 0; j < node._matrix[0].length; j++) {
            already_put = false;
            for (int i = 0; i < node._matrix.length; i++) {
                if(node._matrix[i][j] == 1){
                    if(already_put == true){
                        return false;
                    }else{
                        already_put = true;
                    }
                }
            }
        }
        return true;
    }

    public static Node GeneticAlgo(ArrayList<KnapSack2> listSac, ArrayList<KpObject> listObject,
        ArrayList<Node> Solutions, int nbr_iterations, int value) {

    double mutationRate = 0.5; // Adjust mutation rate as needed
    int iteration;
    for ( iteration = 0; iteration < nbr_iterations; iteration++) {
        // Select parents for crossover (you can use different selection methods)
        Node parent1 = Solutions.get(Solutions.size()-1);
        Node parent2 = Solutions.get(Solutions.size()-2);

        // Perform crossover
        Node offspring = crossover(parent1, parent2, listSac, listObject);
        
        // Mutate offspring
        mutate(offspring, mutationRate, listObject);
        
        offspring._vTotal = offspring.calc_value2(listObject);

        // Evaluate offspring
        if (checkSolution(offspring, listSac, listObject)) {
            // Replace old solution with offspring
            Solutions.remove(0); // Remove the worst solution
            Solutions.add(offspring);
        }

        // Sort solutions based on the comparator
        Collections.sort(Solutions, new NodeComparator(listSac, listObject));
        if(Solutions.get(Solutions.size() - 1)._vTotal >= value){
            break;
        }

    }
    return Solutions.get(Solutions.size() - 1);
}

    JLabel Titre;
    JButton MenuSacs;
    JButton MenuObjets;
    JButton MenuAfficher;
    JButton SolBFS;
    JButton SolDFS;
    JButton SolA;
    JButton SolGA;
    JButton SolBSO;

    MainWindow(ArrayList<KpObject> listObjects, ArrayList<KnapSack> listSacks) {
        Font Title_font = new Font("Cosmos", Font.BOLD, 40);
        Font Text_Font = new Font("Cosmos", Font.PLAIN, 20);

        this.setTitle("Probleme de Sac a Dos");
        this.setSize(500, 900);
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

        SolGA = new JButton();
        SolGA.setText("GA");
        SolGA.setBounds(125, 725, 100, 100);
        SolGA.setFont(Text_Font);
        SolGA.setHorizontalAlignment(JLabel.CENTER);
        SolGA.setVerticalAlignment(JLabel.CENTER);
        SolGA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                ArrayList<Node> Solutions = new ArrayList<>();
                int value = 2000;
                int x = 0;
                ArrayList <KnapSack2> listSac = new ArrayList<KnapSack2>();
                
                for (int i = 0; i < listSacks.size(); i++) {
                    KnapSack2 new_sac = new KnapSack2(listSacks.get(i).getId(), listSacks.get(i).getKnapSackCapacity());
                    listSac.add(new_sac);
                }

                while (x < 30) {
                    Node sol = new Node(generateMatrix(listSac.size(), listObjects.size()), listObjects);
                    if (checkSolution(sol, listSac, listObjects) && sol._vTotal > 0) {
                        System.out.println(sol._vTotal);
                        Solutions.add(sol);
                        x++;
                    }
                }

                int[][] matrix = initMatrix(listSacks.size(), listObjects.size());
                Node initialState = new Node(matrix, 0, 0);
                long startTime = System.currentTimeMillis();
                
                Node Best_Sol = GeneticAlgo(listSac, listObjects, Solutions, 350, value);
                long endTime = System.currentTimeMillis();

                double executionTimeSeconds = (endTime - startTime) / 1000.0;

                new WindowResult(Best_Sol, listObjects, listSacks, executionTimeSeconds, "GA");   

            }

        });
        SolGA.setFocusable(false);


        SolBSO = new JButton();
        SolBSO.setText("BSO");
        SolBSO.setBounds(275, 725, 100, 100);
        SolBSO.setFont(Text_Font);
        SolBSO.setHorizontalAlignment(JLabel.CENTER);
        SolBSO.setVerticalAlignment(JLabel.CENTER);
        SolBSO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] matrix = initMatrix(listSacks.size(), listObjects.size());
                Node initialState = new Node(matrix, 0, 0);
                long startTime = System.currentTimeMillis();

                // ArrayList <KpObject> listObject = initObjectList(7, 20, 25);
                ArrayList <KnapSack2> listSac = new ArrayList<KnapSack2>();
                
                for (int i = 0; i < listSacks.size(); i++) {
                    KnapSack2 new_sac = new KnapSack2(listSacks.get(i).getId(), listSacks.get(i).getKnapSackCapacity());
                    listSac.add(new_sac);
                }

                Node Solution = bso(6, 300, 50, 10, listSac, listObjects);
                long endTime = System.currentTimeMillis();

                double executionTimeSeconds = (endTime - startTime) / 1000.0;

                new WindowResult(Solution, listObjects, listSacks, executionTimeSeconds, "BSO");
            }

        });
        SolBSO.setFocusable(false);

        // ajout des elements :
        this.add(Titre);
        this.add(MenuSacs);
        this.add(MenuObjets);
        this.add(MenuAfficher);
        this.add(SolBFS);
        this.add(SolDFS);
        this.add(SolA);
        this.add(SolGA);
        this.add(SolBSO);
        this.setVisible(true);
    }

}
