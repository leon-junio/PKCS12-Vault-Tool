package src.com.leon_junio;

/**
 * PKCS12Builder vault builder class
 * 
 * @author Leon Junio Martins Ferreira - https://github.com/leon-junio
 * @since 2024-01-19
 * @version 1.0.0
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;

public class PKCS12Builder {

    private static final String DEFAULT_CRYPTO_MODE = "AES";

    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                throw new Exception(
                        "Usage: java PKCS12Builder <vault name> <vault password> <vault alias> <vault data (only in write mode)> optional: --r (read pkcs12 file in current directory) --c <algorithm name> (A crypto algorithm - Default: "
                                + DEFAULT_CRYPTO_MODE + ")");
            }
            if (args[0] == null || args[0].isEmpty())
                throw new Exception("Vault name is empty");
            String keyStoreName = args[0] + ".p12"; // "vault.p12"
            if (args[1] == null || args[1].isEmpty())
                System.out.println("Vault password is empty (not recommended)");
            String keyStorePassword = args[1]; // "vault"
            if (args[2] == null || args[2].isEmpty())
                throw new Exception("Vault alias is empty");
            String keyStoreAlias = args[2]; // "vault"
            if (args.length == 4 && args[3].equals("--r")) {
                String response = read(keyStoreName, keyStorePassword, keyStoreAlias);
                System.out.println(
                        "Data read from PKCS#12 KeyStore successfully." + System.lineSeparator() + "Data: " + response);
            } else {
                if (args[3] == null || args[3].isEmpty())
                    throw new Exception("Vault data is empty (write mode must have data)");
                String keyStoreData = args[3]; // "vault data"
                String keyStoreCriptoMode = args.length == 6 && args[4].equals("--c") ? args[5] : null;
                build(keyStoreName, keyStorePassword, keyStoreAlias, keyStoreData, keyStoreCriptoMode);
                System.out.println("Data saved to PKCS#12 KeyStore successfully.");
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }
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
    public static void build(String keyStoreName, String keyStorePassword, String keyStoreAlias, String keyStoreData,
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
    public static String read(String keyStoreName, String keyStorePassword, String keyStoreAlias)
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