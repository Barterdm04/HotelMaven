/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.controller;

import hotel.model.DBAccessorStrategy;
import hotel.model.Hotel;
import hotel.model.HotelService;
import hotel.model.MySessionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author DB7
 */
public class HotelController extends HttpServlet {
    private final static String RESULT_PAGE = "/hotel/hotels.jsp";  
    private final static String SAVE_HOTEL = "saveHotel";
    private final static String DELETE_HOTEL = "deleteHotel";
    private final static String DELETE_FROM_FIELD = "hotel_id";
    private final static String LOGIN = "logIn";
    private final static String LOGOUT = "logOut";

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
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        
        MySessionListener sl = new MySessionListener();
        
        String driverClassName = request.getServletContext().getInitParameter("driverName");
        String url = request.getServletContext().getInitParameter("url");
        String password = request.getServletContext().getInitParameter("pass");        
        String username = request.getServletContext().getInitParameter("userName");
        
        String className = request.getServletContext().getInitParameter("dbStrategy");
        Class clazz = Class.forName(className);
        Constructor constructor = clazz.getConstructor(new Class[]{String.class,String.class,String.class,String.class});
        DBAccessorStrategy db = (DBAccessorStrategy)constructor.newInstance(driverClassName,url,username,password);
   
        //create new hotel service
        HotelService hs = new HotelService(db);
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
                    hs.deleteHotelbyId(hotelDelete, DELETE_FROM_FIELD );
                    break;
                case LOGIN:
                    String sessionUser = request.getParameter("sessionName");
                    session.setAttribute("sessionUser", sessionUser);
                    break;
                case LOGOUT:
                    session.invalidate();
                    break;
                default:
                    break;
            }     
        }
           
        List<Hotel> hotelList = hs.getAllHotels();
        request.setAttribute("hotelList", hotelList);
        String sessionCount = Integer.toString(sl.getTotalSessions());
        request.setAttribute("activeSessionCount", sessionCount);
              
        
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
