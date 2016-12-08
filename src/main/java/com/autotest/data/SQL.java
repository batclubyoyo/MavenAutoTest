package com.autotest.data;

import com.autotest.utils.LogRecord;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;

/**
 * Created by batcl on 10/15/2016.
 * Usage:
 */
public class SQL {

    private Logger logger = LogRecord.getLogger();

    private boolean MYSQL = true;
    private String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private int local_port = 3306;

    private String db_name = null;

    private String db_user = null;
    private String db_user_pwd = null;
    private String db_host = null;
    private int db_port = 3306;

    private boolean using_ssh = false;

    private String ssh_user = null;
    private String ssh_user_pwd = null;
    private String ssh_host = null;
    private int ssh_port = 22;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result_set = null;

    public SQL() {

    }

    public SQL(String db_name) {
        if (!this.MYSQL) {
            this.DB_DRIVER = "org.mariadb.jdbc.Driver";
        }
        this.db_name = db_name;
    }

    public SQL(String db_name, boolean using_mysql) {
        this.MYSQL = using_mysql;
        if (!this.MYSQL) {
            this.DB_DRIVER = "org.mariadb.jdbc.Driver";
        }
        this.db_name = db_name;
    }

    private void initConnectionString(String db_name, String db_user, String db_user_pwd, String db_host, String db_port,
                                      String ssh_user, String ssh_user_pwd, String ssh_host, String ssh_port,
                                      String local_port) {
        this.db_name = db_name;
        this.db_user = db_user;
        this.db_user_pwd = db_user_pwd;
        this.db_host = db_host;
        this.db_port = Integer.parseInt(db_port);

        this.ssh_user = ssh_user;
        this.ssh_user_pwd = ssh_user_pwd;
        this.ssh_host = ssh_host;
        this.ssh_port = Integer.parseInt(ssh_port);

        this.local_port = Integer.parseInt(local_port);
    }

    private void initConnectionString(String db_name, String db_user, String db_user_pwd, String db_host, String db_port,
                                      String local_port) {
        this.db_name = db_name;
        this.db_user = db_user;
        this.db_user_pwd = db_user_pwd;
        this.db_host = db_host;
        this.db_port = Integer.parseInt(db_port);

        this.local_port = Integer.parseInt(local_port);
    }

    private void initConnectionString() {
        Map<String, String> map = XmlUtils.getDBMap("db", this.db_name, "");

        this.db_user = map.get("db_user");
        this.db_user_pwd = map.get("db_user_pwd");
        this.db_host = map.get("db_host");
        this.db_port = Integer.parseInt(map.get("db_port"));

        if (map.get("ssh").equals("y")) {
            this.using_ssh = true;
            this.ssh_user = map.get("ssh_user");
            this.ssh_user_pwd = map.get("ssh_user_pwd");
            this.ssh_host = map.get("ssh_host");
            this.ssh_port = Integer.parseInt(map.get("ssh_port"));
        }

        this.local_port = Integer.parseInt(map.get("local_port"));
    }

    private void connectSSH() {
        if (this.using_ssh) {
            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(this.ssh_user, this.ssh_host, this.ssh_port);
                session.setPassword(this.ssh_user_pwd);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                System.out.println(session.getServerVersion());

                int assign_port = session.setPortForwardingL(this.local_port, this.db_host, this.db_port);
                logger.info("Port forward from local port: " + assign_port + " to database: " + this.db_host + ":" + this.db_port);
            } catch (JSchException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void connectDB() {
        String connect_url;
        if (this.using_ssh) {
            if (!this.MYSQL) {
                connect_url = "jdbc:mariadb://localhost:" + this.local_port + "/" + this.db_name + "?";
            } else {
                connect_url = "jdbc:mysql://localhost:" + this.local_port + "/" + this.db_name + "?";
            }
        } else {
            if (!this.MYSQL) {
                connect_url = "jdbc:mariadb://" + this.db_host + ":" + this.db_port + "/" + this.db_name + "?";
            } else {
                connect_url = "jdbc:mysql://" + this.db_host + ":" + this.db_port + "/" + this.db_name + "?";
            }
        }

        try {
            Class.forName(this.DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(connect_url, this.db_user, this.db_user_pwd);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private void connectServer() {
        disConnect();
        connectSSH();
        connectDB();
    }

    /*
    1. Index in MetaData of the column name begins with 1, not 0
    2. Row first, Column second
     */
    private void executeSyntax() {
        String syntax = "select * from buyproject order by id desc limit 1";
        try {
            statement = connection.createStatement();
            result_set = statement.executeQuery(syntax);
            ResultSetMetaData resultSetMetaData = result_set.getMetaData();
            while (result_set.next()) {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    String column = resultSetMetaData.getColumnName(i);
                    System.out.print(result_set.getString(column) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            disConnect();
        }
    }

    private void disConnect() {
        try {
            if (result_set != null) {
                result_set.isClosed();
            }
            if (statement != null) {
                statement.isClosed();
            }
            if (connection != null) {
                connection.isClosed();
            }
        } catch (SQLException e) {
            logger.error("Close database error!\n" + e.getMessage());
        } finally {
            result_set = null;
            statement = null;
            connection = null;
        }

    }

    public void operateDB() {
        initConnectionString();
        connectServer();
        executeSyntax();
    }

}
