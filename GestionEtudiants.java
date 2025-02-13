import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*;
public class GestionEtudiants extends JFrame { 
    private JTable jTable; 
    private JTextField txtID, txtPrenom, txtAge, txtVille, txtRecherche; 

    public GestionEtudiants() { // Constructeur de la classe
        setTitle("Gestion des Étudiants"); // Définition du titre de la fenêtre
        setSize(800, 600); // Définition de la taille de la fenêtre
        setResizable(false); // Empêche le redimensionnement de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Ferme l'application à la fermeture de la fenêtre
        setLayout(new BorderLayout()); // Utilise un layout en border pour organiser les composants

        // Panneau de saisie
        JPanel panelInput = new JPanel(); // Création d'un panneau pour la saisie
        panelInput.setLayout(new GridLayout(3, 4, 10, 10)); // Définit un layout en grille de 3 lignes et 4 colonnes
        Font font = new Font("Arial", Font.PLAIN, 16); // Définition de la police

        // Ajout des labels et champs de texte pour l'entrée
        panelInput.add(new JLabel("ID:")); // Ajout d'un label pour l'ID
        txtID = new JTextField(); // Création d'un champ de texte pour l'ID
        txtID.setFont(font); // Application de la police au champ de texte
        panelInput.add(txtID); // Ajout du champ de texte au panneau

        panelInput.add(new JLabel("Nom:")); // Ajout d'un label pour le prénom
        txtPrenom = new JTextField(); // Création d'un champ de texte pour le prénom
        txtPrenom.setFont(font); // Application de la police au champ de texte
        panelInput.add(txtPrenom); // Ajout du champ de texte au panneau

        panelInput.add(new JLabel("Age:")); // Ajout d'un label pour l'âge
        txtAge = new JTextField(); // Création d'un champ de texte pour l'âge
        txtAge.setFont(font); // Application de la police au champ de texte
        panelInput.add(txtAge); // Ajout du champ de texte au panneau

        panelInput.add(new JLabel("Ville:")); 
        txtVille = new JTextField(); 
        txtVille.setFont(font); 
        panelInput.add(txtVille); 

        // Boutons pour les actions
        JButton btnAjouter = new JButton("Ajouter"); // Création d'un bouton pour ajouter un étudiant
        btnAjouter.setFont(font); // Application de la police au bouton
        panelInput.add(btnAjouter); // Ajout du bouton au panneau de saisie

        JButton btnModifier = new JButton("Modifier"); 
        btnModifier.setFont(font);
        panelInput.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer"); 
        btnSupprimer.setFont(font);
        panelInput.add(btnSupprimer); 

        // Panneau de recherche
        JPanel panelSearch = new JPanel(); // Création d'un panneau pour la recherche
        panelSearch.add(new JLabel("Rechercher par ID:")); // Ajout d'un label pour la recherche par ID
        txtRecherche = new JTextField(10); // Création d'un champ de texte pour la recherche (10 colonnes)
        txtRecherche.setFont(font); // Application de la police au champ de texte
        panelSearch.add(txtRecherche); // Ajout du champ de texte au panneau de recherche

        JButton btnRechercher = new JButton("Ok"); // Création d'un bouton pour effectuer la recherche
        btnRechercher.setFont(font); // Application de la police au bouton
        panelSearch.add(btnRechercher); // Ajout du bouton au panneau de recherche

        // Modèle de table pour afficher les étudiants
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nom", "Age", "Ville"}, 0); // Création du modèle de table avec les colonnes
        jTable = new JTable(model); // Création de la JTable avec le modèle
        jTable.setFont(font); // Application de la police à la table
        jTable.setRowHeight(25); // Définition de la hauteur des lignes
        JScrollPane scrollPane = new JScrollPane(jTable); // Création d'un panneau de défilement pour la table

        // Ajout des panneaux à la fenêtre principale
        add(panelInput, BorderLayout.NORTH); // Ajout du panneau de saisie en haut de la fenêtre
        add(scrollPane, BorderLayout.CENTER); // Ajout de la table au centre de la fenêtre
        add(panelSearch, BorderLayout.SOUTH); // Ajout du panneau de recherche en bas de la fenêtre

        // Actions des boutons
        btnAjouter.addActionListener(e -> ajouterEtudiant(model)); // Action pour ajouter un étudiant
        btnModifier.addActionListener(e -> modifierEtudiant(model)); // Action pour modifier un étudiant
        btnSupprimer.addActionListener(e -> supprimerEtudiant(model)); // Action pour supprimer un étudiant
        btnRechercher.addActionListener(e -> chercherEtudiant(model)); // Action pour rechercher un étudiant
    }

    // Méthode pour ajouter un étudiant
    private void ajouterEtudiant(DefaultTableModel model) {
        try {
            int id = Integer.parseInt(txtID.getText()); // Convertir le texte de l'ID en entier
            if (id < 0) throw new NumberFormatException(); // Lancer une exception si l'ID est négatif
            String prenom = txtPrenom.getText(); // Obtenir le prénom
            int age = Integer.parseInt(txtAge.getText()); // Convertir le texte de l'âge en entier
            if (age < 0) throw new NumberFormatException(); // Lancer une exception si l'âge est négatif
            String ville = txtVille.getText(); // Obtenir la ville

            if (idExists(model, id)) { // Vérifier si l'ID existe déjà
                JOptionPane.showMessageDialog(this, "Erreur : L'ID doit être unique.", "Erreur d'unicité", JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode
            }

            // Ajouter l'étudiant au modèle
            model.addRow(new Object[]{id, prenom, age, ville}); // Ajouter une nouvelle ligne avec les données de l'étudiant
            clearFields(); // Effacer les champs de texte
        } catch (NumberFormatException e) { // Attraper les exceptions de format
            if (!isInteger(txtID.getText())) { // Vérifier si l'ID est un entier
                JOptionPane.showMessageDialog(this, "Erreur : L'ID doit être un entier valide.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : L'âge doit être un entier valide.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Méthode pour modifier un étudiant
    private void modifierEtudiant(DefaultTableModel model) {
        int selectedRow = jTable.getSelectedRow(); // Obtenir la ligne sélectionnée
        if (selectedRow != -1) { // Si une ligne est sélectionnée
            try {
                int id = Integer.parseInt(txtID.getText()); // Convertir le texte de l'ID en entier
                if (id < 0) throw new NumberFormatException(); // Lancer une exception si l'ID est négatif
                model.setValueAt(id, selectedRow, 0); // Mettre à jour l'ID dans la table
                model.setValueAt(txtPrenom.getText(), selectedRow, 1); // Mettre à jour le prénom dans la table
                int age = Integer.parseInt(txtAge.getText()); // Convertir le texte de l'âge en entier
                if (age < 0) throw new NumberFormatException(); // Lancer une exception si l'âge est négatif

                if (idExists(model, id) && !model.getValueAt(selectedRow, 0).equals(id)) {
                    JOptionPane.showMessageDialog(this, "Erreur : L'ID doit être unique.", "Erreur d'unicité", JOptionPane.ERROR_MESSAGE);
                    return; // Sortir de la méthode
                }

                model.setValueAt(age, selectedRow, 2); // Mettre à jour l'âge dans la table
                model.setValueAt(txtVille.getText(), selectedRow, 3); // Mettre à jour la ville dans la table
                clearFields(); // Effacer les champs de texte
            } catch (NumberFormatException e) { // Attraper les exceptions de format
                if (!isInteger(txtID.getText())) { // Vérifier si l'ID est un entier
                    JOptionPane.showMessageDialog(this, "Erreur : L'ID doit être un entier valide.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur : L'âge doit être un entier valide.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Méthode pour supprimer un étudiant
    private void supprimerEtudiant(DefaultTableModel model) {
        int selectedRow = jTable.getSelectedRow(); // Obtenir la ligne sélectionnée
        if (selectedRow != -1) { // Si une ligne est sélectionnée
            model.removeRow(selectedRow); // Supprimer la ligne de la table
        }
    }

    // Méthode pour chercher un étudiant par ID
    private void chercherEtudiant(DefaultTableModel model) {
        String idCherche = txtRecherche.getText(); // Obtenir l'ID à rechercher
        for (int i = 0; i < model.getRowCount(); i++) { // Parcourir les lignes du modèle
            if (model.getValueAt(i, 0).toString().equals(idCherche)) { // Vérifier si l'ID correspond
                jTable.setRowSelectionInterval(i, i); // Sélectionner la ligne correspondante dans la table
                return; // Sortir de la méthode
            }
        }
        JOptionPane.showMessageDialog(this, "Étudiant non trouvé."); // Message si non trouvé
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        txtID.setText(""); // Effacer le champ ID
        txtPrenom.setText(""); // Effacer le champ Prénom
        txtAge.setText(""); // Effacer le champ Âge
        txtVille.setText(""); // Effacer le champ Ville
        txtRecherche.setText(""); // Effacer le champ de recherche
    }

    // Méthode pour vérifier si une chaîne est un entier
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str); // Essayer de convertir la chaîne en entier
            return true; // Si réussi, retourner vrai
        } catch (NumberFormatException e) { // Attraper l'exception si la conversion échoue
            return false; // Si échoue, retourner faux
        }
    }

    // Méthode pour vérifier si un ID existe déjà
    private boolean idExists(DefaultTableModel model, int id) {
        for (int i = 0; i < model.getRowCount(); i++) { // Parcourir les lignes du modèle
            if (Integer.parseInt(model.getValueAt(i, 0).toString()) == id) { // Vérifier si l'ID correspond
                return true; // Retourner vrai si trouvé
            }
        }
        return false; // Retourner faux si non trouvé
    }

    public static void main(String[] args) {
        // Lancer l'application dans le thread de l'interface utilisateur
        SwingUtilities.invokeLater(() -> {
            new GestionEtudiants().setVisible(true); // Créer une instance de la classe et l'afficher
        });
    }
}