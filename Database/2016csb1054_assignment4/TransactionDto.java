// A class to have a proper data Abstraction and Code thin Soft way.

// Import Statements
import java.text.SimpleDateFormat;
import java.text.DateFormat;  
import java.util.Date;  
public class TransactionDto {

	// Different Column names of the Transaction table.
	String type;
	java.util.Date tDate ;
	float amount;
	Integer accountIDFrom;
	Integer accountIDTo;
	String category;
	String remarks;

	// Method to set the class variable.
	public void set(String t, Date d, float f, int to, int from, String cat, String rem){
		this.type = t;
		this.tDate = d;
		this.amount = f;
		this.accountIDTo = to;
		this.accountIDFrom = from;
		this.category = cat;
		this.remarks = rem;
	}
}
