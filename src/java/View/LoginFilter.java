/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dennisschmock
 */
@WebFilter(urlPatterns = {"/frontpage"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        //Get session, but do not create a new if it doesn't exist
        HttpSession session = req.getSession(false);
        if(session ==null || session.getAttribute("loggedInUser")==null){
            res.sendRedirect(req.getContextPath()+"/index.jsp");
        } else{
            chain.doFilter(req, res);
        }
        
        
        
        
    }

    @Override
    public void destroy() {
    }
    
}
