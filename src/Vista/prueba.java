/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;
import Negocio.*;
import Util.*;
import java.io.IOException;
/**
 *
 * @author Lonnath
 */
public class prueba {
    public static void main(String[] args) throws IOException {
        SopaBinaria sopa = new SopaBinaria();
        sopa.leerMatrizExcel("C:\\Users\\Lonnath\\Documents\\Jesus\\sopabinaria-master\\src\\Excel\\bits.xls");
        System.out.println(sopa.mostrarSopa());
        
    }
}
