package com.implementLife.BankMock.services.interfaces;

import java.awt.image.BufferedImage;

public interface ImageCodeGenerator {
    BufferedImage generateQRCodeImage(String barcodeText);
}
