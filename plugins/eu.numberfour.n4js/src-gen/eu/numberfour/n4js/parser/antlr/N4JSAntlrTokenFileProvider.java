/*
 * generated by Xtext
 */
package eu.numberfour.n4js.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class N4JSAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("eu/numberfour/n4js/parser/antlr/internal/InternalN4JSParser.tokens");
	}
}
