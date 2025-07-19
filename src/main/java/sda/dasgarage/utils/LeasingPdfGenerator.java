package sda.dasgarage.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import sda.dasgarage.models.LeasingRequest;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;

@Component
public class LeasingPdfGenerator {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ByteArrayInputStream generateLeasingPdf(LeasingRequest rec) {
        return generateLeasingPdf(
                rec.getClient(),
                rec.getBunFinantat(),
                rec.getPretFaraTVA(),
                rec.getPerioada(),
                rec.getRezidualaProc(),
                rec.getDobanda()
        );
    }

    public ByteArrayInputStream generateLeasingPdf(String clientName, String bunFinantat, double pretFaraTVA,
                                                   int perioada, double valoareRezidualaProc, double dobandaProc) {

        double avansProc = 20.0;
        double taxaAdministrareProc = 2.5;
        double comisionProc = 0.0005;

        double valoareReziduala = pretFaraTVA * valoareRezidualaProc / 100;
        double taxaAdministrare = pretFaraTVA * taxaAdministrareProc / 100;
        double avans = pretFaraTVA * avansProc / 100 + taxaAdministrare;
        double sumaFinantata = pretFaraTVA - pretFaraTVA * avansProc / 100;

        double dobandaLunara = (dobandaProc / 100) / 12;
        double rataAnuitate = (sumaFinantata * dobandaLunara) / (1 - Math.pow(1 + dobandaLunara, -perioada));
        double comisionLunar = pretFaraTVA * comisionProc;
        double rataLunara = rataAnuitate + comisionLunar;

        double casco = (pretFaraTVA * 0.04) / 12;
        double totalRate = rataLunara * perioada;
        double costTotalLeasing = totalRate + valoareReziduala;

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Header cu logouri
            PdfPTable header = new PdfPTable(4);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{2, 2, 2, 2});

            String[] logos = {"logobcr.png", "logoraiffeisen.png", "logobtleasing.jpg", "logooperational.jpg"};
            for (String logo : logos) {
                Image img = Image.getInstance("src/main/resources/static/imagines/" + logo);
                img.scaleToFit(100, 40);
                PdfPCell cell = new PdfPCell(img, false);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.addCell(cell);
            }
            document.add(header);
            document.add(new Paragraph(" "));

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11);

            Paragraph title = new Paragraph("OFERTA LEASING FINANCIAR", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Client: " + clientName, normalFont));
            document.add(new Paragraph("Bun finantat: " + bunFinantat, normalFont));
            document.add(new Paragraph("Pret fara TVA: " + df.format(pretFaraTVA) + " EUR", normalFont));
            document.add(new Paragraph("Valoare reziduala (" + valoareRezidualaProc + "%): " + df.format(valoareReziduala) + " EUR", normalFont));
            document.add(new Paragraph("Dobanda fixa: " + df.format(dobandaProc) + "%", normalFont));
            document.add(new Paragraph("Taxa administrare (2.5%): " + df.format(taxaAdministrare) + " EUR", normalFont));
            document.add(new Paragraph("Avans estimativ (20% + taxa): " + df.format(avans) + " EUR", normalFont));
            document.add(new Paragraph("Suma finantata: " + df.format(sumaFinantata) + " EUR", normalFont));
            document.add(new Paragraph("Rata lunara estimata: " + df.format(rataLunara) + " EUR", normalFont));
            document.add(new Paragraph("Asigurare CASCO estimata: " + df.format(casco) + " EUR / luna", normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Simulare rate:", sectionFont));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.addCell("Nr. Rata");
            table.addCell("Rata lunara (EUR)");
            for (int i = 1; i <= perioada; i++) {
                table.addCell("Rata " + i);
                table.addCell(df.format(rataLunara));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Cost total leasing (fara TVA): " + df.format(costTotalLeasing) + " EUR", sectionFont));
            document.add(new Paragraph("Aceasta simulare are caracter informativ si poate suferi modificari in functie de ofertele societatilor de leasing.", normalFont));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}



