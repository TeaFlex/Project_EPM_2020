package be.heh.std.epm.web;

import be.heh.std.epm.application.adapter.TestPersistence;
import be.heh.std.epm.application.data.*;
import be.heh.std.epm.application.service.OperationEmp;
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

    public EmployeeController() {
        gson = new Gson();
        operationEmp = new OperationEmp(new TestPersistence());
    }

    @PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> addEmployee(@RequestBody String body) {
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        if (!jsonObject.has("type") || !jsonObject.has("employee")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed data in request body.");
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee type not supported.");
        }

        try {
            operationEmp.addEmployee(dataEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee created.");
    }

    @DeleteMapping(value = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> removeEmployee(@PathVariable int id) {
        try {
            operationEmp.deleteEmployee(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no employee associated to the specified ID.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted.");
    }

    @PutMapping(value = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateEmployee(@PathVariable int id) {
        //TODO
        return ResponseEntity.status(HttpStatus.OK).body("Employee updated.");
    }

    @PostMapping(value = "/timecard", consumes =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addTimeCard(@RequestBody String body) {
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        if (!jsonObject.has("employeeID") || !jsonObject.has("timecard")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed data in request body.");
        }
        int id = jsonObject.get("employeeID").getAsInt();
        DataTimeCard dataTimeCard = gson.fromJson(jsonObject.get("timecard"), DataTimeCard.class);
        try {
            operationEmp.postTimeCard(id, dataTimeCard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Time card added.");
    }

    @PostMapping(value = "/salesreceipt", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addSalesReceipt(@RequestBody String body) {
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        if (!jsonObject.has("employeeID") || !jsonObject.has("salesreceipt")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed data in request body.");
        }
        int id = jsonObject.get("employeeID").getAsInt();
        DataReceipt dataReceipt = gson.fromJson(jsonObject.get("salesreceipt"), DataReceipt.class);
        try {
            operationEmp.postSaleReceipt(id, dataReceipt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Sales receipt posted.");
    }
}
