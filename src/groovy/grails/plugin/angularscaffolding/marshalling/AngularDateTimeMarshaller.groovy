package grails.plugin.angularscaffolding.marshalling

import grails.converters.JSON
 
class AngularDateMarshaller {
 
    void register() {
		JSON.registerObjectMarshaller(Date) {
			return it?.format("yyyy-MM-dd") 
		}
    }
}