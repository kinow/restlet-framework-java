/**
 * Copyright 2005-2014 Restlet
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: Apache 2.0 or LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL
 * 1.0 (the "Licenses"). You can select the license that you prefer but you may
 * not use this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the Apache 2.0 license at
 * http://www.opensource.org/licenses/apache-2.0
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://restlet.com/products/restlet-framework
 * 
 * Restlet is a registered trademark of Restlet S.A.S.
 */

package org.restlet.ext.apispark.internal.conversion;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.ext.apispark.internal.model.Definition;
import org.restlet.ext.apispark.internal.model.swagger.ApiDeclaration;
import org.restlet.ext.apispark.internal.model.swagger.ResourceDeclaration;
import org.restlet.ext.apispark.internal.model.swagger.ResourceListing;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tools library for Swagger.
 * 
 * @author Cyprien Quilici
 */
public abstract class SwaggerUtils {

    /** Internal logger. */
    protected static Logger LOGGER = Logger.getLogger(SwaggerUtils.class
            .getName());

    private static ClientResource createAuthenticatedClientResource(String url,
            String userName, String password) {
        ClientResource cr = new ClientResource(url);
        cr.accept(MediaType.APPLICATION_JSON);
        if (!isEmpty(userName) && !isEmpty(password)) {
            cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, userName,
                    password);
        }
        return cr;
    }

    /**
     * Returns the {@link Definition} by reading the Swagger definition URL.
     * 
     * @param swaggerUrl
     *            The URl of the Swagger definition service.
     * @param userName
     *            The user name for service authentication.
     * @param password
     *            The paswword for service authentication.
     * @return A {@link Definition}.
     * @throws TranslationException
     */
    public static Definition getDefinition(String swaggerUrl, String userName,
            String password) throws TranslationException {

        // Check that URL is non empty and well formed
        if (swaggerUrl == null) {
            throw new TranslationException("url", "You did not provide any URL");
        }
        Pattern p = Pattern
                .compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        boolean remote = p.matcher(swaggerUrl).matches();
        ResourceListing resourceListing = new ResourceListing();
        Map<String, ApiDeclaration> apis = new HashMap<String, ApiDeclaration>();
        if (remote) {
            LOGGER.log(Level.FINE, "Reading file: " + swaggerUrl);
            resourceListing = createAuthenticatedClientResource(swaggerUrl,
                    userName, password).get(ResourceListing.class);
            for (ResourceDeclaration api : resourceListing.getApis()) {
                LOGGER.log(Level.FINE,
                        "Reading file: " + swaggerUrl + api.getPath());
                apis.put(
                        api.getPath().replaceAll("/", ""),
                        createAuthenticatedClientResource(
                                swaggerUrl + api.getPath(), userName, password)
                                .get(ApiDeclaration.class));
            }
        } else {
            File resourceListingFile = new File(swaggerUrl);
            ObjectMapper om = new ObjectMapper();
            try {
                resourceListing = om.readValue(resourceListingFile,
                        ResourceListing.class);
                String basePath = resourceListingFile.getParent();
                LOGGER.log(Level.FINE, "Base path: " + basePath);
                for (ResourceDeclaration api : resourceListing.getApis()) {
                    LOGGER.log(Level.FINE,
                            "Reading file " + basePath + api.getPath());
                    apis.put(api.getPath(), om.readValue(new File(basePath
                            + api.getPath()), ApiDeclaration.class));
                }
            } catch (IOException e) {
                throw new TranslationException("file", e.getMessage());
            }
        }
        return SwaggerTranslator.translate(resourceListing, apis);
    }

    /**
     * Converts Java types to Swagger types
     * 
     * @param dataType
     *            The Java type
     * @return The corresponding Swagger type
     */
    public static SwaggerTypeFormat toSwaggerType(String dataType) {
        if ("string".equals(dataType)) {
            return new SwaggerTypeFormat("string");
        } else if ("byte".equals(dataType)) {
            return new SwaggerTypeFormat("string", "byte");
        } else if ("short".equals(dataType)) {
            return new SwaggerTypeFormat("integer", "int32");
        } else if ("integer".equals(dataType)) {
            return new SwaggerTypeFormat("integer", "int32");
        } else if ("long".equals(dataType)) {
            return new SwaggerTypeFormat("integer", "int64");
        } else if ("float".equals(dataType)) {
            return new SwaggerTypeFormat("number", "float");
        } else if ("double".equals(dataType)) {
            return new SwaggerTypeFormat("number", "double");
        } else if ("boolean".equals(dataType)) {
            return new SwaggerTypeFormat("boolean");
        } else if ("date".equals(dataType)) {
            return new SwaggerTypeFormat("string", "date");
        } else {
            return new SwaggerTypeFormat(dataType);
        }
    }

    /**
     * Converts Swagger types to Java types
     * 
     * @param dataType
     *            The Swagger type
     * @return The corresponding Java type
     */
    public static String toDefinitionType(SwaggerTypeFormat dataType) {
        if ("string".equals(dataType.getType())) {
            if ("date".equals(dataType.getFormat())) {
                return "date";
            } else if ("byte".equals(dataType.getFormat())) {
                return "byte";
            } else {
                return "string";
            }
        } else if ("integer".equals(dataType.getType())) {
            if ("int64".equals(dataType.getFormat())) {
                return "long";
            } else {
                return "integer";
            }
        } else if ("number".equals(dataType.getType())) {
            if ("float".equals(dataType.getFormat())) {
                return "Float";
            } else {
                return "double";
            }
        } else if ("boolean".equals(dataType.getType())) {
            return "boolean";
        } else {
            return dataType.getType();
        }
    }

    /**
     * Internal class representing a Swagger type
     */
    public static class SwaggerTypeFormat {
        private String type;

        private String format;

        public SwaggerTypeFormat(String type) {
            this(type, null);
        }

        public SwaggerTypeFormat(String type, String format) {
            this.type = type;
            this.format = format;
        }

        public String getType() {
            return type;
        }

        public String getFormat() {
            return format;
        }
    }

    /**
     * Indicates if the given velue is either null or empty.
     * 
     * @param value
     *            The value.
     * @return True if the value is either null or empty.
     */
    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Private constructor to ensure that the class acts as a true utility class
     * i.e. it isn't instantiable and extensible.
     */
    private SwaggerUtils() {
    }
}