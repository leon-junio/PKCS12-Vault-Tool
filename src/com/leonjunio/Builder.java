package src.com.leonjunio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;

public class Builder {

    protected static final String DEFAULT_CRYPTO_MODE = "AES";

    public Builder() {
    }

    /**
     * Build a PKCS12 KeyStore file with the given data
     * 
     * @param keyStoreName       String - The name of the KeyStore file
     * @param keyStorePassword   String - The password of the KeyStore file
     * @param keyStoreAlias      String - The alias of the KeyStore file
     * @param keyStoreData       String - The data to be stored in the KeyStore file
     * @param keyStoreCriptoMode String - The cripto mode to be used in the KeyStore
     *                           file
     * @throws Exception - If any error occurs during the process
     */
    public void build(String keyStoreName, String keyStorePassword, String keyStoreAlias, String keyStoreData,
            String keyStoreCriptoMode) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, keyStorePassword.toCharArray());
        byte[] dbPasswordCharArray = keyStoreData.getBytes();
        SecretKeyEntry secretKeyEntry = new SecretKeyEntry(
                new javax.crypto.spec.SecretKeySpec(dbPasswordCharArray,
                        keyStoreCriptoMode != null ? keyStoreCriptoMode : DEFAULT_CRYPTO_MODE));
        PasswordProtection passwordProtection = new PasswordProtection(keyStorePassword.toCharArray());
        keyStore.setEntry(keyStoreAlias, secretKeyEntry, passwordProtection);
        try (FileOutputStream fos = new FileOutputStream(keyStoreName)) {
            keyStore.store(fos, keyStorePassword.toCharArray());
        }
    }

    /**
     * Read a PKCS12 KeyStore file with the given data
     * 
     * @param keyStoreName     String - The name of the KeyStore file
     * @param keyStorePassword String - The password of the KeyStore file
     * @param keyStoreAlias    String - The alias of the KeyStore file
     * @return String - The data stored in the KeyStore file
     * @throws Exception - If any error occurs during the process
     */
    public String read(String keyStoreName, String keyStorePassword, String keyStoreAlias)
            throws Exception {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new FileInputStream(keyStoreName)) {
                keyStore.load(fis, keyStorePassword.toCharArray());
            }
            PasswordProtection passwordProtection = new PasswordProtection(keyStorePassword.toCharArray());
            SecretKeyEntry secretKeyEntry = (SecretKeyEntry) keyStore.getEntry(keyStoreAlias, passwordProtection);
            byte[] dbPasswordCharArray = ((javax.crypto.spec.SecretKeySpec) secretKeyEntry.getSecretKey())
                    .getEncoded();
            return new String(dbPasswordCharArray);
    }
}
