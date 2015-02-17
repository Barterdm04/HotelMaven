/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author DB7
 */
public interface IHotelDAO {
    public abstract DBAccessorStrategy getDb();
    public abstract void setDb(DBAccessorStrategy db);
    public abstract List<Hotel> getAllHotels() throws Exception;
    public abstract void SaveHotel(Hotel hotel) throws Exception;
    public abstract void DeleteHotelById(Hotel hotel) throws Exception;
    public abstract Hotel getHotelByID(int id) throws Exception;
    
}
