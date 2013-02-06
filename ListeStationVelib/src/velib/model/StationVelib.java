package velib.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class StationVelib extends ModelObject {

	private static final long serialVersionUID = -3774666415177207686L;

	public static final String COLUMN_VELIB_NUMBER = "number";
	public static final String COLUMN_VELIB_NAME = "name";
	public static final String COLUMN_VELIB_ADDRESS = "VelibStationAddress";
	public static final String COLUMN_VELIB_ID = "id";
	public static final String COLUMN_VELIB_ISPREFERED= "isPrefered";

	
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	protected String name;
	@DatabaseField
	protected int number;
	@DatabaseField
	protected String address;
	@DatabaseField
	protected Double latitude;
	@DatabaseField
	protected Double longitude;
	@DatabaseField
	private Boolean open;
	@DatabaseField
	private Boolean bonus;
	@DatabaseField
	private String fullAddress;
	@DatabaseField
	private int isPrefered;
	
	
	public int getIsPrefered() {
		return isPrefered;
	}

	public void setIsPrefered(int isPrefered) {
		this.isPrefered = isPrefered;
	}

	public StationVelib() {

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	
	public int getId(){
		return id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getBonus() {
		return bonus;
	}

	public void setBonus(Boolean bonus) {
		this.bonus = bonus;
	}

	public String toString() {
		return getName();
	}

}
