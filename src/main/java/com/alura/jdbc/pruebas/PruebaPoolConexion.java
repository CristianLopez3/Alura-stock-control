package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;

public class PruebaPoolConexion {
    public static void main (String[] args){
        ConnectionFactory connectionFactory = new ConnectionFactory();

        /**
         * com.alura.jdbc.factory
         * Observamos que el setMaxPoolSize del objeto poolDataSource, se encuentra configurado
         * en 10, lo que nos permite abrir maximo 10 conexiones. Para comprobar esto podemos abrir la terminal
         * y ir a mysql y ejecutar el comando <show processlist>, el cual nos mostrará las conexiones
         * abiertas.
         */

        for(int i = 0; i < 20; i++){
            Connection conection = connectionFactory.recuperarConexion();
            System.out.println("Abriendo la conexion numero: " + (i+1));
        }

    }
}
