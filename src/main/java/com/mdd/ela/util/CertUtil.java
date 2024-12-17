package com.mdd.ela.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author TruongVD
 * @project config-ticket-service
 * @date 18/03/2024
 */
@Slf4j
public class CertUtil {
    public static PrivateKey getPrivateKey(String fileName,
                                           String instance,
                                           String passWord)
            throws IOException {

        KeyStore ks = null;
        String alias = null;
        KeyStore.ProtectionParameter protParam = null;
        Enumeration en = null;
        KeyStore.PrivateKeyEntry pkEntry = null;
        PrivateKey privateKey = null;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ks = KeyStore.getInstance(instance);
            ks.load(fis, passWord.toCharArray());
            en = ks.aliases();
            protParam = new KeyStore.PasswordProtection(passWord.toCharArray());
            while (en.hasMoreElements()) {
                alias = (String) en.nextElement();
                if (ks.isKeyEntry(alias)) {
                    // Get private key
                    pkEntry = (KeyStore.PrivateKeyEntry)
                            ks.getEntry(alias, protParam);
                    privateKey = pkEntry.getPrivateKey();
                }
            }
            return privateKey;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }


    /**
     * Doc Public Key tu file *.jks, *.pfx
     * *.jks: instance jks
     * *.pfx: instance pkcs12
     **/
    public static PublicKey getPublicKey(String fileName,
                                         String instance,
                                         String passWord,
                                         String alias)
            throws IOException{
        FileInputStream fis = null;
        KeyStore ks = null;

        KeyStore.ProtectionParameter protParam = null;
        Enumeration en = null;
        PublicKey publicKey = null;

        try {
            fis = new FileInputStream(fileName);
            ks = KeyStore.getInstance(instance);
            ks.load(fis, passWord.toCharArray());
            en = ks.aliases();
            protParam = new KeyStore.PasswordProtection(passWord.toCharArray());

           /*
           while (en.hasMoreElements()) {
                alias = (String) en.nextElement();
                //if (ks.isKeyEntry(alias)) {
                if (ks.isCertificateEntry(alias)) {
                    // Get Public key
                    Certificate[] chain = ks.getCertificateChain(alias);
                    publicKey = chain[0].getPublicKey();
                 }
            }
            */

            Certificate cert = ks.getCertificate(alias);

            publicKey = getPublicKey(cert);

            return publicKey;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        finally{
            if(fis != null){
                fis.close();
            }
        }
        return null;
    }

    /**
     * Doc Certificate tu file *.cer
     **/
    public Certificate getCertificate(String certFile) {
        FileInputStream fis = null;
        CertificateFactory certFact = null;
        X509Certificate x509cert = null;

        try{
            fis = new FileInputStream(certFile);
            certFact = CertificateFactory.getInstance("X.509");
            x509cert = (X509Certificate)certFact.generateCertificate(fis);
        } catch (FileNotFoundException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "][FileNotFoundException]" + ex.getMessage());
        } catch (CertificateException e) {
            System.out.println("[" + getCurrentDateTimeNow() + "][CertificateException]" + e.getMessage());
        }
        return x509cert;
    }
      /*
      public PublicKey getPublicKey(X509Certificate cert) {
          return cert.getPublicKey();
      }
      */

    public static PublicKey getPublicKey(Certificate cert) {
        return cert.getPublicKey();
    }

    public static PublicKey getPublicKey(String certFile) {
        FileInputStream fis = null;
        PublicKey publicKey = null;
        CertificateFactory certFact = null;
        X509Certificate x509cert = null;

        try{
            fis = new FileInputStream(certFile);
            certFact = CertificateFactory.getInstance("X.509");
            x509cert = (X509Certificate)certFact.generateCertificate(fis);
            publicKey = x509cert.getPublicKey();
        } catch (FileNotFoundException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "][FileNotFoundException]" + ex.getMessage());
        } catch (CertificateException e) {
            System.out.println("[" + getCurrentDateTimeNow() + "][CertificateException]" + e.getMessage());
        }
        return publicKey;
    }

    public static boolean isExpireCert(X509Certificate cert, String dateSign) {
        Boolean blResult = true;
        Date dExpire = cert.getNotAfter();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dSign = null;
        try {
            dSign = dateFormat.parse(dateSign);
        } catch (ParseException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "[isExpireCert]" + ex.getMessage());
        }
        if (dExpire.before(dSign)) {
            blResult = false;
        }
        return blResult;
    }

    public static boolean isCertValidSubCA(X509Certificate cert,
                                           X509Certificate caCert) {
        boolean rc = false;
        try {
            cert.checkValidity();
            String algName = cert.getSigAlgName();
            Signature sig = Signature.getInstance(algName);
            sig.initVerify(caCert.getPublicKey());
            try {
                sig.update(cert.getTBSCertificate());
            } catch (Exception ex) {
                System.out.println("[" + getCurrentDateTimeNow() + "[isCertValidSubCA]" + ex.getMessage());
            }
            rc = sig.verify(cert.getSignature());
        } catch (CertificateException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "[isCertValidSubCA]" + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "[isCertValidSubCA]" + ex.getMessage());
        } catch (InvalidKeyException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "[isCertValidSubCA]" + ex.getMessage());
        } catch (SignatureException ex) {
            System.out.println("[" + getCurrentDateTimeNow() + "[isCertValidSubCA]" + ex.getMessage());
        }
        return (rc);
    }

    private static String getCurrentDateTimeNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }


    private static void dumpChain(Certificate[] chain)
    {
        for (int i = 0; i < chain.length; i++) {
            Certificate cert = chain[i];
            if (cert instanceof X509Certificate) {
                X509Certificate x509 = (X509Certificate)chain[i];
                System.err.println("Subject: " + x509.getSubjectDN());
                System.err.println("Issuer: " + x509.getIssuerDN());
            }
        }
    }

      /*
    public static int getKeyLength(final PublicKey pk) {
        int len = -1;
        if (pk instanceof RSAPublicKey) {
            final RSAPublicKey rsapub = (RSAPublicKey) pk;
            len = rsapub.getModulus().bitLength();
        } else if (pk instanceof JCEECPublicKey) {
            final JCEECPublicKey ecpriv = (JCEECPublicKey) pk;
            final org.bouncycastle.jce.spec.ECParameterSpec spec = ecpriv.getParameters();
            if (spec != null) {
                len = spec.getN().bitLength();
            } else {
                // We support the key, but we don't know the key length
                len = 0;
            }
        } else if (pk instanceof ECPublicKey) {
            final ECPublicKey ecpriv = (ECPublicKey) pk;
            final java.security.spec.ECParameterSpec spec = ecpriv.getParams();
            if (spec != null) {
                len = spec.getOrder().bitLength(); // does this really return something we expect?
            } else {
                // We support the key, but we don't know the key length
                len = 0;
            }
        } else if (pk instanceof DSAPublicKey) {
            final DSAPublicKey dsapub = (DSAPublicKey) pk;
            if ( dsapub.getParams() != null ) {
                len = dsapub.getParams().getP().bitLength();
            } else {
                len = dsapub.getY().bitLength();
            }
        }
        return len;
    }
    */

    public static String[] splitString(String strSource,
                                       int splitLength){
        String[] arrayReturn = null;
        int i;
        for (i = 0; i < strSource.length(); i = i + splitLength){
            if((i + splitLength) < strSource.length())
                arrayReturn = add(arrayReturn, strSource.substring(i, i + splitLength));
            else
                arrayReturn = add(arrayReturn, strSource.substring(i, strSource.length()));
        }
        return arrayReturn;
    }

    private static String[] add(String[] originalArray, String newItem)
    {
        int currentSize;
        if (originalArray == null)
            currentSize = 0;
        else
            currentSize = originalArray.length;
        int newSize = currentSize + 1;
        String[] tempArray = new String[newSize];
        for (int i=0; i < currentSize; i++)
        {
            tempArray[i] = originalArray[i];
        }
        tempArray[newSize- 1] = newItem;
        return tempArray;
    }

    public static byte[] concatTwoByteArrays(byte[] arr1, byte[] arr2) {

        int arr1Size;
        int arr2Size;
        byte[] out;

        if (arr1 == null)
            arr1Size = 0;
        else
            arr1Size = arr1.length;

        if (arr2 == null)
            arr2Size = 0;
        else
            arr2Size = arr2.length;

        out = new byte[arr1Size + arr2Size];

        if(arr1==null){
            System.arraycopy(arr2, 0, out, arr1Size, arr2Size);
        }
        else {
            System.arraycopy(arr1, 0, out, 0, arr1Size);
            System.arraycopy(arr2, 0, out, arr1Size, arr2Size);
        }

        return out;
    }
}
