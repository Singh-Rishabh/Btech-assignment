import java.util.List;
import java.util.*;
import java.io.*;
import java.lang.*;

public class data{

	String pid;
	String user;
	String cpu;
	String mem;
	String time;
	String command;
	int time_use;
	public void data(){
		// topData = new ArrayList<stringArr>();
	}
	public void setData(String pid , String user, String cpu , String mem, String time, String command,int time_use){
		this.pid = pid;
		this.user = user;
		this.cpu = cpu;
		this.mem = mem;
		this.time = time;
		this.command = command;
		this.time_use = time_use;
	}
}