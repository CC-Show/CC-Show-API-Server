package com.boxfox.core.account.login;

import com.boxfox.support.data.AbstractDAO;
import com.boxfox.support.data.Database;
import com.boxfox.support.secure.AES256;
import com.boxfox.support.secure.SHA256;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoginPerformer extends AbstractDAO {

    public LoginDTO login(String email, String password) {
        LoginDTO loginDTO = null;
        String loginQuery = Database.getQueryFromResource("login.sql");
        try {
            ResultSet rs = Database.executeQuery(loginQuery, AES256.encrypt(email), SHA256.encrypt(password));
            if (rs.next() && rs.getInt(1) == 1) {
                String uid = AES256.decrypt(rs.getString("uid"));
                String jti = createJti(uid);
                loginDTO = new LoginDTO(uid, jti);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginDTO;
    }

    private String createJti(String uid) throws SQLException {
        String query = Database.getQueryFromResource("createJTI.sql");
        String uuid = UUID.randomUUID().toString();
        int count = Database.executeUpdate(query, SHA256.encrypt(uid), SHA256.encrypt(uuid));
        if (count == 1) {
            return uuid;
        } else {
            throw new SQLException();
        }
    }
}
