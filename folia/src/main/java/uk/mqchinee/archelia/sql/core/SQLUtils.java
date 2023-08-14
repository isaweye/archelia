package uk.mqchinee.archelia.sql.core;

import java.util.Collection;
import java.util.Map;

public class SQLUtils {

	public static void debug(String msg) {
		System.out.println(msg);
	}

	public static String getCreateCommand(final SQLTable sqlTable) {

		final StringBuilder builder = (new StringBuilder("CREATE TABLE IF NOT EXISTS '")).append(sqlTable.getTableName()).append("' (");

		int columnAmount = 0;
		for (Map.Entry<String, SQLField> entry : sqlTable.getColumns()) {
			builder.append("'").append(entry.getKey()).append("' ").append(entry.getValue().getDataType().toString());

			if (entry.getValue().isNotNull()) {
				builder.append(" NOT NULL");
			}
			if (entry.getValue().isUnique()) {
				builder.append(" UNIQUE");
			}

			if (sqlTable.columnAmount() != ++columnAmount) {
				builder.append(", ");
			}
		}

		int primaryKeys = 0;
		for (Map.Entry<String, SQLField> entry : sqlTable.getColumns()) {
			if (entry.getValue().isPrimaryKey()) {
				primaryKeys++;
				if (primaryKeys == 1) {
					builder.append(", PRIMARY KEY('").append(entry.getKey());
					continue;
				}
				builder.append(", '").append(entry.getKey()).append("'");
			}
		}

		if (primaryKeys != 0) {
			builder.append("')");
		}
		builder.append(")");

		return builder.toString();
	}

	public static String getUpdateCommand(final String table, final String where, final Object whereValue, final SQLUpdateColumn sqlupdateColumn) {
		final Map<String, String> columns = sqlupdateColumn.getColumns();
		final StringBuilder stringBuilder = (new StringBuilder("UPDATE ")).append(table).append(" SET ");
		int i = 0;
		for (String str : columns.keySet()) {
			i++;
			stringBuilder.append(str).append(" = ").append("'").append(columns.get(str)).append("'");
			if (i != columns.size()) stringBuilder.append(", ");
		}
		stringBuilder.append(" WHERE ").append(where).append(" = '").append(whereValue.toString()).append("'");

		return stringBuilder.toString();
	}

	public static String getInsertCommand(final String table, final SQLUpdateColumn sqlUpdateColumn) {
		final StringBuilder stringBuilder = (new StringBuilder("INSERT INTO ")).append(table).append(" VALUES(");
		final Collection<String> columns = sqlUpdateColumn.getColumns().values();
		int i = 0;
		for (String str : columns) {
			i++;
			stringBuilder.append("'").append(str).append("'");
			if (i != columns.size()) stringBuilder.append(", ");
		}
		stringBuilder.append(")");

		return stringBuilder.toString();
	}


}
