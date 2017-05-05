package pt.archive.utils;

import java.util.LinkedList;
import java.util.List;

public class RequestData {
	
	private String criteriaRank;
	private List< String > terms;
	private List< String > allterms;
	private boolean sort;
	private boolean site;
	private boolean type;
	private boolean size;
	private boolean neg;
	private boolean exp;
	
	public RequestData( ){ 
		this.terms 		=  new LinkedList< >( );
		this.allterms 	=  new LinkedList< >( );
		this.sort		= false;
		this.site		= false;
		this.size 		= false;
		this.type 		= false;
		this.neg		= false;
		this.exp 		= false;
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

	public boolean isSort() {
		return sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public boolean isSite() {
		return site;
	}

	public void setSite(boolean site) {
		this.site = site;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public boolean isSize() {
		return size;
	}

	public void setSize(boolean size) {
		this.size = size;
	}

	public boolean isNeg() {
		return neg;
	}

	public void setNeg(boolean neg) {
		this.neg = neg;
	}

	public boolean isExp() {
		return exp;
	}

	public void setExp(boolean exp) {
		this.exp = exp;
	}
	
	
	
	
	
}
