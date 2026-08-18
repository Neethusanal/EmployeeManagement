
package com.example.demo.service;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public Employee createEmployee(Employee employee) {

        // Check duplicate email
        Employee existingEmployee =
                employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee != null) {
            throw new RuntimeException("Email already exists");
        }

        // Generate random UUID employee ID
        String employeeId = UUID.randomUUID().toString();

        employee.setEmployeeId(employeeId);

        // Set status based on joining date
        LocalDate today = LocalDate.now();

        if (employee.getJoiningDate() != null
                && employee.getJoiningDate().isAfter(today)) {

            // Joining date is in the future
            employee.setStatus(Employee.Status.INACTIVE);
            employee.setAutoActivationPending(true);

        } else {

            // Joining date is today or already passed
            employee.setStatus(Employee.Status.ACTIVE);
            employee.setAutoActivationPending(false);
        }

        // Only ADMIN needs password for login
        if (employee.getRole() == Employee.Role.ADMIN) {

            if (employee.getPassword() == null ||
                employee.getPassword().isBlank()) {

                throw new RuntimeException(
                        "Password is required for admin");
            }

            employee.setPassword(
                    passwordEncoder.encode(employee.getPassword())
            );

        } else {

            // Normal employees don't need login password
            employee.setPassword(null);
        }

        return employeeRepository.save(employee);
    }

    // GET ALL
//   public List<Employee> getAllEmployees() {
//
//        return employeeRepository.findAll();
//   }
//    
    public List<Employee> getAllEmployees(int page, int size) {

        List<Employee> employees =
                new ArrayList<>(employeeRepository.findAll());

        // Sorting
        employees.sort(Comparator.comparing(Employee::getName));

        // Pagination
        int start = page * size;
        int end = Math.min(start + size, employees.size());

        if (start >= employees.size()) {
            return List.of();
        }

        return employees.subList(start, end);
    }
 // GET BY ID
 public Employee getEmployeeById(String employeeId) {

     return employeeRepository.findById(employeeId);
 }

    // UPDATE
 public Employee updateEmployee(
 String employeeId,
 Employee employee) {

Employee existing =
     employeeRepository.findById(employeeId);

if (existing == null) {
 throw new RuntimeException("Employee not found");
}

employee.setEmployeeId(employeeId);

// Email cannot be changed
employee.setEmail(existing.getEmail());

LocalDate today = LocalDate.now();

boolean joiningDateChanged =
     existing.getJoiningDate() == null
     || !existing.getJoiningDate()
                .equals(employee.getJoiningDate());

if (joiningDateChanged
     && employee.getJoiningDate() != null
     && employee.getJoiningDate().isAfter(today)) {

 // Admin changed joining date to a future date
 employee.setStatus(Employee.Status.INACTIVE);
 employee.setAutoActivationPending(true);

} else if (joiningDateChanged
     && employee.getJoiningDate() != null
     && !employee.getJoiningDate().isAfter(today)) {

 // Joining date changed to today/past
 employee.setAutoActivationPending(false);

} else {

 // Joining date was not changed
 // Keep existing pending state
 employee.setAutoActivationPending(
         existing.isAutoActivationPending()
 );
}

// Password handling
if (employee.getRole() == Employee.Role.ADMIN) {

 if (employee.getPassword() != null &&
     !employee.getPassword().isBlank()) {

     employee.setPassword(
             passwordEncoder.encode(employee.getPassword())
     );

 } else {

     employee.setPassword(existing.getPassword());
 }

} else {

 employee.setPassword(null);
}

return employeeRepository.update(employee);
}

    // DELETE
    public void deleteEmployee(String employeeId) {

        Employee existing =
                employeeRepository.findById(employeeId);

        if (existing == null) {
            throw new RuntimeException("Employee not found");
        }

        employeeRepository.delete(employeeId);
    }
}
