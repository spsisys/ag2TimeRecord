package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.MySQLAccess;

public class Boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Workers");
		MySQLAccess dao = new MySQLAccess();
		dao.readWorkers();
	}

}
