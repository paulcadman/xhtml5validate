package com.corefiling.xhtml5validate;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A subclass of XHTML5Validator with a default error handler
 * This class is intended to be used in conjunction with unittests.
 *
 * @author pwc
 */
public class XHTML5AssertionValidator extends XHTML5Validator {
  
  public XHTML5AssertionValidator() {
    this(true);
  }

  public XHTML5AssertionValidator(final boolean ignoreWarn) {
    super(new ErrorHandler() {
      
      @Override
      public void warning(SAXParseException exception) throws SAXException {
        if (!ignoreWarn) {
          throw new AssertionError(String.format("WARN. Line: %d. Col: %d. %s", exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));
        }
      }
      
      @Override
      public void fatalError(SAXParseException exception) throws SAXException {
        throw new AssertionError(String.format("FATAL_ERROR. Line: %d. Col: %d. %s", exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));
      }
      
      @Override
      public void error(SAXParseException exception) throws SAXException {
        throw new AssertionError(String.format("ERROR. Line: %d. Col: %d. %s", exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));
      }
    });
  }
}
