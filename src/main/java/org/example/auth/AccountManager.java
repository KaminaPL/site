package org.example.auth;

import org.example.database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    private DatabaseConnection dbc;

    public AccountManager(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public Boolean register(Account account) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO accounts VALUES (?,?)";
        PreparedStatement statement = dbc.connection.prepareStatement(insert);

        statement.setInt(1,account.id);
        statement.setString(2, account.name);
        statement.executeUpdate();

        return true;
    }
    public Boolean authenticate(Account account) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM accounts WHERE id = ?";
        PreparedStatement statement = dbc.connection.prepareStatement(select);
        statement.setInt(1, account.id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.getInt(1) == account.id && resultSet.getString(2).equals(account.name)) {
            return true;
        }

        return false;
    }
    public Account getAccount(int id) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM accounts WHERE id = ?";
        PreparedStatement statement = dbc.connection.prepareStatement(select);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Account account = new Account(resultSet.getInt(1), resultSet.getString(2));

        if(account.id != 0 && ! account.name.equals(null)) {
            return account;
        }

        System.err.println("Account not found");
        return null;
    }

}
