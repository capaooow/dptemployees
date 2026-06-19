package com.uv.fiee.pdp2_2026.equipo_l.repository;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;

/*
 *  Repositorio encargado de las consultas a la vista vEmployeeDepartment.
 */

@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer> {
  @Query("Select BusinessEntityID, FirstName, LastName, JobTitle, Department, StartDate from EmployeeDepartment")
  List<EmployeeDepartment> findAllEmployee(Pageable pageable);
  /*
   * Busca y retorna la lista de empleados filtrados por su departamento.
   * 
   * @param department: Nombre del departamento.
   * 
   * @return: Una colección List con los empleados encontrados.
   */
  @Query("Select BusinessEntityID, FirstName, LastName, JobTitle, StartDate from EmployeeDepartment WHERE department=?1")
  List<EmployeeDepartment> findByDepartment(String department,Pageable pageable);
  @Query("Select FirstName, LastName, JobTitle, StartDate from EmployeeDepartment ORDER BY StartDate ASC LIMIT 1")
  EmployeeDepartment findOldest();
  //Busca el empleado mas antiguo de un departamento
  @Query("Select FirstName, LastName, JobTitle, StartDate from EmployeeDepartment WHERE department=?1 ORDER BY StartDate ASC LIMIT 1")
  EmployeeDepartment findOldest(String departent);
  /*
   * Busca y retorna la lista de departamentos que existen.
   * 
   * @return: Una colección List con los departamentos encontrados.
   */
  @Query("SELECT DISTINCT e.department FROM EmployeeDepartment e")
  List<String> findDistinctDepartments();
}
