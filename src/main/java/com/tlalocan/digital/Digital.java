/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.tlalocan.digital;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author chris
 */
public class Digital {

    private static String rutaArchivo = "c://archivo.xlsx"; // ruta al archivo
    
    public static void main(String[] args) {
        
        try {
            FileInputStream file = new FileInputStream(new File(rutaArchivo));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int numFilas = sheet.getLastRowNum() + 1; // Número de filas
            int numColumnas = sheet.getRow(0).getLastCellNum() + 1; // Número de columnas más la columna de conteo

            String[][] datos = new String[numFilas][numColumnas];

            for (int i = 0; i < numFilas; i++) {
                Row row = sheet.getRow(i);
                int contadorUnosFila = 0;

                for (int j = 0; j < numColumnas - 1; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String contenidoCelda = obtenerContenidoCelda(cell);
                    datos[i][j] = contenidoCelda;
                    contadorUnosFila += contarUnos(contenidoCelda);
                }

                datos[i][numColumnas - 1] = Integer.toString(contadorUnosFila); // Almacenar conteo de '1' en la última columna
            }

            workbook.close();
            file.close();

           printDoubleArray(numFilas, numColumnas, datos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
       private static void printDoubleArray(int numFilas, int numColumnas, String[][] datos)
       {
            // Imprimir el arreglo bidimensional
            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    System.out.print(datos[i][j] + "\t");
                }
                System.out.println();
            }
       }
    
       private static String obtenerContenidoCelda(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue());
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private static int contarUnos(String texto) {
        int contador = 0;
        for (char c : texto.toCharArray()) {
            if (c == '1') {
                contador++;
            }
        }
        return contador;
    }
}
