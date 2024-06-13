//package quandev.com.springbootelaticsearchdemo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "quandev.com.springbootelaticsearchdemo.repo.elastic")
//class ElasitcSearchConfig extends ElasticsearchConfiguration {
//
////    @Value("${spring.elasticsearch.client.certificate}")
////    private String certificateBase64;
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("103.160.89.20:9200")
////                .usingSsl(getSSLConetxt())
//                .withBasicAuth("elastic", "lilama1996")
//                .build();
//        return clientConfiguration;
//    }
//
////    private SSLContext getSSLContext() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
////        byte[] decode = Base64.getDecoder().decode(certificateBase64);
////
////        CertificateFactory cf = CertificateFactory.getInstance("X.509");
////
////        Certificate ca;
////        try (InputStream certificateInputStream = new ByteArrayInputStream(decode)) {
////            ca = cf.generateCertificate(certificateInputStream);
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////
////        String keyStoreType = KeyStore.getDefaultType();
////        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
////        keyStore.load(null, null);
////        keyStore.setCertificateEntry("ca", ca);
////
////        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
////        TrustManagerFactory tmf =
////                TrustManagerFactory.getInstance(tmfAlgorithm);
////        tmf.init(keyStore);
////
////        SSLContext context = SSLContext.getInstance("TLS");
////        context.init(null, tmf.getTrustManagers(), null);
////        return context;
////    }
//
//}