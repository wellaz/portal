package com.equation.database.connection.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 *
 * @author Wellington
 */
public class MainDatabaseConnectionPool {
	public Statement stmt, stmt1, stm;
	public Connection conn, conn1;
	public ResultSet rs, rs1;
	public PreparedStatement pstmt;

	public MainDatabaseConnectionPool() {
		return;
	}

	public void connectionDb() {

		try {
			String url = "jdbc:mysql://localhost:3306/system_dataset";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, "root", "");
			conn1 = DriverManager.getConnection(url, "root", "");
			stmt = conn1.createStatement();
			stm = conn.createStatement();
			stmt1 = conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			new Notification("Connected", "Connection Established", Notification.Type.TRAY_NOTIFICATION)
					.show(Page.getCurrent());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException sqle) {
			sqle.printStackTrace();
			new Notification("Offline Mode", "Connection Failed!", Notification.Type.ERROR_MESSAGE)
					.show(Page.getCurrent());
		}
	}
}