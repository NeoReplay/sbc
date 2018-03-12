package test;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


public class FirstClass {

	public static void main(String[] args) {
		
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		
		/**
		Model model = ModelFactory.createDefaultModel();
		
		//Some definition for the UI and person name
		
		final String personURI = "http://www.johnwebpage.com";
		final String givenName = "John";
		final String familyName = "smith";
		final String fullName = givenName + " " + familyName;
		
		//creation de la ressource
		Resource johnSmith = model.createResource(personURI);
		
		//creation de la property
		Property name = model.createProperty(familyName);
		
		//add the Property
		johnSmith.addProperty(name, "this is a property", XSDDatatype.XSDstring);
		
		model.write(System.out, "TURTLE");
		**/
		
		
		sparqlTest();
		
	}
	
	
	public static void sparqlTest()
	{
		String str = "Obama";
		
		/**
		String queryString =	"PREFIX pr:<http://xmlns.com/foaf/0.1/>\n" +
								"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
				                "SELECT DISTINCT ?s ?label WHERE {" +
								"?s rdfs:label ?label . "+
				                "?s a pr:Person . "+
				                "FILTER (lang(?label) = 'en') . "+
				                "?label <bif:contains> \"" + str + "\" ."+
				                "}";
		**/
		
		String queryString = 	"PREFIX cat: <http://dbpedia.org/resource/Category:>\n "+
								"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
								"PREFIX dct: <http://purl.org/dc/terms/>\n"+
								"SELECT ?person WHERE { " +
								"?person dct:subject cat:French_philosophers ."
								+ "} LIMIT 15";
		    
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql",query);
		try
		{
			ResultSet results = qexec.execSelect();
			while(results.hasNext())
			{
				QuerySolution soln = results.nextSolution();
				String abc = soln.toString();
				abc = abc.substring(13, abc.length()-3);
				System.out.println("abc = " + abc);
				String queryStringBis = 	"PREFIX cat: <http://dbpedia.org/resource/Category:>\n "+
											"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
											"PREFIX dct: <http://purl.org/dc/terms/>\n"+
											"SELECT ?o ?p WHERE { " +
											"<" + abc + "> ?p ?o ."
											+ "}";
				Query queryBis = QueryFactory.create(queryStringBis);
				QueryExecution qexecBis = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql",queryBis);
				try
				{
					ResultSet resultsBis = qexecBis.execSelect();
					while(resultsBis.hasNext())
					{
						QuerySolution solnBis = resultsBis.nextSolution();
						System.out.println(solnBis);
					}
				}
				finally
				{
					qexecBis.close();
				}
			}
			
		}
		finally
		{
			qexec.close();
		}
	
	
	}

}
