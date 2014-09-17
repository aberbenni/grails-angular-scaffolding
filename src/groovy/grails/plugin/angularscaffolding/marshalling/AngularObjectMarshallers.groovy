package grails.plugin.angularscaffolding.marshalling

class AngularObjectMarshallers {
 
    List marshallers = []
 
    def register() {
        marshallers.each{ it.register() }
    }
}