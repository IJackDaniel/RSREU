package ru.rsreu.afonin0817.datalayer;

import java.sql.SQLException;
import ru.rsreu.afonin0817.datalayer.oracledb.OracleDBDAOFactory;
import ru.rsreu.afonin0817.datalayer.xml.XMLDAOFactory;

public enum DBType {
	XML {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory xmlDAOFactory = new XMLDAOFactory();
			return xmlDAOFactory;
		}
	},
	ORACLE {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory oracleDBDAOFactory = null;
			try {
				oracleDBDAOFactory = OracleDBDAOFactory.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oracleDBDAOFactory;
		}
	};

	public static DBType getTypeByName(String dbType) {
		try {
			return DBType.valueOf(dbType.toUpperCase());
		} catch (Exception e) {
			throw new DBTypeException();
		}
	}

	public abstract DAOFactory getDAOFactory();
}