package com.example.lab3_20216583.controller;

import com.example.lab3_20216583.entity.Employee;
import com.example.lab3_20216583.repository.EmployeeRepository;
import com.example.lab3_20216583.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    public EmployeeController(
            EmployeeRepository employeeRepository,
            JobRepository jobRepository) {

        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "employees",
                employeeRepository.listarEmpleados()
        );

        model.addAttribute("texto", "");

        return "employees/list";
    }

    @GetMapping("/buscar")
    public String buscar(
            @RequestParam("texto") String texto,
            Model model) {

        List<Employee> employees;
        //trim para separar una cadena de texto//
        if (texto == null || texto.trim().isEmpty()) {

            employees =
                    employeeRepository.listarEmpleados();

        } else {

            employees =
                    employeeRepository.buscarPorTexto(
                            texto.trim()
                    );
        }

        model.addAttribute("employees", employees);
        model.addAttribute("texto", texto);

        return "employees/list";
    }

    @GetMapping("/editar")
    public String editar(
            @RequestParam("id") Integer id,
            Model model) {

        Optional<Employee> employee =
                employeeRepository.obtenerPorId(id);

        if (employee.isEmpty()) {
            return "redirect:/employees";
        }

        model.addAttribute(
                "employee",
                employee.get()
        );

        model.addAttribute(
                "jobs",
                jobRepository.listarJobs()
        );

        return "employees/edit";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute("employee")
            Employee employee) {

        employeeRepository.actualizarEmpleado(

                employee.getEmployeeId(),

                employee.getFirstName(),

                employee.getLastName(),

                employee.getEmail(),

                employee.getPhoneNumber(),

                employee.getJobId(),

                employee.getHireDate(),

                employee.getSalary(),

                employee.getCommissionPct()
        );

        return "redirect:/employees";
    }
}
