package be.heh.std.epm.persistence;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

public class SQLPersistence implements OutPersistence{

    @Override
    public void save(Employee emp) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void replace(Employee emp) throws Exception {

    }

    @Override
    public Employee getData(int id) throws Exception {
        return null;
    }
}
