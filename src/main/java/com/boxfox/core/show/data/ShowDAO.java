package com.boxfox.core.show.data;

import com.boxfox.support.data.AbstractDAO;
import com.boxfox.support.data.Database;
import com.boxfox.support.secure.AES256;
import io.vertx.core.json.JsonArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowDAO extends AbstractDAO {

    /**
     * show create
     *
     * @param uid      user unique id
     * @param showName show name
     * @return result boolean
     */
    public boolean create(String uid, String showName) {
        boolean result = false;
        String query = Database.getQueryFromResource("show/create.sql");
        try {
            int idx = getShowCount(uid);
            int count = Database.executeUpdate(query, AES256.encrypt(uid), idx + 1, AES256.encrypt(showName));
            if (count > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getShowCount(String uid) throws SQLException {
        int count = 0;
        String query = Database.getQueryFromResource("show/count.sql");
        ResultSet rs = Database.executeQuery(query, AES256.encrypt(uid));
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    /**
     * delete show
     *
     * @param showId
     * @return result boolean
     */
    public boolean delete(int showId) {
        boolean result = false;
        String query = Database.getQueryFromResource("show/delete.sql");
        try {
            int count = Database.executeUpdate(query, showId);
            if (count > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * show information update
     *
     * @param showId
     * @param name         show name
     * @param sizeUnit     px or %
     * @param positionUnit px or %
     * @return result boolean
     */
    public boolean update(int showId, String name, String sizeUnit, String positionUnit) {
        boolean result = false;
        String query = Database.getQueryFromResource("show/update.sql");
        try {
            int count = Database.executeUpdate(query, AES256.encrypt(name), sizeUnit, positionUnit, showId);
            if (count == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * lookup show information
     *
     * @param showId
     * @return
     */
    public ShowDTO lookup(int showId) {
        ShowDTO result = null;
        String query = Database.getQueryFromResource("show/lookup.sql");
        try {
            ResultSet rs = Database.executeQuery(query, showId);
            if (rs.next()) {
                int idx = rs.getInt("idx");
                int selectedSlide = rs.getInt("selectedSlide");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String sizeUnit = rs.getString("sizeUnit");
                String positionUnit = rs.getString("positionUnit");
                result = new ShowDTO(showId, email, idx, selectedSlide, name, sizeUnit, positionUnit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * create slide
     *
     * @param showId
     * @return slide index
     */
    public int createSlide(int showId, String name) {
        int result = -1;
        String query = Database.getQueryFromResource("slide/create.sql");
        try {
            int idx = getSlideCount(showId);
            ResultSet rs = Database.executeQuery(query, showId, idx + 1, name);
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getSlideCount(int showid) throws SQLException {
        int count = 0;
        String query = Database.getQueryFromResource("slide/count.sql");
        ResultSet rs = Database.executeQuery(query, showid);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    /**
     * delete slide
     *
     * @param showId
     * @param slideIdx
     * @return
     */
    public boolean deleteSlide(int showId, int slideIdx) {
        boolean result = false;
        String query = Database.getQueryFromResource("slide/delete.sql");
        try {
            int count = Database.executeUpdate(query, showId, slideIdx);
            if (count > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get all slide data of show
     *
     * @param showId
     * @return
     */
    public SlideDTO[] slides(int showId) {
        List<SlideDTO> slideList = new ArrayList();
        String query = Database.getQueryFromResource("slide/select/list.sql");
        try {
            ResultSet rs = Database.executeQuery(query, showId);
            while (rs.next()) {
                slideList.add(createSlideDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slideList.toArray(new SlideDTO[slideList.size()]);
    }

    /**
     * get single slide data of show
     *
     * @param showId
     * @param slideIdx
     * @return
     */
    public SlideDTO slide(int showId, int slideIdx) {
        SlideDTO slide = null;
        String query = Database.getQueryFromResource("slide/select/select.sql");
        try {
            ResultSet rs = Database.executeQuery(query, showId, slideIdx);
            if (rs.next()) {
                slide = createSlideDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slide;
    }

    /**
     * create slide DTO from resultSet
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SlideDTO createSlideDTO(ResultSet rs) throws SQLException {
        int showId = rs.getInt("showid");
        int idx = rs.getInt("idx");
        String name = rs.getString("name");
        String note = rs.getString("note");
        int selectedAsset = rs.getInt("selectedAsset");
        JsonArray assets = new JsonArray(rs.getString("selectedAsset"));
        return new SlideDTO(showId, idx, name, note, selectedAsset, assets);
    }

    /**
     * get show id from email & show index of user
     *
     * @param email
     * @param showIdx
     * @return
     */
    public int getShowId(String email, int showIdx) {
        int showId = -1;
        String query = Database.getQueryFromResource("show/select/id.sql");
        try {
            ResultSet rs = Database.executeQuery(query, AES256.encrypt(email), showIdx);
            if (rs.next()) {
                showId = rs.getInt("showid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showId;
    }

    public boolean updateSlide(int showId, int slideIdx, String name, String note, String assets) {
        boolean result = false;
        String query = Database.getQueryFromResource("slide/update/update.sql");
        try {
            int count = Database.executeUpdate(query, name, note, assets, showId, slideIdx);
            if (count == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * update assets data of slide
     *
     * @param showId
     * @param slideIdx
     * @param assets   JsonArray as String
     * @return result boolean
     */
    public boolean updateSlideAssets(int showId, int slideIdx, String assets) {
        boolean result = false;
        String query = Database.getQueryFromResource("slide/update/assets.sql");
        try {
            int count = Database.executeUpdate(query, assets, showId, slideIdx);
            if (count == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * update slide name
     *
     * @param showId
     * @param slideIdx
     * @param name
     * @return resunt boolean
     */
    public boolean updateSlideName(int showId, int slideIdx, String name) {
        boolean result = false;
        String query = Database.getQueryFromResource("slide/update/name.sql");
        try {
            int count = Database.executeUpdate(query, name, showId, slideIdx);
            if (count == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * update slide note
     *
     * @param showId
     * @param slideIdx
     * @param note
     * @return resunt boolean
     */
    public boolean updateSlideNote(int showId, int slideIdx, String note) {
        boolean result = false;
        String query = Database.getQueryFromResource("slide/update/note.sql");
        try {
            int count = Database.executeUpdate(query, note, showId, slideIdx);
            if (count == 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
