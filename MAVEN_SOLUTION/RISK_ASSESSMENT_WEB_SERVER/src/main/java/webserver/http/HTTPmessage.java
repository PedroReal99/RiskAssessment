package webserver.http;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ANDRE MOREIRA (asc@isep.ipp.pt)
 */
public class HTTPmessage {

    private static final int CR = 13;
    private static final int LF = 10;

    private static final String VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE = "Content-type:";
    private static final String CONTENT_LENGTH = "Content-length:";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    public static final String CONNECTION = "Connection";

    public static final Map<String, String> KNOWN_FILE_EXT;

    static {
        KNOWN_FILE_EXT = new HashMap<>();
        KNOWN_FILE_EXT.put(".pdf", "application/pdf");
        KNOWN_FILE_EXT.put(".xml", "application/xml");
        KNOWN_FILE_EXT.put(".json", "application/json");
        KNOWN_FILE_EXT.put(".js", "application/javascript");
        KNOWN_FILE_EXT.put(".txt", "text/plain");
        KNOWN_FILE_EXT.put(".html", "text/html");
        KNOWN_FILE_EXT.put(".gif", "image/gif");
        KNOWN_FILE_EXT.put(".png", "image/png");
    }

    static private String readHeaderLine(DataInputStream in) throws IOException {
        String ret = "";
        int val;
        do {
            val = in.read();
            if (val == -1) {
                throw new IOException();
            }
            if (val != CR) {
                ret = ret + (char) val;
            }
        } while (val != CR);
        val = in.read(); // read LF
        if (val == -1) {
            throw new IOException();
        }
        return ret;
    }

    static private void writeHeaderLine(DataOutputStream out, String line) throws IOException {
        out.write(line.getBytes(), 0, line.length());
        out.write(CR);
        out.write(LF);
    }

    //// NON-STATIC (INSTANCE) ELEMENTS
    private boolean isRequest;
    private String method;
    private URI uri;
    private String status;

    private Map<String, String> header;
    private String contentType;
    private byte[] content;

    /**
     * Creates a new HTTPmessage by receiving it from an DataInputStream
     *
     * @param in
     * @throws IOException
     */
    public HTTPmessage(DataInputStream in) throws IOException {
        header = new HashMap<>();
        String firstLine = readHeaderLine(in);
        isRequest = !firstLine.startsWith("HTTP/");
        method = null;
        uri = null;
        content = null;
        status = null;
        contentType = null;

        String[] firstLineComp = firstLine.split(" ");
        if (isRequest) {
            method = firstLineComp[0];
            uri = new URI(firstLineComp[1]);
        } else {  // response
            status = firstLineComp[1];
            for (int i = 2; i < firstLineComp.length; i++) {
                status += " " + firstLineComp[i];
            }
        }

        String headerLine;
        String headerSplit[];
        do {
            headerLine = readHeaderLine(in);
            headerSplit = headerLine.split(":");
            if (headerSplit.length > 1) {
                header.put(headerSplit[0].trim(), headerSplit[1].trim());
            } else {
                header.put(headerSplit[0].trim(), "");
            }

            if (headerLine.toUpperCase().startsWith(CONTENT_TYPE.toUpperCase())) {
                contentType = headerLine.substring(CONTENT_TYPE.length()).trim();
            } else if (headerLine.toUpperCase().startsWith(CONTENT_LENGTH.toUpperCase())) {
                String cLen = headerLine.substring(CONTENT_LENGTH.length()).trim();
                int len;
                try {
                    len = Integer.parseInt(cLen);
                } catch (NumberFormatException ne) {
                    throw new IOException();
                }
                content = new byte[len];
            }
        } while (!headerLine.isEmpty());

        // READ CONTENT
        if (content != null) {
            in.readFully(content, 0, content.length);
        }
    }

    public HTTPmessage() {
        isRequest = true;
        method = null;
        uri = null;
        content = null;
        status = null;
        contentType = null;
        header = new HashMap<>();
        header.put(CONNECTION, "close");
    }

    public void setResponseStatus(String sT) {
        isRequest = false;
        status = sT;
    }

    public void setContent(String cnt, String cType) {
        content = cnt.getBytes();
        contentType = cType;
    }

    public void setRequestMethod(String m) {
        isRequest = true;
        method = m;
    }

    public boolean send(DataOutputStream out) throws IOException {
        if (isRequest) {
            if (method == null || uri == null) {
                return false;
            }
            writeHeaderLine(out, method + " " + uri + " " + VERSION);
        } else {
            if (status == null) {
                return false;
            }
            writeHeaderLine(out, VERSION + " " + status);
        }

        if (content != null) {
            if (contentType != null) {
                writeHeaderLine(out, CONTENT_TYPE + " " + contentType);
            }
            writeHeaderLine(out, CONTENT_LENGTH + " " + content.length);
        }

        for (String headerKey : header.keySet()) {
            writeHeaderLine(out, headerKey + ": " + header.get(headerKey));
        }
        writeHeaderLine(out, "");
        if (content != null) {
            out.write(content, 0, content.length);
        }
        return true;
    }

    public String getMethod() {
        return method;
    }

    public URI getURI() {
        return uri;
    }

    public String getStatus() {
        return status;
    }

    public void setURI(String u) {
        uri = new URI(u);
    }

    public boolean hasContent() {
        return (content != null);
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentAsString() {
        if (content == null) {
            return "";
        }
        try {
            return (new String(content, 0, content.length, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
        }
        return "";
    }

    public byte[] getContent() {
        return (content);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(String headerField, String headerValue) {
        header.put(headerField.trim(), headerValue.trim());
    }

    public void setContentFromString(String c, String ct) {
        content = c.getBytes();
        contentType = ct;
    }

    public boolean setContentFromFile(String fname) {
        File f = new File(fname);
        contentType = null;
        if (!f.exists()) {
            content = null;
            return false;
        }
        for (String k : KNOWN_FILE_EXT.keySet()) {
            if (fname.endsWith(k)) {
                contentType = KNOWN_FILE_EXT.get(k);
            }
        }
        if (contentType == null) {
            contentType = "text/html";
        }

        int cLen = (int) f.length();
        if (cLen == 0) {
            content = null;
            contentType = null;
            System.out.println("flag1 " + fname);
            return false;
        }

        content = new byte[cLen];

        DataInputStream fr;
        try {
            fr = new DataInputStream(new FileInputStream(f));
            try {
                fr.readFully(content, 0, cLen);
                fr.close();
            } catch (IOException ex) {
                System.out.println("Error reading file");
                content = null;
                contentType = null;
                return false;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            content = null;
            contentType = null;
            return false;
        }
        return true;
    }

} // CLASS END
