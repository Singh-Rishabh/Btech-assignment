import java.sql.*;  
import java.util.*;

public class mainclass{  

	// A method to get minimum element in a Arraylist
	public static int min(ArrayList<Integer> a){
		int minn = a.get(0);
		for(int i=0; i<a.size(); i++){
				if (a.get(i) < minn ) minn = a.get(i);
			}
		return minn;
	}

	// A method to get Maximum element in a Arraylist
	public static int max(ArrayList<Integer> a){
		int minn = a.get(0);
		for(int i=0; i<a.size(); i++){
				if (a.get(i) > minn ) minn = a.get(i);
			}
		return minn;
	}

	
	// A method to get Average of all element of array List

	public static float avg(ArrayList<Integer> a){
		float avg = 0;
		for(int i=0; i<a.size(); i++){
			avg = avg + a.get(i);
		}
		avg = avg/a.size();
		return avg;
	}

	// Main function to perform all the tasks of the assignment
	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");

			// For taking the user name and password of sql by user 
			System.out.println("Enter your username: ");
			Scanner scanner = new Scanner(System.in);
			String username = scanner.nextLine();
			System.out.println("Your username is " + username);

			String password = scanner.nextLine();
			System.out.println("Your password is " + password);

			// Connecting to database on your computer
			Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/sakila?useSSL=false",username,password);
			Statement stmt=con.createStatement(); 

			// creating a database meta data object
			DatabaseMetaData dbmd=con.getMetaData();  
			String table[]={"TABLE"};

			// List to store the name of tables in the database
			ArrayList<String> al = new ArrayList<String>(); 

			// store the result of the fnction getTables in rs variable.
			ResultSet rs=dbmd.getTables(null,null,null,table);
			while(rs.next()){
				al.add(rs.getString(3));
			}
  
			System.out.println("Total number of tables in the database are "+ al.size());  

			// for(String table_name : al){
			// 	System.out.println(table_name);
			// }

			// For getting Information about Columns
			ArrayList<Integer> colDetail = new ArrayList<Integer>();
			int i=0;
			for(String table_name : al){
				//System.out.println(table_name);
				rs=dbmd.getColumns(null,null,table_name,null);
				int count = 0;
				while(rs.next()) 
					count++;
				colDetail.add(count);
				//System.out.println("name of column " + colDetail.get(i));
				i++;
			}
			System.out.println("Min, Max, and Average number of columns per table are " + min(colDetail) + " " + max(colDetail) + " " + avg(colDetail) + " respectively" );


			// For getting Information about the rows of the tables
			ArrayList<Integer> rowDetail = new ArrayList<Integer>();
			for(String table_name : al){
				rs = stmt.executeQuery("select count(*) from "+ table_name + ";");
				while(rs.next()) rowDetail.add( rs.getInt(1) ) ;
			}
			System.out.println("Min, Max, and Average number of rows per table are " + min(rowDetail) + " " + max(rowDetail) + " " + avg(rowDetail) + " respectively" );

			// Printing Maxium minimum and avergar number of foregin keys in the tables 

			ArrayList<Integer> fkDetail = new ArrayList<Integer>();
			i=0;
			for(String table_name : al){
				rs=dbmd.getImportedKeys(null,null,table_name);
				int count = 0;
				while(rs.next()) count++;
				fkDetail.add(count);
				i++;
			}
			System.out.println("Min, Max, and Average number of FKs present per table are " + min(fkDetail) + " " + max(fkDetail) + " " + avg(fkDetail) + " respectively" );

			// Printing Maxium minimum and avergar number of indexs in the tables  

			ArrayList<Integer> indexDetail = new ArrayList<Integer>();
			i=0;
			for(String table_name : al){
				rs=dbmd.getIndexInfo(null,null,table_name,true,true);
				int count = 0;
				while(rs.next()) count++;
				indexDetail.add(count);
				// System.out.println("name of column " + fkDetail.get(i));
				i++;
			}
			System.out.println("Min, Max, and Average number of indexes present per table are " + min(indexDetail) + " " + max(indexDetail) + " " + avg(indexDetail) + " respectively" );

			// Printing Maxium minimum and avergae of AutoIncrement columns per table

			ArrayList<Integer> autoIncDetail = new ArrayList<Integer>();
			i=0;
			for(String table_name : al){
				rs=dbmd.getColumns(null,null,table_name,null);
				int count = 0;
				while(rs.next()) {
					if (rs.getString("IS_AUTOINCREMENT").toUpperCase().equals("YES")) count++;
				}
				autoIncDetail.add(count);
				//System.out.println("name of column " + autoIncDetail.get(i));
				i++;
			}
			System.out.println("Min, Max, and Average number of Auto Increment columns present per table are " + min(autoIncDetail) + " " + max(autoIncDetail) + " " + avg(autoIncDetail) + " respectively" );

			// Printing Maxium minimum and avergae of BloB columns per table

			ArrayList<Integer> blobDetail = new ArrayList<Integer>();
			i=0;
			for(String table_name : al){
				rs=dbmd.getColumns(null,null,table_name,null);
				int count = 0;
				while(rs.next()) {
					if (rs.getString("TYPE_NAME").toUpperCase().equals("BLOB")) count++;
				}
				blobDetail.add(count);
				//System.out.println("name of column " + blobDetail.get(i));
				i++;
			}
			System.out.println("Min, Max, and Average number of Blob columns present per table are " + min(blobDetail) + " " + max(blobDetail) + " " + avg(blobDetail) + " respectively" );

			// Printing Maxium minimum and avergae of Int columns per table

			ArrayList<Integer> intDetail = new ArrayList<Integer>();
			i=0;
			for(String table_name : al){
				rs=dbmd.getColumns(null,null,table_name,null);
				int count = 0;
				while(rs.next()) {
					if (rs.getString("TYPE_NAME").toUpperCase().equals("INT") || rs.getString("TYPE_NAME").toUpperCase().equals("INTEGER")) count++;
				}
				intDetail.add(count);
				//System.out.println("name of column " + blobDetail.get(i));
				i++;
			}
			System.out.println("Min, Max, and Average number of Int columns present per table are " + min(intDetail) + " " + max(intDetail) + " " + avg(intDetail) + " respectively" );

			con.close();  
		}catch(Exception e){
			System.out.println(e);
		}  
	}  
}  
