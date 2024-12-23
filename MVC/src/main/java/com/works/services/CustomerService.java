package com.works.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final DB db;

    public boolean login(String email, String password) {
        try {
            // select * from customer where email = 'a@a.com' and passowrd = '' or 1 = 1 --'
            // String sql = "select * from customer where email = '"+email+"' and password = '"+password+"'";
            // Statement st = db.source().getConnection().createStatement();
            // ResultSet rs = st.executeQuery(sql);

            String sql = "select * from customer where email = ? and password = ?";
            PreparedStatement st = db.source().getConnection().prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            return rs.next();
        }catch (Exception ex) {
            System.err.println("Login Error : " + ex.getMessage());
        }
        return false;
    }
}
