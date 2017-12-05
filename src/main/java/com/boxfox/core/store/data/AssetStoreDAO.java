package com.boxfox.core.store.data;

import com.boxfox.support.data.AbstractDAO;
import com.boxfox.support.data.Database;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssetStoreDAO extends AbstractDAO {

    public AssetDTO createAsset(String uid) {
        AssetDTO result = null;
        String query = Database.getQueryFromResource("create.sql");
        try {
            ResultSet rs = Database.executeQuery(query, uid);
            if (rs.next()) {
                result = createAssetDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateAsset(int id, String name, String date, String content, String license, boolean openToStore) {
        boolean result = false;
        String query = Database.getQueryFromResource("update/update.sql");
        try {
            int count = Database.executeUpdate(query, id, name, date, content, license, openToStore);
            result = count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateAssetCode(int id, String html, String css, String js) {
        boolean result = false;
        String query = Database.getQueryFromResource("update/updateCode.sql");
        try {
            int count = Database.executeUpdate(query, id, html, css, js);
            result = count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteAsset(int id) {
        boolean result = false;
        String query = Database.getQueryFromResource("delete.sql");
        try {
            int count = Database.executeUpdate(query, id);
            result = count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public AssetDTO getAsset(int id) {
        AssetDTO result = null;
        String query = Database.getQueryFromResource("select/select.sql");
        try {
            ResultSet rs = Database.executeQuery(query, id);
            if (rs.next()) {
                result = createAssetDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public AssetCodeDTO getAssetCode(int id) {
        AssetCodeDTO result = null;
        String query = Database.getQueryFromResource("select/selectCode.sql");
        try {
            ResultSet rs = Database.executeQuery(query, id);
            if (rs.next()) {
                String html = rs.getString("html");
                String css = rs.getString("css");
                String js = rs.getString("js");
                result = new AssetCodeDTO(html, css, js);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public SimpleAssetDTO[] getSimpleAssetList(int page, int sum) {
        List<SimpleAssetDTO> result = new ArrayList();
        String query = Database.getQueryFromResource("select/selectSimpleList.sql");
        try {
            ResultSet rs = Database.executeQuery(query, page, sum);
            while (rs.next()) {
                result.add(createSimpleAssetDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toArray(new SimpleAssetDTO[result.size()]);
    }

    public AssetDTO[] getAssetList(int page, int sum) {
        List<AssetDTO> result = new ArrayList();
        String query = Database.getQueryFromResource("select/selectList.sql");
        try {
            ResultSet rs = Database.executeQuery(query, page, sum);
            while (rs.next()) {
                result.add(createAssetDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toArray(new AssetDTO[result.size()]);
    }

    private SimpleAssetDTO createSimpleAssetDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String date = rs.getDate("date").toString();
        String content = rs.getString("content");
        String license = rs.getString("license");
        String email = rs.getString("email");
        int view = rs.getInt("view");
        int star = rs.getInt("star");
        boolean openToStore = rs.getBoolean("openToStore");
        return new SimpleAssetDTO(id, name, date, email);
    }

    private AssetDTO createAssetDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String date = rs.getDate("date").toString();
        String content = rs.getString("content");
        String license = rs.getString("license");
        String email = rs.getString("email");
        int view = rs.getInt("view");
        int star = rs.getInt("star");
        boolean openToStore = rs.getBoolean("openToStore");
        String html = rs.getString("html");
        String css = rs.getString("css");
        String js = rs.getString("js");
        return new AssetDTO(id, name, date, content, license, email, view, star, openToStore, html, css, js);
    }
}
