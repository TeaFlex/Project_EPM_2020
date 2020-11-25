package be.heh.std.epm.persistence;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean(name = "h2DataSource")
    public DataSource h2DataSource() {

        String username = "";
        String password = "";
        String database = "";

        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url(String.format("jdbc:h2:file:C:/temp/%s", database));
        dataSource.username(username);
        dataSource.password(password);
        return dataSource.build();
    }

    @Bean(name = "PostGreSQLDataSource")
    @Primary
    public DataSource PostGreSQLDataSource() {

        String username = "";
        String password = "";
        String database = "";

        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.url(String.format("jdbc:postgresql://localhost:5432/%s", database));
        dataSource.username(username);
        dataSource.password(password);
        return dataSource.build();
    }
}
