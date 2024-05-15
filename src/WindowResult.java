
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.*;

import Classes.KnapSack;
import Classes.KnapSack2;
import Classes.KpObject;
import Classes.Node;

import java.awt.*;
import java.awt.event.*;

public class WindowResult extends JFrame {

    public static int Space_left(Node node, ArrayList<KnapSack> listSac, ArrayList<KpObject> listObject, int indice){
        int S = listSac.get(indice)._capacity;
            for (int j = 0; j < node._matrix[indice].length; j++) {
                if(node._matrix[indice][j] == 1){
                    S -= listObject.get(j).getWeight();
                }
            }
        return S;
    }

    public static int total_val(Node node, ArrayList<KnapSack> listSac, ArrayList<KpObject> listObject, int indice){
        int S = 0;
            for (int j = 0; j < node._matrix[indice].length; j++) {
                if(node._matrix[indice][j] == 1){
                    S += listObject.get(j).getValue();
                }
            }
        return S;
    }


    JLabel Titre;
    JLabel Time;
    JTable table;    
    JTable table_sacs;
    JButton Retour;

    public WindowResult(Node Sol,ArrayList<KpObject> listObjects, ArrayList<KnapSack> listSacks, double time, String type) {

        
        Font Title_font = new Font("Cosmos", Font.BOLD, 50);
        Font Text_font = new Font("Cosmos", Font.PLAIN, 20);

        Titre = new JLabel("Resultat de "+type);
        Time = new JLabel("Le temps d'execution est de : "+time+" s");
        Retour = new JButton("Retour");

        this.setTitle("Resultat de "+type);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        Titre.setBounds(0, 0, 500, 75);
        Titre.setFont(Title_font);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setVerticalAlignment(JLabel.CENTER);

        Time.setBounds(0, 375, 500, 75);
        Time.setFont(Text_font);
        Time.setHorizontalAlignment(JLabel.CENTER);
        Time.setVerticalAlignment(JLabel.CENTER);

        String[] heads = { "Numero du Sac", "Numero D'objet", "Valeur d'objet", "Poids d'objet" };
        String[][] infos = new String[listObjects.size()][heads.length];
        System.out.println(Sol._matrix[0].length);
        int ind = 0;

        for (int i = 0; i < Sol._matrix.length-1; i++) {
            for (int j = 0; j < Sol._matrix[i].length; j++) {
                System.out.println(i);
                if (Sol._matrix[i][j] == 1) {
                    infos[ind][0] = String.valueOf(i);
                    infos[ind][1] = String.valueOf(listObjects.get(j).getId());
                    infos[ind][2] = String.valueOf(listObjects.get(j).getValue());
                    infos[ind][3] = String.valueOf(listObjects.get(j).getWeight());
                    ind ++;
                }
            }
        }


        table = new JTable(infos, heads);
        table.setEnabled(false);
        JScrollPane panel = new JScrollPane(table);
        panel.setPreferredSize(new Dimension(400, 175));
        panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(panel, BorderLayout.CENTER);


        String[] Sacs_heads = { "Numero du Sac", "Capacite du Sac", "Capacite restante", "valeur total du sac" };
        String[][] Sacs_infos = new String[listSacks.size()][4];

        ListIterator<KnapSack> f = listSacks.listIterator();
        int i = 0;
        while (f.hasNext()) {
                f.next();
                Sacs_infos[i][0] = listSacks.get(i).toString().split(",")[0];
                Sacs_infos[i][1] = listSacks.get(i).toString().split(",")[1];
                Sacs_infos[i][2] = String.valueOf(Space_left(Sol, listSacks, listObjects, i));
                Sacs_infos[i][3] = String.valueOf(total_val(Sol, listSacks, listObjects, i));

                i++;    
            
        }

        table_sacs = new JTable(Sacs_infos, Sacs_heads);
        table_sacs.setEnabled(false);
        JScrollPane panel_sacs = new JScrollPane(table_sacs);
        panel_sacs.setPreferredSize(new Dimension(400, 100));
        panel_sacs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(panel_sacs, BorderLayout.CENTER);

        Retour.setBounds(200, 400, 100, 50);
        Retour.setFont(Text_font);
        Retour.setHorizontalAlignment(JLabel.CENTER);
        Retour.setVerticalAlignment(JLabel.CENTER);
        Retour.setFocusable(false);
        Retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(Titre);
        this.add(Retour);
        this.add(panel_sacs);
        this.add(panel);        
        this.add(Time);        
        this.setVisible(true);
    }
}
