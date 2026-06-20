package com.uv.fiee.pdp2_2026.equipo_l.repository;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Pageable;

/*
 *  Repositorio encargado de las consultas a la vista de Azure.
 */

@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer> {
  /*
   * Obtiene todos los empleados de la base de datos de forma paginada.
   */
  @Query("SELECT e FROM EmployeeDepartment e")
  List<EmployeeDepartment> findAllEmployees(Pageable pageable);
  @Query("SELECT Count(BusinessEntityID)as Cuenta FROM EmployeeDepartment e")
  Long getEmployeeCount();
  /*
   * Busca y retorna la lista de empleados filtrados por su departamento.
   * 
   * @param department: Nombre del departamento.
   * 
   * @return: Una colección List con los empleados encontrados.
   */
  List<EmployeeDepartment> findByDepartment(String department, Pageable pageable);
  @Query("SELECT Count(BusinessEntityID)as Cuenta FROM EmployeeDepartment e WHERE Department=?1")
  Long getEmployeeCountInDpt(String department);
  @Query("SELECT TOP 1 e FROM EmployeeDepartment e ORDER BY StartDate ASC")
  EmployeeDepartment getSeniorEmployee();
  @Query("SELECT TOP 1 e FROM EmployeeDepartment e WHERE Department=?1 ORDER BY StartDate ASC")
  EmployeeDepartment getSeniorEmployeeInDpt(String department);
  /*
   * Busca y retorna la lista de departamentos que existen.
   * 
   * @return: Una colección List con los departamentos encontrados.
   */
  @Query("SELECT DISTINCT e.department FROM EmployeeDepartment e")
  List<String> findDistinctDepartments();
}
