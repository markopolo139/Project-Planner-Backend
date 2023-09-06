package pl.ms.projectoverview.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class DatabaseConfiguration {

    private final DataSource mDataSource;


    public DatabaseConfiguration(DataSource mDataSource) throws IOException {
        this.mDataSource = mDataSource;
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        FileSystemResourceLoader loader = new FileSystemResourceLoader();
        populator.addScript(loader.getResource("./schemas/db-schema.sql"));
        populator.execute(this.mDataSource);
    }

}
