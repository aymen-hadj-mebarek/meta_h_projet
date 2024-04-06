
import java.util.ArrayList;

import javax.swing.*;

import Classes.KnapSack;
import Classes.KpObject;
import Classes.Node;

import java.awt.*;
import java.awt.event.*;

public class WindowResult extends JFrame {

    JLabel Titre;
    JLabel Time;
    JTable table;    
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
        for (int i = 0; i < Sol._matrix.length; i++) {
            for (int j = 0; j < Sol._matrix[i].length; j++) {
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
        panel.setPreferredSize(new Dimension(400, 275));
        panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(panel, BorderLayout.CENTER);



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
        this.add(panel);        
        this.add(Time);        
        this.setVisible(true);
    }
}
