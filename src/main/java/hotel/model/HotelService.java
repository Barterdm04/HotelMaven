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
    
    public HotelService(){
        DBAccessorStrategy db = new MySqlDb();
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
    
    public void deleteHotelbyId(Hotel hotel) throws Exception{
        hotelDao.DeleteHotelById(hotel);
    }
    
    public static void main(String[] args) throws IllegalArgumentException, ClassNotFoundException, SQLException, Exception {
        HotelService hotelService = new HotelService();
        
        System.out.println("Get all hotels: \n");
        for (Hotel hotel : hotelService.getAllHotels()){
            System.out.println(hotel);
        }
        
        Hotel newHotel = new Hotel("New Hotel", "123 Fake Street", "Fakeville", "WI", "53000", "NO NOTES");
        
        System.out.println("Add new hotel: \n");
        hotelService.saveHotel(newHotel);
        newHotel.setHotelId(6);
        
        System.out.println("Get all hotels: \n");
        for (Hotel hotel : hotelService.getAllHotels()){
            System.out.println(hotel);
        }
        
        System.out.println("Update a hotel \n");
        
        for(Hotel hotel : hotelService.getAllHotels()){
            if(hotel.getHotelId() == 3){
                hotel.setHotelName("UPDATED HOTEL NAME");
                hotelService.saveHotel(hotel);
            }
        }
            
        for (Hotel hotel : hotelService.getAllHotels()){
            System.out.println(hotel);
        }
        
        System.out.println("Delete a hotel \n");
        hotelService.deleteHotelbyId(newHotel);
        for (Hotel hotel : hotelService.getAllHotels()){
            System.out.println(hotel);
        }
        
    }
    
}
