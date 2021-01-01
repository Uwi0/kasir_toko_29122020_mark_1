/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.*;
/**
 *
 * @author Asus
 */
public class Connection {
    
    private java.sql.Connection connection;
    private Statement statement;
    private String query;
    
    public Connection(){
        
    }
    
    public java.sql.Connection connectionDatabase(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql:///apotik_lama", "root","");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Koneksi Database error : " + e.getMessage());
        }
        
        return connection;
        
    }
    
    public java.sql.Connection closeDatabase(){
        
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("Close Database Error : " + e.getMessage());
        }
        
        return connection;
        
    }
    
    public ResultSet eksekusiQuery(String sql){
        
        connectionDatabase();
        ResultSet resultSet = null;
        
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch(SQLException e){
            System.out.println("eksekusi query Error : " + e.getMessage());
        }
        
        return resultSet;
    }
    
    public String eksekusiUpdate(String sql){
        
        connectionDatabase();
        String result = "";
        System.out.println(sql);
        
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println("eksekusi update ERROR : " + e.getMessage());
        }
        
        return result;
    }
    
    
    //overload fungsi untuk eksekusi query sellect semua kolom dengan where
    public ResultSet querySellect(String nameTable, String condition){
        
        query = " SELECT * FROM " + nameTable + " WHERE " + condition;
        System.out.println(query);
        return this.eksekusiQuery(query);
        
    }
    
    //fungsi untuk eksekusi query select dengan kolom spesifik
    public ResultSet querySellect(String[] nameColumn, String nameTable){
        query = "SELECT ";
        for(int i = 0; i < nameColumn.length; i++){
            query += nameColumn[i];
            if(i < nameColumn.length - 1){
                query += ",";
            }
            
        }
        query += " FROM " + nameTable;
        System.out.println(query);
        return this.eksekusiQuery(query);
    }
    
    //overlaod fungsi eksekuis query dengan kolom spsifik dengan where
    public ResultSet sellectCommand(
            String[] column,
            String nameTable,
            String condition
    ){
        query = "SELECT ";
        for(int i = 0; i < column.length; i++){
            query += column[i];
            if( i < column.length - 1){
                query += ",";
            }
        }
        
        query += " FROM " + nameTable + " WHERE " + condition;
        System.out.println(query);
        return this.eksekusiQuery(query);
    }
    
    //fungsi eksekusi query insert
    public String queryInsert(
            String nameTable,
            String[] nameColumn,
            String[] value
    ){
        
        query = " INSERT INTO " + nameTable + " (";
        for(int i = 0; i < nameColumn.length; i++){
            query += nameColumn[i];
            if(i < nameColumn.length - 1){
                query += ",";
            }
        }
        
        query += ") VALUES (";
        for (int i = 0; i < value.length; i++) {
            query += "'" + value[i] + "'";
            if(i < value.length - 1){
                query += ",";
            }
        }
        
        query += ")";
        System.out.println(query);
        return this.eksekusiUpdate(query);
    }
    
    //fungsi eksekusi query update
    public String queryUpdate(
            String nameTable,
            String[] nameColumn,
            String[] value,
            String condition
    ){
        
        query = "UPDATE " + nameTable + " SET ";
        
        for (int i = 0; i < nameColumn.length; i++) {
            query += nameColumn[i] + " = '" + value[i] + "' ";
            if(i < nameColumn.length - 1){
                query += ",";
            }
        }
        
        query += " WHERE " + condition;
        System.out.println(query);
        return this.eksekusiUpdate(query);
    }
    
    //fungsi eksekusi query delete
    public String queryDelete(String nameTable){
        query = " DELETE FROM " + nameTable;
        return this.eksekusiUpdate(query);
    }
    
    //overload fungsi eksekusi query delete dengan where
    public String queryDelete(String nameTable, String condition){
        query = " DELETE FROM " + nameTable + " WHERE " + condition;
        return this.eksekusiUpdate(query);
    }
}
