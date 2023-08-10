package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    final private Connection connection;
    public CategoriaDAO(Connection connection){
        this.connection = connection;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();
        try{
            final PreparedStatement statement = connection.prepareStatement("SELECT ID, NOMBRE FROM CATEGORIA");
            try(statement){
                final ResultSet resultSet = statement.executeQuery();
                try(resultSet){
                    while(resultSet.next()){
                        Categoria categoria = new Categoria(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"));
                        resultado.add(categoria);
                    }
                }
            }

            return resultado;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }


    }
}
