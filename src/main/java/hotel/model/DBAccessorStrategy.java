/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DB7
 */
public interface DBAccessorStrategy {
    public abstract void openConnection(String driverClassName, String url, String username, String password) throws Exception;
    public abstract void closeConnection() throws SQLException;
    public abstract List findRecords(String sqlString, boolean closeConnection) throws Exception;
    public abstract boolean insertRecord(String tableName, List<String> fieldList, List<Object> valueList, boolean closeConnection) throws Exception;
    public abstract int updateRecords(String tableName, List<String> fieldList, List<Object> valueList, String whereField, Object whereValue, boolean closeConnection) throws Exception;
    public abstract int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection) throws Exception;
    public abstract Map getRecordByID(String table, String primaryKeyField, Object keyValue, boolean closeConnection) throws Exception;
}
