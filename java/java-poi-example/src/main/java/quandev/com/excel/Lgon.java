//package quandev.com.excel;
//
//import java.security.KeyStore;
//import java.security.Security;
//
//public class Lgon {
//
//    public static void main(String[] args) {
//    /*
//        Properties config = new Properties();
//        config.put("library", "/path/someVendor_pkcs11.dll");
//        config.put("name", "Token1");
//    */
//        try {
//
//            SunPKCS11 result = new SunPKCS11("res/pkcs11.cfg");
//            if (result.getService("KeyStore", "PKCS11") == null) {
//                throw new RuntimeException("No PKCS#11 Service available. Probably Security Token (Smartcard) not inserted");
//            }
//
//            // Register the Provider
//            if (Security.getProvider(result.getName()) != null) {
//                Security.removeProvider(result.getName());
//            }
//
//            Security.addProvider(result);
//            //throw new RuntimeException("Failed to install SUN PKCS#11 Provider",e);
//
//            AuthProvider pkcs11Prov = result;
//            //Provider pkcs11Prov = result; //no possibility of explicit login
//            pkcs11Prov.login(new Subject(), new SwingPasswordCallbackHandler()); //explicit login
//
//            KeyStore.CallbackHandlerProtection pwCallbackProt = new CallbackHandlerProtection( new SwingPasswordCallbackHandler() );
//            KeyStore.Builder builder = KeyStore.Builder.newInstance("PKCS11", pkcs11Prov, pwCallbackProt);
//            KeyStore ks = builder.getKeyStore();
//
//            for(Enumeration<String> aliases = ks.aliases(); aliases.hasMoreElements(); ) {
//                String alias = aliases.nextElement();
//                System.out.println(alias);
//
//                // print certifcate
//                Certificate cert = ks.getCertificate(alias);
//                if (cert != null) {
//                    System.out.print(" Certificate found. type="+cert.getType());
//
//                    if (cert instanceof X509Certificate){
//                        System.out.print(" SubjectDN="+((X509Certificate)cert).getSubjectDN());
//                    }
//                    System.out.println();
//                }
//
//                // private key is accessed without password
//                Key pk = ks.getKey(alias, null);
//                if (pk != null) {
//                    System.out.println(" Private key found. algorithm="+pk.getAlgorithm());
//                }
//
//                System.out.println();
//            }
//
//        } catch (KeyStoreException e) {
//            throw new RuntimeException("Failed to load PKCS#11 Keystore",e);
//        } catch (LoginException e) {
//            System.out.println("LoginException: Here, you can SWING!!!");
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
