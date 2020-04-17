package com.abc.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Bean
    public AlipayClient getAlipayClient() {

        return new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                "2016092800618043",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/IOqWqKvUgNSWQg8XNHN/ikU2mEepI+d+BmsA/Ix2cYxQlxIKDcKZGjzhBgnd8v2ka9zdfQtzNf+FK4UERx5swA2vd/xUYk/1z573BLVhlZ15di2ijzzny6GLX62+qAHU4cIlBDe6vb+ltrbBIMftlvnrDURYatESGg5jYoLwrj69pCj4jRWKYScCtwZ/UrGgYhApI9xEmKTNeUrnoT1w9Ij/4E6s5ZOfXd43kx3TbwARTiP5JTc5/GzAwjhhTqIpvtCML5wMmzDvj3lmgbtER9nWbFUjf2ba+IFR4ynpTxBdxl2y1sWI3D9poSQQt/V/cMwIL7xb4TqJ53hE2w5NAgMBAAECggEAX+dvbxvB5QakJaWOFvG9xtCl/EgbJTcb8LPdu4f+H2zIoKO9bIgNDnAI/kzeZJ2KQpfRIt6dATmmNrdoroXVMXKJK3QEjH8saka9GGwna4j8lLcFHR3MzO7z55CiCRoamSyTE6nzIOQXp1pwh6YgTArG6vgkRE2NDQ1ADQSgeY3F9p+72amiOE0r1Gt0ouYpTmew12r2DHnt0pzWI6knWHFQDb6r9LPeF+MJZ03zvy+D1Lc/sXKJKhp1ujJlTutiWNES0DJ1IETSEpRikP6/rlcR4klRFdg0F9Oh4TJ0VoDSc3Bp91p17QmV8DXmqATnAsteHth9bMR1aWqnU3CeLQKBgQD4c6mbIVp4NtQTGcsmgPf2RHvXq5qv6V+K6qKYcRxro87oXXlloq3kzH70Mqw6G+asxqSnhEtDKRKI+ak6MpFIKctdyXhtokYrqZLfu8nOrNkbakF4tG2BXxqvxWsVxHBfUJY+3uzaM9XUNfuXqt61BAeqstQrwHUkA+mufbsP1wKBgQDE72vvQVybLaTx1OOdbnyj9CkM6Scy+ku+P4Sp8U/+E3wErNfqWEavhLoVGtYg70zTpODdBTGOxDq8N/GZLm4/xYYDh9hzW9xa4u46gu+oS657tm6TyyUoAQCsRuEFJqi8djxe0eLrLCy8Of4RwB2f7Ma/7fbaH7VnJornTfLeewKBgQC/Ou9BuxNSUgk7xm+p7Dxu7dlQcAH5AiyK1PxfTzhr1UJtcXz98I/d+lSBCvurIkyTkbWsRlRTlipxCZDqfX97RsgEIQ0zfWJ7OuMUmArFrB7TTh6klGNw303AGrjIoVFI29M5AwbVEG62DvLekyMlurtX9JsALfs7xFM5/gZcMwKBgDDXqI9YSJ17/pKz2BftlSAcqCm5ka1FApAWtNJpNQuwzWtjatGGP1G8u2IMm9rjPsym8dYfOaFM7bxMq7DwIQvT817gzEAgIlCwQ6FjZAj7bDTmlBo3oemABii0E47xfZvwPxzNNVhaRmYQhADnAvHtCYYEgNqsTpNcrcqcPIYNAoGBAN2R6VPE/gYAs34Hcwm/utBFJZd4AVJ5O7XNSG6P5FC5o5wreaMEF9qmC80JaJWgNPzHb/onForuF2seXRfiFd/r420IQyOCwotwttNLZvcwSI12xf6GtqTN2DzO1it3guT7rO/B0j3I54OwJRkcmtGuMVwVHppbWWCKnX2/1wHT",
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhyWOkQPIpVNB8DbqE53pFXhXEQsOasMWr5oQW2a3LP1L07z6oB+Aw3BVeI1AwLkd4KbOCiUu3we6WfFtV1QeXUTXjAiKgbIXR98EuF1q+ZW2ZBEt/uuKdA6LnGmf/x1kuldyefDmQd3jMN3MI93Q135tumzWHd8H0neNUebs84NabqBdFCG1NOq0PwDchk80tqa0+O37umS78ox1cB8GFQbp9JRbp6C9eoFd68LjVuw9Z+Os7imVDmyLtSVsfyepdjnbv1qw3uhwGeMHS0VyMc5MZtPMhWui99FMSTBgn9UAjeraWbTmmpXG+is7pbx6r+3XA/UxK552BH7AT3vO0QIDAQAB",
                "RSA2");

    }

}


