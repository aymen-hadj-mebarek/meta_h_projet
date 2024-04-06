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

import Classes.KpObject;

public class WindowObjects extends JFrame {
    JLabel Titre;
    JLabel Nom_F;        
    JLabel Capacite_F;
    JLabel Value_F;

    JTextField Nom_E;        
    JTextField Value_E;        
    JTextField Capacite_E;

    JButton Confirmer;
    JButton ajouter;

    public WindowObjects(ArrayList<KpObject> listObjects) {
        Font Title_font = new Font("Cosmos", Font.BOLD, 30);
        Font Text_Font = new Font("Cosmos", Font.PLAIN, 20);

        Titre = new JLabel("Ajouter un Objet");
        Nom_F = new JLabel("Saisie le nom d'objet :");
        Capacite_F = new JLabel("Saisie le poids d'objet :");
        Value_F = new JLabel("Saisie la valeur d'objet :");
        ajouter = new JButton("Ajouter un autre");
        Confirmer = new JButton("Terminer");
        Nom_E = new JTextField();                
        Capacite_E = new JTextField();
        Value_E = new JTextField();

        this.setTitle("Ajouter un Objet");
        this.setSize(400, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);

        Titre.setBounds(0, 0, 400, 75);
        Titre.setFont(Title_font);
        Titre.setHorizontalAlignment(JLabel.CENTER);
        Titre.setVerticalAlignment(JLabel.CENTER);

        Nom_F.setBounds(0, 75, 400, 50);
        Nom_F.setFont(Text_Font);
        Nom_F.setHorizontalAlignment(JLabel.CENTER);
        Nom_F.setVerticalAlignment(JLabel.CENTER);
        
        Nom_E.setBounds(125, 125, 150, 50);
        Nom_E.setFont(Text_Font);
        Nom_E.setHorizontalAlignment(JLabel.LEFT);
                        

        Capacite_F.setBounds(0, 175, 400, 50);
        Capacite_F.setFont(Text_Font);
        Capacite_F.setHorizontalAlignment(JLabel.CENTER);
        Capacite_F.setVerticalAlignment(JLabel.CENTER);                

        Capacite_E.setBounds(125, 225, 150, 50);
        Capacite_E.setFont(Text_Font);
        Capacite_E.setHorizontalAlignment(JLabel.LEFT);

        Value_F.setBounds(0, 275, 400, 50);
        Value_F.setFont(Text_Font);
        Value_F.setHorizontalAlignment(JLabel.CENTER);
        Value_F.setVerticalAlignment(JLabel.CENTER);                

        Value_E.setBounds(125, 325, 150, 50);
        Value_E.setFont(Text_Font);
        Value_E.setHorizontalAlignment(JLabel.LEFT);

        ajouter.setBounds(100, 390, 200, 50);
        ajouter.setFont(Text_Font);
        ajouter.setHorizontalAlignment(JLabel.CENTER);
        ajouter.setVerticalAlignment(JLabel.CENTER);
        ajouter.setFocusable(false);
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Capacite_E.getText().length() == 0 || Nom_E.getText().length() == 0 || Value_E.getText().length() == 0){                        
                    JOptionPane.showMessageDialog(null, "Erreur veuillez Remplir toutes les champs",
                            "ERROR!", JOptionPane.WARNING_MESSAGE);
                }else{
                    KpObject sac = new KpObject(Nom_E.getText(), Integer.valueOf(Capacite_E.getText()), Integer.valueOf(Value_E.getText()) );
                    listObjects.add(sac);
                    dispose();
                    new WindowObjects(listObjects);
                }
            }
        });

        Confirmer.setBounds(125, 450, 150, 50);
        Confirmer.setFont(Text_Font);
        Confirmer.setHorizontalAlignment(JLabel.CENTER);
        Confirmer.setVerticalAlignment(JLabel.CENTER);
        Confirmer.setFocusable(false);
        Confirmer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Capacite_E.getText().length() == 0 || Nom_E.getText().length() == 0 || Value_E.getText().length() == 0){                        
                    JOptionPane.showMessageDialog(null, "Erreur veuillez Remplir toutes les champs",
                            "ERROR!", JOptionPane.WARNING_MESSAGE);
                }else{
                    KpObject sac = new KpObject(Nom_E.getText(), Integer.valueOf(Capacite_E.getText()), Integer.valueOf(Value_E.getText()) );
                    listObjects.add(sac);
                    dispose();
                }
            }
        });

        this.add(Titre);
        this.add(Nom_F);        
        this.add(Nom_E);        
        this.add(Capacite_F);        
        this.add(Capacite_E);        
        this.add(Value_F);        
        this.add(Value_E);        
        this.add(ajouter);
        this.add(Confirmer);
        this.setVisible(true);
    }
}
