package Interface;

import DB.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Connexion");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom d'utilisateur :"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Mot de passe :"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Se connecter");
        btnLogin.addActionListener(e -> authenticate());
        panel.add(new JLabel(""));
        panel.add(btnLogin);

        add(panel);
        setVisible(true);
    }

    private void authenticate() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new InterfaceGUI(userId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect !");
            }
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.");
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}