package uk.ac.ed.ph.snuggletex.dombuilding;

import org.w3c.dom.Element;

import uk.ac.ed.ph.snuggletex.internal.DOMBuilder;
import uk.ac.ed.ph.snuggletex.internal.SnuggleParseException;
import uk.ac.ed.ph.snuggletex.tokens.ArgumentContainerToken;
import uk.ac.ed.ph.snuggletex.tokens.CommandToken;

/**
 * Handles \genfrac{...}{...} and creates <mfrac>
 */
public class GenfracHandler implements CommandHandler {

    public void handleCommand(DOMBuilder builder, Element parentElement, CommandToken token) throws SnuggleParseException {

        Element result = builder.appendMathMLElement(parentElement, "mfrac");
        ArgumentContainerToken[] arguments = token.getArguments();
        String attr = new String(arguments[2].getSlice().extract().toString());
        result.setAttribute("linethickness", attr);

        builder.appendSimpleMathElement(result, arguments[4].getContents().get(0));
        builder.appendSimpleMathElement(result, arguments[5].getContents().get(0));
        //builder.handleMathTokensAsSingleElement(result, arguments[4]);
    }
}
