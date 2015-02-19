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
public class HotelService {
    private IHotelDAO hotelDao;
    
    public HotelService(DBAccessorStrategy db){
        hotelDao = new HotelDAO(db);
    }
    
    public List<Hotel> getAllHotels() throws Exception{
        return hotelDao.getAllHotels();
    }
    
    public Hotel getHotelByID(int id) throws Exception{
        return hotelDao.getHotelByID(id);
    }
    
    public void saveHotel(Hotel hotel) throws Exception{
        hotelDao.SaveHotel(hotel);
    }
    
    public void deleteHotelbyId(Hotel hotel, String deleteFromKey) throws Exception{
        hotelDao.DeleteHotel(hotel, deleteFromKey);
    }    
}
