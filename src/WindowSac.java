import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Classes.KnapSack;

public class WindowSac extends JFrame {
    JLabel Titre;    
    JLabel Capacite_F;
    
    JTextField Capacite_E;

    JButton Confirmer;
    JButton ajouter;

    public WindowSac(ArrayList<KnapSack> listSacks, int i) {
        Font Title_font = new Font("Cosmos", Font.BOLD, 30);
        Font Text_Font = new Font("Cosmos", Font.PLAIN, 20);

        Titre = new JLabel("Ajouter un Sac");        
        Capacite_F = new JLabel("Saisie la capacité de Sac numero "+i+" : ");
        ajouter = new JButton("Ajouter un autre");
        Confirmer = new JButton("Terminer");        
        Capacite_E = new JTextField();

        this.setTitle("Ajouter un Sac");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);

        Titre.setBounds(0, 0, 400, 75);
        Titre.setFont(Title_font);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setVerticalAlignment(JLabel.CENTER);
                                
                                
                                

        Capacite_F.setBounds(0, 125, 400, 50);
        Capacite_F.setFont(Text_Font);
        Capacite_F.setHorizontalAlignment(JLabel.CENTER);
        Capacite_F.setVerticalAlignment(JLabel.CENTER);
                        
                        
                        

        Capacite_E.setBounds(125, 175, 150, 50);
        Capacite_E.setFont(Text_Font);
        Capacite_E.setHorizontalAlignment(JLabel.LEFT);

        ajouter.setBounds(100, 325, 200, 50);
        ajouter.setFont(Text_Font);
        ajouter.setHorizontalAlignment(JLabel.CENTER);
        ajouter.setVerticalAlignment(JLabel.CENTER);
        ajouter.setFocusable(false);
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Capacite_E.getText().length() == 0){                        
                    JOptionPane.showMessageDialog(null, "Erreur veuillez Remplir toutes les champs",
                            "ERROR!", JOptionPane.WARNING_MESSAGE);
                }else{
                    int capacite = Integer.valueOf(Capacite_E.getText());   
                    KnapSack sac = new KnapSack(capacite);
                    listSacks.add(sac);
                    dispose();
                    new WindowSac(listSacks, KnapSack._total);
                }
            }
        });

        Confirmer.setBounds(125, 400, 150, 50);
        Confirmer.setFont(Text_Font);
        Confirmer.setHorizontalAlignment(JLabel.CENTER);
        Confirmer.setVerticalAlignment(JLabel.CENTER);
        Confirmer.setFocusable(false);
        Confirmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Capacite_E.getText().length() == 0){                        
                    JOptionPane.showMessageDialog(null, "Erreur veuillez Remplir toutes les champs",
                            "ERROR!", JOptionPane.WARNING_MESSAGE);
                }else{
                    int capacite = Integer.valueOf(Capacite_E.getText());   
                    KnapSack sac = new KnapSack(capacite);
                    listSacks.add(sac);
                    dispose();
                }
            }
        });

        this.add(Titre);        
        this.add(Capacite_F);                
        this.add(Capacite_E);        
        this.add(ajouter);
        this.add(Confirmer);
        this.setVisible(true);
    }
}
