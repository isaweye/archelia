package uk.mqchinee.featherlib.sql.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class SQLUpdateColumn {

	private final Map<String, String> columns = new LinkedHashMap<>();
	private final Object id;

	protected SQLUpdateColumn(Object id) {
		this.id = id;
	}

	public SQLUpdateColumn putData(final String where, final Object newValue) {
		columns.put(where,newValue.toString());
		return this;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public Object getId() {
		return id;
	}
}
