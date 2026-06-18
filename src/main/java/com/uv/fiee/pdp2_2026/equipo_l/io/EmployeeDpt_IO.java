/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uv.fiee.pdp2_2026.equipo_l.io;
import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iflme
 */
public class EmployeeDpt_IO {
    public static List<EmployeeDepartment> read(String dir){
        File file = new File(dir);
        if(!file.canRead())
            return null;
        LinkedList<EmployeeDepartment> lista=new LinkedList<>();
        try (Scanner s=new Scanner(file)){
            while(s.hasNextLine()){
                String info=s.nextLine();
                String data[]=info.split(",");
                String fechaString[]=data[5].split("-");
                LocalDate fecha= LocalDate.of(Integer.parseInt(fechaString[0]),Integer.parseInt(fechaString[1]),Integer.parseInt(fechaString[2]));
                EmployeeDepartment ed= new EmployeeDepartment(Integer.valueOf(data[0]),data[1],data[2],data[3],data[4],fecha);
                lista.add(ed);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeDpt_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public static void write(List<EmployeeDepartment> lista, String dir){
        File file = new File(dir);
        if(!file.canWrite())
            return;
            try(FileWriter fw = new FileWriter(file)){
                ListIterator<EmployeeDepartment> li =lista.listIterator();
                while(li.hasNext())
                    fw.write(li.next().toString());
            } catch (IOException ex) {
                Logger.getLogger(EmployeeDpt_IO.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("File Write Succesful!");
    }
}
