package org.example.C5;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignatureUtil;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;

public class C5_01_SignatureIntegrity {
    public static final String DEST = "./target/test/resources/signatures/chapter05/";

    public static final String EXAMPLE4 = "./src/test/resources/pdfs/6_Nguyen Thi Hong Hien.pdf";
    public static final String EXPECTED_OUTPUT =
            "./src/test/resources/pdfs/hello_level_1_annotated.pdf\n" +
            "===== sig =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 1 of 2\n" +
            "Integrity check OK? true\n" +
            "./src/test/resources/pdfs/step_4_signed_by_alice_bob_carol_and_dave.pdf\n" +
            "===== sig1 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 1 of 4\n" +
            "Integrity check OK? true\n" +
            "===== sig2 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 2 of 4\n" +
            "Integrity check OK? true\n" +
            "===== sig3 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 3 of 4\n" +
            "Integrity check OK? true\n" +
            "===== sig4 =====\n" +
            "Signature covers whole document: true\n" +
            "Document revision: 4 of 4\n" +
            "Integrity check OK? true\n" +
            "./src/test/resources/pdfs/step_6_signed_by_dave_broken_by_chuck.pdf\n" +
            "===== sig1 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 1 of 5\n" +
            "Integrity check OK? true\n" +
            "===== sig2 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 2 of 5\n" +
            "Integrity check OK? true\n" +
            "===== sig3 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 3 of 5\n" +
            "Integrity check OK? true\n" +
            "===== sig4 =====\n" +
            "Signature covers whole document: false\n" +
            "Document revision: 4 of 5\n" +
            "Integrity check OK? true\n";

    //Nhận đầu vào là đường dẫn tới một tệp PDF và kiểm tra chữ ký trong tệp đó
    public void verifySignatures(String path) throws IOException, GeneralSecurityException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
        SignatureUtil signUtil = new SignatureUtil(pdfDoc);

        //Lấy danh sách tên chữ ký trong tệp PDF
        List<String> names = signUtil.getSignatureNames();
        System.out.println(path);

        //Duyệt qua danh sách các tên chữ ký và gọi phương thức verifySignature() để kiểm tra từng chữ ký.
        for (String name : names) {
            System.out.println("===== " + name + " =====");
            verifySignature(signUtil, name);
        }

        pdfDoc.close();
    }
    //Nhận đối tượng SignatureUtil và tên của một chữ ký làm đầu vào
    public PdfPKCS7 verifySignature(SignatureUtil signUtil, String name) throws GeneralSecurityException {

        //Đọc dữ liệu chữ ký từ chữ ký
        PdfPKCS7 pkcs7 = signUtil.readSignatureData(name);

        //Xác định xem chữ ký có bao phủ toàn bộ tài liệu không
        System.out.println("Signature covers whole document: " + signUtil.signatureCoversWholeDocument(name));

        //Xác định phiên bản của tài liệu
        System.out.println("Document revision: " + signUtil.getRevision(name) + " of " + signUtil.getTotalRevisions());

        //Kiểm tra tính toàn vẹn và xác thực của chữ ký
        System.out.println("Integrity check OK? " + pkcs7.verifySignatureIntegrityAndAuthenticity());

        return pkcs7;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        C5_01_SignatureIntegrity app = new C5_01_SignatureIntegrity();

        app.verifySignatures(EXAMPLE4);

    }
}
