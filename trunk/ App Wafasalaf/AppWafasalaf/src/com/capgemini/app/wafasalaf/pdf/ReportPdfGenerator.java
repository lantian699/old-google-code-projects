package com.capgemini.app.wafasalaf.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.j256.ormlite.dao.Dao;

public class ReportPdfGenerator extends AsyncTask<Void, Void, Void>{

	private String reportDir ;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.NORMAL);
	
	private DatabaseHelper dataHelper;
	private Context context;
	private ProgressDialog dialog;
	private File fileReport;
	private File fileReportPdf;
	private Client client;
	private String montant;
	private String sigName;
	private double solde;
	
	
	public ReportPdfGenerator(Context context, DatabaseHelper dataHelper, Client client, String montant, String sigName, double solde){
		this.dataHelper = dataHelper;
		this.context =context;
		this.client = client;
		this.montant = montant;
		this.sigName = sigName;
		this.solde = solde;
		
		reportDir = Environment.getExternalStorageDirectory().getPath()+"/AppWafasalaf";
		fileReport = new File(reportDir);
		if(!fileReport.exists())
			fileReport.mkdirs();
		fileReportPdf = new File(fileReport,"Lettre de recouvrement pour M "+client.getNom()+".pdf");
		
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		dialog = ProgressDialog.show(context, "", "En cours de générer une lettre de recouvrement...");
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
			final Document document = new Document();

			try {
				PdfWriter.getInstance(document, new FileOutputStream(fileReportPdf));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			document.open();
			addMetaData(document);

			addTitlePage(document);

			document.close();

	
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		dialog.dismiss();

		sendMail();
		
	}
	
	private void addTitlePage(Document document){
		Image logo;
		try {
			
			Paragraph info_societe = new Paragraph();
			info_societe.setAlignment(Image.ALIGN_LEFT);
			info_societe.add(new Paragraph("Crédit Wafasalaf Maroc",small));
			info_societe.add(new Paragraph("72, rue Ram Allah Casablanca-Maroc", small));
			info_societe.add(new Paragraph("Tel : 0522 54 51 00", small));
			info_societe.add(new Paragraph("Site web : http://www.wafasalaf.ma/", small));
			addEmptyLine(info_societe, 3);
			document.add(info_societe);
			
			logo = Image.getInstance(new URL("http://dl.dropbox.com/u/110360849/images/wafasalaf_logo.png"));
			/*logo.setAlignment(Image.ALIGN_RIGHT);
			document.add(logo);*/
			
			
			
			Paragraph infoGeo = new Paragraph();
			Date date = new Date();
			Paragraph p1 = new Paragraph("Casablanca le "+ date.toGMTString(),small);
			p1.setAlignment(Paragraph.ALIGN_RIGHT);
			Paragraph p2 = new Paragraph("A "+ client.getNom(),small);
			p2.setAlignment(Paragraph.ALIGN_RIGHT);
			infoGeo.add(p1);
			infoGeo.add(p2);
			addEmptyLine(infoGeo, 3);
			document.add(infoGeo);
			
	
			
			Paragraph reference = new Paragraph();
			reference.add(new Paragraph("Ref : "+ client.getnAffaire(), smallBold));
			reference.add(new Paragraph("Objet : Regularisation du prêt " + client.getnAffaire(),smallBold));
			addEmptyLine(reference, 3);
			document.add(reference);
			
			
			Paragraph content = new Paragraph();
			content.add(new Paragraph("Madame, Monsieur, "));
			addEmptyLine(content, 2);
			content.add(new Paragraph("Ayant contracté un contrat de prêt N."+client.getnAffaire()+" le " +
					client.getPremierEch()+", a remboursé le "+date.toLocaleString()+" à la société Wafasalaf " +
							"représentée par M." +client.getResponsable()+" le montant de "+ montant +" Dh."));
			
			addEmptyLine(content, 2);
			content.add(new Paragraph("La somme restant à rembouser par "+client.getNom()+" est de "+solde + " Dh."));
			
			addEmptyLine(content, 2);
			content.add(new Paragraph("Veuillez recevoir nos salutations distinguées."));
			
			addEmptyLine(content, 4);
	
			createTable(content);
			document.add(content);

			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
	private static void addMetaData(Document document) {
		document.addTitle("PDF");
		document.addSubject("PDF");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("App Wafasalaf");
		document.addCreator("App Wafasalaf");
	}

	private void createTable(Paragraph para){
		
		try {
			
			PdfPTable table = new PdfPTable(3);

			
			PdfPCell c1 = new PdfPCell(new Phrase("Le client"));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("      "));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
	
			c1 = new PdfPCell(new Phrase("Le Directeur"));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
	
			table.setHeaderRows(1);
			
			
			c1 = new PdfPCell(new Phrase(client.getNom()));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(""));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase(client.getResponsable()));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			Image signature_client = Image.getInstance(sigName);
			c1 = new PdfPCell(signature_client);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(""));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			Image signature_responsable = Image.getInstance(new URL("http://dl.dropbox.com/u/110360849/images/signature_responsable.png"));
			c1 = new PdfPCell(signature_responsable);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			
			
			
			
	

		para.add(table);
		

		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	  
	
	
	private void sendMail(){
		

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, fileReportPdf.getName());
		intent.putExtra(Intent.EXTRA_TEXT,"Veuillez trouver ci-joint la lettre de recouvrement. Merci bien.");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ntic2.smc@gmail.com"});
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileReportPdf));
		if (fileReportPdf.getName().endsWith(".gz")) {
			intent.setType("application/x-gzip");
		} else if (fileReportPdf.getName().endsWith(".txt")) {
			intent.setType("text/plain");
		} else {
			intent.setType("application/octet-stream");
		}
		context.startActivity(intent);
		
	}
}
