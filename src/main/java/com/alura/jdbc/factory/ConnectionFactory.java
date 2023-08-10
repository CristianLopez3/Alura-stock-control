package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private DataSource dataSource;

    /**
     * Al iniciar un connection factory ejecutamos un poolDataSource para establecer el minimo y
     * maximo de conexiones permitidas por nuestra base de datos.
     */

    public ConnectionFactory(){
        var poolDataSource = new ComboPooledDataSource();
        poolDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&&serverTimeZone=UTC");
        poolDataSource.setUser("root");
        poolDataSource.setPassword("");
        poolDataSource.setMaxPoolSize(10);

        dataSource = poolDataSource;
    }

    public Connection recuperarConexion(){
        try{
            return this.dataSource.getConnection();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


}
