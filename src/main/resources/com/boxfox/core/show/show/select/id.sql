SELECT showid FROM `show` WHERE uid = (select uid from user where email = ? ) AND idx = ?