/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.util.XMLUlti;

/**
 *
 * @author khai
 */
@WebServlet(name = "InsertServlet", urlPatterns = {"/InsertServlet"})
public class InsertServlet extends HttpServlet {

    private final String xmlFile = "WEB-INF/studentAccounts.xml";

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
        String password = request.getParameter("txtPassword");
        String id = request.getParameter("txtId");
        String firstname = request.getParameter("txtFirst");
        String lastname = request.getParameter("txtLast");
        String middle = request.getParameter("txtMiddle");
        String address = request.getParameter("txtAddress");
        String sClass = request.getParameter("txtClass");
        String s = request.getParameter("chkSex");
        String sex = "1";
        if (s != null) {
            sex = "0";
        }
        try {
            System.out.println("register here");
            ServletContext context = this.getServletContext();
            Document doc = (Document) context.getAttribute("DOMTREE");
            if (doc == null) {
                String realPath = this.getServletContext().getRealPath("/");
                String xmlFilePath = realPath + xmlFile;
                doc = XMLUlti.parseFileTODOMTREE(xmlFilePath);
                context.setAttribute("DOMTREE", doc);
            }
            if (doc != null) {
                Map<String, String> studentAttribute = new HashMap();
                studentAttribute.put("id", id);
                studentAttribute.put("class", sClass);
                Element student = XMLUlti.createDOMElement(doc, "student", null, studentAttribute);
                Element lastnameNode = XMLUlti.createDOMElement(doc, "lastname", lastname, null);
                student.appendChild(lastnameNode);
                Element firstnameNode = XMLUlti.createDOMElement(doc, "firstname", firstname, null);
                student.appendChild(firstnameNode);
                Element middlenameNode = XMLUlti.createDOMElement(doc, "middlename", middle, null);
                student.appendChild(middlenameNode);
                Element addressNode = XMLUlti.createDOMElement(doc, "address", address, null);
                student.appendChild(addressNode);
                Element sexNode = XMLUlti.createDOMElement(doc, "sex", sex, null);
                student.appendChild(sexNode);
                Element passwordNode = XMLUlti.createDOMElement(doc, "password", password, null);
                student.appendChild(passwordNode);

                // cach1:  lay doc.getdocumentelement()
                NodeList students = doc.getElementsByTagName("students");
                //khong can duyet for vi chi co 1 node duy nhat

                students.item(0).appendChild(student);
                String realPath = this.getServletContext().getRealPath("/");
                String xmlFilePath = realPath + xmlFile;
                XMLUlti.tranformXMLtoFile(doc, xmlFilePath);

            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            String uri = "index.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(uri);
            rd.forward(request, response);
            out.close();
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
