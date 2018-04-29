package application.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import application.SQLiteConnection;

public class UploadCSVModel {

	private final static Logger LOGGER = Logger.getLogger(UploadCSVModel.class.getName());

	Connection connection;

	public UploadCSVModel() {
		connection = SQLiteConnection.Connector();
		if (connection == null) {
			LOGGER.log(Level.WARNING, "No db connection established");

		}
	}

		public boolean isDBConnected() {
			try {
				return !connection.isClosed();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		}

	}

