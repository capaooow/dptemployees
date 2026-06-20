package com.uv.fiee.pdp2_2026.equipo_l.io;

import org.springframework.stereotype.Repository;
import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class EmployeeDptIO {
  public List<EmployeeDepartment> read(String dir) {
    File file = new File(dir);
    LinkedList<EmployeeDepartment> lista = new LinkedList<>();
    if (!file.canRead()) {
      Logger.getLogger(EmployeeDptIO.class.getName()).log(Level.WARNING, "No se puede leer el archivo en la ruta: {0}.",
          dir);
      return lista;
    }

    try (Scanner s = new Scanner(file)) {
      while (s.hasNextLine()) {
        String info = s.nextLine();

        // Ignora líneas vacías de un archivo csv
        if (info.trim().isEmpty())
          continue;

        String data[] = info.split(",");
        String fechaString[] = data[5].split("-");

        LocalDate fecha = LocalDate.of(
            Integer.parseInt(fechaString[0].trim()),
            Integer.parseInt(fechaString[1].trim()),
            Integer.parseInt(fechaString[2].trim()));

        EmployeeDepartment ed = new EmployeeDepartment(
            Integer.valueOf(data[0].trim()),
            data[1].trim(),
            data[2].trim(),
            data[3].trim(),
            data[4].trim(),
            fecha);
        lista.add(ed);
      }
    } catch (Exception ex) {
      Logger.getLogger(EmployeeDptIO.class.getName()).log(Level.SEVERE, "Error parseando el archivo .csv.", ex);
    }
    return lista;
  }

  public void write(List<EmployeeDepartment> lista, String dir) {
    File file = new File(dir);
    try (FileWriter fw = new FileWriter(file)) {
      if (!lista.isEmpty()) {
        fw.write("ID,First Name,Last Name,Department,Job Title,Start Date\n");
      }

      ListIterator<EmployeeDepartment> li = lista.listIterator();
      while (li.hasNext()) {
        fw.write(li.next().toString() + "\n");
      }
      System.out.println("File Write Succesful!");
    } catch (IOException ex) {
      Logger.getLogger(EmployeeDptIO.class.getName()).log(Level.SEVERE, "Error al escribir el archivo .csv.", ex);
    }
  }
}
