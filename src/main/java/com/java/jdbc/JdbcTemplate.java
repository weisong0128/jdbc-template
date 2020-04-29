package com.java.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * description: 增删改查
 * author: ws
 * time: 2020/4/29 22:58
 */
public class JdbcTemplate {
    public static Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private static JdbcTemplate jdbcTemplate = null;

    private JdbcTemplate() {
    }

    public static JdbcTemplate getInstance() {
        if(jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate();
        }
        return jdbcTemplate;
    }

    public boolean insert(String sql) {
        boolean f = false;
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = JdbcConnectionPool.getConnection();
            ps = conn.prepareStatement(sql);
            f = ps.execute();
        } catch (SQLException e) {
//            e.printStackTrace();
            logger.error("[JDBC Exception] -->"
                            + "Can not insert, the exceprion message is:" + e.getMessage());
        }  finally {
            try {
                if (null != ps) {
                    ps.close();
                }
            } catch (SQLException e) {
//                e.printStackTrace();
                logger.error("[JDBC Exception] -->"
                                + "Failed to close connection, the exceprion message is:" + e.getMessage());
            }

        }
        return f;

    }

    public void insert(String sql, List<List<String>> data) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = JdbcConnectionPool.getConnection();
            ps = conn.prepareStatement(sql);
            JdbcConnectionPool.startTransaction();
            for (int i = 0; i < data.size(); i++) {
                List<String> list = data.get(i);
                for (int j = 0; j < list.size(); j++) {
                    ps.setObject(j+1, list.get(j));
                }
                ps.addBatch();
            }
            ps.executeBatch();
            ps.clearBatch();
            JdbcConnectionPool.commit();

        } catch (SQLException e) {
//            e.printStackTrace();
            logger.error("[JDBC Exception] -->"
                            + "Can not insert, the exceprion message is:" + e.getMessage());
        } finally {
            try {
                if (null != ps){
                    ps.close();
                }
                JdbcConnectionPool.closeConnection();
            } catch (SQLException e) {
//                e.printStackTrace();
                logger.error("[JDBC Exception] -->"
                                + "Failed to close connection, the exceprion message is:" + e.getMessage());
            }
        }
        return;

    }


}
