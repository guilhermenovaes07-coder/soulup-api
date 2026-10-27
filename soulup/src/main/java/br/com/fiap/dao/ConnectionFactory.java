package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final ThreadLocal<Connection> connection = new ThreadLocal<>();

    public static void closeConnection() {
        try {
            Connection atual = connection.get();
            if (atual != null && !atual.isClosed()) {
                atual.close();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            connection.remove();
        }
    }

    public static Connection getConnection() {
        try {
            Connection atual = connection.get();
            if (atual != null && !atual.isClosed()) {
                return atual;
            }
            Class.forName("oracle.jdbc.OracleDriver");

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Variáveis de ambiente do banco não configuradas!");
            }

            atual = DriverManager.getConnection(url, user, password);
            connection.set(atual);
            return atual;
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar no banco de dados");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro nome da classe: " + e.getMessage());
            throw new RuntimeException("Driver do Oracle não encontrado");
        }
    }
}
