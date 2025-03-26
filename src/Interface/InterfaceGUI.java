package Interface;

import dao.implem.TaskDaoImpl;
import entity.Task;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGUI extends JFrame implements ActionListener, ListSelectionListener {
    private int userId;
    private JTextField txtTitle, txtDescription, txtDueDate, txtStatus;
    private JButton btnAjouter, btnModifier, btnSupprimer;
    private JTable jTable;
    private DefaultTableModel tableModel;
    private TaskDaoImpl taskDao;

    public InterfaceGUI(int userId) {
        this.userId = userId;
        setTitle("Gestion des Tâches");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        taskDao = new TaskDaoImpl();

        tableModel = new DefaultTableModel(new String[]{"ID", "Titre", "Description", "Date d'échéance", "Statut"}, 0);
        jTable = new JTable(tableModel);
        ListSelectionModel listSelectionModel = jTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(jTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(new TitledBorder("Informations de la tâche"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels et champs
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Titre :"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; txtTitle = new JTextField(20); panelForm.add(txtTitle, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Description :"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtDescription = new JTextField(20); panelForm.add(txtDescription, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Date d'échéance (YYYY-MM-DD) :"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; txtDueDate = new JTextField(20); panelForm.add(txtDueDate, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Statut :"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; txtStatus = new JTextField(20); panelForm.add(txtStatus, gbc);

        // Boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(panelBoutons, gbc);

        add(panelForm, BorderLayout.SOUTH);

        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);

        chargerTaches();
    }

    private void viderChamps() {
        txtTitle.setText("");
        txtDescription.setText("");
        txtDueDate.setText("");
        txtStatus.setText("");
    }

    private void ajouterTache() {
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        String dueDate = txtDueDate.getText();
        String status = txtStatus.getText();

        if (!title.isEmpty() && !dueDate.isEmpty() && !status.isEmpty()) {
            taskDao.insert(new Task(0, userId, title, description, dueDate, status));
            chargerTaches();
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les champs obligatoires (Titre, Date, Statut) !");
        }
    }

    private void modifierTache() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            String dueDate = txtDueDate.getText();
            String status = txtStatus.getText();

            if (!title.isEmpty() && !dueDate.isEmpty() && !status.isEmpty()) {
                taskDao.update(new Task(id, userId, title, description, dueDate, status));
                chargerTaches();
                viderChamps();
                JOptionPane.showMessageDialog(this, "Tâche modifiée avec succès");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir les champs obligatoires !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une tâche à modifier !");
        }
    }

    private void supprimerTache() {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            taskDao.deleteById(id);
            chargerTaches();
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une tâche à supprimer !");
        }
    }

    private void chargerTaches() {
        tableModel.setRowCount(0);
        for (Task t : taskDao.findAllByUserId(userId)) {
            tableModel.addRow(new Object[]{t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getStatus()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ajouter")) {
            ajouterTache();
        } else if (e.getActionCommand().equals("Modifier")) {
            modifierTache();
        } else if (e.getActionCommand().equals("Supprimer")) {
            supprimerTache();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = jTable.getSelectedRow();
            if (selectedRow != -1) {
                txtTitle.setText(jTable.getValueAt(selectedRow, 1).toString());
                txtDescription.setText(jTable.getValueAt(selectedRow, 2) != null ? jTable.getValueAt(selectedRow, 2).toString() : "");
                txtDueDate.setText(jTable.getValueAt(selectedRow, 3).toString());
                txtStatus.setText(jTable.getValueAt(selectedRow, 4).toString());
            }
        }
    }
}