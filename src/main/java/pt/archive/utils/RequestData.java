package pt.archive.utils;

import java.util.LinkedList;
import java.util.List;

public class RequestData {
	
	private String criteriaRank;
	private List< String > terms;
	private List< String > allterms;
	
	public RequestData( ){ 
		this.terms 		=  new LinkedList< >( );
		this.allterms 	=  new LinkedList< >( );
	} 
	
	public RequestData(String criteriaRank, List<String> terms, List<String> allterms) {
		super();
		this.criteriaRank = criteriaRank;
		this.terms = terms;
		this.allterms = allterms;
	}
	
	public String getCriteriaRank() {
		return criteriaRank;
	}
	public void setCriteriaRank(String criteriaRank) {
		this.criteriaRank = criteriaRank;
	}
	public List<String> getTerms() {
		return terms;
	}
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
	public List<String> getAllterms() {
		return allterms;
	}
	public void setAllterms(List<String> allterms) {
		this.allterms = allterms;
	}
	
	
	
	
	
}
