package com.boxfox.core.account.register;

import com.boxfox.support.data.AbstractDAO;
import com.boxfox.support.data.Database;
import com.boxfox.support.secure.AES256;
import com.boxfox.support.secure.SHA256;

import java.sql.SQLException;

public class RegistPerformer extends AbstractDAO {

    public boolean register(String email, String password, String nickname) {
        boolean result = false;
        String registerQuery = Database.getQueryFromResource("register.sql");
        try {
            int count = Database.executeUpdate(registerQuery, AES256.encrypt(email), SHA256.encrypt(password), nickname);
            if (count > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
