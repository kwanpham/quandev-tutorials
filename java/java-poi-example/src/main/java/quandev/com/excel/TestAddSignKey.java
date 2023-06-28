package quandev.com.excel;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.dsig.SignatureConfig;
import org.apache.poi.poifs.crypt.dsig.SignatureInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSignatureLine;
import org.junit.jupiter.api.Test;

import javax.security.auth.callback.*;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;


public class TestAddSignKey {

    public Pair<KeyPair, X509Certificate> getKeyStoreAndCertFromWindow() throws Exception {


        KeyStore ks = KeyStore.getInstance("Windows-MY");
        ks.load(null, null);
        // extracting private key and certificate
        Iterator<String> listKey = ks.aliases().asIterator();
        String alias1 = listKey.next(); // alias of the keystore entry
        String alias = listKey.next();

        System.out.println(alias);
        Key key =  ks.getKey(alias , null);
        X509Certificate x509Certificate = (X509Certificate) ks.getCertificate(alias);

        System.out.println("access private key :: " + key);
        System.out.println("Algorithm  :: " + key.getAlgorithm());
        System.out.println("SubjectAlternativeNames  :: " + x509Certificate.getSubjectAlternativeNames());
        System.out.println("IssuerDN   :: " + x509Certificate.getIssuerDN());
        System.out.println("SubjectDN  :: " + x509Certificate.getSubjectDN());
        System.out.println("PublicKey  :: " + x509Certificate.getPublicKey());

        // Get public key
        PublicKey publicKey = x509Certificate.getPublicKey();
        // Return a key pair
        KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);
        return Pair.create(keyPair, x509Certificate);
    }

    public Pair<KeyPair, X509Certificate> getKeyStoreAndCertFromPrivateKey() throws Exception {
        File file = new File("temp/privateKey.pfx");
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(file);
        keystore.load(fis, "123456".toCharArray());
        fis.close();


        // extracting private key and certificate
        String alias = keystore.aliases().nextElement(); // alias of the keystore entry
        Key key = keystore.getKey(alias, "123456".toCharArray());
        X509Certificate x509Certificate = (X509Certificate) keystore.getCertificate(alias);


//        System.out.println("access private key :: " + key);
//        System.out.println("Algorithm  :: " + key.getAlgorithm());
//        System.out.println("SubjectAlternativeNames  :: " + x509Certificate.getSubjectAlternativeNames());
//        System.out.println("IssuerDN   :: " + x509Certificate.getIssuerDN());
//        System.out.println("SubjectDN  :: " + x509Certificate.getSubjectDN());
//        System.out.println("PublicKey  :: " + x509Certificate.getPublicKey());

        // Get public key
        PublicKey publicKey = x509Certificate.getPublicKey();

        // Return a key pair
        KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);
        return Pair.create(keyPair, x509Certificate);
    }


    public void signExcel(String pathFile) throws Exception {


        Workbook workbook = WorkbookFactory.create(Files.newInputStream(Paths.get(pathFile)));
        FileInputStream signatureImageFile = new FileInputStream("temp/info.png");

        // Read the signature image from file
        byte[] signatureImageBytes = IOUtils.toByteArray(signatureImageFile);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        XSSFSignatureLine signatureLine = new XSSFSignatureLine();
        signatureLine.setCaption("caption");
        signatureLine.setSigningInstructions("SigningInstructions");
        signatureLine.setSuggestedSignerEmail("Quan@gmail.com");
        signatureLine.setSuggestedSigner2("setSuggestedSigner2");
        signatureLine.setSuggestedSigner("Wuan");
        signatureLine.setContentType("CLGT");
        signatureLine.setInvalidStamp("43534543");
        signatureLine.setPlainSignature(signatureImageBytes);


        Drawing<XSSFShape> drawing = sheet.createDrawingPatriarch();
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);

//         Path to the signature image
//
//
//         Insert the signature image into the Excel file
        int pictureIndex = workbook.addPicture(signatureImageBytes, Workbook.PICTURE_TYPE_PNG);
        drawing.createPicture(anchor, pictureIndex);
        signatureLine.add(sheet, (XSSFClientAnchor) anchor);
        workbook.write(Files.newOutputStream(Paths.get("temp/result.xlsx")));
        workbook.close();


        Pair<KeyPair, X509Certificate> pair = getKeyStoreAndCertFromWindow();


        // filling the SignatureConfig entries (minimum fields, more options are available ...)
        // filling the SignatureConfig entries (minimum fields, more options are available ...)
        SignatureConfig signatureConfig = new SignatureConfig();
        signatureConfig.setKey(pair.getFirst().getPrivate());
        signatureConfig.setSigningCertificateChain(Collections.singletonList(pair.getSecond()));
        signatureConfig.setPackageSignatureId(signatureLine.getSetupId().toString());
        OPCPackage pkg = OPCPackage.open("temp/result.xlsx", PackageAccess.READ_WRITE);
        signatureConfig.setOpcPackage(pkg);
        signatureLine.updateSignatureConfig(signatureConfig);

        // adding the signature document to the package
        SignatureInfo si = new SignatureInfo();
        si.setSignatureConfig(signatureConfig);

//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.execute(() -> {
//
//            try {
//
//                Thread.sleep(2000);
//                System.out.println("Auto fill pin number");
//                Screen screen = new Screen();
//                screen.type("12345678");
//                screen.type(org.sikuli.script.Key.ENTER);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });


        si.confirmSignature();


        // optionally verify the generated signature
        boolean b = si.verifySignature();
        assert (b);
        // write the changes back to disc
        pkg.close();

    }

    private static void lockMouseCursor(Robot robot) {
        // Get the current mouse position
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        // Move the mouse cursor to a specific position (e.g., top-left corner)
        robot.mouseMove(0, 0);

        // Disable mouse events
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // Move the mouse cursor back to the original position
        robot.mouseMove(mouseX, mouseY);
    }

    private static void unlockMouseCursor(Robot robot) {
        // Enable mouse events
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @Test
    public void testSign() throws Exception {
        signExcel("temp/excel1.xlsx");
    }


}
