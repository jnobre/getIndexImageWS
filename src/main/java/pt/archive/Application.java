package pt.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	/*public Application( ) {
	    super( );
	    setRegisterErrorPageFilter( false ); 
	}*/

	
    public static void main( String[ ] args ) {
        SpringApplication.run( Application.class , args );
    }
    
}

