package Model.dao.impl;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Conexao conexaoSingleton;
    private Connection connection;

    private Conexao() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/desafiotres", "root", "root");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.");
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar a classe de conexão.");
        }
    }

    public static Conexao getInstance() {
        if (conexaoSingleton == null) {
            conexaoSingleton = new Conexao();
        }
        return conexaoSingleton;
    }

    public Connection getConnection() {
        return connection;
    }

}
