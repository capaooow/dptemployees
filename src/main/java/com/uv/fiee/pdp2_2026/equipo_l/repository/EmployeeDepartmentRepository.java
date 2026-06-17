package com.uv.fiee.pdp2_2026.equipo_l.repository;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 *  Repositorio encargado de las consultas a la vista vEmployeeDepartment.
 */

@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer> {
  /*
   * Busca y retorna la lista de empleados filtrados por su departamento.
   * 
   * @param department: Nombre del departamento.
   * 
   * @return: Una colección List con los empleados encontrados.
   */
  List<EmployeeDepartment> findByDepartment(String department);

  /*
   * Busca y retorna la lista de departamentos que existen.
   * 
   * @return: Una colección List con los departamentos encontrados.
   */
  @Query("SELECT DISTINCT e.department FROM EmployeeDepartment e")
  List<String> findDistinctDepartments();
}
