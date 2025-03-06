package com.example.server.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.type.StandardBasicTypes;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        registerColumnType(java.sql.Types.BIT, "integer");
        registerColumnType(java.sql.Types.TINYINT, "tinyint");
        registerColumnType(java.sql.Types.SMALLINT, "smallint");
        registerColumnType(java.sql.Types.INTEGER, "integer");
        registerColumnType(java.sql.Types.BIGINT, "bigint");
        registerColumnType(java.sql.Types.FLOAT, "float");
        registerColumnType(java.sql.Types.REAL, "real");
        registerColumnType(java.sql.Types.DOUBLE, "double");
        registerColumnType(java.sql.Types.NUMERIC, "numeric");
        registerColumnType(java.sql.Types.DECIMAL, "decimal");
        registerColumnType(java.sql.Types.CHAR, "char");
        registerColumnType(java.sql.Types.VARCHAR, "varchar");
        registerColumnType(java.sql.Types.LONGVARCHAR, "longvarchar");
        registerColumnType(java.sql.Types.DATE, "date");
        registerColumnType(java.sql.Types.TIME, "time");
        registerColumnType(java.sql.Types.TIMESTAMP, "timestamp");
        registerColumnType(java.sql.Types.BINARY, "blob");
        registerColumnType(java.sql.Types.VARBINARY, "blob");
        registerColumnType(java.sql.Types.LONGVARBINARY, "blob");
        registerColumnType(java.sql.Types.BLOB, "blob");
        registerColumnType(java.sql.Types.CLOB, "clob");
        registerColumnType(java.sql.Types.BOOLEAN, "integer");

        registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
        registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        return "";
    }

    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
        return "";
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        return "";
    }

    @Override
    public String getAddColumnString() {
        return "add column";
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    @Override
    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public LimitHandler getLimitHandler() {
        return new AbstractLimitHandler() {
            @Override
            public String processSql(String sql, RowSelection selection) {
                final boolean hasOffset = selection != null && selection.getFirstRow() != null;
                return sql + (hasOffset ? " limit ? offset ?" : " limit ?");
            }

            @Override
            public boolean supportsLimit() {
                return true;
            }

            @Override
            public boolean bindLimitParametersInReverseOrder() {
                return true;
            }
        };
    }
}

class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) {
        return "integer primary key autoincrement";
    }

    @Override
    public boolean supportsInsertSelectIdentity() {
        return true;
    }
}