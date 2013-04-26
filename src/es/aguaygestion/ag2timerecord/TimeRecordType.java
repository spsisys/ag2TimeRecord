package es.aguaygestion.ag2timerecord;

public class TimeRecordType {

	private Integer _id;
	private String _name;
	
	TimeRecordType (Integer _typeId, String _typeName) {
		_id = _typeId;
		_name = _typeName;
	}
	
	/*
	 * Accessors 
	 */
	public Integer getId() {
		return _id;
	}
	public String getName() {
		return _name;
	}
	
	/*
	 * Mutators 
	 */
	public void setId(Integer _typeId) {
		_id = _typeId;
	}
	public void setName(String _typeName) {
		_name = _typeName;
	}
	
	/*
	 * Override default methods
	 */
	@Override
	public String toString() {
		return _name;
	}
	
}
