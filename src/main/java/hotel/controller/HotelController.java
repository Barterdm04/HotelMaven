/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.Hotel;
import hotel.model.HotelService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DB7
 */
@WebServlet(name = "HotelController", urlPatterns = {"/HotelController"})
public class HotelController extends HttpServlet {
    private final static String RESULT_PAGE = "/hotel/hotels.jsp";  
    private final static String SAVE_HOTEL = "saveHotel";
    private final static String DELETE_HOTEL = "deleteHotel";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        //create new hotel service
        HotelService hs = new HotelService();
        RequestDispatcher view;
        
        if(request.getParameter("action") != null){
            switch(request.getParameter("action")){
                case SAVE_HOTEL:
                    Hotel hotel = new Hotel();
                    hotel.setHotelId(Integer.parseInt(request.getParameter("hotelId")));
                    hotel.setHotelName(request.getParameter("hotelName"));
                    hotel.setStreetAddress(request.getParameter("streetAddress"));
                    hotel.setCity(request.getParameter("city"));
                    hotel.setState(request.getParameter("state"));
                    hotel.setPostalCode(request.getParameter("postalCode"));
                    hotel.setNotes(request.getParameter("notes"));

                    hs.saveHotel(hotel);
                    break;
                case DELETE_HOTEL:
                    Hotel hotelDelete = new Hotel();

                    hotelDelete.setHotelId(Integer.parseInt(request.getParameter("hotelId")));
                    hs.deleteHotelbyId(hotelDelete);
                    break;
                default:
                    break;
            }     
        }
           
        List<Hotel> hotelList = hs.getAllHotels();
        request.setAttribute("hotelList", hotelList);
        view = request.getRequestDispatcher(RESULT_PAGE);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
