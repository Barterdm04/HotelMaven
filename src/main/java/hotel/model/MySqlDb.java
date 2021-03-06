/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DB7
 */
public class MySqlDb implements DBAccessorStrategy{
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public MySqlDb(String driverClassName, String url, String userName, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public void openConnection() throws SQLException, ClassNotFoundException {
        String msg = "Error: url is null or zero length!";
	if( url == null || url.length() == 0 ) throw new IllegalArgumentException(msg);
	userName = (userName == null) ? "" : userName;
	password = (password == null) ? "" : password;
	Class.forName (driverClassName);
	conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public List findRecords(String sqlString, boolean closeConnection) throws SQLException, Exception {
        Statement statement = null;
	ResultSet resultSet = null;
	ResultSetMetaData metaData = null;
	final List list = new ArrayList();
	Map record = null;

	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sqlString);
		metaData = rs.getMetaData();
		final int fields = metaData.getColumnCount();

		while( rs.next() ) {
			record = new HashMap();
			for( int i=1; i <= fields; i++ ) {
				try {
					record.put( metaData.getColumnName(i), rs.getObject(i) );
				} catch(NullPointerException npe) { 
					
				}
			}
			list.add(record);
		}
		} catch (SQLException sqle) {
        		throw sqle;
                } catch (Exception e) {
			throw e;
		} finally {
			try {
                            stmt.close();
                            if(closeConnection) conn.close();
			} catch(SQLException e) {
                            throw e;
			}
		}
		return list;
    }
    
    @Override
    public Map getRecordByPrimaryKey(String table, String primaryKeyField, Object keyValue, boolean closeConnection)
	throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		final Map record=new HashMap();

		// do this in an excpetion handler so that we can depend on the
		// finally clause to close the connection
		try {
			String sql = "Select * From " + table + "WHERE " + primaryKeyField + " = ?";
                        pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, keyValue);
                        
			rs = pstmt.executeQuery(sql);
			metaData = rs.getMetaData();
			metaData.getColumnCount();
			final int fields=metaData.getColumnCount();

			if(rs.next() ) {
				for( int i=1; i <= fields; i++ ) {
					record.put( metaData.getColumnName(i), rs.getObject(i) );
				}
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
				if(closeConnection) conn.close();
			} catch(SQLException e) {
				throw e;
			}
		}

		return record;
	}
    
    @Override
    public boolean insertRecord(String tableName, List<String> fieldList, List<Object> valueList, boolean closeConnection) throws SQLException{
        PreparedStatement pstmt = null;
	int recsUpdated = 0;

	try {
            pstmt = buildInsertStatement(conn, tableName, fieldList);

		final Iterator i = valueList.iterator();
		int index = 1;
		while( i.hasNext() ) {
			final Object obj=i.next();
			if(obj instanceof String){
				pstmt.setString( index++,(String)obj );
			} else if(obj instanceof Integer ){
				pstmt.setInt( index++,((Integer)obj).intValue() );
			} else if(obj instanceof Long ){
				pstmt.setLong( index++,((Long)obj).longValue() );
			} else if(obj instanceof Double ){
				pstmt.setDouble( index++,((Double)obj).doubleValue() );
			} else if(obj instanceof java.sql.Date ){
				pstmt.setDate(index++, (java.sql.Date)obj );
			} else if(obj instanceof Boolean ){
				pstmt.setBoolean(index++, ((Boolean)obj).booleanValue() );
			} else {
				if(obj != null) pstmt.setObject(index++, obj);
			}
		}
		recsUpdated = pstmt.executeUpdate();
	} catch (SQLException sqle) {
                throw sqle;
	} catch (Exception e) {
		throw e;
	} finally {
		try {
                    pstmt.close();
                    if(closeConnection) conn.close();
		} catch(SQLException e) {
                    throw e;
		}
	} 
        if(recsUpdated == 1){
            return true;
	} else {
            return false;
	}
    }
    @Override
    public int updateRecords(String tableName, List<String> fieldList, List<Object> valueList, String whereField, Object whereValue, boolean closeConnection)
		throws SQLException, Exception
    {
	PreparedStatement pstmt = null;
	int recsUpdated = 0;
		// do this in an excpetion handler so that we can depend on the
	// finally clause to close the connection
	try {
            pstmt = buildUpdateStatement(conn, tableName, fieldList, whereField);

            final Iterator i = valueList.iterator();
            int index = 1;
            boolean doWhereValueFlag = false;
            Object obj = null;

            while( i.hasNext() || doWhereValueFlag) {
		if(!doWhereValueFlag){ obj = i.next();}
		if(obj instanceof String){
                    pstmt.setString( index++,(String)obj );
		} else if(obj instanceof Integer ){
                    pstmt.setInt( index++,((Integer)obj).intValue() );
		} else if(obj instanceof Long ){
                    pstmt.setLong( index++,((Long)obj).longValue() );
		} else if(obj instanceof Double ){
                    pstmt.setDouble( index++,((Double)obj).doubleValue() );
                } else if(obj instanceof java.sql.Timestamp ){
                    pstmt.setTimestamp(index++, (java.sql.Timestamp)obj );
		} else if(obj instanceof java.sql.Date ){
                    pstmt.setDate(index++, (java.sql.Date)obj );
		} else if(obj instanceof Boolean ){
                    pstmt.setBoolean(index++, ((Boolean)obj).booleanValue() );
		} else {
                    if(obj != null) pstmt.setObject(index++, obj);
		}

		if(doWhereValueFlag){ break;} 
		if(!i.hasNext() ) {          
                    doWhereValueFlag = true;
                    obj = whereValue;
		}
            }

            recsUpdated = pstmt.executeUpdate();
            } catch (SQLException sqle) {
                throw sqle;
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    pstmt.close();
                    if(closeConnection) conn.close();
                } catch(SQLException e) {
                    throw e;
                }
            }
            return recsUpdated;
    }    
    
    public int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection)
	throws SQLException, Exception
	{
            PreparedStatement pstmt = null;
            int recsDeleted = 0;

            try {
		pstmt = buildDeleteStatement(conn, tableName, whereField);

		if(whereField != null ) {
                    if(whereValue instanceof String){
			pstmt.setString( 1,(String)whereValue );
                    } else if(whereValue instanceof Integer ){
			pstmt.setInt( 1,((Integer)whereValue).intValue() );
                    } else if(whereValue instanceof Long ){
                    	pstmt.setLong( 1,((Long)whereValue).longValue() );
                    } else if(whereValue instanceof Double ){
                    	pstmt.setDouble( 1,((Double)whereValue).doubleValue() );
                    } else if(whereValue instanceof java.sql.Date ){
			pstmt.setDate(1, (java.sql.Date)whereValue );
                    } else if(whereValue instanceof Boolean ){
			pstmt.setBoolean(1, ((Boolean)whereValue).booleanValue() );
                    } else {
			if(whereValue != null) pstmt.setObject(1, whereValue);
                    }
		}

		recsDeleted = pstmt.executeUpdate();

            } catch (SQLException sqle) {
		throw sqle;
            } catch (Exception e) {
		throw e;
            } finally {
		try {
                    pstmt.close();
                    if(closeConnection) conn.close();
                        } catch(SQLException e) {
                            throw e;
			}
		}
            return recsDeleted;
	}
    
    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
	throws SQLException {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		(sql.append(tableName)).append(" (");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(", ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
		for( int j = 0; j < colDescriptors.size(); j++ ) {
			sql.append("?, ");
		}
		final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
		return conn_loc.prepareStatement(finalSQL);
	}
    
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colDescriptors, String whereField)
	throws SQLException {
		StringBuffer sql = new StringBuffer("UPDATE ");
		(sql.append(tableName)).append(" SET ");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(" = ?, ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) );
		((sql.append(" WHERE ")).append(whereField)).append(" = ?");
		final String finalSQL=sql.toString();
		return conn_loc.prepareStatement(finalSQL);
    }

    private PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String whereField)
	throws SQLException {
		final StringBuffer sql=new StringBuffer("DELETE FROM ");
		sql.append(tableName);

		// delete all records if whereField is null
		if(whereField != null ) {
			sql.append(" WHERE ");
			(sql.append( whereField )).append(" = ?");
		}

		final String finalSQL=sql.toString();
		return conn_loc.prepareStatement(finalSQL);
    }

    
    
}
