package com.main.app_firmas_segucor.itext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.models.FotoConformidad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConformidadDoc {
    public static final BaseColor BLUE_BLACK = new BaseColor(0, 66, 165);
    private int anchopagina=100;
    private int anchocontenido=90;
    private boolean isFrontal;
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    //private Paragraph paragraph;
    //private Font fontEncabezado = new Font(Font.FontFamily.TIMES_ROMAN,9,Font.BOLD);
    //private Font fontDetalles = new Font(Font.FontFamily.TIMES_ROMAN,10);
    //private Font fontTotales = new Font(Font.FontFamily.TIMES_ROMAN,8);
    //private Font FontTituloItem = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD);
    private Font BLUE_LINK = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE, BaseColor.BLUE);
    private Font BLUE_BLACK_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BLUE_BLACK);
    private Font fontTituloParrafo = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD|Font.UNDERLINE, BaseColor.BLACK);
    private Font BLACK_BOLD = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD, BaseColor.BLACK);
    private Font fontTituloAdvertencia = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD|Font.UNDERLINE, BaseColor.RED);
    private Font fontParrafoAdvertencia = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.ITALIC, BaseColor.RED);
    private ParagraphBorder paragraphBorder;
    private String pathFile, pathImgFirma, day, monthyear, comentario, direcciontrabajo, nombreFirma;
    private Bitmap firmaRepresentante, firmaVendedor;
    //private File[] fotoFiles;
    private List<FotoConformidad> listFoto = new ArrayList<>();

    public ConformidadDoc(Context context) {
        this.context = context;
    }

    public String getPathFile() {
        return pathFile;
    }

    void docBody(){
        try {
            PdfPTable tabletitulo = new PdfPTable(3);
            tabletitulo.setWidthPercentage(anchopagina);
            //
            Drawable drawableLogo = context.getResources().getDrawable(R.drawable.logo_segucor);
            Bitmap bitDwLogo = ((BitmapDrawable) drawableLogo).getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitDwLogo.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            Image imageLogo = Image.getInstance(stream1.toByteArray());
            //
            PdfPCell cell;
            cell = new PdfPCell(imageLogo, true);
            cell.setBorder(Rectangle.NO_BORDER);
            tabletitulo.addCell(cell);
            cell = new PdfPCell(new Phrase("MANUAL DE USUARIO", BLUE_LINK));
            cell.setPaddingTop(90);
            cell.setPaddingLeft(90);
            cell.setBorder(Rectangle.NO_BORDER);
            tabletitulo.addCell(cell);
            cell = new PdfPCell(new Phrase("FOLIO N°"));
            cell.setPaddingTop(20);
            cell.setPaddingLeft(90);
            cell.setBorder(Rectangle.NO_BORDER);
            tabletitulo.addCell(cell);
            document.add(tabletitulo);
            /****/
            PdfPTable tablebody = new PdfPTable(1);
            tablebody.setSpacingBefore(5);
            tablebody.setWidthPercentage(anchocontenido);
            PdfPCell cellbody;
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_1));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.TITULO_PARRAFO_2, fontTituloParrafo));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingTop(20);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_2));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellbody.setPaddingTop(1);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.TITULO_PARRAFO_3, fontTituloParrafo));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingTop(20);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_3));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cellbody.setPaddingTop(1);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_ADVERTENCIA_1, fontParrafoAdvertencia));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingTop(10);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.TITULO_PRECAUCION, fontTituloAdvertencia));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingTop(10);
            cellbody.setPaddingBottom(5);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_PRECAUCION));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.TITULO_FUNC_MANUALES, fontTituloParrafo));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellbody.setPaddingTop(10);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            //
            cellbody = new PdfPCell(new Phrase(ConformidadContent.PARRAFO_FUNC_MANUALES));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingBottom(10);
            tablebody.addCell(cellbody);
            document.add(tablebody);
            //
            PdfPTable table3 = new PdfPTable(2);
            table3.setWidthPercentage(anchocontenido);
            cellbody = new PdfPCell(new Phrase("DECLARACIÓN DE CONFORMIDAD.", BLUE_BLACK_BOLD));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_LEFT);
            table3.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cellbody);
            document.add(table3);
            //
            PdfPTable table4 = new PdfPTable(6);
            table4.setWidthPercentage(anchocontenido);
            cellbody = new PdfPCell(new Phrase("Fabricante: ", BLACK_BOLD));
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase("Segucor Ltda.\n" +
                    "Dalmiro Barriga 315 Buen retiro. Coronel, Concepción.\n"));
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            table4.addCell(cellbody);
            document.add(table4);
            //
            PdfPTable table5 = new PdfPTable(1);
            table5.setSpacingBefore(5);
            table5.setWidthPercentage(anchocontenido);
            cellbody = new PdfPCell(new Phrase("DESCRIPCIÓN: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            table5.addCell(cellbody);
            document.add(table5);
            //
            PdfPTable table6 = new PdfPTable(2);
            table6.setWidthPercentage(anchocontenido);
            table6.setSpacingBefore(5);
            //
            PdfPTable subtable6_1 = new PdfPTable(2);
            subtable6_1.setWidthPercentage(anchocontenido);
            cellbody = new PdfPCell(new Phrase("FIRMA : "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            //
            if(!pathImgFirma.isEmpty()){
                /**AQUI VA LA FIRMA CLIENTE*/
                Bitmap bitmapFirma;
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    bitmapFirma = BitmapFactory.decodeFile(String.valueOf(pathImgFirma), options);
                }else{
                    bitmapFirma = BitmapFactory.decodeFile(String.valueOf(pathImgFirma));
                }
                ByteArrayOutputStream streamFirma = new ByteArrayOutputStream();
                bitmapFirma.compress(Bitmap.CompressFormat.PNG, 100, streamFirma);
                Image imageFirma = Image.getInstance(streamFirma.toByteArray());
                imageFirma.setAbsolutePosition(250, 280);
                imageFirma.scaleAbsolute(159f, 159f);
                document.add(imageFirma);
                //
                /**AQUI VA LA FIRMA REPRESENTANTE*/
                ByteArrayOutputStream streamFirmaRep = new ByteArrayOutputStream();
                firmaRepresentante.compress(Bitmap.CompressFormat.PNG, 100, streamFirmaRep);
                Image imageFirmaRep = Image.getInstance(streamFirmaRep.toByteArray());
                imageFirmaRep.setAbsolutePosition(550, 50);
                imageFirmaRep.scaleAbsolute(159f, 159f);
                document.add(imageFirmaRep);
                //
                /**AQUI VA LA FIRMA VENDEDOR*/
                ByteArrayOutputStream streamFirmaVend = new ByteArrayOutputStream();
                firmaVendedor.compress(Bitmap.CompressFormat.PNG, 100, streamFirmaVend);
                Image imageFirmaVend = Image.getInstance(streamFirmaVend.toByteArray());
                imageFirmaVend.setAbsolutePosition(650, 280);
                imageFirmaVend.scaleAbsolute(159f, 159f);
                document.add(imageFirmaVend);
            }
            //
            cellbody = new PdfPCell(new Phrase("........................................."));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            /***/
            cellbody = new PdfPCell(new Phrase("NOMBRE FIRMA: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase(nombreFirma));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);

            cellbody = new PdfPCell(new Phrase("FECHA: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase(day+" de "+monthyear ));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase("LUGAR DE OBRA: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase(direcciontrabajo));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_1.addCell(cellbody);
            //
            PdfPTable subtable6_2 = new PdfPTable(2);
            table6.setWidthPercentage(anchocontenido);
            cellbody = new PdfPCell(new Phrase("FIRMA REPRESENTANTE VENTA: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_2.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase("........................................."));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_2.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase("COMENTARIO: "));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_2.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase(comentario));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPadding(5);
            subtable6_2.addCell(cellbody);
            //
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.addElement(subtable6_1);
            table6.addCell(cellbody);
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.addElement(subtable6_2);
            table6.addCell(cellbody);
            document.add(table6);
            //
            PdfPTable table7 = new PdfPTable(6);
            table7.setWidthPercentage(anchocontenido);
            PdfPTable subtable7_1 = new PdfPTable(1);
            cellbody = new PdfPCell(new Phrase("www.segucor.cl", BLUE_LINK));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setPaddingTop(100);
            cellbody.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellbody.setCellEvent(new LinkInCell("https://www.segucor.cl/"));
            subtable7_1.addCell(cellbody);
            cellbody = new PdfPCell(new Phrase("comercial@segucor.cl"));
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setHorizontalAlignment(Element.ALIGN_CENTER);
            subtable7_1.addCell(cellbody);
            //
            cellbody = new PdfPCell();
            cellbody.setBorder(Rectangle.NO_BORDER);
            cellbody.setColspan(5);
            cellbody.setPaddingBottom(50);
            cellbody.addElement(subtable7_1);
            table7.addCell(cellbody);
            //
            Drawable drawableTimbre = context.getResources().getDrawable(R.drawable.timbre_segucor);
            Bitmap bitDwTimbre = ((BitmapDrawable) drawableTimbre).getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitDwTimbre.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            Image imageTimbre = Image.getInstance(stream2.toByteArray());
            cellbody = new PdfPCell(imageTimbre, true);
            cellbody.setBorder(Rectangle.NO_BORDER);

            table7.addCell(cellbody);
            //pega la tabla al final de la pagina
            table7.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
            table7.writeSelectedRows(0, -1, document.left(document.leftMargin()),
                    table7.getTotalHeight() + document.bottom(document.bottomMargin()), pdfWriter.getDirectContent());
            //document.add(table7);
            //
            document.newPage();
            //
            PdfPTable tableFotos = new PdfPTable(1);
            tableFotos.setWidthPercentage(anchopagina);
            tableFotos.setSpacingBefore(25);
            PdfPCell cellfoto = null;
            cellfoto = new PdfPCell(new Phrase("FOTOS"));
            cellfoto.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFotos.addCell(cellfoto);
            PdfPTable tableCFotos = new PdfPTable(1);
            tableCFotos.setWidthPercentage(75);
            for (int i = 0; i < listFoto.size(); i++) {
                FotoConformidad item = listFoto.get(i);
                if(item.getIdFoto() > 0 && ( isFrontal || item.getIdFrontal() == 0)) {
                    Bitmap bitmap = BitmapFactory.decodeFile(item.getPathFoto());
                    ByteArrayOutputStream streamfoto = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamfoto);
                    Image foto = Image.getInstance(streamfoto.toByteArray());
                    cellfoto = new PdfPCell(foto, true);
                    cellfoto.setPaddingTop(10);
                    cellfoto.setPadding(5);
                    cellfoto.setBorder(Rectangle.NO_BORDER);
                    tableCFotos.addCell(cellfoto);
                }
            }
            document.add(tableFotos);
            document.add(tableCFotos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openDocument(String pathFirma, String strday, String strmonthyear, Bitmap firmaRepresentante, Bitmap firmaVendedor,
                             String comentario, String direcciontrabajo, String nombreFirma, List<FotoConformidad> listFoto, boolean isFrontal){
        pathImgFirma = pathFirma;
        day = strday;
        monthyear = strmonthyear;
        this.firmaRepresentante = firmaRepresentante;
        this.firmaVendedor = firmaVendedor;
        this.comentario = comentario;
        this.direcciontrabajo = direcciontrabajo;
        this.listFoto = listFoto;
        this.nombreFirma = nombreFirma;
        this.isFrontal = isFrontal;
        //
        /*
        File folder2 = new File (context.getFilesDir() + "/fotos");
        if(folder2.exists()) {
            String path = context.getFilesDir() + "/fotos";
            File directory = new File(path);
            fotoFiles = directory.listFiles();
        }
        File folder= new File(context.getFilesDir() + "/conformidad");
        if(!folder.exists()){
            folder.mkdirs();
        }
        */
        String nomDoc = UUID.randomUUID().toString()+".pdf";
        //pdfFile = new File (folder+"/"+nomDoc);
        pdfFile = new File (context.getCacheDir(), nomDoc);
        pathFile = pdfFile.getPath();
        try{
            document = new Document(new Rectangle(950,  1600), 10, 10, 10, 0);
            pdfWriter =  PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            paragraphBorder = new ParagraphBorder();
            pdfWriter.setPageEvent(paragraphBorder);
            document.open();
            docBody();
            document.close();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    class LinkInCell implements PdfPCellEvent {
        protected String url;
        public LinkInCell(String url) {
            this.url = url;
        }
        public void cellLayout(PdfPCell cell, Rectangle position,
                               PdfContentByte[] canvases) {
            PdfWriter writer = canvases[0].getPdfWriter();
            PdfAction action = new PdfAction(url);
            PdfAnnotation link = PdfAnnotation.createLink(
                    writer, position, PdfAnnotation.HIGHLIGHT_INVERT, action);
            writer.addAnnotation(link);
        }
    }

}
