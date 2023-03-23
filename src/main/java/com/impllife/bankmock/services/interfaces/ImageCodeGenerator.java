package com.impllife.bankmock.services.interfaces;

import java.awt.image.BufferedImage;

public interface ImageCodeGenerator {
    BufferedImage generateQRCodeImage(String barcodeText);
}
