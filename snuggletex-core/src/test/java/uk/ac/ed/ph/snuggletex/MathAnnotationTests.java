/* $Id: MathAnnotationTests.java 742 2012-05-07 13:09:53Z davemckain $
 *
 * Copyright (c) 2008-2011, The University of Edinburgh.
 * All Rights Reserved
 */
package uk.ac.ed.ph.snuggletex;

import java.io.IOException;

import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import junit.framework.Assert;
import uk.ac.ed.ph.snuggletex.utilities.SnuggleUtilities;

/**
 * Tests the generation of MathML annotations.
 * 
 * TODO: Currently the annotations are exactly the same as the input so this could maybe be moved
 * into {@link MathTests}.
 *
 * @author  David McKain
 * @version $Revision: 742 $
 */
public final class MathAnnotationTests {

    /**
     * Tests basic annotations of bog-standard Math regions.
     * 
     * @throws Exception
     */
    @Test
    public void testMathEnvironmentAnnotation() throws Exception {
		SnuggleEngine engine = new SnuggleEngine();
		SnuggleSession session = engine.createSession();

		/* Parse some very basic Math Mode input */
		String str = "$\\genfrac{}{}{0pt}{}{n}{k}$";
		//str = "${\\left(x+a\\right)}^{n}={\\sum }_{k=0}^{n}\\left(\\genfrac{}{}{0pt}{}{n}{k}\\right){x}^{k}{a}^{n-k}$";
		//str = "$\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}$";
		SnuggleInput input = new SnuggleInput(str);
		try {
			session.parseInput(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* Convert the results to an XML String, which in this case will
		 * be a single MathML <math>...</math> element. */
		String xmlString = session.buildXMLString();
		System.out.println(xmlString);

        doTest("$\\frac{1}{2}$");
    }
    
    @Test
    public void testEqnArrayAnnotation() throws Exception {
        doTest("\\begin{eqnarray*} x &=& 1 \\end{eqnarray*}");
    }
    
    protected void doTest(final String inputMathLaTeXAndExpectedAnnotation) throws Exception {
        doTest(inputMathLaTeXAndExpectedAnnotation, inputMathLaTeXAndExpectedAnnotation);
    }
    
    protected void doTest(final String inputMathLaTeX, final String expectedAnnotation) throws Exception {
        SnuggleEngine engine = new SnuggleEngine();
        SnuggleSession session = engine.createSession();
        session.parseInput(new SnuggleInput(inputMathLaTeX));
        
        DOMOutputOptions domOptions = new DOMOutputOptions();
        domOptions.setAddingMathSourceAnnotations(true);
        NodeList result = session.buildDOMSubtree(domOptions);
        
        Assert.assertEquals(1, result.getLength());
        Assert.assertTrue(result.item(0) instanceof Element);
        
        Element mathElement = (Element) result.item(0);
        String annotation = SnuggleUtilities.extractSnuggleTeXAnnotation(mathElement);
        Assert.assertEquals(expectedAnnotation, annotation);
    }
}
