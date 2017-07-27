package com.interfaceTest;

/**
 * Created by AHernandezS on 7/07/2017.
 */
public class Prueba implements PruebaI {
    @Override
    public String getNombre() {
        return "Alberto";
    }

    public String getApellido(){
        return "Hernandez";
    }


    public static PruebaI getPrueba() {
        return new Prueba();
    }

    public static void main(String...args){
        Prueba x = (Prueba)Prueba.getPrueba();
        System.out.println(x.getApellido());
    }
}
