/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Modelo.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author madar
 */
public class SopaBinaria {

    private Bit mySopaBinaria[][] = new Bit[1][1];
    private int paintSopa [][] = new int[1][1];

    public SopaBinaria() {
    }

    public void leerMatrizExcel(String rutaArchivoExcel) throws FileNotFoundException, IOException {
        HSSFWorkbook archivoExcel = new HSSFWorkbook(new FileInputStream(rutaArchivoExcel));
        HSSFSheet hoja = archivoExcel.getSheetAt(0);
        //Obtiene el número de la última fila con datos de la hoja.
        int canFilas = hoja.getLastRowNum() + 1;
        this.mySopaBinaria = new Bit[canFilas][hoja.getRow(0).getLastCellNum()];
        this.paintSopa = new int[canFilas][hoja.getRow(0).getLastCellNum()];

        for (int i = 0; i < canFilas; i++) {
            //Obtiene el índice de la última celda contenida en esta fila MÁS UNO.
            HSSFRow filas = hoja.getRow(i);
            //Obtiene la cantidad de colomunas de esa fila
            int cantCol = filas.getLastCellNum();

            for (int j = 0; j < cantCol; j++) {
                //Obtiene la celda y su valor respectivo
                //double r=filas.getCell(i).getNumericCellValue();
                double valor = filas.getCell(j).getNumericCellValue();
                if (valor > 0) {
                    Bit response = new Bit(true);
                    this.añadirValor(response);
                } else {
                    Bit response = new Bit(false);
                    this.añadirValor(response);
                }
            }

        }
    }

    public String mostrarSopa() {
        String sopa = "";
        for (int i = 0; i < this.mySopaBinaria.length; i++) {
            for (int j = 0; j < this.mySopaBinaria[0].length; j++) {
                if (this.mySopaBinaria[i][j].isValor()) {
                    sopa += 1 + "   ";
                } else {
                    sopa += 0 + "   ";
                }
            }
            sopa += "\n";
        }
        return sopa;
    }

    public void añadirValor(Bit response) {
        int pos = this.posValor();
        if (pos > -1) {
            this.mySopaBinaria[pos / 10][pos % 10] = response;
        }

    }

    public int posValor() {
        int pos = 0;
        int pos1 = 0;
        for (int i = 0; i < this.mySopaBinaria.length; i++) {
            for (int j = 0; j < this.mySopaBinaria[0].length; j++) {
                if (this.mySopaBinaria[i][j] == null) {
                    pos = i * 10;
                    pos1 = j;
                    return pos + pos1;
                }
            }
        }
        return -1;
    }

    public int getCuantasVeces_Horizontal(int decimal) {
        String tmp = this.convertirBinario(decimal);
        int contador = 0;
        for (int i = 0; i < this.mySopaBinaria.length; i++) {
            String tmpo = "";
            for (int j = 0; j < this.mySopaBinaria[0].length; j++) {
                if (this.mySopaBinaria[i][j].isValor()) {
                    tmpo += "1";
                } else {
                    tmpo += "0";
                }

            }

            if (tmpo.contains(tmp)) {
                contador++;
                int index = tmpo.indexOf(tmp);
                int end = index + tmp.length() - 1;
                if (index >= 0 && end >= 0) {
                    for (int j = index; j <= end; j++) {
                        this.paintSopa[i][j] = 1;
                    }
                    
                    for (int j = 0; j < index; j++) {
                        this.paintSopa[i][j]=0;
                    }
                    for (int j = end+1; j < tmp.length(); j++) {
                        this.paintSopa[i][j]=0;
                    }
                    
                }
            }
        }
        for (int i = 0; i <this.paintSopa.length; i++) {
            for (int j = 0; j < this.paintSopa[0].length; j++) {
                System.out.print(this.paintSopa[i][j]+"   ");
            }
            System.out.println("");
        }
        return contador;
    }


    public int getCuantasVeces_Vertical(int decimal) {
        String tmp = this.convertirBinario(decimal);
        int contador = 0;
        for (int i = 0; i < this.mySopaBinaria[0].length; i++) {
            String tmpo = "";
            for (int j = 0; j < this.mySopaBinaria.length; j++) {
                if (this.mySopaBinaria[j][i].isValor()) {
                    tmpo += "1";
                } else {
                    tmpo += "0";
                }

            }
            if (tmpo.contains(tmp)) {
                contador++;
                int index = tmpo.indexOf(tmp);
                int end = index + tmp.length() - 1;
                 if (index >= 0 && end >= 0) {
                    for (int j = index; j <= end; j++) {
                        this.paintSopa[j][i] = 1;
                    }
                    
                    for (int j = 0; j < index; j++) {
                        if(this.paintSopa[j][i]!=1) this.paintSopa[j][i]=0;
                    }
                    for (int j = end+1; j < tmp.length(); j++) {
                        if(this.paintSopa[j][i]!=1) this.paintSopa[j][i]=0;
                    }
                    
                }
            }
        }
        return contador;
    }

    public int getCuantasVeces_Diagonal(int decimal) {
        return 0;
    }

    public String convertirBinario(int number) {
        String chain = "";
        String out = "";
        int division = number;
        int residuo = 0;
        while (division > 0) {
            residuo = division % 2;
            division = division / 2;
            chain += residuo;
        }
        for (int i = chain.length() - 1; i >= 0; i--) {
            out += chain.charAt(i);
        }
        return out;
    }

    public boolean soupIsEmpty() {

        return this.mySopaBinaria[0][0] == null;
    }
    
    public Bit[][] soupData(){
        return this.mySopaBinaria;
    }
    public int[][] getPaintSopa(){
        return this.paintSopa;
    }
    public void setPaintSopa(){
        this.paintSopa =null;
    }
    
    public void setPaintSopaTotal(){
        for (int i = 0; i < this.paintSopa.length; i++) {
            for (int j = 0; j < this.paintSopa[0].length; j++) {
                this.paintSopa[i][j] = 0;
            }
        }
    }
}
