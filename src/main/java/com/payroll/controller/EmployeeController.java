package com.payroll.controller;

import com.payroll.model.Employee;
import com.payroll.repository.EmployeeRepository;
import com.payroll.exception.EmployeeNotFoundException;
import com.payroll.resources.EmployeeModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @RestController annotation tells Spring that this code describes an endpoint that should be made available over the web.
 * @RestController indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
 *
*/
@RestController
public class EmployeeController {

    // An EmployeeRepository is injected into the controller using the constructor
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    @GetMapping("/employees") // tells Spring to use our all() method to answer requests that get sent to the http://localhost:8080/employees
    public CollectionModel<EntityModel<Employee>> all() { // CollectionModel is another Spring HATEOAS container aimed at encapsulating collections of employee resources. It includes links
        List<EntityModel<Employee>> employees = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
        /**
         * The new Employee object is saved as before. But the resulting object is wrapped using the EmployeeModelAssembler.
         * Spring MVC’s ResponseEntity is used to create an HTTP 201 Created status message.
         * This type of response typically includes a Location response header, and we use the URI derived from the model’s self-related link.
         * A hypermedia powered client could opt to "surf" to this new resource and proceed to interact with it.
         * Additionally, return the model-based version of the saved object.
         */
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item
    // EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links.
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        /**
         * The Employee object built from the save() operation is then wrapped using the EmployeeModelAssembler into an EntityModel<Employee> object.
         * Using the getRequiredLink() method, you can retrieve the Link created by the EmployeeModelAssembler with a SELF rel.
         * This method returns a Link which must be turned into a URI with the toUri method.
         * Since we want a more detailed HTTP response code than 200 OK, we will use Spring MVC’s ResponseEntity wrapper.
         * It has a handy static method created() where we can plug in the resource’s URI.
         * It comes pre-loaded with a Location response header
         */
        Employee updatedEmployee = repository.findById(id) //
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }) //
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
