
# PKCS12Builder tool and library

A simple pkcs#12 builder that can be used as a tool and an embed library inside any java project (Java 8 or higher). You can create and read pkcs12 files as personal vault, saving your keys with strong secure passwords.

# PKCS#12 Files

PKCS #12, or Public-Key Cryptography Standards #12, is a file format designed for storing cryptographic objects like private keys, public keys, and certificates. Commonly known as a PFX file, it includes components such as the private and public keys, certificate chain, and often employs password-based encryption for added security. The strength of PKCS #12 lies in its use of encryption algorithms, password protection, standardization, and versatility. Encrypted PKCS #12 files safeguard sensitive data, ensuring confidentiality even if accessed by unauthorized individuals, and its standardized format enhances interoperability across different systems. The overall security also depends on the strength of encryption algorithms, password complexity, and secure password management practices employed by users.

## Run

 - Java runtime 8 or higher required
 - Just execute the .jar file at Linux, MAC or Windows

> java -jar PKCS12Builder.jar [vault name] [vault password] [vault alias] [vault data] [--r] [--c]

- This tool only works at terminal

### Params: 

 - Vault name: The name of the vault file (without extension) it will be created at current directory
 - Vault password: The password of the vault file. It must be a strong password and can not be changed after vault creation
 - Vault alias: The alias of the vault file. It is used to identify the vault file and can not be changed after vault creation
 - Vault data: Only used at write mode. This is the data to be saved at vault file. It must be any value that will be read as String and after can be parsed. It will be stored using key-value pair. It can be read after vault creation using the vault alias and the key
 - --r: Read mode. It will read the vault file at current directory using the vault alias and the vault password
 - --c: The crypto algorithm to be used. It must be a valid algorithm name. The default algorithm is AES


### Examples

- First of all, you need to build the project. Jump into build folder and run the build.sh file or just download the .jar file at releases

- Inside the root folder of this repository there is a vault file called example.p12. It was created using the command below:

> java -jar PKCS12Builder.jar example 1234567890 example "This is a test"

- To read the vault file above, use the command below:

> java -jar PKCS12Builder.jar example 1234567890 example --r

## Build

- JDK 8 or higher required
- Jump into build folder and run the build.sh file

> cd build

- Linux or MAC

> ./build.sh

- Windows

> ./build
    
## Authors

- [@leonjrmartins](https://www.github.com/leon-junio)
