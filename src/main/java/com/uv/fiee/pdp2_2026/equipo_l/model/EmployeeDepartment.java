package com.uv.fiee.pdp2_2026.equipo_l.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

/*
 *  Entidad de solo lectura, mapea la vista "vEmployeeDepartment".
 */

@Entity
@Table(name = "vEmployeeDepartment", schema = "HumanResources")
@Immutable
public class EmployeeDepartment {

  @Id
  @Column(name = "BusinessEntityID")
  private Integer id;

  @Column(name = "FirstName")
  private String firstName;

  @Column(name = "LastName")
  private String lastName;

  @Column(name = "JobTitle")
  private String jobTitle;

  @Column(name = "Department")
  private String department;

  @Column(name = "StartDate")
  private LocalDate startDate;

  // Constructors
  public EmployeeDepartment() {

  }

  // Getters and Setters
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getJobTitle() {
    return this.jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getDepartment() {
    return this.department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
}
