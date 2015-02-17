<%-- 
    Document   : hotels
    Created on : Feb 10, 2015, 12:30:14 PM
    Author     : DB7
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="hotel.model.Hotel"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link href='<%= request.getContextPath() + "/Content/Styles/jquery-ui.theme.css"%>' rel="stylesheet" type="text/css"/>
        <link href='<%= request.getContextPath() + "/Content/Styles/hotels.css"%>' rel="stylesheet" type="text/css"/>
        
        <title>Hotel List</title>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
        <script src='<%= request.getContextPath() + "/Content/Scripts/hotels.js"%>' type="text/javascript"></script>
    </head>
    
    <body>
        <h3>Hotel Directory</h3>
        <div id="tabs">
            <ul>
                <c:forEach var="hotel" items="${hotelList}" varStatus="rowCount">
                    <li><a href="#tabs-${hotel.hotelId}" class="clickableTab">${hotel.hotelName}</a></li>
                </c:forEach>
            </ul>
            <c:forEach var="hotel" items="${hotelList}" varStatus="rowCount">
                <div id="tabs-${hotel.hotelId}">
                        <div id="editHotel">
                            <form id="formSave" name="formSave" method="POST" action='<%= request.getContextPath() + "/HotelController?action=saveHotel"%>'>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td>Hotel ID</td>
                                            <td>${hotel.hotelId}<input type="hidden" name="hotelId" value="${hotel.hotelId}"</td>
                                        </tr>
                                        <tr>
                                            <td>Hotel Name</td>
                                            <td><input type="text" name="hotelName" value="${hotel.hotelName}"></td>
                                        </tr>
                                        <tr>
                                            <td>Street Address</td>
                                            <td><input type="text" name="streetAddress" value="${hotel.streetAddress}"></td>
                                        </tr>
                                        <tr>
                                            <td>City</td>
                                            <td><input type="text" name="city" value="${hotel.city}"></td>
                                        </tr>
                                        <tr>
                                            <td>State</td> 
                                            <td><input type="text" name="state" value="${hotel.state}"></td>
                                        </tr>
                                        <tr>
                                            <td>Postal Code</td>
                                            <td><input type="text" name="postalCode" value="${hotel.postalCode}"></td>
                                        </tr>
                                        <tr>
                                            <td>Notes</td>
                                            <td><textarea name="notes" value="${hotel.notes}" cols="40" rows="6">${hotel.notes}</textarea></td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                <input type="submit" name="submit" class="btn btn-lg btn-primary" value="Save Hotel" />
                            </form>
                            <form class="confirm" id="formDelete" name="formDelete" method="POST" action='<%= request.getContextPath() + "/HotelController?action=deleteHotel"%>'>
                                <input type="text" name="hotelId" value="${hotel.hotelId}" hidden>
                                <input type="submit" name="btnDelete" id="btnDelete" class="btn btn-lg btn-warning" value ="Delete Hotel"/>
                            </form>
                        </div>   
                </div>   
            </c:forEach>
        </div>      
        <input type="button" name="addHotel" id="addHotel" class="btn btn-lg btn-success" value="Add Hotel" />
        <div id="addHotelForm">
            <br>
            <form id="formAdd" name="formAdd" method="POST" action='<%= request.getContextPath() + "/HotelController?action=saveHotel"%>'>
                <table style='width:60%'>
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
                                <td>Not yet assigned<input type="text" name="hotelId" value="0" hidden></td>
                                <td><input type="text" name="hotelName" placeholder="Hotel Name"></td>
                                <td><input type="text" name="streetAddress" placeholder="Street Address"></td>
                                <td><input type="text" name="city" placeholder="City"></td>
                                <td><input type="text" name="state" placeholder="State"></td>
                                <td><input type="text" name="postalCode" placeholder="Zip Code"></td>
                                <td><input type="text" name="notes" placeholder="Notes"></td>
                            </tr>
                        </tbody>
                    </table>
                <br>
                <input type="submit" name="submit" class="btn btn-lg btn-success" value="Save Hotel" />
            </form>
        </div>
    </body>
</html>
<script>
    $(function(){
        $(".confirm").click(function(){
        return window.confirm("Are you sure you want to delete this Hotel?");
    });
    });
</script>