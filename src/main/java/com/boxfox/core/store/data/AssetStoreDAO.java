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

    private SimpleAssetDTO createSimpleAssetDTO(ResultSet rs) {

        return new SimpleAssetDTO();
    }

    private AssetDTO createAssetDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String date = rs.getDate("date").toString();
        String content = rs.getString("content");
        String license = rs.getString("license");
        String uid = rs.getString("uid");
        int view = rs.getInt("view");
        int price = rs.getInt("price");
        boolean openToStore = rs.getBoolean("openToStore");
        return new AssetDTO(id, name, date, content, license, uid, view, price, openToStore);
    }
}
