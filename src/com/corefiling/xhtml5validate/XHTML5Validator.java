package com.corefiling.xhtml5validate;

import java.io.IOException;

import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.validation.SimpleDocumentValidator.SchemaReadException;
import nu.validator.xml.SystemErrErrorHandler;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * An XHTML5 validator using nu.validator from http://github.com/validator
 *
 * @author pwc
 */
public class XHTML5Validator {
  private final SimpleDocumentValidator _validator = new SimpleDocumentValidator();
  private boolean _loadExternalEnts = true;
  private boolean _noStream = true;
  //http://s.validator.nu/* schemas are retrieved from the local entity cache in vnu.jar
  private static String _schemaLoc = "http://s.validator.nu/html5-all.rnc";
  
  public XHTML5Validator(final ErrorHandler errorHandler) {
    setUpValidator(errorHandler);
  }

  private void setUpValidator(final ErrorHandler errorHandler) {
    try {
     
      _validator.setUpMainSchema(_schemaLoc, new SystemErrErrorHandler());
      _validator.setUpValidatorAndParsers(errorHandler, _noStream, _loadExternalEnts);
    }
    catch (SchemaReadException e) {
      throw new RuntimeException(String.format("Failed to read schema from local entity cache: %s", _schemaLoc));
    }
    catch (Exception e) {
      // test setup failed
      throw new RuntimeException(String.format("Failed to initialize validator: %s", e.getMessage()));
    }
  }
  
  public void setLoadExternalEntities(final boolean b) {
    _loadExternalEnts = b;
  }
  
  public void setSchemaLocation(final String loc) {
    _schemaLoc = loc;
  }
  
  public void validate(final InputSource inputSource) throws IOException, SAXException {
    _validator.checkXmlInputSource(inputSource);
  }
}

