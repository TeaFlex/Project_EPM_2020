package be.heh.std.epm.web;

import be.heh.std.epm.application.service.*;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.PayCheck;
import be.heh.std.epm.persistence.access.H2Persistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


@RestController
public class EmployeeController {

    private OutPersistence db;
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController() {
        db = new H2Persistence("file:./h2DBs/mydb", "user", "123");
    }

    private ResponseEntity<String> executeWriteOperation(WriteOperation writeOperation, HttpServletRequest req) {
        String current_operation = writeOperation.getClass().getSimpleName();
        String sub_message = String.format("%s from [%s]",current_operation, req.getRemoteAddr());
        try {
            logger.info("Operation incoming: {}.", sub_message);
            writeOperation.execute(db);
        } catch (SQLException e) {
            logger.error("An error occured in the database: {}", e.getMessage());
            return new ErrorResponseEntity("Oops, an error occured in the database. The request has been rejected.");
        } catch (Exception e) {
            logger.error("An error occured during an operation ({}): {}",sub_message, e.getMessage());
            return new ErrorResponseEntity(e.getMessage());
        }
        logger.info("Operation {} successful !", sub_message);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/commissioned", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addCommissionedEmployee(@RequestBody AddCommissionEmployee addCommissionEmployee, HttpServletRequest req) {
        return executeWriteOperation(addCommissionEmployee, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/hourly", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addHourlyEmployee(@RequestBody AddHourlyEmployee addHourlyEmployee, HttpServletRequest req) {
        return executeWriteOperation(addHourlyEmployee, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/salaried", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addSalariedEmployee(@RequestBody AddSalariedEmployee addSalariedEmployee, HttpServletRequest req) {
        return executeWriteOperation(addSalariedEmployee, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployee deleteEmployee, HttpServletRequest req) {
        return executeWriteOperation(deleteEmployee, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/timecard", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postTimeCard(@RequestBody PostTimeCard postTimeCard, HttpServletRequest req) {
        return executeWriteOperation(postTimeCard, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/salesreceipt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postSalesReceipt(@RequestBody PostReceipt postReceipt, HttpServletRequest req) {
        return executeWriteOperation(postReceipt, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/type/commissioned", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateToCommissioned(@RequestBody UpdateToCommissioned updateToCommissioned, HttpServletRequest req) {
        return executeWriteOperation(updateToCommissioned, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/type/hourly", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateToHourly(@RequestBody UpdateToHourly updateToHourly, HttpServletRequest req) {
        return executeWriteOperation(updateToHourly, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/type/salaried", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateToSalaried(@RequestBody UpdateToSalaried updateToSalaried, HttpServletRequest req) {
        return executeWriteOperation(updateToSalaried, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/method/direct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateToDirectDepositMethod(@RequestBody UpdateToDirectDepositMethod updateToDirectDepositMethod, HttpServletRequest req) {
        return executeWriteOperation(updateToDirectDepositMethod, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/method/mail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateToMailMethod(@RequestBody UpdateToMailMethod updateToMailMethod, HttpServletRequest req) {
        return executeWriteOperation(updateToMailMethod, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/field/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateAddress(@RequestBody UpdateAddress updateAddress, HttpServletRequest req) {
        return executeWriteOperation(updateAddress, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employee/field/name", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateName(@RequestBody UpdateName updateName, HttpServletRequest req) {
        return executeWriteOperation(updateName, req);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/payday", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> payday(@RequestBody PayDay payDay, HttpServletRequest req) {
        try {
            PayCheck payCheck = payDay.payday(db);
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(payCheck));
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
    }
}
