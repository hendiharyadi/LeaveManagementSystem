package Client.Client.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.Charset;

public class BasicHeader {

    public static String createHeaders() {
        Authentication authtication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authtication.getName() + ":" + authtication.getCredentials();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }
}
