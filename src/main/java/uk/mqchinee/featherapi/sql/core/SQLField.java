package uk.mqchinee.featherapi.sql.core;

public class SQLField {
	private final DataType dataType;
	private final boolean isNotNull;
	private final boolean isUnique;
	private final boolean isPrimaryKey;

	protected SQLField(final String fieldName, final DataType dataType, final boolean isNotNull, final boolean isUnique, final boolean isPrimaryKey) {
		this.dataType = dataType;
		this.isNotNull = isNotNull;
		this.isUnique = isUnique;
		this.isPrimaryKey = isPrimaryKey;
	}


	protected DataType getDataType() {
		return this.dataType;
	}

	protected boolean isNotNull() {
		return this.isNotNull;
	}

	protected boolean isUnique() {
		return this.isUnique;
	}

	protected boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}

}
