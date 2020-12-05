package com.payroll.resources;

import com.payroll.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Function that converts Employee objects to EntityModel<Employee> objects.
 * While you could easily code this method yourself, there are benefits
 * down the road of implementing Spring HATEOAS’s RepresentationModelAssembler interface.
 */

@Component // signifies to Spring that this component will be automatically created when the app starts.
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    /**
     * This simple interface has one method: toModel().
     * It is based on converting a non-model object (Employee) into a model-based object (EntityModel<Employee>).
     * @param employee
     * @return
     */
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee, //
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(), // Spring HATEOAS builds a link to the EmployeeController's one() method, and flag it as a self link.
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));  // asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "employees"
    }
}
