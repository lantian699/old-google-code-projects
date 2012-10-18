package com.alstom.lean.all.pdfviewer;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.alstom.lean.all.R;
import com.alstom.lean.all.fragments.MesureDocumentFragment;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Task;
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
	
	
	private DatabaseHelper dataHelper;
	private Dao<Task, ?> taskDao;
	private java.util.List<Task> listTask;
	private Context context;
	private ProgressDialog dialog;
	private boolean signed;
	private String imageName;
	private String projectName;
	private File fileReport;
	private File fileReportPdf;
	
	
	public ReportPdfGenerator(Context context, DatabaseHelper dataHelper, boolean signed, String imageName, String projectName){
		this.dataHelper = dataHelper;
		this.context =context;
		this.signed = signed;
		this.imageName = imageName;
		this.projectName = projectName;
		
		reportDir = Environment.getExternalStorageDirectory().getPath()+"/Alstom/Project "+projectName +"/Report/";
		fileReport = new File(reportDir);
		if(!fileReport.exists())
			fileReport.mkdirs();
		fileReportPdf = new File(fileReport,"report.pdf");
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		if(signed)
		dialog = ProgressDialog.show(context, "", "Generating report with signature, please wait....");
		else
			dialog = ProgressDialog.show(context, "", "Generating report without signature, please wait....");
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		
		
		try {
			taskDao = dataHelper.getDao(Task.class);
			listTask = taskDao.queryForAll();
			final Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(fileReportPdf));

			document.open();
			addMetaData(document);

			addTitlePage(document);

			addContent(document);
			
			if(signed)
				addSignature(document);
			

			document.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		dialog.dismiss();
		
		
		 Uri uri = Uri.parse("file://"+fileReportPdf.getPath());
	     System.out.println(uri.getPath());
	     Fragment fragment_document = new MesureDocumentFragment(uri, "pdf");
	     ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.activity_detail_container_2, fragment_document).commit();
		
		
		
	}
	
	private static void addTitlePage(Document document)
			throws DocumentException{
		Image cap_alstom_img;
		Image alstom;
		try {
			
			cap_alstom_img = Image
					.getInstance(new URL("http://dl.dropbox.com/u/110360849/images/cap_alstom.png"));
			document.add(cap_alstom_img);
			
			Paragraph preface = new Paragraph();
			// We add one empty line
			addEmptyLine(preface, 1);
			// Lets write a big header
			preface.add(new Paragraph("FIELD SERVICE REPORT", catFont));

			addEmptyLine(preface, 1);
			// Will create: Report generated by: _name, _date
			preface.add(new Paragraph(
					"Report generated by: " + "M. Lucent" + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					smallBold));
			addEmptyLine(preface, 3);
			preface.add(new Paragraph(
					"This document contains the task imformation  ", smallBold));

			addEmptyLine(preface, 8);

			document.add(preface);
			// Start a new page
			document.newPage();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	private void addContent(Document document) throws DocumentException {
		Anchor anchor = new Anchor("TASK INFORMATION", catFont);
		anchor.setName("TASK INFORMATION");

		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		for (Task task : listTask) {
			
			Paragraph subPara = new Paragraph(task.getName(), subFont);
			Section subCatPart = catPart.addSection(subPara);
			createTable(subPara, task);

			
		}
		
		
		document.add(catPart);
		
		
	}
	
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Lars Vogel");
		document.addCreator("Lars Vogel");
	}

	private static void createTable(Paragraph subPara, Task task)
			throws BadElementException {
		PdfPTable table = new PdfPTable(2);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Attribute"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Value"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		table.addCell("Name");
		table.addCell(task.getName());
		
		table.addCell("Work Instruction");
		table.addCell(task.getWorkInstruction());
		
		table.addCell("Description");
		table.addCell(task.getDescription());
		
		table.addCell("Record Sheet");
		table.addCell(task.getRecordSheet());
		
		table.addCell("Begin");
		table.addCell(task.getBegin());
		
		table.addCell("End");
		table.addCell(task.getEnd());
		
		table.addCell("Status");
		table.addCell(task.getStatus());
		
		table.addCell("Type");
		table.addCell(task.getType());
		
		table.addCell("Customer name");
		table.addCell(task.getCustomerName());
		
		table.addCell("Responsible");
		table.addCell(task.getResponsible());
		
		table.addCell("Related task");
		table.addCell(task.getRelatedTask());
		
		table.addCell("Related object");
		table.addCell(task.getRelatedObject());
		
		table.addCell("RequiresWitnessPoint");
		table.addCell(task.getRequiresWitnessPoint());
		
		table.addCell("Project name");
		table.addCell(task.getParentProject());
		
		table.addCell("Folder");
		table.addCell(task.getAttachment());
		
		table.addCell("Signature");
		table.addCell(task.getSignature());

		subPara.add(table);

	}

/*	private static void createList(Section subCatPart) {
		List list = new List(true, false, 10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}*/
	
	private void addSignature(Document document){
		
		try {
			Image image = Image.getInstance(imageName);
			image.scalePercent(50f);
			document.add(image);
			
			
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

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
}
	  
	
}
