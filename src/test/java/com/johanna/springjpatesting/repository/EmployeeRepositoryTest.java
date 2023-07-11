package com.johanna.springjpatesting.repository;

import com.johanna.springjpatesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {

        employee = Employee.builder()
                .firstName("Jonah")
                .lastName("Mfundizi")
                .email("jmfundizi@gmail.com")
                .build();

    }

    @DisplayName("Test case for saving an employee")
    @Test
    public void givenEmployeeObject_whenSaved_thenReturnSavedEmployee() {

        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    @DisplayName("Test case for finding all employees")
    @Test
    public void givenEmployees_whenFindAll_thenReturnEmployeesList() {

        Employee employee2 = Employee.builder()
                .firstName("Lucy")
                .lastName("Mokwe")
                .email("mokwel@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);

    }

    @DisplayName("Test case for getting employee by Id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findById(employee.getId()).get();

        assertThat(employeeInDB).isNotNull();

    }

    @DisplayName("Test case for getting employee by email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employeeInDB).isNotNull();

    }

    @DisplayName("Test case for updating employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findById(employee.getId()).get();
        employeeInDB.setFirstName("Jonas");
        employeeInDB.setEmail("jsmfundizi@gmail.com");
        Employee updatedEmployee = employeeRepository.save(employeeInDB);

        assertThat(updatedEmployee.getFirstName()).isEqualTo("Jonas");
        assertThat(updatedEmployee.getEmail()).isEqualTo("jsmfundizi@gmail.com");

    }

    @DisplayName("Test case for deleting employee")
    @Test
    public void givenEmployeeObject_whenDelete_thenNoReturnedEmployee() {

        employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        assertThat(optionalEmployee).isEmpty();

    }

    @DisplayName("Test case for custom query using JPQL with index params")
    @Test
    public void givenFirstAndLastName_whenFindByJpql_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findByJPQL(
                employee.getFirstName(),
                employee.getLastName()
        );

        assertThat(employeeInDB).isNotNull();

    }

    @DisplayName("Test case for custom query using JPQL with named params")
    @Test
    public void givenFirstAndLastName_whenFindByJpqlNamedParams_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findByJPQLNamedParams(
                employee.getFirstName(),
                employee.getLastName()
        );

        assertThat(employeeInDB).isNotNull();

    }

    @DisplayName("Test case for custom query using native SQL with index params")
    @Test
    public void givenFirstAndLastName_whenFindByNativeSql_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findByNativeSQL(
                employee.getFirstName(),
                employee.getLastName()
        );

        assertThat(employeeInDB).isNotNull();

    }

    @DisplayName("Test case for custom query using native SQL with named params")
    @Test
    public void givenFirstAndLastName_whenFindByNativeSqlNamedParams_thenReturnEmployee() {

        employeeRepository.save(employee);

        Employee employeeInDB = employeeRepository.findByNativeSQLNamedParams(
                employee.getFirstName(),
                employee.getLastName()
        );

        assertThat(employeeInDB).isNotNull();

    }

}