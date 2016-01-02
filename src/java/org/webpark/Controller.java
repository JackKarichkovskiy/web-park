/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.webpark.dao.CRUDDaoInterface;
import org.webpark.dao.DaoFactory;
import org.webpark.dao.entities.Plant;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class Controller extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        InitialContext initCtx = null;
        try {
            initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/WebPark");
            // Allocate and use a connection from the pool
            Connection conn = ds.getConnection();

            DaoFactory factory = DaoFactory.getInstance(DaoFactory.DaoType.MYSQL);

            CRUDDaoInterface crudDao = factory.getCRUDDao();
            
            //INSERT
//            Plant plant = new Plant();
//            plant.setId(UUID.randomUUID());
//            plant.setId(UUID.fromString("a6f40940-a8c0-4975-a365-c8a6e30d04c2"));
//            plant.setName("Updated Rose");
//            plant.setOrigin("China");
//            plant.setColor("Red");
//            plant.setSector(40);
//            try {
//                crudDao.insert(plant);
//            } catch (DAOException ex) {
//                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            //DELETE
            Plant delPlant = new Plant();
            delPlant.setId(UUID.fromString("cb2cb2b1-085f-4f28-be7b-8b199e9ef8d3"));
            try {
                crudDao.delete(delPlant);
            } catch (DAOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //READ
            Plant read = null;
            try {
                read = crudDao.read(Plant.class, UUID.fromString("a6f40940-a8c0-4975-a365-c8a6e30d04c2"));
            } catch (DAOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Controller</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
                out.println("<br>");
                out.println(read.toString());
                out.println("</body>");
                out.println("</html>");
            }

            conn.close();
        } catch (NamingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
