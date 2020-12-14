package be.heh.std.epm.web;

import be.heh.std.epm.application.service.*;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.persistence.access.H2Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private OutPersistence db;

    public EmployeeController() {
        db = new H2Persistence("file:./h2DBs/mydb", "user", "123");
    }

    private ResponseEntity<String> executeOperation(Operation operation) {
        try {
            operation.execute(db);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/commissioned", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addCommissionedEmployee(@RequestBody AddCommissionEmployee addCommissionEmployee) {
        return executeOperation(addCommissionEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/hourly", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addHourlyEmployee(@RequestBody AddHourlyEmployee addHourlyEmployee) {
        return executeOperation(addHourlyEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/salaried", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addSalariedEmployee(@RequestBody AddSalariedEmployee addSalariedEmployee) {
        return executeOperation(addSalariedEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployee deleteEmployee) {
        return executeOperation(deleteEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/timecard", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postTimeCard(@RequestBody PostTimeCard postTimeCard) {
        return executeOperation(postTimeCard);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/salesreceipt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postSalesReceipt(@RequestBody PostReceipt postReceipt) {
        return executeOperation(postReceipt);
    }
}
