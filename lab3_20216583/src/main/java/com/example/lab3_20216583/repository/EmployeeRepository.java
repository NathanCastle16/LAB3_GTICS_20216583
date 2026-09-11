package com.example.lab3_20216583.repository;

import com.example.lab3_20216583.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("""
            SELECT e
            FROM Employee e
            ORDER BY e.employeeId
            """)
    List<Employee> listarEmpleados();


    @Query("""
            SELECT e
            FROM Employee e
            WHERE LOWER(e.firstName)
                    LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(e.lastName)
                    LIKE LOWER(CONCAT('%', :texto, '%'))
            ORDER BY e.employeeId
            """)
    List<Employee> buscarPorTexto(
            @Param("texto") String texto
    );


    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.employeeId = :id
            """)
    Optional<Employee> obtenerPorId(
            @Param("id") Integer id
    );


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Employee e

            SET e.firstName = :firstName,
                e.lastName = :lastName,
                e.email = :email,
                e.phoneNumber = :phoneNumber,
                e.jobId = :jobId,
                e.hireDate = :hireDate,
                e.salary = :salary,
                e.commissionPct = :commissionPct

            WHERE e.employeeId = :employeeId
            """)
    int actualizarEmpleado(

            @Param("employeeId")
            Integer employeeId,

            @Param("firstName")
            String firstName,

            @Param("lastName")
            String lastName,

            @Param("email")
            String email,

            @Param("phoneNumber")
            String phoneNumber,

            @Param("jobId")
            String jobId,

            @Param("hireDate")
            LocalDate hireDate,

            @Param("salary")
            BigDecimal salary,

            @Param("commissionPct")
            BigDecimal commissionPct
    );
}
