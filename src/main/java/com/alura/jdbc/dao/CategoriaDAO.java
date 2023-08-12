package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

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

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();
        try {
            final PreparedStatement statement = connection.prepareStatement(
                    "SELECT C.ID, C.NOMBRE, P.NOMBRE, P.CANTIDAD, P.ID " +
                            "FROM CATEGORIA C JOIN PRODUCTO P ON C.ID = P.CATEGORIAID ");
            try (statement) {
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                       Integer categoriaId = resultSet.getInt("C.ID");
                       String categoriaNombre = resultSet.getString("C.NOMBRE");
                       var categoria = resultado
                               .stream()
                               .filter(category -> category.getId().equals(categoriaId))
                               .findAny().orElseGet(()-> {
                                   Categoria category = new Categoria(categoriaId, categoriaNombre);
                                   resultado.add(category);
                                   return category;
                               });
                       Producto producto = new Producto(
                               resultSet.getInt("P.ID"),
                               resultSet.getString("P.NOMBRE"),
                               resultSet.getInt("P.CANTIDAD")
                       );
                       categoria.agregar(producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
