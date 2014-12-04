package grails.plugin.angularscaffolding.marshalling
 
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
 
class DomainClassMarshaller {
 
	public static List<String> globalRestrictedFields = ['class']
 
	public static Closure createIncludeMarshaller(Class clazz, List<String> fieldsToInclude) {
		return { domainItem ->
			DefaultGrailsDomainClass domain = new DefaultGrailsDomainClass(clazz)
			def results = [:]
			
			def persistentPropNames = domain.persistentProperties*.name
			
			if(fieldsToInclude.contains('id'))
				results[domain.identifier.name] = domainItem[domain.identifier.name]
			
			persistentPropNames.each { field ->
				if(!(field in globalRestrictedFields) && (field in fieldsToInclude))
					results[field] = domainItem[field]
			}
			
			if(fieldsToInclude.contains('version'))
				results['version'] = domainItem['version']
			
			if(fieldsToInclude.contains('toString'))
				results['desc'] = domainItem.toString()
			
			return results
		}
	}
 
	public static Closure createExcludeMarshaller(Class clazz, List<String> fieldsToExclude = []) {
		return { domainItem ->
			DefaultGrailsDomainClass domain = new DefaultGrailsDomainClass(clazz)
 			def results = [:]
			
			def persistentPropNames = domain.persistentProperties*.name
			
			if(!fieldsToExclude.contains('id'))
				results[domain.identifier.name] = domainItem[domain.identifier.name]
			
			persistentPropNames.each { field ->
				if(!(field in globalRestrictedFields) && !(field in fieldsToExclude))
					results[field] = domainItem[field]
			}
			
			if(!fieldsToExclude.contains('version'))
				results['version'] = domainItem['version']
			
			return results
		}
	}
}  