package org.jdom.test.cases.output;

/* Please run replic.pl on me ! */
/**
 * Please put a description of your test here.
 *
 * @author unascribed
 * @version 0.1
 */

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.Format.TextMode;
import org.jdom.output.LineSeparator;
import org.jdom.output.XMLOutputter;
import org.jdom.output.support.AbstractXMLOutputProcessor;
import org.jdom.output.support.XMLOutputProcessor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import javax.xml.transform.Result;
import java.io.*;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public final class TestXMLOutputter extends AbstractTestOutputter {

    /**
     * The main method runs all the tests in the text ui
     */
    public static void main (String args[])
     {
        JUnitCore.runClasses(TestXMLOutputter.class);
    }

    public TestXMLOutputter() {
		super(true, true, false, false, false);
	}

    private XMLOutputter getOutputter(Format format) {
    	return new XMLOutputter(format);
    }

    @Override
	public String outputDocumentAsString(Format format, Document doc) {
		return getOutputter(format).outputString(doc);
	}

	@Override
	public String outputDocTypeAsString(Format format, DocType doctype) {
		return getOutputter(format).outputString(doctype);
	}

	@Override
	public String outputElementAsString(Format format, Element element) {
		return getOutputter(format).outputString(element);
	}

	@Override
	public String outputListAsString(Format format, List<? extends Content> list) {
		return getOutputter(format).outputString(list);
	}

	@Override
	public String outputCDataAsString(Format format, CDATA cdata) {
		return getOutputter(format).outputString(cdata);
	}

	@Override
	public String outputTextAsString(Format format, Text text) {
		return getOutputter(format).outputString(text);
	}

	@Override
	public String outputCommentAsString(Format format, Comment comment) {
		return getOutputter(format).outputString(comment);
	}

	@Override
	public String outputPIAsString(Format format, ProcessingInstruction pi) {
		return getOutputter(format).outputString(pi);
	}

	@Override
	public String outputEntityRefAsString(Format format, EntityRef entity) {
		return getOutputter(format).outputString(entity);
	}

	@Override
	public String outputElementContentString(Format format, Element element) {
		StringWriter out = new StringWriter();
		try {
			getOutputter(format).outputElementContent(element, out); // output() flushes
		} catch (IOException e) {
			// swallow - will never happen.
		}
		return out.toString();
	}

  @Test @Ignore
	@Override
  public void testTextWhitespace() {
  }

  @Test @Ignore
  @Override
  public void testCDATAEmpty() {
  }

  @Test @Ignore
  @Override
  public void testCDATAWhitespace() {
  }

  @Test @Ignore
  @Override
  public void testMultiText() {
  }

  @Test @Ignore
  @Override
  public void testDocumentSimple() {
  }

  @Test @Ignore
  @Override
  public void testDocumentDocType() {
  }

  @Test @Ignore
  @Override
  public void testDocumentComment() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementAttributeNotSpecifiedA() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementAttributeNotSpecifiedB() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementPreserveSpaceComplex() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMultiText() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMultiMostWhiteExpandEmpty() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMixedMultiCDATA() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMixedMultiEntityRef() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMixedMultiText() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMixedMultiZeroText() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementInterleavedEmptyText() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMultiEntityLeftRight() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMultiTrimLeftRight() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementMultiCDATALeftRight() {
  }

  @Test @Ignore
  @Override
  public void testOutputElementNamespaces() {
  }

  @Test @Ignore
  @Override
  public void testOutputDocumentSimple() {
  }

  @Test @Ignore
  @Override
  public void testOutputDocumentOmitEncoding() {
  }

  @Test @Ignore
  @Override
  public void testOutputDocumentOmitDeclaration() {
  }

  @Test @Ignore
  @Override
  public void testOutputDocumentFull() {
  }

  @Test @Ignore
  @Override
  public void testDeepNesting() {
  }

  @Test @Ignore
  @Override
  public void testOutputEscapedMixedMultiText() {
  }


  @Test
    public void test_HighSurrogatePair() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root>&#x10000; &#x10000;</root>"));
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      StringWriter sw = new StringWriter();
      outputter.output(doc, sw);
      String xml = sw.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + format.getLineSeparator() +
                   "<root>&#x10000; &#x10000;</root>" + format.getLineSeparator(), xml);
    }

    @Test
    public void test_HighSurrogatePairDecimal() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root>&#x10000; &#65536;</root>"));
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      outputter.output(doc, baos);
      String xml = baos.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + format.getLineSeparator() +
                   "<root>&#x10000; &#x10000;</root>" + format.getLineSeparator(), xml);
    }

    @Test
    public void test_HighSurrogateAttPair() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root att=\"&#x10000; &#x10000;\" />"));
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      outputter.output(doc, baos);
      String xml = baos.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + format.getLineSeparator() +
                   "<root att=\"&#x10000; &#x10000;\" />" + format.getLineSeparator(), xml);
    }

    @Test
    public void test_HighSurrogateAttPairDecimal() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root att=\"&#x10000; &#65536;\" />"));
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      outputter.output(doc, baos);
      String xml = baos.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + format.getLineSeparator() +
                   "<root att=\"&#x10000; &#x10000;\" />" + format.getLineSeparator(), xml);
    }

    // Construct a raw surrogate pair character and confirm it outputs hex escaped
    @Test
    public void test_RawSurrogatePair() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root>\uD800\uDC00</root>"));
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      outputter.output(doc, baos);
      String xml = baos.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + format.getLineSeparator() +
                   "<root>&#x10000;</root>" + format.getLineSeparator(), xml);
    }

    // Construct a raw surrogate pair character and confirm it outputs hex escaped, when UTF-8 too
    @Test
    public void test_RawSurrogatePairUTF8() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root>\uD800\uDC00</root>"));
      Format format = Format.getCompactFormat().setEncoding("UTF-8");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      outputter.output(doc, baos);
      String xml = baos.toString();
      assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + format.getLineSeparator() +
                   "<root>&#x10000;</root>" + format.getLineSeparator(), xml);
    }

    // Construct illegal XML and check if the parser notices
    @Test
    public void test_ErrorSurrogatePair() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root></root>"));
      try {
        doc.getRootElement().setText("\uD800\uDBFF");
        fail("Illegal surrogate pair should have thrown an exception");
      }
      catch (IllegalDataException e) {
    	  // do nothing
      } catch (Exception e) {
    	  fail ("Unexpected exception " + e.getClass());
      }
    }

    // Manually construct illegal XML and make sure the outputter notices
    @Test
    public void test_ErrorSurrogatePairOutput() throws JDOMException, IOException {
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(true);
      Document doc = builder.build(new StringReader("<?xml version=\"1.0\"?><root></root>"));
      Text t = new UncheckedJDOMFactory().text("\uD800\uDBFF");
      doc.getRootElement().setContent(t);
      Format format = Format.getCompactFormat().setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
        outputter.output(doc, baos);
        fail("Illegal surrogate pair output should have thrown an exception");
      }
      catch (IllegalDataException e) {
    	  // do nothing
      } catch (Exception e) {
    	  fail ("Unexpected exception " + e.getClass());
      }
    }


	@Test
	public void testXMLOutputter() {
		XMLOutputter out = new XMLOutputter();
		TestFormat.checkEquals(out.getFormat(), Format.getRawFormat());
	}


	@Test
	public void testXMLOutputterFormat() {
		Format mine = Format.getCompactFormat();
		mine.setEncoding("US-ASCII");
		XMLOutputter out = new XMLOutputter(mine);
		TestFormat.checkEquals(mine, out.getFormat());
	}

	@Test
	public void testFormat() {
		Format mine = Format.getCompactFormat();
		mine.setEncoding("US-ASCII");
		// double-constcut it.
		XMLOutputter out = new XMLOutputter();
		TestFormat.checkEquals(Format.getRawFormat(), out.getFormat());
		out.setFormat(mine);
		TestFormat.checkEquals(mine, out.getFormat());
	}

	@Test
	public void testNoLineSeparator() {
		XMLOutputter out = new XMLOutputter(Format.getRawFormat().setLineSeparator(LineSeparator.NONE));
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root />",
				out.outputString(new Document(new Element("root"))));
	}

	@Test
	public void testEscapeAttributeEntities() {
		Map<String,String> totest = new LinkedHashMap<String,String>();

		totest.put("\"", "&quot;");
		totest.put("&", "&amp;");
		totest.put("<", "&lt;");
		totest.put(">", "&gt;");
		totest.put("\t", "&#x9;");
		totest.put("\r", "&#xD;");
		totest.put("\n", "&#xA;");
		totest.put("\nz\n\n", "&#xA;z&#xA;&#xA;");

		totest.put("'", "'");

		totest.put("Frodo's Journey", "Frodo's Journey");


		XMLOutputter out = new XMLOutputter();

		for (Map.Entry<String,String> me : totest.entrySet()) {
			if (!me.getValue().equals(out.escapeAttributeEntities(me.getKey()))) {
				assertEquals("Failed attempt to escape '" + me.getKey() + "'",
						me.getValue(), out.escapeAttributeEntities(me.getKey()));
			}
		}
	}

	@Test
	public void testEscapeElementEntities() {
		Map<String,String> totest = new LinkedHashMap<String,String>();

		totest.put("\"", "\"");
		totest.put("&", "&amp;");
		totest.put("<", "&lt;");
		totest.put(">", "&gt;");
		totest.put("> >>", "&gt; &gt;&gt;");
		totest.put("\t", "\t");
		totest.put("\r", "&#xD;");
		totest.put("\n", "\r\n");

		totest.put("'", "'");

		totest.put("Frodo's Journey", "Frodo's Journey");


		XMLOutputter out = new XMLOutputter();

		for (Map.Entry<String,String> me : totest.entrySet()) {
			if (!me.getValue().equals(out.escapeElementEntities(me.getKey()))) {
				assertEquals("Failed attempt to escape '" + me.getKey() + "'",
						me.getValue(), out.escapeElementEntities(me.getKey()));
			}
		}
	}



	@Test
	public void testClone() {
		XMLOutputter xo = new XMLOutputter();
		assertTrue(xo != xo.clone());
	}

	@Test
	public void testToString() {
		Format fmt = Format.getCompactFormat();
		fmt.setLineSeparator("\n\t ");
		XMLOutputter out = new XMLOutputter(fmt);
		assertNotNull(out.toString());
	}

	/**
	 * The following method will run the output data through each of the three base
	 * formatters, raw, compact, and pretty. It will also run each of those
	 * formatters as the outputString(content), output(content, OutputStream)
	 * and output(content, Writer).
	 *
	 * The expectation is that the results of the three output forms (String,
	 * OutputStream, and Writer) will be identical, and that it will match
	 * the expected value for the appropriate formatter.
	 *
	 * @param content The content to output
	 * @param methodprefix What the methods are called
	 * @param clazz The class used as the parameter for the methods.
	 * @param setup A callback mechanism to modify the formatters
	 * @param raw  What we expect the content to look like with the RAW format
	 * @param compact What we expect the content to look like with the COMPACT format
	 * @param pretty What we expect the content to look like with the PRETTY format
	 * @param trimfw What we expect the content to look like with the TRIM_FULL_WHITE format
	 */
	@Override
	protected void checkOutput(Object content, String methodprefix, Class<?> clazz,
			FormatSetup setup, String raw, String compact, String pretty, String tso, String trimfw) {

		super.checkOutput(content, methodprefix, clazz, setup, raw, compact, pretty, tso, trimfw);

		Method mstring = getMethod(methodprefix + "String", clazz);
		Method mstream = getMethod(methodprefix, clazz, OutputStream.class);
		Method mwriter = getMethod(methodprefix, clazz, Writer.class);

		String[] descn   = new String[] {"Raw", "Compact", "Pretty", "TrimFullWhite"};
		Format ftrimfw = Format.getPrettyFormat();
		ftrimfw.setTextMode(TextMode.TRIM_FULL_WHITE);
		Format[] formats = new Format[] {
				getFormat(setup, Format.getRawFormat()),
				getFormat(setup, Format.getCompactFormat()),
				getFormat(setup, Format.getPrettyFormat()),
				getFormat(setup, ftrimfw)};
		String[] result  = new String[] {expect(raw), expect(compact), expect(pretty), expect(trimfw)};

		for (int i = 0; i < 4; i++) {
			XMLOutputter out = new XMLOutputter(formats[i]);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(result[i].length() * 2);
			CharArrayWriter caw = new CharArrayWriter(result[i].length() + 2);
			try {
				if (mstring != null) {
					String rstring = (String) mstring.invoke(out, content);
					assertEquals("outputString Format " + descn[i], result[i], rstring);
				}
				if (mstream != null) {
					mstream.invoke(out, content, baos);
					String rstream = new String(baos.toByteArray());
					assertEquals("output OutputStream Format " + descn[i], result[i], rstream);
				}
				if (mwriter != null) {
					mwriter.invoke(out, content, caw);
					String rwriter = String.valueOf(caw.toCharArray());
					assertEquals("output Writer Format " + descn[i], result[i], rwriter);
				}

			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to process " + descn[i] + " on content " + clazz + ": " + e.getMessage());
			}
		}
	}

	private Method getMethod(String name, Class<?>...classes) {
		try {
			return XMLOutputter.class.getMethod(name, classes);
		} catch (Exception e) {
			// ignore.
			System.out.println("Can't find " + name + " on " + this.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}


}
