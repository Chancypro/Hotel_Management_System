package application;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.BookRoomController.RoomInfo;
import application.ConsumeController.Book;
import application.goodsController.goodTable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyOrdersController {
	private Connection con;//���ݿ�
	private String guestid="";//��ʱ��Ҫ�ӵ�¼���ڶ�ȡ�û���¼id���Ѹ�
	//���������
	@FXML
    private RXFillButton CancelBtn;
	
	@FXML private Button returnBtn;
	@FXML
    void handleReturn(ActionEvent event) {
		try
    	{
    		Stage stage=(Stage)returnBtn.getScene().getWindow();
    		stage.close();
    		
//    		StageController oc=loader.getController();
//			if (oc!=null) oc.setUser(guestid);
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
	}
	
//	private int jiaotong,tingche,sheshi,ganjing,jiage,fuwu;
//	private double score;
	
	
    @FXML
    void cancel(ActionEvent event) {
    	Room selectedroom = roomTable.getSelectionModel().getSelectedItem();
    	String id0 = tableId.getCellData(selectedroom);
    	String Date = tableDate.getCellData(selectedroom);
    	Double deleteprice = tablePrice.getCellData(selectedroom);
    	
    	if(id0==null) {
    		System.out.println(1);
//    		Alert alert2 = new Alert(AlertType.INFORMATION);
    		Alert alert2 = new Alert(AlertType.INFORMATION);
    		alert2.setHeaderText("");
    		alert2.setContentText("��ѡ��Ҫɾ���ķ��䶩����");
    		alert2.show();
    		//System.out.println(2);
    		return;
    	}

    	int year11 = 0;//�������ڵôӶ�����ȡ��
		int mon11 = 0;
		int day11 = 0;
		if(!Date.isEmpty()) {
			if(Date.contains("~")) {
		    	String[] parts = Date.split("~");
		    	String begin = parts[0];
			   	String[] bt = begin.split("-");
			   	String by = bt[0];
			   	String bm = bt[1];
			   	String bd = bt[2];
		//	        	System.out.println("!:"+by+bm+bd);
		    	year11 = Integer.parseInt(by);
			   	mon11 = Integer.parseInt(bm);
			   	day11 = Integer.parseInt(bd);
		   }
		   else {
			    String[] bt = Date.split("-");
			   	String by = bt[0];
			   	String bm = bt[1];
			   	String bd = bt[2];
			   	
		    	year11 = Integer.parseInt(by);
		    	mon11 = Integer.parseInt(bm);
		    	day11 = Integer.parseInt(bd);
		    }
		}
		
    	int year2 = 0;
    	int month2 = 0;
    	int day2 = 0;
    	Date date2 = null;
    	SimpleDateFormat formatter2 = null;
    	try
		{
			date2=new Date();   //��ȡϵͳʱ��
			formatter2=new SimpleDateFormat("yyyy-MM-dd");  
		    System.out.println(formatter2.format(date2));
//			orderTime.setText(formatter.format(date));
//			
			year2 = Integer.parseInt(formatter2.format(date2).substring(0,4));
	        month2 = Integer.parseInt(formatter2.format(date2).substring(5,7));
	        day2 = Integer.parseInt(formatter2.format(date2).substring(8,10));
	        System.out.println("year2:"+year2);
	        System.out.println("month2:"+month2);
	        System.out.println("day2:"+day2);
		}
		catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    	
		System.out.println("year11:"+year11);
		System.out.println("mon11:"+mon11);
		System.out.println("day11:"+day11);
    	
    	if(year11<year2 || (year11==year2 && mon11 < month2) || (year11==year2 && mon11==month2 && day11 < day2)) {
    		Alert alert2 = new Alert(AlertType.INFORMATION);
    		alert2.setHeaderText("");
    		alert2.setContentText("����ѡ������������");
    		alert2.show();
    		//System.out.println(2);
    		return;
    	}
    	else {
    		//System.out.println(3);
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//�޸�4
        	alert.setTitle("��ʾ");
        	alert.setHeaderText("");
    		alert.setContentText("ȷ��Ҫ�˶����䣺<"+id0 +"> ��");
    		Optional<ButtonType> result = alert.showAndWait();
        	if(result.get() == ButtonType.CANCEL) {
        		return ;
        	}
    	}
    	
    	String rp0 = RoomAllPrice.getText().substring(6);
    	String ap0 = AllPrice.getText().substring(4);
    	RoomAllPrice.setText("�����ܼƷѣ�"+Double.toString(Double.parseDouble(rp0)-deleteprice));
		AllPrice.setText("�ܼƷѣ�"+Double.toString(Double.parseDouble(ap0)-deleteprice));
    	
        if(Date.contains("~")) {
        	String[] parts = Date.split("~");
        	String begin = parts[0];
        	String end = parts[1];
        	String[] bt = begin.split("-");
        	String by = bt[0];
        	String bm = bt[1];
        	String bd = bt[2];
//        	System.out.println("!:"+by+bm+bd);
        	
        	LocalDate beginday = LocalDate.of(Integer.parseInt(by), Integer.parseInt(bm), Integer.parseInt(bd));
        	
        	String[] et = end.split("-");
        	String ey = et[0];
        	String em = et[1];
        	String ed = et[2];
//        	System.out.println("!!:"+ey+em+ed);
        	
        	LocalDate endday = LocalDate.of(Integer.parseInt(ey), Integer.parseInt(em), Integer.parseInt(ed));
        	
        	try {
				Statement stmt = con.createStatement();
				String sql = "DELETE from book_table WHERE broomid = '"+id0+"' AND byear = "+by+" AND bmon = "+bm+" AND bday="+bd ;
				stmt.executeUpdate(sql);
//				roomData.clear();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//ɾ����һ��
        	while(!beginday.equals(endday)) {//�м�����
        		LocalDate mindate = beginday.plusDays(1);
        		int minyear = mindate.getYear();
        		int minmon = mindate.getMonthValue();
        		int minday = mindate.getDayOfMonth();
        		try {
    				Statement stmt = con.createStatement();
    				String sql = "DELETE from book_table WHERE broomid = '"+id0+"' AND byear = "+minyear+" AND bmon = "+minmon+" AND bday="+minday ;
    				stmt.executeUpdate(sql);
//    				roomData.clear();
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}//ɾ���м�����
        		beginday = mindate;//����һ�¿�ʼ����
        	}
        	if(beginday.equals(endday)) {//ɾ�����һ��
            	try {
    				Statement stmt = con.createStatement();
    				String sql = "DELETE from book_table WHERE broomid = '"+id0+"' AND byear = "+ey+" AND bmon = "+em+" AND bday="+ed ;
    				stmt.executeUpdate(sql);
//    				roomData.clear();
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}//ɾ�����һ��
        	}
        	roomData.clear();
        }
        else {
        	String[] bt = Date.split("-");
        	String by = bt[0];
        	String bm = bt[1];
        	String bd = bt[2];
        	
//        	System.out.println(by+bm+bd);
        	try {
				Statement stmt = con.createStatement();
				String sql = "DELETE from book_table WHERE broomid = '"+id0+"' AND byear = "+by+" AND bmon = "+bm+" AND bday="+bd ;
				stmt.executeUpdate(sql);
				roomData.clear();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

    
			
    		//���±��	
        double roomAll = 0;
		double goodAll = 0;
		double Sum = 0;
		String roomid = null;

		try 
		{        
			Statement stmt = con.createStatement();
			String sql = "SELECT bid,byear,bmon,bday,broomid,bprice from book_table WHERE bguestid = '"+guestid+"'";
			ResultSet rs = stmt.executeQuery( sql );
	
			int roomListNumber = 0;//�ų�ֻ��һ����¼�����
			while(rs.next()) {
				roomListNumber++;
			}
			
			rs = stmt.executeQuery( sql );
			//���ҵ�һ����¼ 
			String id1 = null;//��һ�β���id ʱ�� roomid
			String year1 = null;
			String mon1 = null;
			String day1 = null;
			String roomid1 = null;
			String time1 = null;
			String type1 = null;
			int price1 = 0;
			while(rs.next()) {
				id1 = rs.getString("bid");//��һ�β���id ʱ�� roomid
				year1 = rs.getString("byear");
				mon1 = rs.getString("bmon");
				day1 = rs.getString("bday");
				roomid1 = rs.getString("broomid");
				price1 = rs.getInt("bprice");
				time1 = year1+"-"+mon1+"-"+day1;
				roomAll += price1;
				break;
			}
			
			LocalDate date1 = LocalDate.of(Integer.parseInt(year1), Month.of(Integer.parseInt(mon1)), Integer.parseInt(day1)); 
			String time2 = "";
			
			//�ڶ��β��Ҷ�Ӧroomid��type��price 		
			Statement st2 = con.createStatement();
			String sql2 = "SELECT rtype from room_table WHERE rid = '"+roomid1+"'";//roomid1!!!!!!!!!
			ResultSet rs2= st2.executeQuery( sql2 );
			
			while(rs2.next()) //��һ����¼��Ӧ�ļ۸������
			{   
//				int p = rs2.getInt("rprice");
				type1 = rs2.getString("rtype");
//				price1 = (double) p;
				
//				System.out.println(price1);
//				roomData.add(new Room(roomid,type,time,price));//id type date price	
			}	
			
			rs2.close();
			st2.close();
			
			if(roomListNumber==1) {//���û�ֻ��һ����¼ һ���ʱ��
				roomData.add(new Room(roomid1,type1,time1,price1));//id type date price	
				roomTable.setItems(roomData);
				//�رն��� 
		        rs.close();
		        stmt.close();
			}
			else {
				String type = null;
				int price = 0;
				while(rs.next()) //�ټ������²�ѯ
				{   
					String id = rs.getString("bid");//��һ�β���id ʱ�� roomid
					String year = rs.getString("byear");
					String mon = rs.getString("bmon");
					String day = rs.getString("bday");
					roomid = rs.getString("broomid");
					price = rs.getInt("bprice");
					roomAll += price;
					String time = year+"-"+mon+"-"+day;
					LocalDate date = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); 
					
					//�ڶ��β��Ҷ�Ӧroomid��type��price 		
					Statement st3 = con.createStatement();
					String sql3 = "SELECT rprice,rtype from room_table WHERE rid = '"+roomid+"'";
					ResultSet rs3= st3.executeQuery( sql3 );
					
					while(rs3.next()) 
					{   
//						int p = rs3.getInt("rprice");
						type = rs3.getString("rtype");
//						price = (double) p;
						
						
						if(roomid.equals(roomid1)) {//�������ͬ �ж������Ƿ�����
							if(date1.plusDays(1).equals(date)) {//������ʱ��  
								time2 = time;
								price1 += price;
//								System.out.println(price1);
//								continue;
							}
							else {//���ڲ�ͬ ��2����
								if(time2.equals("")) {
									roomData.add(new Room(roomid1,type1,time1,price1));//����ʱ��Ϊ���� 
									price1 = price;//��ʼ�۸�����Ϊ���μ۸�
									time2 = "";//����ʱ�����ÿ�
								}
								else {
									roomData.add(new Room(roomid1,type1,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
									price1 = price;//��ʼ�۸�����Ϊ���μ۸�
									time2 = "";//����ʱ�����ÿ�
								}
								roomid1 = roomid;//������η��� �´���������ж�
								time1 = time;//ԭʼʱ������Ϊ��ε�ʱ��
							}
							date1 = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); //����
						}
						else {//����Ų�ͬ
							if(time2.equals("")) {
								roomData.add(new Room(roomid1,type1,time1,price1));//����ʱ��Ϊ���� 
								price1 = price;//��ʼ�۸�����Ϊ���μ۸�
								time2 = "";//����ʱ�����ÿ�
							}
							else {
								roomData.add(new Room(roomid1,type1,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
								price1 = price;//��ʼ�۸�����Ϊ���μ۸�
								time2 = "";//����ʱ�����ÿ�
							}
							roomid1 = roomid;//������η��� �´���������ж�
							time1 = time;//ԭʼʱ������Ϊ��ε�ʱ��
							date1 = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); //����
						}	
					}	
					rs3.close();
					st3.close();
		        }

				if(time2.equals("")) {
					roomData.add(new Room(roomid,type,time1,price1));//����ʱ��Ϊ���� 
//					price1 = price;//��ʼ�۸�����Ϊ���μ۸�
//					time2 = "";//����ʱ�����ÿ�
				}
				else {
					roomData.add(new Room(roomid,type,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
					price1 = price;//��ʼ�۸�����Ϊ���μ۸�
					time2 = "";//����ʱ�����ÿ�
				}
				
				roomTable.setItems(roomData);
				//�رն��� 
		        rs.close();
		        stmt.close();
			}
			
			Alert alert2 = new Alert(AlertType.INFORMATION);
    		alert2.setHeaderText("");
    		alert2.setContentText("�˶��ɹ���");
    		alert2.show();
		}
		catch(SQLException e) {
//		    System.out.println("����" + e);
		    e.printStackTrace();
		}
    }
	
    @FXML
    private Button backBtn;
	@FXML
    void goBack(ActionEvent event) {
		try
    	{
			//ɾ���򿪴��ڣ�
			con.close();			
			Stage preStage=(Stage)backBtn.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
	
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	@FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> tableType;
    @FXML private TableColumn<Room, Double> tablePrice;
    @FXML private TableColumn<Room, String> tableId;
    @FXML private TableColumn<Room, String> tableDate;
    public class Room{
    	private SimpleStringProperty id;
    	private SimpleStringProperty type;
    	private SimpleStringProperty date;
    	private SimpleDoubleProperty price;
    	public Room(String i,String t,String da,double pri) {
    		this.id = new SimpleStringProperty(i);
    		this.type = new SimpleStringProperty(t);
    		this.date = new SimpleStringProperty(da);
    		this.price = new SimpleDoubleProperty(pri);
    	}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id.set(id);
		}
		public String getType() {
			return type.get();
		}
		public void setType(String type) {
			this.type.set(type);
		}
		public String getDate() {
			return date.get();
		}
		public void setDate(String date) {
			this.date.set(date);
		}
		public Double getPrice() {
			return price.get();
		}
		public void setPrice(Double price) {
			this.price.set(price);
		}
		
    }
    
    private ObservableList<Good> goodData = FXCollections.observableArrayList();
    @FXML private TableView<Good> goodsTable;
    @FXML private TableColumn<Good, String> goodsTime;
    @FXML private TableColumn<Good, String> goodsName;
    @FXML private TableColumn<Good, Integer> goodsNum;
    @FXML private TableColumn<Good, Double> goodsPrice;
    public class Good{
    	private SimpleStringProperty time;
    	private SimpleStringProperty name;
    	private SimpleIntegerProperty num;
    	private SimpleDoubleProperty price;
    	public Good(String t,String na,int nu,double pri) {
    		this.time = new SimpleStringProperty(t);
    		this.name = new SimpleStringProperty(na);
    		this.num = new SimpleIntegerProperty(nu);
    		this.price = new SimpleDoubleProperty(pri);
    	}
		public String getTime() {
			return time.get();
		}
		public void setTime(String time) {
			this.time.set(time);
		}
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name.set(name);
		}
		public Integer getNum() {
			return num.get();
		}
		public void setNum(Integer num) {
			this.num.set(num);
		}
		public Double getPrice() {
			return price.get();
		}
		public void setPrice(Double price) {
			this.price.set(price);
		}
    }
    
    
    @FXML private Text RoomAllPrice;
    @FXML private Text GoodAllPrice;
    @FXML private Text AllPrice;
    
    private int []days = new int[] {31,28,31,30,31,30,31,31,30,31,30,31};
    
    void setUser(String user) {
		// TODO Auto-generated method stub
    	this.guestid=user;
		System.out.println("MyOrders��user:"+guestid);
		
		double roomAll = 0;
		double goodAll = 0;
		double Sum = 0;
		String roomid = null;
		
		tableType.setCellValueFactory(new PropertyValueFactory<>("type"));//������
		tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		try 
		{        
            String DBDriver="com.mysql.cj.jdbc.Driver";  // ���ݿ� MYSQL װ��
            String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
            String DBUser="root", DBPassword="sysu";
			Class.forName(DBDriver);
			con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			Statement stmt = con.createStatement();
			String sql = "SELECT bid,byear,bmon,bday,broomid,bprice from book_table WHERE bguestid = '"+guestid+"'";
			ResultSet rs = stmt.executeQuery( sql );
	
			int roomListNumber = 0;//�ų�ֻ��һ����¼�����
			while(rs.next()) {
				roomListNumber++;
			}
			
			rs = stmt.executeQuery( sql );
			//���ҵ�һ����¼ 
			String id1 = null;//��һ�β���id ʱ�� roomid
			String year1 = null;
			String mon1 = null;
			String day1 = null;
			String roomid1 = null;
			String time1 = null;
			String type1 = null;
			int price1 = 0;
			while(rs.next()) {
				id1 = rs.getString("bid");//��һ�β���id ʱ�� roomid
				year1 = rs.getString("byear");
				mon1 = rs.getString("bmon");
				day1 = rs.getString("bday");
				roomid1 = rs.getString("broomid");
				price1 = rs.getInt("bprice");
				time1 = year1+"-"+mon1+"-"+day1;
				roomAll += price1;
				break;
			}
			
			LocalDate date1 = LocalDate.of(Integer.parseInt(year1), Month.of(Integer.parseInt(mon1)), Integer.parseInt(day1)); 
			String time2 = "";
			
			//�ڶ��β��Ҷ�Ӧroomid��type��price 		
			Statement st2 = con.createStatement();
			String sql2 = "SELECT rtype from room_table WHERE rid = '"+roomid1+"'";//roomid1!!!!!!!!!
			ResultSet rs2= st2.executeQuery( sql2 );
			
			while(rs2.next()) //��һ����¼��Ӧ�ļ۸������
			{   
				type1 = rs2.getString("rtype");
			}	
			
			rs2.close();
			st2.close();
			
			if(roomListNumber==1) {//���û�ֻ��һ����¼ һ���ʱ��
				roomData.add(new Room(roomid1,type1,time1,price1));//id type date price	
				roomTable.setItems(roomData);
				//�رն��� 
		        rs.close();
		        stmt.close();
			}
			else {
				String type = null;
				int price = 0;
				while(rs.next()) //�ټ������²�ѯ
				{   
					String id = rs.getString("bid");//��һ�β���id ʱ�� roomid
					String year = rs.getString("byear");
					String mon = rs.getString("bmon");
					String day = rs.getString("bday");
					roomid = rs.getString("broomid");
					price = rs.getInt("bprice");
					roomAll += price;
					String time = year+"-"+mon+"-"+day;
					LocalDate date = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); 
					
					//�ڶ��β��Ҷ�Ӧroomid��type��price 		
					Statement st3 = con.createStatement();
					String sql3 = "SELECT rprice,rtype from room_table WHERE rid = '"+roomid+"'";
					ResultSet rs3= st3.executeQuery( sql3 );
					
					while(rs3.next()) 
					{   
//						int p = rs3.getInt("rprice");
						type = rs3.getString("rtype");
//						price = (double) p;
						
						
						if(roomid.equals(roomid1)) {//�������ͬ �ж������Ƿ�����
							if(date1.plusDays(1).equals(date)) {//������ʱ��  
								time2 = time;
								price1 += price;
//								System.out.println(price1);
//								continue;
							}
							else {//���ڲ�ͬ ��2����
								if(time2.equals("")) {
									roomData.add(new Room(roomid1,type1,time1,price1));//����ʱ��Ϊ���� 
									price1 = price;//��ʼ�۸�����Ϊ���μ۸�
									time2 = "";//����ʱ�����ÿ�
								}
								else {
									roomData.add(new Room(roomid1,type1,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
									price1 = price;//��ʼ�۸�����Ϊ���μ۸�
									time2 = "";//����ʱ�����ÿ�
								}
								roomid1 = roomid;//������η��� �´���������ж�
								time1 = time;//ԭʼʱ������Ϊ��ε�ʱ��
							}
							date1 = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); //����
						}
						else {//����Ų�ͬ
							if(time2.equals("")) {
								roomData.add(new Room(roomid1,type1,time1,price1));//����ʱ��Ϊ���� 
								price1 = price;//��ʼ�۸�����Ϊ���μ۸�
								time2 = "";//����ʱ�����ÿ�
							}
							else {
								roomData.add(new Room(roomid1,type1,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
								price1 = price;//��ʼ�۸�����Ϊ���μ۸�
								time2 = "";//����ʱ�����ÿ�
							}
							roomid1 = roomid;//������η��� �´���������ж�
							time1 = time;//ԭʼʱ������Ϊ��ε�ʱ��
							date1 = LocalDate.of(Integer.parseInt(year), Month.of(Integer.parseInt(mon)), Integer.parseInt(day)); //����
						}	
					}	
					rs3.close();
					st3.close();
		        }

				if(time2.equals("")) {
					roomData.add(new Room(roomid,type,time1,price1));//����ʱ��Ϊ���� 
//					price1 = price;//��ʼ�۸�����Ϊ���μ۸�
//					time2 = "";//����ʱ�����ÿ�
				}
				else {
					roomData.add(new Room(roomid,type,time1+"~"+time2,price1));//����ʱ��Ϊ���� 
					price1 = price;//��ʼ�۸�����Ϊ���μ۸�
					time2 = "";//����ʱ�����ÿ�
				}
				
				roomTable.setItems(roomData);
				//�رն��� 
		        rs.close();
		        stmt.close();
			}
		}
		catch(ClassNotFoundException e) {
//		    System.out.println("����" + e);
			e.printStackTrace();
		}
		catch(SQLException e) {
//		    System.out.println("����" + e);
		    e.printStackTrace();
		}
		
		goodsTime.setCellValueFactory(new PropertyValueFactory<>("time"));//��Ʒ��
		goodsName.setCellValueFactory(new PropertyValueFactory<>("name"));
		goodsNum.setCellValueFactory(new PropertyValueFactory<>("num"));
		goodsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		try 
		{     
			Statement stmt0 = con.createStatement();
			String sql0 = "SELECT byear,bmon,bday,broomid from book_table WHERE bguestid = '"+guestid+"'";
			ResultSet rs0 = stmt0.executeQuery( sql0 );
			//���ҵ�һ����¼ 
//			String id1 = null;//��һ�β���id ʱ�� roomid
			String year1 = null;
			String mon1 = null;
			String day1 = null;
			String roomid1 = null;
//			String time1 = null;
//			String type1 = null;
//			int price1 = 0;
			while(rs0.next()) {
				year1 = rs0.getString("byear");
				mon1 = rs0.getString("bmon");
				day1 = rs0.getString("bday");
				roomid1 = rs0.getString("broomid");

				Statement stmt = con.createStatement();
				String sql = "SELECT cfood,cyear,cmon,cday,cnum,cprice from consume_table WHERE croom = '"+roomid1+"' AND cyear = "+year1+" AND cmon = "+mon1+" AND cday="+day1 ;
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) 
				{   
					String good = rs.getString("cfood");
					String year = rs.getString("cyear");
					String mon= rs.getString("cmon");
					String day = rs.getString("cday");
					int num = rs.getInt("cnum");
					double price = rs.getDouble("cprice");
					goodAll += price;
					String time = year+"-"+mon+"-"+day;
					goodData.add(new Good(time,good,num,price));
				}
				
						
				//�رն��� 
		        rs.close();
		        stmt.close();
			}
			goodsTable.setItems(goodData);
			//�رն��� 
	        rs0.close();
	        stmt0.close();       
	        
	        roomTable.setRowFactory( tv -> {   //tableview˫���¼����۸ö���
	            TableRow<Room> row = new TableRow<Room>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                    Room roomInfo = row.getItem();
	                    System.out.println("Date:"+roomInfo.getDate());
	                    String Date=roomInfo.getDate();
	                    String id0=roomInfo.getId();
	                    try
	                	{
	                    	int year11,mon11,day11;
	                    	if(!Date.isEmpty()) {
	                			if(Date.contains("~")) {
	                		    	String[] parts = Date.split("~");
	                		    	String end = parts[1];
	                			   	String[] bt = end.split("-");   //���һ��
	                			   	String by = bt[0];
	                			   	String bm = bt[1];
	                			   	String bd = bt[2];
	                		//	        	System.out.println("!:"+by+bm+bd);
	                		    	year11 = Integer.parseInt(by);
	                			   	mon11 = Integer.parseInt(bm);
	                			   	day11 = Integer.parseInt(bd);  //����ʱ��
	                		   }
	                		   else {  //ֻ����һ��
	                			    String[] bt = Date.split("-");
	                			   	String by = bt[0];
	                			   	String bm = bt[1];
	                			   	String bd = bt[2];
	                			   	
	                			   	System.out.println("by:"+by);
	                			   	System.out.println("bm:"+bm);
	                			   	System.out.println("bd:"+bd);
	                			   	
	                		    	year11 = Integer.parseInt(by);
	                		    	mon11 = Integer.parseInt(bm);
	                		    	day11 = Integer.parseInt(bd);
	                		    }
	        		    	Date date2=new Date();   //��ȡϵͳʱ��
	        		    	SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");  
	            		    System.out.println(formatter2.format(date2));
//	            			orderTime.setText(formatter.format(date));
//	            			
	            			int year2 = Integer.parseInt(formatter2.format(date2).substring(0,4));
	            	        int month2 = Integer.parseInt(formatter2.format(date2).substring(5,7));
	            	        int day2 = Integer.parseInt(formatter2.format(date2).substring(8,10));
	        			   	
	            	        if(year11>year2 || (year11==year2 && mon11 > month2) || (year11==year2 && mon11==month2 && day11 >= day2)) {
	                    		Alert alert2 = new Alert(AlertType.INFORMATION);
	                    		alert2.setTitle("��ܰ��ʾ");
	                    		alert2.setHeaderText("");
	                    		alert2.setContentText("���Ѻ�������ۣ�");
	                    		alert2.show();
	                    		//System.out.println(2);
	                    		return;
	                    	}
	            	        
	            	        String d = Date;
	            	        if (Date.contains("~")) Date=Date.substring(0,Date.indexOf("~"));
	            	        String[] bt = Date.split("-");
            			   	String by = bt[0];
            			   	String bm = bt[1];
            			   	String bd = bt[2];
	                    	Statement stmt00=con.createStatement();
	                    	String sql = "SELECT * FROM book_table WHERE broomid = '"+id0+"' AND byear = "+by+" AND bmon = "+bm+" AND bday="+bd ;
	        				ResultSet rs00=stmt00.executeQuery(sql);
	            			
	        				while (rs00.next())
	        				{
	        					if (rs00.getInt("bassess")==1)  //�Ѿ����۹�
	        					{
	        						Alert alert2 = new Alert(AlertType.INFORMATION);
	        						alert2.setTitle("��ܰ��ʾ");
	        			    		alert2.setHeaderText("");
	        			    		alert2.setContentText("�ö����Ѿ�������^^");
	        			    		alert2.show();
	        			    		return;
	        					}
	        				}
	        				//css��
	        				Stage stage=new Stage();
	            			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Assess.fxml"));
	            			Parent root=loader.load();
	            			Scene scene=new Scene(root);
	        				URL css=this.getClass().getClassLoader().getResource("application/Assess.css");
	        				scene.getStylesheets().add(css.toExternalForm());
	        				stage.setResizable(false);
	            			stage.setScene(scene);
	            			stage.setResizable(false);
	            			//���ô���ͼ�꣡
	            			Image icon = new Image("/resourcefile/duck100.png");
							stage.getIcons().add(icon);
	            			stage.setTitle("ס����������");
	            			stage.show();
	            			
	            			AssessController ac=loader.getController();
	            			ac.setInfo(id0,d);
	                    	}
	                	}
	                	catch(Exception e)
	                	{
	                		System.out.println(e);
	                	}
	                }
	            });
	            return row ;
	        });
		}
		catch(SQLException e) {
		    System.out.println("����" + e);
		}
		BigDecimal tmp=new BigDecimal(goodAll);
		goodAll=tmp.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
		
		BigDecimal tmp1=new BigDecimal(roomAll);
		roomAll=tmp1.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
		
		Sum += (goodAll + roomAll);
		BigDecimal tmp2=new BigDecimal(Sum);
		Sum=tmp2.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
		RoomAllPrice.setText("�����ܼƷѣ�"+roomAll);
		GoodAllPrice.setText("��Ʒ�ܼƷѣ�"+goodAll);
		AllPrice.setText("�ܼƷѣ�"+Sum);
	}
    
}
