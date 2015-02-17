<%-- 
    Document   : edithotel
    Created on : Feb 16, 2015, 2:18:42 PM
    Author     : DB7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Hotel - ${hotel.hotelName}</title>
    </head>
    <body>
        <div id="editHotel">
                            <form id="formSave" name="formSave" method="POST" action='<%= request.getContextPath() + "/HotelController?action=saveHotel"%>'>
                                <table>
                                    <thead>
                                        <th>Hotel ID</th>
                                        <th>Hotel Name</th>
                                        <th>Street Address</th>
                                        <th>City</th>
                                        <th>State</th>
                                        <th>Postal Code</th>
                                        <th>Notes</th>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${hotel.hotelId}<input type="hidden" name="hotelId" value="${hotel.hotelId}"</td>
                                            <td><input type="text" name="hotelName" value="${hotel.hotelName}"></td>
                                            <td><input type="text" name="streetAddress" value="${hotel.streetAddress}"></td>
                                            <td><input type="text" name="city" value="${hotel.city}"></td>
                                            <td><input type="text" name="state" value="${hotel.state}"></td>
                                            <td><input type="text" name="postalCode" value="${hotel.postalCode}"></td>
                                            <td><input type="text" name="notes" value="${hotel.notes}"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <input type="submit" name="submit" class="btn btn-lg" value="Save Hotel" />
                            </form>
                        </div>

    </body>
</html>
