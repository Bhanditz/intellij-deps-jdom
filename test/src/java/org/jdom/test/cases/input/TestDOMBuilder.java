package org.jdom.test.cases.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.jdom.Attribute;
import org.jdom.DefaultJDOMFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.input.sax.XMLReaders;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter2;
import org.jdom.test.util.FidoFetch;
import org.jdom.test.util.UnitTestUtil;

@SuppressWarnings("javadoc")
public class TestDOMBuilder {

	@Test
	public void testDOMBuilder() {
		DOMBuilder db = new DOMBuilder();
		assertNotNull(db);
	}

	@Test
	public void testFactory() {
		DOMBuilder db = new DOMBuilder();
		assertTrue(db.getFactory() instanceof DefaultJDOMFactory);
		DefaultJDOMFactory fac = new DefaultJDOMFactory();
		assertFalse(db.getFactory() == fac);
		db.setFactory(fac);
		assertTrue(db.getFactory() == fac);
	}
	
	@Test
	public void testSimpleDocument() {
		checkDOM("/DOMBuilder/simple.xml", false);
	}
	
	@Test
	public void testAttributesDocument() {
		checkDOM("/DOMBuilder/attributes.xml", false);
	}
	
	@Test
	public void testNamespaceDocument() {
		checkDOM("/DOMBuilder/namespaces.xml", false);
	}
	
	@Test
	public void testDocTypeDocument() {
		checkDOM("/DOMBuilder/doctype.xml", false);
	}
	
	@Test
	public void testComplexDocument() {
		checkDOM("/DOMBuilder/complex.xml", false);
	}
	
	@Test
	public void testXSDDocument() {
		checkDOM("/xsdcomplex/input.xml", true);
	}
	
	@Test
	public void testNoNamespaceDOM() throws Exception {
		// https://github.com/hunterhacker/jdom/issues/138
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		org.w3c.dom.Document doc = dbFactory.newDocumentBuilder().newDocument();
		doc.setXmlVersion("1.0");

		org.w3c.dom.Element root = doc.createElement("Document");

		root.setAttribute("xmlns", "urn:iso:foo");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "urn:iso:foo bar.xsd");
		doc.appendChild(root);

		// The above is a badly-formed DOM document without the correct
		// namespaceing. The second attribute should use root.setAttributeNS
		DOMBuilder dbuilder = new DOMBuilder();
		Document jdoc = dbuilder.build(doc);

		Namespace xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Attribute att = jdoc.getRootElement().getAttribute("schemaLocation", xsi);
		assertTrue(att != null);
		assertTrue("xsi".equals(att.getNamespacePrefix()));

	}
	
	private void checkDOM(String resname, boolean xsdvalidate) {
		try {
			org.w3c.dom.Document domdoc = HelpTestDOMBuilder.getDocument(resname, xsdvalidate);
			DOMBuilder db = new DOMBuilder();
			Document dombuild = db.build(domdoc);
			Element domroot = db.build(HelpTestDOMBuilder.getRoot(domdoc));
			
			SAXBuilder sb = new SAXBuilder(xsdvalidate
					? XMLReaders.XSDVALIDATING
					: XMLReaders.NONVALIDATING );
			sb.setExpandEntities(false);
			
			Document saxbuild = sb.build(FidoFetch.getFido().getURL(resname));
			Element saxroot = saxbuild.hasRootElement() ? saxbuild.getRootElement() : null;
			
			assertEquals(toString(saxbuild), toString(dombuild));
			assertEquals(toString(saxroot), toString(domroot));
			
		} catch (Exception e) {
			UnitTestUtil.failException(
					"Could not parse file '" + resname + "': " + e.getMessage(), e);
		}
	}
	
	private void normalizeDTD(DocType dt) {
		if (dt == null) {
			return;
		}
		// do some tricks so that we can compare the results.
		// these may well break the actual syntax of DTD's but for testing
		// purposes it is OK.
		String internalss = dt.getInternalSubset().trim() ;
		// the spaceing in and around the internal subset is different between
		// our SAX parse, and the DOM parse.
		// make all whitespace a single space.
		internalss = internalss.replaceAll("\\s+", " ");
		// It seems the DOM parser internally quotes entities with single quote
		// but our sax parser uses double-quote.
		// simply replace all " with ' and be done with it.
		internalss = internalss.replaceAll("\"", "'");
		dt.setInternalSubset("\n" + internalss + "\n");
	}
	
	private String toString(Document doc) {
		UnitTestUtil.normalizeAttributes(doc.getRootElement());
		normalizeDTD(doc.getDocType());
		XMLOutputter2 out = new XMLOutputter2(Format.getPrettyFormat());
		CharArrayWriter caw = new CharArrayWriter();
		try {
			out.output(doc, caw);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return caw.toString();
	}

	private String toString(Element emt) {
		UnitTestUtil.normalizeAttributes(emt);
		XMLOutputter2 out = new XMLOutputter2(Format.getPrettyFormat());
		CharArrayWriter caw = new CharArrayWriter();
		try {
			out.output(emt, caw);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return caw.toString();
	}

}
