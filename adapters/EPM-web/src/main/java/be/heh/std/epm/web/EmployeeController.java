package be.heh.std.epm.web;

import be.heh.std.epm.application.data.*;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.service.OperationEmp;
import be.heh.std.epm.persistence.access.H2Persistence;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private Gson gson;
    private OperationEmp operationEmp;
    private OutPersistence db;

    public EmployeeController() {
        db = new H2Persistence("file:./h2DBs/mydb", "user", "123");
        gson = new Gson();
        operationEmp = new OperationEmp(db);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> addEmployee(@RequestBody String body) {
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        if (!jsonObject.has("type") || !jsonObject.has("employee")) {
            return new ErrorResponseEntity("Malformed data in request body.");
        }
        String type = jsonObject.get("type").getAsString();
        DataEmployee dataEmployee;
        switch (type) {
            case "salaried":
                dataEmployee = gson.fromJson(jsonObject.get("employee"), DataSalariedEmployee.class);
                break;
            case "hourly":
                dataEmployee = gson.fromJson(jsonObject.get("employee"), DataHourlyEmployee.class);
                break;
            case "commission":
                dataEmployee = gson.fromJson(jsonObject.get("employee"), DataCommissionEmployee.class);
                break;
            default:
                return new ErrorResponseEntity("Employee type not supported.");
        }

        try {
            operationEmp.addEmployee(dataEmployee);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> removeEmployee(@PathVariable int id) {
        try {
            operationEmp.deleteEmployee(id);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody String body) {
        //TODO
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/timecard/{id}", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addTimeCard(@PathVariable int id, @RequestBody DataTimeCard dataTimeCard) {
        try {
            operationEmp.postTimeCard(id, dataTimeCard);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/salesreceipt/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addSalesReceipt(@PathVariable int id, @RequestBody DataReceipt dataReceipt) {
        try {
            operationEmp.postSaleReceipt(id, dataReceipt);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
