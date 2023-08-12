package com.alura.jdbc.modelo;
import java.util.List;
import java.util.ArrayList;
public class Categoria {

    private Integer id;
    private String nombre;
    private List<Producto> productos;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre;
    }

    public Integer getId() {
        return this.id;
    }

    public void agregar(Producto producto) {
        if(productos == null){
            productos = new ArrayList<>();
        }
        productos.add(producto);

    }

    public List<Producto> getProductos() {
        return this.productos;
    }
}
