package com.capgemini.app.wafasalaf.tools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;


import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.spreadsheet.SpreadsheetAndroidRequestInitializer;
import com.capgemini.app.wafasalaf.spreadsheet.Worksheet;
import com.capgemini.app.wafasalaf.spreadsheet.Worksheet.Table;
import com.google.api.services.spreadsheet.client.SpreadsheetClient;
import com.google.api.services.spreadsheet.model.ListEntry;
import com.google.api.services.spreadsheet.model.ListFeed;
import com.google.api.services.spreadsheet.url.ListUrl;
import com.j256.ormlite.dao.Dao;

public class Tools {

	public static final String PREF_SPREADSHEET_KEY = "spreadsheetKey";
	public static final String PREF_SPREADSHEET_KEY_TOTO = "spreadsheetKey_toto";
	public static final String PREF_SPREADSHEET_KEY_QIQI = "spreadsheetKey_qiqi";
	public static final String PREF_SPREADSHEET_KEY_MIMI = "spreadsheetKey_mimi";
	public static final String _UPDATED = "updated";
	public static final String KEY_SIEBEL_ID = "siebelid";
	public static final String TAG = "Tools";

	/*public static void DrawOneStationOnMap(Activity context, Factory factory,
			MapView mapView) {

		List<Overlay> mapOverlays = mapView.getOverlays();

		Drawable drawable = context.getResources().getDrawable(
				R.drawable.pointer);
		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(
				drawable, context, mapView);

		GeoPoint StationPos = new GeoPoint((int) (factory.getLatitude() * 1E6),
				(int) (factory.getLongitude() * 1E6));

		OverlayItem overlayitem = new OverlayItem(StationPos,
				factory.getName(), factory.getAddress());

		velibItemizedOverlay.addOverlay(overlayitem);

		mapOverlays.add(velibItemizedOverlay);

		mapView.postInvalidate();

		MapController mapController = mapView.getController();
		mapController.animateTo(StationPos);
		mapController.setZoom(10);

	}*/

	public static void getAll(DatabaseHelper dataHelper,
			SpreadsheetAndroidRequestInitializer requestInitializer,
			Table table) {
		try {
			String spreadsheetKey = requestInitializer.settings.getString(PREF_SPREADSHEET_KEY, null);
			SpreadsheetClient spreadsheetClient = new SpreadsheetClient(requestInitializer.createRequestFactory());
			ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey,table.toString());

			ListFeed listFeed = spreadsheetClient.listFeed().list().execute(listUrl);

			Dao<Recouvrement, ?> recouvertDao = dataHelper.getDao(Recouvrement.class);
			Dao<Client, ?> clientDao = dataHelper.getDao(Client.class);

			for (ListEntry listEntry : listFeed.getEntries()) {

				Map<String, String> content = listEntry.customElements;

				if (table.toString().equals(Worksheet.TABLE_NAME_RECOUVREMENT)) {
					Recouvrement recouvert = new Recouvrement();
					
					recouvert.setClientId(content.get(Worksheet.TABLE_RECOUVREMENT_COLUMN_CLIENT_ID));
					recouvert.setDateVisitePro(content.get(Worksheet.TABLE_RECOUVREMENT_COLUMN_DATE_VISITE));
					recouvert.setStatut(content.get(Worksheet.TABLE_RECOUVREMENT_COLUMN_STATUT));
					recouvert.setDateArrive(content.get(Worksheet.TABLE_RECOUVREMENT_COLUMN_DATE_ARRIVE));
					recouvert.setDateDepart(content.get(Worksheet.TABLE_RECOUVREMENT_COLUMN_DATE_DEPART));
					
					recouvertDao.create(recouvert);
					Log.i(TAG, "Recouvrement created !");
				}else if (table.toString().equals(Worksheet.TABLE_NAME_CLIENT)) {
					Client client = new Client();
					
					client.setResponsable(content.get(Worksheet.TABLE_CLIENT_COLUMN_RESPONSABLE));
					client.setProduit(content.get(Worksheet.TABLE_CLIENT_COLUMN_PRODUIT));
					client.setnAffaire(content.get(Worksheet.TABLE_CLIENT_COLUMN_NUMERO_AFFAIRE));
					client.setNom(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOM_COMPLET));
					client.setAdresse(content.get(Worksheet.TABLE_CLIENT_COLUMN_ADRESSE));
					client.setGsm(content.get(Worksheet.TABLE_CLIENT_COLUMN_GSM));
					client.setTel(content.get(Worksheet.TABLE_CLIENT_COLUMN_TEL_DOMICILE));
					client.setEmployeur(content.get(Worksheet.TABLE_CLIENT_COLUMN_EMPLOYEUR));
					client.setAdressePro(content.get(Worksheet.TABLE_CLIENT_COLUMN_ADRESSE_PROFESSIONNELLE));
					client.setNomConjoint(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOM_CONJOINT));
					client.setEmployeurConjoint(content.get(Worksheet.TABLE_CLIENT_COLUMN_EMPLOYEUR_CONJOINT));
					client.setTypeBien(content.get(Worksheet.TABLE_CLIENT_COLUMN_TYPE_BIEN));
					client.setNbDossierVivant(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOMBRE_DOSSIER));
					client.setMontantPret(content.get(Worksheet.TABLE_CLIENT_COLUMN_MONTANT_PRET));
					client.setMensualite(content.get(Worksheet.TABLE_CLIENT_COLUMN_MENSUALITE));
					client.setCodeBanque(content.get(Worksheet.TABLE_CLIENT_COLUMN_CODE_BANQUE));
					client.setAgence(content.get(Worksheet.TABLE_CLIENT_COLUMN_AGENCE));
					client.setNbEcheance(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOMBRE_ECHEANCE));
					client.setPremierEch(content.get(Worksheet.TABLE_CLIENT_COLUMN_PREMIER_ECHEANCE));
					client.setDernierEch(content.get(Worksheet.TABLE_CLIENT_COLUMN_DERNIER_ECHEANCE));
					client.setMontantCRD(content.get(Worksheet.TABLE_CLIENT_COLUMN_MONTANT_CRD));
					client.setNbIncident(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOMBRE_INCIDENT));
					client.setNbImpayes(content.get(Worksheet.TABLE_CLIENT_COLUMN_NOMBRE_IMPAYES));
					client.setMontantChaqueImpayes(content.get(Worksheet.TABLE_CLIENT_COLUMN_MONTANT_CHAQUES_IMPAYES));
					
					clientDao.create(client);
					
					
					Log.i(TAG, "Recouvrement created !");
				} 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/*public static void sendAll(DatabaseHelper dataHelper,
			SpreadsheetAndroidRequestInitializer requestInitializer, Table table, User user) {

		try {
			Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
			Dao<VisualInspection, ?> inspectionDao = dataHelper
					.getDao(VisualInspection.class);
			Dao<Mesurement, ?> mesurementDao = dataHelper
					.getDao(Mesurement.class);

			// String spreadsheetKey =
			// requestInitializer.settings.getString(PREF_SPREADSHEET_KEY,
			// null);
			SpreadsheetClient client = new SpreadsheetClient(
					requestInitializer.createRequestFactory());
			String spreadsheetKey = requestInitializer.settings.getString(
					user.toString(), null);
			ListUrl listUrl = ListUrl.forListFeedByKey(spreadsheetKey,
					table.toString());

			if (table.toString().equals(Worksheet.TABLE_NAME_TASK)) {
				List<Task> listTask = taskDao.queryForAll();

				for (Task task : listTask) {

					ListEntry listEntryModified = null;
					if (task.getSelfUrl() != null) {
						listEntryModified = client.listEntry().get()
								.execute(new ListUrl(task.getSelfUrl()));

						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_NAME,
								task.getName());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_BEGIN,
								task.getBegin());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_END, task.getEnd());
						// listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME,
						// task.getParentProject());
						// listEntryModified.setCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
						// task.isRequiresWitnessPoint());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_STATUS,
								task.getStatus());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_TASK_COLUMN_TYPE,
								task.getType());
						if (task.getType().equals("finding")) {
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_STATUS,
									task.getStatus());
							listEntryModified
									.setCustomElement(
											Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
											task.getRequiresWitnessPoint());
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RELATED_TASK,
									task.getRelatedTask());
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE,
									task.getResponsible());
						}

						if (task.getAttachment() != null)
							listEntryModified.setCustomElement(
									Worksheet.TABLE_TASK_COLUMN_ATTACHMENT,
									task.getAttachment());

						client.listFeed().update().execute(listEntryModified);
					} else {

						ListEntry entry = new ListEntry();
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_NAME,
								task.getName());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_BEGIN,
								task.getBegin());
						entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_END,
								task.getEnd());
						// entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_PROJECT_NAME,
						// task.getParentProject());
						// entry.addCustomElement(Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
						// task.isRequiresWitnessPoint());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_STATUS,
								task.getStatus());
						entry.addCustomElement(
								Worksheet.TABLE_TASK_COLUMN_TYPE,
								task.getType());
						if (task.getType().equals("finding")) {
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_STATUS,
									task.getStatus());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_REQUIRE_WITNESS_POINT,
									task.getRequiresWitnessPoint());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RELATED_TASK,
									task.getRelatedTask());
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_RESPONSIBLE,
									task.getResponsible());
						}

						if (task.getAttachment() != null)
							entry.addCustomElement(
									Worksheet.TABLE_TASK_COLUMN_ATTACHMENT,
									task.getAttachment());

						ListEntry entryInsert = client.listFeed().insert()
								.execute(listUrl, entry);
						task.setSelfUrl(entryInsert.getSelfLink());
						taskDao.update(task);
					}

				}
			} else if (table.toString().equals(
					Worksheet.TABLE_NAME_VISUALINSPECTION)) {

				List<VisualInspection> listVi = inspectionDao.queryForAll();

				
				 * for (VisualInspection vi : listVi) { ListEntry
				 * listEntryModified = null; if(vi.getSelfUrl() != null){
				 * listEntryModified = client.listEntry().get().execute(new
				 * ListUrl(vi.getSelfUrl()));
				 * 
				 * // listEntryModified.setCustomElement(Worksheet.
				 * TABLE_INSPECTION_COLUMN_DESCRIPTION, vi.getDescription()); //
				 * listEntryModified
				 * .setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_KEY,
				 * vi.getKey()); //
				 * listEntryModified.setCustomElement(Worksheet.
				 * TABLE_INSPECTION_COLUMN_PARENT, vi.getDescription()); //
				 * listEntryModified
				 * .setCustomElement(Worksheet.TABLE_INSPECTION_COLUMN_VALUE,
				 * vi.getValue()); }
				 * client.listFeed().update().execute(listEntryModified);
				 * 
				 * }
				 

			} else if (table.toString().equals(Worksheet.TABLE_NAME_MESUREMENT)) {
				List<Mesurement> listMesure = mesurementDao.queryForAll();

				for (Mesurement mesure : listMesure) {
					ListEntry listEntryModified = null;
					if (mesure.getSelfUrl() != null) {
						listEntryModified = client.listEntry().get()
								.execute(new ListUrl(mesure.getSelfUrl()));
						listEntryModified.setCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_VALUE,
								mesure.getValue());
						listEntryModified.setCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_TIME,
								mesure.getTimeStamp());

						client.listFeed().update().execute(listEntryModified);
					} else {

						ListEntry entry = new ListEntry();
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_DESCRIPTION,
								mesure.getDescription());
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_VALUE,
								mesure.getValue());
						entry.addCustomElement(
								Worksheet.TABLE_MESUREMENT_COLUMN_TIME,
								mesure.getTimeStamp());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_UNIT, mesure.getUnit());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_RULE, mesure.getRule());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_LOW, mesure.getLow());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_HIGH, mesure.getHigh());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_TYPE, mesure.getType());
						entry.addCustomElement(Worksheet.TABLE_MESUREMENT_COLUMN_PARENT, mesure.getParent());
						
						ListEntry entryInsert = client.listFeed().insert()
						.execute(listUrl, entry);
						mesure.setSelfUrl(entryInsert.getSelfLink());
						mesurementDao.update(mesure);

					}
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
