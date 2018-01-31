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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import sample.util.XMLUlti;

/**
 *
 * @author khai
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {

    private final String xmlFile = "WEB-INF/studentAccounts.xml";
    private final String searchPage = "search.jsp";
    private final String invalidPage = "invalidPage.html";
    String uri = invalidPage;

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
        String id = request.getParameter("id");
        String searchValue = request.getParameter("lastSearchValue");
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
                XPath xpath = XMLUlti.getXPath();
                String exp = "//student[@id='" + id + "']";
                System.out.println(id);
                Node student = (Node) xpath.evaluate(exp, doc, XPathConstants.NODE);
                if (student != null) {
                    Node parent = student.getParentNode();
                    parent.removeChild(student);
                    String realPath = this.getServletContext().getRealPath("/");
                    String xmlFilePath = realPath + xmlFile;
                    XMLUlti.tranformXMLtoFile(parent, xmlFilePath);

                    uri = "CenterServlet?btAction=Search&txtSearchValue=" + searchValue;
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println(uri);
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
