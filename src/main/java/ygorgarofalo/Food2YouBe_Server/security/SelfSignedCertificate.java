package ygorgarofalo.Food2YouBe_Server.security;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;


public class SelfSignedCertificate {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generateKeyPair();
        X509Certificate certificate = generateSelfSignedCertificate(keyPair);

        saveCertificateToFile(certificate, "cert.pem");

        System.out.println("Self-signed certificate generated successfully.");
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static X509Certificate generateSelfSignedCertificate(KeyPair keyPair)
            throws CertificateEncodingException, OperatorCreationException, CertificateException, NoSuchAlgorithmException, IOException {
        X509Certificate cert = null;
        Date startDate = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000); // 1 year validity

        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
        X500Name dnName = new X500Name("CN=Example");

        X509CertificateHolder certHolder = new JcaX509v3CertificateBuilder(dnName, serialNumber, startDate, expiryDate, dnName, keyPair.getPublic())
                .build(new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate()));

        // Converti X509CertificateHolder in X509Certificate
        cert = new JcaX509CertificateConverter().getCertificate(certHolder);

        return cert;
    }

    private static void saveCertificateToFile(X509Certificate certificate, String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName);
             JcaPEMWriter pemWriter = new JcaPEMWriter(fileWriter)) {
            pemWriter.writeObject(certificate);
        }
    }

}
