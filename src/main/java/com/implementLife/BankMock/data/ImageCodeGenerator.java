package com.implementLife.BankMock.data;

import java.awt.image.BufferedImage;

public interface ImageCodeGenerator {
    BufferedImage generateQRCodeImage(String barcodeText);
}
