package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoDAO {
    final private Connection connection;
    public ProductoDAO(Connection connection){
        this.connection = connection;
    }

    public void guardar(Producto producto, Integer id)  {
        try{
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO producto(nombre, descripcion, cantidad, categoriaId)  VALUES(?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );
            try(statement){
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setInt(3, producto.getCantidad());
                statement.setInt(4, producto.getCategoriaId());
                statement.execute();
                final ResultSet resultSet = statement.getGeneratedKeys();
                try(resultSet){
                    while(resultSet.next()){
                        producto.setId(resultSet.getInt(1));
                        System.out.println(String.format(
                                "El id del producto insertado es %s ", producto
                        ));
                    }
                }
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();
        final PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
            try (statement) {
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet){
                    while (resultSet.next()) {
                        Producto fila = new Producto(
                                    resultSet.getInt("ID"),
                                    resultSet.getString("NOMBRE"),
                                    resultSet.getString("DESCRIPCION"),
                                    resultSet.getInt("CANTIDAD")
                            );
                        resultado.add(fila);
                    }
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Integer categoriaId){
        List<Producto> resultado = new ArrayList<>();
        final PreparedStatement statement;
        try{
            statement = connection.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD " +
                    "FROM PRODUCTO WHERE categoriaId = ?");
            try(statement){
                statement.setInt(1, categoriaId);
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet){
                    while(resultSet.next()){
                        Producto fila = new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD")
                        );
                        resultado.add(fila);
                    }
                }
            }
            return resultado;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    public int eliminar(Integer id) {
        try{
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM producto WHERE id = ?");
            try(statement){
                statement.setInt(1, id);
                statement.execute();
                // updateCount = statement.getUpdateCount();
                return statement.getUpdateCount();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        try{
            final PreparedStatement statement = connection.prepareStatement(
                        "UPDATE producto SET " +
                                "nombre = ?, " +
                                "descripcion = ?," +
                                "cantidad = ? " +
                                " WHERE id = ?");
            try(statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();
                return statement.getUpdateCount();
            }
        } catch(SQLException e){
                throw new RuntimeException(e);
        }

    }


}

