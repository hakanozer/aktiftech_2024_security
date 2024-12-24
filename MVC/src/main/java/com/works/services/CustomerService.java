package com.works.services;

import com.works.configs.GlobalException;
import com.works.controllers.DashboardController;
import com.works.entities.Customer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final DB db;
    final TinkEncDec tinkEncDec;
    final HttpServletRequest request;

    public boolean login(String email, String password) {
        try {
            // select * from customer where email = 'a@a.com' and passowrd = '' or 1 = 1 --'
            // String sql = "select * from customer where email = '"+email+"' and password = '"+password+"'";
            // Statement st = db.source().getConnection().createStatement();
            // ResultSet rs = st.executeQuery(sql);

            String sql = "select * from customer where email = ?";
            PreparedStatement st = db.source().getConnection().prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String cipherPassword = rs.getString("password");
                String plainPassword = tinkEncDec.decrypt(cipherPassword);
                boolean status = password.equals(plainPassword);
                if (status) {
                    long cid = rs.getLong("cid");
                    String stStatus = rs.getString("status");
                    Customer customer = new Customer();
                    customer.setCid(cid);
                    customer.setEmail(email);
                    customer.setPassword(password);
                    customer.setStatus(stStatus);

                    request.getSession().setAttribute("customer", customer);
                    return true;
                }else {
                    return false;
                }
            }

        }catch (Exception ex) {
            System.err.println("Login Error : " + ex.getMessage());
        }
        return false;
    }

    public void logout() {
        request.getSession().removeAttribute("customer");
    }


}
