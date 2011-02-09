/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.data;

/**
 * Protocol used by client and server connectors. Connectors enable the
 * communication between components by implementing standard protocols.
 * 
 * @author Jerome Louvel
 */
public final class Protocol {

    /** Indicates that the port number is undefined. */
    public static final int UNKNOWN_PORT = -1;

    /**
     * AJP 1.3 protocol to communicate with Apache HTTP server or Microsoft IIS.
     */
    public static final Protocol AJP = new Protocol("ajp", "AJP",
            "Apache Jakarta Protocol", 8009);

    /** All protocols wildcard. */
    public static final Protocol ALL = new Protocol("all", "ALL",
            "Wildcard for all protocols", UNKNOWN_PORT);

    /**
     * CLAP (ClassLoader Access Protocol) is a custom scheme to access to
     * representations via classloaders. Example URI:
     * "clap://thread/org/restlet/Restlet.class".<br>
     * <br>
     * In order to work, CLAP requires a client connector provided by the core
     * Restlet engine.
     * 
     * @see org.restlet.data.LocalReference
     */
    public static final Protocol CLAP = new Protocol("clap", "CLAP",
            "Class Loader Access Protocol", UNKNOWN_PORT, true);

    /**
     * FILE is a standard scheme to access to representations stored in the file
     * system (locally most of the time). Example URI:
     * "file:///D/root/index.html".<br>
     * <br>
     * In order to work, FILE requires a client connector provided by the core
     * Restlet engine.
     * 
     * @see org.restlet.data.LocalReference
     */
    public static final Protocol FILE = new Protocol("file", "FILE",
            "Local File System Protocol", UNKNOWN_PORT, true);

    /** FTP protocol. */
    public static final Protocol FTP = new Protocol("ftp", "FTP",
            "File Transfer Protocol", 21);

    /** HTTP protocol. */
    public static final Protocol HTTP = new Protocol("http", "HTTP",
            "HyperText Transport Protocol", 80);

    /** HTTPS protocol (via SSL socket). */
    public static final Protocol HTTPS = new Protocol("https", "HTTPS",
            "HyperText Transport Protocol (Secure)", 443, true);

    /**
     * JAR (Java ARchive) is a common scheme to access to representations inside
     * archive files. Example URI:
     * "jar:http://www.foo.com/bar/baz.jar!/COM/foo/Quux.class".
     * 
     * @see org.restlet.data.LocalReference#createJarReference(Reference,
     *      String)
     */
    public static final Protocol JAR = new Protocol("jar", "JAR",
            "Java ARchive", UNKNOWN_PORT, true);

    /** JDBC protocol. */
    public static final Protocol JDBC = new Protocol("jdbc", "JDBC",
            "Java DataBase Connectivity", UNKNOWN_PORT);

    /** POP protocol. */
    public static final Protocol POP = new Protocol("pop", "POP",
            "Post Office Protocol", 110);

    /** POPS protocol (via SSL/TLS socket).. */
    public static final Protocol POPS = new Protocol("pops", "POPS",
            "Post Office Protocol (Secure)", 995, true);

    /**
     * RIAP (Restlet Internal Access Protocol) is a custom scheme to access
     * representations via internal calls to virtual hosts/components. Example
     * URIs: "riap://component/myAppPath/myResource" and
     * "riap://application/myResource".<br>
     * <br>
     * In order to work, RIAP doesn't requires any client connector and is
     * automatically supported by the Restlet engine.
     * 
     * @see org.restlet.data.LocalReference
     */
    public static final Protocol RIAP = new Protocol("riap", "RIAP",
            "Restlet Internal Access Protocol", UNKNOWN_PORT, true);

    /** SMTP protocol. */
    public static final Protocol SMTP = new Protocol("smtp", "SMTP",
            "Simple Mail Transfer Protocol", 25);

    /** SMTPS protocol (via SSL/TLS socket). */
    public static final Protocol SMTPS = new Protocol("smtps", "SMTPS",
            "Simple Mail Transfer Protocol (Secure)", 465, true);

    /** Local Web Archive access protocol. */
    public static final Protocol WAR = new Protocol("war", "WAR",
            "Web Archive Access Protocol", UNKNOWN_PORT, true);

    /**
     * ZIP is a special scheme to access to representations inside Zip archive
     * files. Example URI: "zip:file:///tmp/test.zip!/test.txt".
     * 
     * @see org.restlet.data.LocalReference#createZipReference(Reference,
     *      String)
     */
    public static final Protocol ZIP = new Protocol("zip", "ZIP",
            "Zip Archive Access Protocol", UNKNOWN_PORT, true);

    /**
     * Creates the protocol associated to a URI scheme name. If an existing
     * constant exists then it is returned, otherwise a new instance is created.
     * 
     * @param name
     *            The scheme name.
     * @return The associated protocol.
     */
    public static Protocol valueOf(final String name) {
        Protocol result = null;

        if ((name != null) && !name.equals("")) {
            if (name.equalsIgnoreCase(AJP.getSchemeName())) {
                result = AJP;
            } else if (name.equalsIgnoreCase(CLAP.getSchemeName())) {
                result = CLAP;
            } else if (name.equalsIgnoreCase(FILE.getSchemeName())) {
                result = FILE;
            } else if (name.equalsIgnoreCase(FTP.getSchemeName())) {
                result = FTP;
            } else if (name.equalsIgnoreCase(HTTP.getSchemeName())) {
                result = HTTP;
            } else if (name.equalsIgnoreCase(HTTPS.getSchemeName())) {
                result = HTTPS;
            } else if (name.equalsIgnoreCase(JAR.getSchemeName())) {
                result = JAR;
            } else if (name.equalsIgnoreCase(JDBC.getSchemeName())) {
                result = JDBC;
            } else if (name.equalsIgnoreCase(POP.getSchemeName())) {
                result = POP;
            } else if (name.equalsIgnoreCase(POPS.getSchemeName())) {
                result = POPS;
            } else if (name.equalsIgnoreCase(RIAP.getSchemeName())) {
                result = RIAP;
            } else if (name.equalsIgnoreCase(SMTP.getSchemeName())) {
                result = SMTP;
            } else if (name.equalsIgnoreCase(SMTPS.getSchemeName())) {
                result = SMTPS;
            } else if (name.equalsIgnoreCase(WAR.getSchemeName())) {
                result = WAR;
            } else if (name.equalsIgnoreCase(ZIP.getSchemeName())) {
                result = ZIP;
            } else {
                result = new Protocol(name);
            }
        }

        return result;
    }

    /** The confidentiality. */
    private volatile boolean confidential;

    /** The default port if known or -1. */
    private volatile int defaultPort;

    /** The description. */
    private final String description;

    /** The name. */
    private volatile String name;

    /** The scheme name. */
    private volatile String schemeName;

    /**
     * Constructor.
     * 
     * @param schemeName
     *            The scheme name.
     */
    public Protocol(final String schemeName) {
        this(schemeName, schemeName.toUpperCase(), schemeName.toUpperCase()
                + " Protocol", UNKNOWN_PORT);
    }

    /**
     * Constructor.
     * 
     * @param schemeName
     *            The scheme name.
     * @param name
     *            The unique name.
     * @param description
     *            The description.
     * @param defaultPort
     *            The default port.
     */
    public Protocol(final String schemeName, final String name,
            final String description, int defaultPort) {
        this(schemeName, name, description, defaultPort, false);
    }

    /**
     * Constructor.
     * 
     * @param schemeName
     *            The scheme name.
     * @param name
     *            The unique name.
     * @param description
     *            The description.
     * @param defaultPort
     *            The default port.
     * @param confidential
     *            The confidentiality.
     */
    public Protocol(final String schemeName, final String name,
            final String description, int defaultPort,
            final boolean confidential) {
        this.name = name;
        this.description = description;
        this.schemeName = schemeName;
        this.defaultPort = defaultPort;
        this.confidential = confidential;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object object) {
        return (object instanceof Protocol)
                && getName().equalsIgnoreCase(((Protocol) object).getName());
    }

    /**
     * Returns the default port number.
     * 
     * @return The default port number.
     */
    public int getDefaultPort() {
        return this.defaultPort;
    }

    /**
     * Returns the description.
     * 
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the name.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the URI scheme name.
     * 
     * @return The URI scheme name.
     */
    public String getSchemeName() {
        return this.schemeName;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return (getName() == null) ? 0 : getName().toLowerCase().hashCode();
    }

    /**
     * Indicates if the protocol guarantees the confidentially of the messages
     * exchanged, for example via a SSL-secured connection.
     * 
     * @return True if the protocol is confidential.
     */
    public boolean isConfidential() {
        return this.confidential;
    }

    /**
     * Returns the name.
     * 
     * @return The name.
     */
    @Override
    public String toString() {
        return getName();
    }

}