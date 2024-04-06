
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.*;

import Classes.KnapSack;
import Classes.KpObject;

import java.awt.*;
import java.awt.event.*;

public class WindowAffich extends JFrame {

    JLabel Titre;
    JTable table_sacs;
    JTable table_objs;
    JButton Retour;

    public WindowAffich(ArrayList<KpObject> listObjects, ArrayList<KnapSack> listSacks) {
        Font Title_font = new Font("Cosmos", Font.BOLD, 50);
        Font Text_Font = new Font("Cosmos", Font.PLAIN, 20);

        Titre = new JLabel("Listes des Donnees");
        Retour = new JButton("Retour");

        this.setTitle("Affichage des Donnees");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        Titre.setBounds(0, 0, 500, 75);
        Titre.setFont(Title_font);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setVerticalAlignment(JLabel.CENTER);

        String[] Sacs_heads = { "Numero du Sac", "Capacite du Sac" };
        String[][] Sacs_infos = new String[listSacks.size()][2];

        ListIterator<KnapSack> f = listSacks.listIterator();
        int i = 0;
        while (f.hasNext()) {
                f.next();
                Sacs_infos[i] = listSacks.get(i).toString().split(",");
                i++;    
            
        }

        table_sacs = new JTable(Sacs_infos, Sacs_heads);
        table_sacs.setEnabled(false);
        JScrollPane panel_sacs = new JScrollPane(table_sacs);
        panel_sacs.setPreferredSize(new Dimension(400, 100));
        panel_sacs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(panel_sacs, BorderLayout.CENTER);



        String[] objs_heads = { "Numero d'objet", "Nom d'objet", "Poids d'objet", "Valeur d'objet" };
        String[][] objs_infos = new String[listObjects.size()][2];

        ListIterator<KpObject> g = listObjects.listIterator();
        i = 0;
        while (g.hasNext()) {
                g.next();
                objs_infos[i] = listObjects.get(i).toString().split(",");
                i++;    
            
        }

        table_objs = new JTable(objs_infos, objs_heads);
        table_objs.setEnabled(false);
        JScrollPane panel_objs = new JScrollPane(table_objs);
        panel_objs.setPreferredSize(new Dimension(400, 200));
        panel_objs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(panel_objs, BorderLayout.CENTER);

        Retour.setBounds(200, 400, 100, 50);
        Retour.setFont(Text_Font);
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
        this.add(panel_objs);
        this.setVisible(true);
    }
}
