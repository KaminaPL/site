package org.example.auth;

import org.example.database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Account(int id, String name) {

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }



    public class AccountManager {

        private DatabaseConnection dbc;

        public AccountManager(DatabaseConnection dbc) {
            this.dbc = dbc;
        }

        //Dodawanie użytkownika do bazy danych
        public Boolean register(Account account, int connectionId) throws SQLException, ClassNotFoundException {
            String insert = "INSERT INTO accounts VALUES (?,?)";
            PreparedStatement statement = dbc.connections.get(connectionId).prepareStatement(insert);

            statement.setInt(1,account.id);
            statement.setString(2, account.name);
            statement.executeUpdate();

            return true;
        }
        //Autentykacja użytkownika
        public Boolean authenticate(Account account, int connectionId) throws SQLException, ClassNotFoundException {
            String select = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement statement = dbc.connections.get(connectionId).prepareStatement(select);
            statement.setInt(1, account.id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.getInt(1) == account.id && resultSet.getString(2).equals(account.name)) {
                System.out.println("Account exists.");
                return true;
            }

            System.out.println("Account does not exist.");
            return false;
        }
        //Zwracanie danych użytkownika z bazy danych
        public Account getAccount(int id, int connectionId) throws SQLException, ClassNotFoundException {
            String select = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement statement = dbc.connections.get(connectionId).prepareStatement(select);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Account account = new Account(resultSet.getInt(1), resultSet.getString(2));

            if(account.id != 0 && ! account.name.equals(null)) {
                return account;
            }

            System.err.println("Error: Account not found.");
            return null;
        }

    }


}
