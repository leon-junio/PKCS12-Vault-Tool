package src.com.leonjunio;

/**
 * PKCS12Builder vault builder class
 * 
 * @author Leon Junio Martins Ferreira - https://github.com/leon-junio
 * @since 2024-01-19
 * @version 1.0.0
 */

public class PKCS12Builder {

    private static final Builder builder = new Builder();
    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                throw new Exception(
                        "Usage: java PKCS12Builder <vault name> <vault password> <vault alias> <vault data (only in write mode)> optional: --r (read pkcs12 file in current directory) --c <algorithm name> (A crypto algorithm - Default: "
                                + Builder.DEFAULT_CRYPTO_MODE + ")");
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
                String response = builder.read(keyStoreName, keyStorePassword, keyStoreAlias);
                System.out.println(
                        "Data read from PKCS#12 KeyStore successfully." + System.lineSeparator() + "Data: " + response);
            } else {
                if (args[3] == null || args[3].isEmpty())
                    throw new Exception("Vault data is empty (write mode must have data)");
                String keyStoreData = args[3]; // "vault data"
                String keyStoreCriptoMode = args.length == 6 && args[4].equals("--c") ? args[5] : null;
                builder.build(keyStoreName, keyStorePassword, keyStoreAlias, keyStoreData, keyStoreCriptoMode);
                System.out.println("Data saved to PKCS#12 KeyStore successfully.");
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }
    }
}