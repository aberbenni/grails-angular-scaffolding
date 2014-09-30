
package grails.plugin.angularscaffolding

import org.grails.plugin.platform.util.TagLibUtils

class HeplerTagLib {
    static namespace = "hlp"
	
	//override UITaglib.attributes (ui:attributes)
    /**
     * Write out a the attributes passed in or available in the pageScope.attrs as passed to the templates
     * used to render UI elements.
     * @attr in Optional list of attribute names
     * @attr from Optional map of attribute key/values
     */
    def attributes = { attrs ->
        def attribs
        def includes = TagLibUtils.attrSetOfItems('include', attrs.include, Collections.EMPTY_SET)
        def excludes = TagLibUtils.attrSetOfItems('exclude', attrs.excludes, Collections.EMPTY_SET)

        def attributeMap = attrs.from != null ? attrs.from : pageScope.variables.attrs

        attribs = attributeMap?.findAll { k, v ->
            boolean keep = !includes || (k in includes)
            if (excludes) {
                keep &= !k in excludes
            }
            return keep
        }

        if (attribs) {
            out << TagLibUtils.attrsToString(attribs)
        }
    }
	
	//override UiExtensionsTagLib.joinClasses (p:joinClasses)
    def joinClasses = { attrs ->
		StringBuilder res = new StringBuilder()
        def first = true
		attrs.values.unique()
		attrs.values?.each { v ->
            if (v) {
                if (!first) {
                    res << ' '
                } else {
                    first = false
                }
                res << v
            }
        }
        out << res.toString()
    }
	
}
