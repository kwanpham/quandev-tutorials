package quandev.com.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSignatureLine;
import org.apache.poi.xssf.usermodel.XSSFVMLDrawing;
import org.junit.jupiter.api.Test;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GetSignatureFromExcel {

    public void getSignatureLines(XSSFSheet sheet) throws Exception {
        XSSFSignatureLine signatureLine = new XSSFSignatureLine();
        signatureLine.parse(sheet);
        System.out.println("Found XSSFSignatureLine:");
        System.out.println(signatureLine.getSuggestedSigner());
        System.out.println(signatureLine.getSuggestedSigner2());
        System.out.println(signatureLine.getSuggestedSignerEmail());
    }

    public void getSignatureLinesFromVMLDrawing(XSSFSheet sheet) throws Exception {
        XSSFVMLDrawing vmlDrawing = sheet.getVMLDrawing(false);
        if (vmlDrawing != null) {
            org.apache.poi.schemas.vmldrawing.XmlDocument vmlDrawingDocument = vmlDrawing.getDocument();
            String declareNameSpaces = "declare namespace v='urn:schemas-microsoft-com:vml'; "
                    + "declare namespace o='urn:schemas-microsoft-com:office:office' ";
            org.apache.xmlbeans.XmlObject[] selectedObjects = vmlDrawingDocument.selectPath(
                    declareNameSpaces
                            + ".//v:shape//o:signatureline");
            for (org.apache.xmlbeans.XmlObject object : selectedObjects) {
                if (object instanceof com.microsoft.schemas.office.office.CTSignatureLine) {
                    com.microsoft.schemas.office.office.CTSignatureLine ctSignatureLine = (com.microsoft.schemas.office.office.CTSignatureLine) object;
                    System.out.println("Found CTSignatureLine:");
                    System.out.println(ctSignatureLine.getSuggestedsigner());
                    System.out.println(ctSignatureLine.getSuggestedsigner2());
                    System.out.println(ctSignatureLine.getSuggestedsigneremail());
                }
            }
        }
    }

    @Test
    public void getSignature() throws Exception {
        Workbook workbook = WorkbookFactory.create(Files.newInputStream(Paths.get("temp/abc2.xlsx")));
        for (Sheet sheet : workbook) {
            if (sheet instanceof XSSFSheet) {
                System.out.println("Sheet " + sheet.getSheetName());
                getSignatureLines((XSSFSheet) sheet);
                getSignatureLinesFromVMLDrawing((XSSFSheet) sheet);
            }
        }
        workbook.close();
    }

    @Test
    public void testA() throws Exception {
        Screen screen = new Screen();
        Pattern inputField = new Pattern("D:\\Documents\\Libgdx Workspace\\box2dtut\\java-poi-example\\temp\\1687245140840.png");
        screen.find(inputField);
      //  screen.wait(1);
                screen.click();

      //  screen.wait(1);
        screen.type("12345678");
        screen.type(Key.ENTER);


    }

}
