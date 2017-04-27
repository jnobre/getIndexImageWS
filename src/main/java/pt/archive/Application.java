package pt.archive;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.solr.server.support.HttpSolrClientFactory;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	HttpSolrClientFactory solrClientFactory = null;
	
	
    public static void main( String[ ] args ) {
        SpringApplication.run( Application.class , args );
    }
	
}