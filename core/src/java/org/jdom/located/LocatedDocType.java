/*-- 

 Copyright (C) 2012 Jason Hunter & Brett McLaughlin.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows 
    these conditions in the documentation and/or other materials 
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.

 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.

 In addition, we request (but do not require) that you include in the 
 end-user documentation provided with the redistribution and/or in the 
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos 
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many 
 individuals on behalf of the JDOM Project and was originally 
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>.

 */

package org.jdom.located;

import org.jdom.DocType;
import org.jdom.IllegalDataException;
import org.jdom.IllegalNameException;

/**
 * An XML DOCTYPE declaration.  Method allow the user to get and set the
 * root element name, public id, and system id.
 *
 * @author Rolf Lear
 */
public class LocatedDocType extends DocType implements Located {

	/**
	 * This will create the <code>DocType</code> with
	 * the specified element name and a reference to an
	 * external DTD.
	 *
	 * @param elementName <code>String</code> name of
	 *        element being constrained.
	 * @param publicID <code>String</code> public ID of
	 *        referenced DTD
	 * @param systemID <code>String</code> system ID of
	 *        referenced DTD
	 * @throws IllegalDataException if the given system ID is not a legal
	 *         system literal or the public ID is not a legal public ID.
	 * @throws IllegalNameException if the given root element name is not a
	 *         legal XML element name.
	 */
	public LocatedDocType(String elementName, String publicID, String systemID) {
		super(elementName, publicID, systemID);
	}

	/**
	 * This will create the <code>DocType</code> with
	 * the specified element name and reference to an
	 * external DTD.
	 *
	 * @param elementName <code>String</code> name of
	 *        element being constrained.
	 * @param systemID <code>String</code> system ID of
	 *        referenced DTD
	 * @throws IllegalDataException if the given system ID is not a legal
	 *         system literal.
	 * @throws IllegalNameException if the given root element name is not a
	 *         legal XML element name.
	 */
	public LocatedDocType(String elementName, String systemID) {
		super(elementName, systemID);
	}

	/**
	 * This will create the <code>DocType</code> with
	 * the specified element name
	 *
	 * @param elementName <code>String</code> name of
	 *        element being constrained.
	 * @throws IllegalNameException if the given root element name is not a
	 *         legal XML element name.
	 */
	public LocatedDocType(String elementName) {
		super(elementName);
	}

	/**
	 * JDOM2 Serialization. In this case, DocType is simple. 
	 */
	private static final long serialVersionUID = 200L;
	
	private int line, col;

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return col;
	}

	@Override
	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public void setColumn(int col) {
		this.col = col;
	}

}
