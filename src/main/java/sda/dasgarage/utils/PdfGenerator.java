
package sda.dasgarage.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import sda.dasgarage.entities.Car;
import sda.dasgarage.models.InvoiceData;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;


@Component
public class PdfGenerator {

    public File generateCarOfferPdf(Car car) throws Exception {
        String outputDir = "src/main/resources/static/pdf/";
        File directory = new File(outputDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "oferta_" + car.getId() + ".pdf";
        File file = new File(outputDir + fileName);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{3, 1});

        PdfPCell titleCell = new PdfPCell(new Phrase("Ofertă auto", titleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setPaddingBottom(10);
        headerTable.addCell(titleCell);

        Image logo = Image.getInstance("src/main/resources/static/imagines/dasgaragelogo1.jpg");
        logo.scaleToFit(100, 50);
        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(logoCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        if (car.getImagePaths() != null && !car.getImagePaths().isEmpty()) {
            String mainImagePath = "uploads/" + car.getImagePaths().get(0);
            Image mainImage = Image.getInstance(mainImagePath);
            mainImage.scaleToFit(500, 300);
            mainImage.setAlignment(Image.ALIGN_CENTER);
            document.add(mainImage);
            document.add(Chunk.NEWLINE);
        }

        document.add(new Paragraph("Marcă: " + car.getMake(), normalFont));
        document.add(new Paragraph("Model: " + car.getModel(), normalFont));
        document.add(new Paragraph("An: " + car.getYear(), normalFont));
        document.add(new Paragraph("Kilometraj: " + car.getKm() + " km", normalFont));
        document.add(new Paragraph("Motorizare: " + car.getEngine(), normalFont));
        document.add(new Paragraph("Culoare: " + car.getColor(), normalFont));

        String pret = "Pret: " + (int) car.getPriceNet();
        if (car.isTvaDeductibil()) {
            pret += " + TVA";
        }
        document.add(new Paragraph(pret + " EUR", normalFont));

        if (car.getDescription() != null && !car.getDescription().trim().isEmpty()) {
            document.add(new Paragraph("Dotari:", normalFont));
            Paragraph descriere = new Paragraph(car.getDescription(), smallFont);
            descriere.setSpacingBefore(5);
            descriere.setSpacingAfter(10);
            document.add(descriere);
        }

        List<String> images = car.getImagePaths();
        if (images != null && images.size() > 1) {
            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setSpacingBefore(10);
            imageTable.setSpacingAfter(10);

            for (int i = 1; i < images.size(); i++) {
                try {
                    Image img = Image.getInstance("uploads/" + images.get(i));
                    img.scaleToFit(250, 180);
                    PdfPCell cell = new PdfPCell(img, false);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setPaddingBottom(10);
                    imageTable.addCell(cell);
                } catch (Exception ignored) {
                }
            }

            document.add(imageTable);
        }

        document.add(Chunk.NEWLINE);
        Paragraph footer = new Paragraph(
                "★ Leasing fara TVA – finantare directa la pretul fara TVA!\n" +
                        "★ Experti in masini la comanda – rapid, sigur si fara batai de cap!\n" +
                        "★ La noi vizitatorii devin clienti multumiti!\n\n" +
                        "★ Seriozitate-Incredere-Profesionalism Garantat!\n" +
                        "★ Parteneri: BCR, Raiffeisen, BT Leasing & Leasing Operational.\n\n" +
                        "★ Fara comisioane ascunse – pretul include transportul si formalitatile de import!\n" +
                        "★ Transport Rapid in doar 5-7 zile lucratoare",
                smallFont
        );

        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return file;
    }
// metoda de generare a Facturii Proforma

    public File generateInvoicePdf(Car car, InvoiceData invoiceData) throws Exception {
        String outputDir = "src/main/resources/static/pdf/";
        File directory = new File(outputDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "factura_proforma_" + car.getId() + ".pdf";
        File file = new File(outputDir + fileName);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        // Titlu centrat
        Paragraph title = new Paragraph("Factura Proforma", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Logo in coltul dreapta sus
        try {
            Image logo = Image.getInstance("src/main/resources/static/imagines/dasgaragelogo1.jpg");
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_RIGHT);
            document.add(logo);
        } catch (Exception e) {
            // daca nu gaseste logo-ul, nu arunca exceptie
        }

        // Data emiterii si scadenta
        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(2);

        Paragraph dates = new Paragraph("Data emiterii: " + now + "\nData scadenta: " + dueDate, normalFont);
        dates.setSpacingAfter(10);
        document.add(dates);

        // Header Furnizor - Client
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{1, 1});

        PdfPCell supplierCell = new PdfPCell();
        supplierCell.setBorder(Rectangle.NO_BORDER);
        supplierCell.addElement(new Paragraph("Furnizor", boldFont));
        supplierCell.addElement(new Paragraph("DAS GARAGE S.R.L.", normalFont));
        supplierCell.addElement(new Paragraph("CUI: 49465106", normalFont));
        supplierCell.addElement(new Paragraph("Reg. Com: J40/1378/2024", normalFont));
        supplierCell.addElement(new Paragraph("Tara: ROMANIA", normalFont));
        supplierCell.addElement(new Paragraph("Jud.: Bucuresti, Loc.: Sector 1", normalFont));
        supplierCell.addElement(new Paragraph("Str.: MUNICIPIUL BUCURESTI, SECTOR 1, STR. CIACOVA, NR.17", normalFont));
        supplierCell.addElement(new Paragraph("Banca: BANCA TRANSILVANIA", normalFont));
        supplierCell.addElement(new Paragraph("IBAN: RO76BTRLRONCRT0681985501", normalFont));
        supplierCell.addElement(new Paragraph("Telefon: 0730505171", normalFont));
        supplierCell.addElement(new Paragraph("Email: danielstoicagarage@gmail.com", normalFont));

        PdfPCell clientCell = new PdfPCell();
        clientCell.setBorder(Rectangle.NO_BORDER);
        clientCell.addElement(new Paragraph("Client", boldFont));
        clientCell.addElement(new Paragraph(invoiceData.getClientName(), normalFont));
        clientCell.addElement(new Paragraph("CUI: " + invoiceData.getCui(), normalFont));
        clientCell.addElement(new Paragraph("Reg. Com: " + invoiceData.getRegCom(), normalFont));
        clientCell.addElement(new Paragraph("Tara: " + invoiceData.getCountry(), normalFont));
        clientCell.addElement(new Paragraph("Jud.: " + invoiceData.getCounty(), normalFont));
        clientCell.addElement(new Paragraph("Str.: " + invoiceData.getStreet(), normalFont));
        clientCell.addElement(new Paragraph("Banca: " + invoiceData.getBank(), normalFont));
        clientCell.addElement(new Paragraph("IBAN: " + invoiceData.getIban(), normalFont));
        clientCell.addElement(new Paragraph("Email: " + invoiceData.getEmail(), normalFont));

        header.addCell(supplierCell);
        header.addCell(clientCell);
        document.add(header);
        document.add(Chunk.NEWLINE);

        // Tabel articol
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 4, 1, 1.2f, 2, 2});

        table.addCell(new PdfPCell(new Phrase("#", boldFont)));
        table.addCell(new PdfPCell(new Phrase("Articol", boldFont)));
        table.addCell(new PdfPCell(new Phrase("UM", boldFont)));
        table.addCell(new PdfPCell(new Phrase("Cantitate", boldFont)));
        table.addCell(new PdfPCell(new Phrase("Pret fara TVA", boldFont)));
        table.addCell(new PdfPCell(new Phrase("TVA 19%", boldFont)));

        double priceNet = car.getPriceNet();
        double tvaValue = priceNet * 0.19;
        double total = priceNet + tvaValue;

        table.addCell(new PdfPCell(new Phrase("1", normalFont)));
        table.addCell(new PdfPCell(new Phrase(
                car.getMake() + " " + car.getModel() + " " + car.getYear() + "\n" + invoiceData.getVin(), normalFont)));
        table.addCell(new PdfPCell(new Phrase("BUC", normalFont)));
        table.addCell(new PdfPCell(new Phrase("1", normalFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f EUR", priceNet), normalFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f EUR", tvaValue), normalFont)));

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Total cu TVA
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(50);
        totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalTable.setWidths(new float[]{3, 2});

        PdfPCell label = new PdfPCell(new Phrase("Total cu TVA:", boldFont));
        label.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell totalCell = new PdfPCell(new Phrase(String.format("%.2f EUR", total), boldFont));
        totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        totalTable.addCell(label);
        totalTable.addCell(totalCell);
        document.add(totalTable);
        document.add(Chunk.NEWLINE);

        // Instructiuni de plata
        Paragraph instr = new Paragraph("Instructiuni de plata:\nBanca / IBAN: BANCA TRANSILVANIA / RO76BTRLRONCRT0681985501", smallFont);
        instr.setSpacingBefore(10);
        document.add(instr);

        document.close();
        return file;
    }


    // Metode helper
    private PdfPCell getNoBorderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell getRightCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }


}