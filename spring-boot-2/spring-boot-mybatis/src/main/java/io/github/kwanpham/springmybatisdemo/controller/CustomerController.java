package io.github.kwanpham.springmybatisdemo.controller;

import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by https://github.com/kwanpham
 */
@Controller
public class CustomerController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/")
    public String listEmp(Model model) {
        model.addAttribute("employeeList" , employeeRepo.findAll());
        return "list_emp";
    }

    @GetMapping("/search")
    public String searchEmp(Model model) {
        model.addAttribute("employeeList" , employeeRepo.findAll());
        return "list_emp";
    }

    @GetMapping("/addEmp")
    public String showFormAddEmp() {
        return "add_emp";
    }

    @PostMapping("/addEmp")
    public String addEmp(@ModelAttribute Employee employee) {
        employeeRepo.insert(employee);
        return "redirect:/";
    }


    @GetMapping("/editEmp/{id}")
    public String showFormEditEmp(@PathVariable("id") int id , Model model) {
        model.addAttribute("emp" , employeeRepo.findById(id));
        return "edit_emp";
    }

    @PostMapping("/editEmp/")
    public String editEmp(@ModelAttribute Employee employee) {
        employeeRepo.update(employee);
        return "redirect:/";
}

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmp(@PathVariable("id") int id) {
        employeeRepo.deleteById(id);
        return "redirect:/";
    }

}
