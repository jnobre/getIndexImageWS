package pt.archive.config;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages={"pt.archive.repository"}, multicoreSupport=true)
public class AppConfig {
	
	static final String SOLR_HOST = "solr.server.host";
	
	@Resource private Environment environment;
	
	@Bean
	public HttpSolrClient solrClient( ) {
		String solrHost = environment.getRequiredProperty( SOLR_HOST );
		return new HttpSolrClient( solrHost );
	}
	
	@Bean
	public SolrTemplate solrTemplate( ) throws Exception {
	    return new SolrTemplate( solrClient( ) );
	}
	
}
