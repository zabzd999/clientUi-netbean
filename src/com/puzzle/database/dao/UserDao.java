/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puzzle.database.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.puzzle.database.Entity;
import com.puzzle.util.IBatisUtils;
import java.sql.SQLException;
import org.sqlite.SQLiteConnection;

/**
 *
 * @author Administrator
 */
public class UserDao {
    
    private static  SqlMapClient sqlMapClient = IBatisUtils.getSqlMapClient(); 
    public static UserDao dao=new UserDao();
    
    public Entity queryForConfigPerperties(String fileType){
        try {
            Entity  query=new Entity();
            query.setFileType(fileType);
         Entity e=(Entity)sqlMapClient.queryForObject("selectConfig",query);
         return e;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
      return null;
    }
    public Entity queryForOrder_cus_adaptorXml(String fileType){
        try {
          Entity  query=new Entity();
            query.setFileType(fileType);
         Entity e=(Entity)sqlMapClient.queryForObject("selectCus_adaptor",query);
         return e;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
      return null;
    }
    public Entity queryForKafkaProperties(){
        try {
         Entity e=(Entity)sqlMapClient.queryForObject("selectKafkaConfig");
         return e;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
      return null;
    }
    public Entity queryForActiveMq(){
        try {
         Entity e=(Entity)sqlMapClient.queryForObject("selectActiveMQconfig");
         return e;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
      return null;
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        UserDao user=new UserDao();
         String fileType="3";
      Entity e=  user.queryForConfigPerperties(fileType);
        System.out.println(e.getXmlName());
        System.out.println(e.getXmlValue());

    }
}
