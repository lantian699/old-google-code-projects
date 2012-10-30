package com.capgemini.app.wafasalaf.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.managers.ListManager;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Impaye;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.pdf.ReportPdfGenerator;
import com.capgemini.app.wafasalaf.signature.SignatureActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class RecouvrementFragment extends Fragment{

	public static final int CODE_RETOUR = 1;
	public static final int CAPTURE_CODE = 2;
	
	private EditText edit_date;
	private EditText edit_heure;
	private TextView tx_statut;
	private EditText edit_commentaire;
	private LinearLayout ll_remboursement;
	private TextView tx_montant_rem;
	private Client client;
	private DatabaseHelper dataHelper;
	private Dao<Client, ?> clientDao;
	private EditText edit_montant_rembourse;
	private Button btn_annuler;
	private Button btn_valider;
	
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	private Calendar calendar;
	private int hour;
	private int minute;
	private ImageView signView;
	private int whichChoise = 0;
	private FileOutputStream out;
	private String sigName;
	private Dao<Impaye, ?> impayeDao;
	private boolean estValider = false;
	private double value;
	private double montant;
	private Recouvrement recouvert;
	private ListManager manager;
	private ImageButton btn_take_photo;
	private ImageView img_photo;
	private Uri currentImageUri;
	
	
	
	public RecouvrementFragment(Recouvrement recouvert, ListManager manager){

		this.recouvert = recouvert;
		this.manager = manager;
		try {
			dataHelper = DatabaseHelper.getInstance(getActivity());
			clientDao = dataHelper.getDao(Client.class);
			QueryBuilder<Client, ?> queryBuilder = clientDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID,recouvert.getClientId());
			PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
			List<Client> listClient = clientDao.query(preparedQuery);

			if (listClient.size() > 0) {
				client = listClient.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_recouvrement, container, false);
		
		edit_date = (EditText)view.findViewById(R.id.edit_date_arrivee_reelle_date);
		edit_heure = (EditText)view.findViewById(R.id.edit_date_arrivee_reelle_heure);
		edit_commentaire = (EditText)view.findViewById(R.id.commentaire);
		edit_montant_rembourse = (EditText)view.findViewById(R.id.edit_montant_rembourse);
		ll_remboursement = (LinearLayout)view.findViewById(R.id.ll_remboursement);
		tx_statut = (TextView)view.findViewById(R.id.tx_statut);
		tx_montant_rem = (TextView)view.findViewById(R.id.tx_remboursement);
		signView = (ImageView)view.findViewById(R.id.signature_view);
		btn_take_photo = (ImageButton)view.findViewById(R.id.btn_take_photo);
		img_photo = (ImageView)view.findViewById(R.id.img_photo);
		
		
		btn_annuler = (Button)view.findViewById(R.id.btn_annuler);
		btn_valider = (Button)view.findViewById(R.id.btn_valider);
		
		
		btn_take_photo.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {

				Intent intent_take_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				currentImageUri = createPictureInMediaStore();
				intent_take_photo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_take_photo.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri);
				startActivityForResult(intent_take_photo, CAPTURE_CODE);
				
			}
		});
		
		/*try {
			impayeDao = dataHelper.getDao(Impaye.class);

			QueryBuilder<Impaye, ?> queryBuilder = impayeDao.queryBuilder();
			queryBuilder.where().eq(Client.COLUMN_NAME_CLIENT_ID,client.getnAffaire());
			PreparedQuery<Impaye> preparedQuery = queryBuilder.prepare();
			List<Impaye> listImpaye = impayeDao.query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		edit_montant_rembourse.addTextChangedListener(new TextWatcher() {
			


			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(!s.toString().equals("")){
				value = Double.parseDouble(s.toString());
				montant = Double.parseDouble(client.getMontantImapye());
				
				if(value < montant || value == montant){
					edit_montant_rembourse.setTextColor(Color.BLACK);
					estValider = true;
				}else{
					edit_montant_rembourse.setTextColor(Color.RED);
					estValider = false;
				}
				}
			}
		});
		
		btn_valider.setEnabled(false);
		btn_valider.setOnClickListener(new OnClickListener() {
			
			private Dao<Recouvrement, ?> recouvertDao;

			@Override
			public void onClick(View v) {
				
				
				recouvert.setStatut(Recouvrement.STATUT_TERMINE);
				try {
					recouvertDao = dataHelper.getDao(Recouvrement.class);
					recouvertDao.update(recouvert);
					
//					manager.notifyListChange();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				switch (whichChoise) {
				case 0:
					
					getActivity().finish();
					break;
					
				case 1:
					getActivity().finish();
					break;
				case 2:
					
					if(estValider){
					new ReportPdfGenerator(getActivity(), dataHelper, client,edit_montant_rembourse.getText().toString(),sigName, montant-value).execute();
					
					client.setMontantImapye(String.valueOf(montant-value));
					
					}
					break;	

				default:
					break;
				}
				
				
				
			}
		});
		
		
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        
        edit_date.setHint(dayOfMonth+"/"+monthOfYear+"/"+year);
		
		edit_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				new DatePickerDialog(getActivity(), new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						edit_date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
					}
				}, year, monthOfYear, dayOfMonth).show();
				
			}
		});
		

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
		
        edit_heure.setHint(hour+":"+minute);
		edit_heure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new TimePickerDialog(getActivity(), new	OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						edit_heure.setText(hourOfDay+":"+minute);
					}
				}, hour, minute,DateFormat.is24HourFormat(getActivity())).show();
			
				
			}
		});
		
		
		tx_statut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final String[] items = new String[] { "Client absent",
						"Client ne pouvant pas rembourser",
						"Client souhaitant rembourser" };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Choisir le statut de visite")
						.setSingleChoiceItems(items, whichChoise,
								new DialogInterface.OnClickListener() {
									

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										whichChoise = which;
										
										tx_statut.setText(items[whichChoise]);
										
										switch (whichChoise) {
										case 0:
											edit_commentaire.setVisibility(View.VISIBLE);
											ll_remboursement.setVisibility(View.GONE);
											edit_commentaire.requestFocus();
											break;
										case 1:
											edit_commentaire.setVisibility(View.VISIBLE);
											ll_remboursement.setVisibility(View.GONE);
											edit_commentaire.requestFocus();
											break;
										case 2:
											edit_commentaire.setVisibility(View.GONE);
											ll_remboursement.setVisibility(View.VISIBLE);
											tx_montant_rem.setText("M. "+client.getNom()+" a remboursé :");
											edit_montant_rembourse.requestFocusFromTouch();
											break;

										default:
											break;
										}
										
										btn_valider.setEnabled(true);
										dialog.dismiss();
									}
								});
				builder.show();

			}
		});
		
		
		signView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent_sig = new Intent(getActivity(),SignatureActivity.class);
				startActivityForResult(intent_sig, CODE_RETOUR);
				
			}
		});
		
		return view;
	}
	
	
	
	public Uri createPictureInMediaStore() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, "TAG PHOTO");
		values.put(MediaStore.Images.Media.DESCRIPTION, "Wafasalaf");
		return getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//		return context.getContentResolver().insert(uri, values);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
		case  Activity.RESULT_OK:
	
			if(requestCode == CODE_RETOUR){
				
				Bitmap image = data.getParcelableExtra(SignatureActivity.KEY_BYTE_SIGNATURE);
				signView.setImageBitmap(image);
				
				try {
					String filename = Environment.getExternalStorageDirectory()
							.getPath() + "/AppWafasalaf/Signature/";
					File fileSign = new File(filename);
					if (!fileSign.exists())
						fileSign.mkdirs();
					String imageName = "signature_" + client.getNom() + ".png";
					

					out = new FileOutputStream(new File(fileSign, imageName));
					sigName = filename+imageName;
					image.compress(Bitmap.CompressFormat.PNG, 90, out);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(requestCode == CAPTURE_CODE){
				String path = getRealPathFromURI(currentImageUri);
				img_photo.setImageURI(Uri.parse(path));
				img_photo.setVisibility(View.VISIBLE);
				
			}
			
			break;
			
		case Activity.RESULT_CANCELED:
			
			if(requestCode == CAPTURE_CODE){
				
				String path = getRealPathFromURI(currentImageUri);
				File originFile = new File(path);
			    if(originFile.length() == 0){
			    	originFile.delete();
			     }
			}
			
			break;

		default:
			break;
		}
	}
	
	
	
	 public String getRealPathFromURI(Uri contentUri) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String path = cursor.getString(column_index);
			cursor.close();
			return path;
	 }
	
}
