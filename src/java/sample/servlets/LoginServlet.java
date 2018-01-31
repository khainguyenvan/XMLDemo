/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.util.XMLUlti;

/**
 *
 * @author khai
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String xmlFile = "/WEB-INF/studentAccounts.xml";
    private final String searchPage = "search.jsp";
    private final String invalidPage = "invalidPage.html";
    private boolean found;
    private String fullName;
    private String uri = invalidPage;

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
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            /* TODO output your page here. You may use following sample code. */

            ServletContext context = this.getServletContext();
            Document doc = (Document) context.getAttribute("DOMTREE");
            if (doc == null) {
                String realPath = this.getServletContext().getRealPath("/");
                String xmlFilePath = realPath + xmlFile;
                doc = XMLUlti.parseFileTODOMTREE(xmlFilePath);
                context.setAttribute("DOMTREE", doc);
            }

            if (doc != null) {
                this.found = false;
                checkLogin(username, password, doc);
                if (this.found) {
                    uri = searchPage;
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", username);
                    session.setAttribute("FULLNAME", this.fullName);

                }

            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(uri);
            rd.forward(request, response);
            out.close();
        }
    }

    private void checkLogin(String username, String password, Node node) {
        if (node == null || this.found) {
            return;
        }
        if (node.getNodeName().equals("student")) {
            NamedNodeMap attrs = node.getAttributes();
            String id = attrs.getNamedItem("id").getNodeValue();
            if (id.equals(username.trim())) {
                NodeList childrenOfStudent = node.getChildNodes();
                for (int i = 0; i < childrenOfStudent.getLength(); i++) {
                    Node tmp = childrenOfStudent.item(i);
                    if (tmp.getNodeName().equals("lastname")) {
                        this.fullName = tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("middlename")) {

                    } else if (tmp.getNodeName().equals("firstname")) {

                    } else if (tmp.getNodeName().equals("password")) {
                        String pass = tmp.getTextContent().trim();
                        if (!pass.equals(password.trim())) {
                            break;
                        }
                    } else if (tmp.getNodeName().equals("status")) {
                        String status = tmp.getTextContent().trim();
                        if (!status.equals("dropout")) {
                            this.found = true;
                            return;
                        }
                    }
                }
            }
        }
        NodeList children = node.getChildNodes();
        int i = 0;
        while (i < children.getLength()) {
            checkLogin(username, password, children.item(i++));
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
