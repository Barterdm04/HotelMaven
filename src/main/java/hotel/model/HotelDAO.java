/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DB7
 */
public class HotelDAO implements IHotelDAO {
    private DBAccessorStrategy db;

    public HotelDAO(DBAccessorStrategy db) {
        this.db = db;
    }
    
    public HotelDAO (){
        
    }
    
    @Override
    public List<Hotel> getAllHotels(String orderByField) throws Exception {
        db.openConnection();
        
        List<Map> rawData = new ArrayList<Map>();
        List<Hotel> records = new ArrayList<Hotel>();
        
        try{
            rawData = db.findRecords("Select * From Hotel Order By " + orderByField, true);
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);

        } catch (Exception e) {
            throw new ClassNotFoundException(e.getMessage(), e);
        }
        
        Hotel hotel = null;
        
        for (Map recMap : rawData){
            hotel = new Hotel();
            hotel.setHotelId(Integer.valueOf(recMap.get("hotel_id").toString()));
            hotel.setHotelName(recMap.get("hotel_name").toString());
            hotel.setStreetAddress(recMap.get("street_address").toString());
            hotel.setCity(recMap.get("city").toString());
            hotel.setState(recMap.get("state").toString());
            hotel.setPostalCode(recMap.get("postal_code").toString());
            hotel.setNotes(recMap.get("notes").toString());
            
            records.add(hotel);
        }
        return records;
    }

    @Override
    public List<Hotel> getHotelsBySearch(String searchField, String searchValue, String orderByField) throws Exception {
        db.openConnection();
        
        List<Map> rawData = new ArrayList<Map>();
        List<Hotel> records = new ArrayList<Hotel>();
        
        try{
            rawData = db.findRecords("Select * From Hotel Where " + searchField + " LIKE '%" + searchValue + "%' ORDER BY " + orderByField, true);
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);

        } catch (Exception e) {
            throw new ClassNotFoundException(e.getMessage(), e);
        }
        
        Hotel hotel = null;
        
        for (Map recMap : rawData){
            hotel = new Hotel();
            hotel.setHotelId(Integer.valueOf(recMap.get("hotel_id").toString()));
            hotel.setHotelName(recMap.get("hotel_name").toString());
            hotel.setStreetAddress(recMap.get("street_address").toString());
            hotel.setCity(recMap.get("city").toString());
            hotel.setState(recMap.get("state").toString());
            hotel.setPostalCode(recMap.get("postal_code").toString());
            hotel.setNotes(recMap.get("notes").toString());
            
            records.add(hotel);
        }
        return records;
    }
    
    @Override
    public Hotel getHotelByID(int id) throws Exception{
        db.openConnection();
        
        String tableName = "HOTEL";
        String primaryKeyField = "hotel_id";
        
        Map recMap;
        try {
            recMap = db.getRecordByPrimaryKey(tableName, primaryKeyField, id, true);
        } catch (SQLException e1) {
            throw new SQLException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new Exception(e2.getMessage(), e2);
        }

        Hotel hotel = new Hotel();
        hotel.setHotelId(Integer.valueOf(recMap.get("hotel_id").toString()));
        hotel.setHotelName(recMap.get("hotel_name").toString());
        hotel.setStreetAddress(recMap.get("street_address").toString());
        hotel.setCity(recMap.get("city").toString());
        hotel.setState(recMap.get("state").toString());
        hotel.setPostalCode(recMap.get("postal_code").toString());
        hotel.setNotes(recMap.get("notes").toString());
        return hotel;

    }
    
    @Override
    public void SaveHotel(Hotel hotel) throws Exception{
        db.openConnection();
        
        String tableName = "HOTEL";
        List<String> fieldList =
                new ArrayList<String>();
        fieldList.add("hotel_name");
        fieldList.add("street_address");
        fieldList.add("city");
        fieldList.add("state");
        fieldList.add("postal_code");
        fieldList.add("notes");

        List fieldValues =
                new ArrayList();
        fieldValues.add(hotel.getHotelName());
        fieldValues.add(hotel.getStreetAddress());
        fieldValues.add(hotel.getCity());
        fieldValues.add(hotel.getState());
        fieldValues.add(hotel.getPostalCode());
        fieldValues.add(hotel.getNotes());

        try {
            // if the id is null, it's a new record, else it's an update
            if (hotel.getHotelId() == 0) {
                db.insertRecord(
                        tableName, fieldList,
                        fieldValues, true);
            } else {
                db.updateRecords(
                        tableName, fieldList,
                        fieldValues, "hotel_id", hotel.getHotelId(), true);
            }
        } catch (SQLException e1) {
            throw new SQLException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new Exception(e2.getMessage(), e2);
        }
    }
    
    @Override
    public void DeleteHotel(Hotel hotel, String deleteFromKey) throws Exception {
        db.openConnection();
        
        try {
            db.deleteRecords("Hotel", deleteFromKey, hotel.getHotelId(), true);
        } catch (SQLException e1) {
            throw new SQLException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new Exception(e2.getMessage(), e2);
        }
    }
    
    @Override
    public DBAccessorStrategy getDb() {
        return db;
    }

    @Override
    public void setDb(DBAccessorStrategy db) {
        this.db = db;
    }
    
}
