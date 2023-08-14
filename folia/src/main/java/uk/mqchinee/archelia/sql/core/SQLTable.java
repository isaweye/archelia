package uk.mqchinee.archelia.sql.core;

import uk.mqchinee.archelia.sql.SQLHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLTable {

	private final HashMap<String, SQLField> columns = new LinkedHashMap();
	private final String tableName;
	private final String iDField;

	public SQLTable(final String tableName, final String iDField) {
		this.tableName = tableName;
		this.iDField = iDField;
	}

	public SQLTable addField(final String fieldName, final DataType dataType, final boolean isNotNull, final boolean isUnique, final boolean isPrimaryKey) {
		columns.put(fieldName, new SQLField(fieldName, dataType, isNotNull, isUnique, isPrimaryKey));
		return this;
	}

	public SQLTable addField(final String fieldName, final DataType dataType) {
		columns.put(fieldName, new SQLField(fieldName, dataType, false, false, false));
		return this;
	}

	protected Set<Map.Entry<String, SQLField>> getColumns() {
		return columns.entrySet();
	}


	protected int columnAmount() {
		return columns.size();
	}

	protected List<String> getColumnNames() {
		return new ArrayList(columns.keySet());
	}

	public String getTableName() {
		return tableName;
	}

	public SQLUpdateColumn createUpdate(Object id) {
		return new SQLUpdateColumn(id);
	}

	public SQLTable create() {
		SQLHandler.sqlUpdate(SQLUtils.getCreateCommand(this));
		return this;
	}

	public String getString(String idValue, String column) {
		final String sql = "SELECT " + column + " FROM " + getTableName() + " WHERE " + iDField + " = '" + idValue + "'";
		final ResultSet rs = SQLHandler.sqlQuery(sql);
		String result = null;
		try {
			while (rs.next()) {
				result = rs.getString(column);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public Integer getInteger(String idValue, String column) {
		final String sql = "SELECT " + column + " FROM " + getTableName() + " WHERE " + iDField + " = '" + idValue + "'";
		final ResultSet rs = SQLHandler.sqlQuery(sql);
		Integer result = null;
		try {
			while (rs.next()) {
				result = rs.getInt(column);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public long getLong(String idValue, String column) {
		final String sql = "SELECT " + column + " FROM " + getTableName() + " WHERE " + iDField + " = '" + idValue + "'";
		final ResultSet rs = SQLHandler.sqlQuery(sql);
		long result = 0;
		try {
			while (rs.next()) {
				result = rs.getInt(column);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public Boolean getBoolean(String idValue, String column) {
		final String sql = "SELECT " + column + " FROM " + getTableName() + " WHERE " + iDField + " = '" + idValue + "'";
		final ResultSet rs = SQLHandler.sqlQuery(sql);
		Boolean result = null;
		try {
			while (rs.next()) {
				result = rs.getBoolean(column);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public Double getDouble(String idValue, String column) {
		final String sql = "SELECT " + column + " FROM " + getTableName() + " WHERE " + iDField + " = '" + idValue + "'";
		final ResultSet rs = SQLHandler.sqlQuery(sql);
		Double result = null;
		try {
			while (rs.next()) {
				result = rs.getDouble(column);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public ResultSet selectAll() {
		final String sql = "SELECT * FROM " + getTableName();
		return SQLHandler.sqlQuery(sql);
	}

	public ResultSet selectAF(String field) {
		final String sql = "SELECT " + field + " FROM " + getTableName();
		return SQLHandler.sqlQuery(sql);
	}

	public ResultSet select(String idValue) {
		return select(iDField, idValue);
	}

	public ResultSet select(String column, String columnValue) {
		final String sql = "SELECT * FROM " + getTableName() + " WHERE " + column + " = '" + columnValue + "'";
		return SQLHandler.sqlQuery(sql);
	}

	public ResultSet selectColumns(String columns) {
		final String sql = "SELECT "+ columns +" FROM " + getTableName();
		return SQLHandler.sqlQuery(sql);
	}

	public void update(SQLUpdateColumn sqlUpdateColumn) {
		final String sql = SQLUtils.getUpdateCommand(getTableName(), iDField, sqlUpdateColumn.getId(), sqlUpdateColumn);
		SQLHandler.sqlUpdate(sql);
	}

	public void delete(Object id) {
		final String sql = "DELETE FROM " + getTableName() + " WHERE " + iDField + " = '" + id + "'";
		SQLHandler.sqlUpdate(sql);
	}


	public void increaseValue(final String idValue, final String fieldName, final int amount) {
		SQLHandler.sqlUpdate("UPDATE " + getTableName() + " SET " + fieldName + " = " + fieldName + " + " + amount + " WHERE " + iDField + " = '" + idValue + "'");
	}

	public void decreaseValue(final String idValue, final String fieldName, final int amount) {
		SQLHandler.sqlUpdate("UPDATE " + getTableName() + " SET " + fieldName + " = " + fieldName + " - " + amount + " WHERE " + iDField + " = '" + idValue + "'");
	}


	public boolean insertOrUpdate(SQLUpdateColumn sqlUpdateColumn) {
		final boolean exist = exist(sqlUpdateColumn.getId());
		if (exist) {
			update(sqlUpdateColumn);
		} else {
			insert(sqlUpdateColumn);
		}
		return exist;
	}

	public boolean exist(final Object whereValue) {
		return getSingleValue(select(iDField, whereValue.toString()), iDField) != null;
	}

	public void insert(SQLUpdateColumn sqlUpdateColumn) {
		final String sql = SQLUtils.getInsertCommand(getTableName(), sqlUpdateColumn);
		SQLHandler.sqlUpdate(sql);
	}

	public ResultSet getHighest(final String fieldName) {
		return getHighest(fieldName, 0);
	}

	public ResultSet getHighest(final String fieldName, final int limit) {
		final String sql = "SELECT * FROM " + getTableName() + " ORDER BY " + fieldName + " DESC" + (limit > 0 ? " LIMIT " + limit : "");
		return SQLHandler.sqlQuery(sql);
	}

	public ResultSet getLowest(final String fieldName) {
		return getLowest(fieldName, 0);
	}

	public ResultSet getLowest(final String fieldName, final int limit) {
		final String sql = "SELECT * FROM " + getTableName() + " ORDER BY " + fieldName + " ASC" + (limit > 0 ? " LIMIT " + limit : "");
		return SQLHandler.sqlQuery(sql);
	}

	public String getSingleHighest(final String fieldName) {
		final ResultSet rs = getHighest(fieldName, 1);
		return getSingleValue(rs, fieldName);
	}


	public String getSingleLowest(final String fieldName) {
		final ResultSet rs = getLowest(fieldName, 1);
		return getSingleValue(rs, fieldName);

	}

	private String getSingleValue(final ResultSet rs, final String fieldName) {
		try {
			String result = null;
			while (rs.next()) {
				result = rs.getString(fieldName);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
