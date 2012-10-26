package com.capgemini.app.wafasalaf.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class RecouvrementFragment extends Fragment{

	public static final int CODE_RETOUR = 1;
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
	
	public RecouvrementFragment(Recouvrement recouvert){

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
		
		btn_annuler = (Button)view.findViewById(R.id.btn_annuler);
		btn_valider = (Button)view.findViewById(R.id.btn_valider);
		
		btn_valider.setEnabled(false);
		btn_valider.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				switch (whichChoise) {
				case 0:
					
					break;
					
				case 1:
					
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
						"Client ayant remboursé" };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Choisir le statut de visite")
						.setSingleChoiceItems(items, whichChoise,
								new DialogInterface.OnClickListener() {
									

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										whichChoise = which;
									}
								})
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										tx_statut.setText(items[whichChoise]);
										
										switch (whichChoise) {
										case 0:
											edit_commentaire.setVisibility(View.GONE);
											ll_remboursement.setVisibility(View.GONE);
											break;
										case 1:
											edit_commentaire.setVisibility(View.VISIBLE);
											ll_remboursement.setVisibility(View.GONE);
											break;
										case 2:
											edit_commentaire.setVisibility(View.GONE);
											ll_remboursement.setVisibility(View.VISIBLE);
											tx_montant_rem.setText("M. "+client.getNom()+" a remboursé :");
											break;

										default:
											break;
										}
										
										btn_valider.setEnabled(true);
									}
								})
						.setNegativeButton("Annuler",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {

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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
		case  Activity.RESULT_OK:
			
			
			
			if(requestCode == CODE_RETOUR){
				
				Bitmap image = data.getParcelableExtra(SignatureActivity.KEY_BYTE_SIGNATURE);
				signView.setImageBitmap(image);
			}
			
			break;
			
		case Activity.RESULT_CANCELED:
			
			
			break;

		default:
			break;
		}
	}
	
	
}
