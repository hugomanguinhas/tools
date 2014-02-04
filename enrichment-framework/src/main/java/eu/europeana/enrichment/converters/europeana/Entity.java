package eu.europeana.enrichment.converters.europeana;

import java.util.List;
/**
 * A Contextual EDM Entity representation
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class Entity {
	String className;
	
	List<Field> fields;

	String uri;
	
	String originalField;
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getOriginalField(){
		return this.originalField;
	}
	
	public void setOriginalField(String originalField){
		this.originalField = originalField;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new  StringBuilder();
		sb.append("[ClassName: " + this.className+ ", URI: " + this.uri +", Original field: " + this.originalField +", fields: ");
		for(Field field: this.fields){
			sb.append(field.toString());
		}
		sb.append("]");
		return sb.toString();
	}
}