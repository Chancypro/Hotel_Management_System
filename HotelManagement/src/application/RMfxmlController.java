package application;
import java.io.IOException;
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

import com.leewyatt.rxcontrols.controls.RXTranslationButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
public class RMfxmlController implements Initializable{

	//������ģ�
    @FXML
    private RXTranslationButton cekBut;
    @FXML
    private RXTranslationButton addBut;
    @FXML
    private RXTranslationButton changeBut;
    @FXML
    private RXTranslationButton priceBtn;
    @FXML
    private RXTranslationButton delBut;
	@FXML
    private BorderPane root;
	
	@FXML
    private ScrollPane scroll;

    @FXML
    private TableView<Room> rooms;

    @FXML
    private TableColumn<Room,String> idcol;

    @FXML
    private TableColumn<Room,String> statuscol;

    @FXML
    private Label datelabel;

    @FXML
    private Label cekLabel;

    @FXML
    private TextField cekText;

    @FXML
    private TableColumn<Room,String> levelcol;

    @FXML
    private TableColumn<Room,Integer> pricecol;
    
    @FXML
    private TextField dateShow;
    
    private ObservableList<Room> data=FXCollections.observableArrayList();
    private String urls="jdbc:mysql://localhost:3306/hoteldatabase?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private String user="root";
    private String pwd="sysu";
    private Connection connection;
    private Statement stmt;
    private String year;
    private String mon;
    private String day;
    
    private String userName;
    
    void setUser1(String userName)
    {
    	this.userName=userName;
    }
    
    @FXML
    private Button returnBtn3;
    @FXML
    void handleReturn3()
    {
    	try
    	{
    		//css��
	    	Stage stage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Stage.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Stage.css");
			scene.getStylesheets().add(css.toExternalForm());
			stage.setResizable(false);
			stage.setScene(scene);
			//���ô���ͼ�꣡
			Image icon = new Image("/resourcefile/duck100.png");
			stage.getIcons().add(icon);
			stage.setTitle("�Ƶ����ϵͳ");
			stage.show();
			
			StageController sc=loader.getController();
			sc.setUser(userName);
			
			Stage preStage=(Stage)returnBtn3.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    public void updateData()
    {
		try {    	
			
				try
				{
					Date date=new Date();   //��ȡϵͳʱ��
					SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    String comdate=formatter.format(date);
					this.year=comdate.substring(0,4);
					this.mon=comdate.substring(5,7);
					this.day=comdate.substring(8,10);
				}
				catch (Exception e)
			    {
			    	System.out.println(e);
			    }
				//ע�ʹ�ӡ��
//				System.out.println("year:"+year);    //��
//				System.out.println("month:"+mon);  //��
//				System.out.println("day:"+day);      //��
				
				rooms.getItems().clear();
				//��ȡ���ݿ�����		
				connection=DriverManager.getConnection(urls,user,pwd);
				Statement stmt = connection.createStatement();
				String sql = "SELECT rid,rprice,rtype from room_table";
				ResultSet rs = stmt.executeQuery( sql );			
				while(rs.next()) 
				{   				
					String name = rs.getString("rid");
					int price = rs.getInt("rprice");
					String type = rs.getString("rtype");
					data.add(new Room(name,price,type,""));						
				}
				//��ȡ�Ƿ�շ�
				String sql1="SELECT bmon,bday,broomid from book_table";
				ResultSet rss = stmt.executeQuery( sql1 );
				while(rss.next()) 
				{   				
					int tmpmon=rss.getInt("bmon");
					int tmpday=rss.getInt("bday");
					
					if(tmpmon==Integer.parseInt(mon)&&tmpday==Integer.parseInt(day))
					{
						String a=rss.getString("broomid");
						//ע�ʹ�ӡ��
						//System.out.println(a);
						for(Room item:data)
						{							
							if(item.getId().equals(a))
							{
								item.setStatus("��Ԥ��");
							}
						}
					}
				}
				for(Room item:data)
				{
					if(item.getStatus()=="")
					{
						item.setStatus("�շ�");
					}
				}
				//�����
				idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		        levelcol.setCellValueFactory(new PropertyValueFactory<>("type"));
		        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
		        statuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
		        rooms.setItems(data);	
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    @FXML
    void addhandler(ActionEvent event) {
    	
		try {
			//css��
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Add.fxml"));
			Parent root;
			root = loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Add.css");
			scene.getStylesheets().add(css.toExternalForm());
			Stage sta = new Stage();
	        sta.setTitle("�ͷ�����");
	        sta.setResizable(false);
	        sta.setScene(scene);	     
	        //���ô���ͼ�꣡
	        Image icon = new Image("/resourcefile/duck100.png");
	        sta.getIcons().add(icon);
	        sta.show();
	        sta.setOnHidden(eventa -> {			            
	            updateData();
	        });
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @FXML
    void changehandler(ActionEvent event) {
			try {
				//css��
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Change.fxml"));
				Parent root;
				root = loader.load();
				Scene scene=new Scene(root);
				URL css=this.getClass().getClassLoader().getResource("application/Change.css");
				scene.getStylesheets().add(css.toExternalForm());
				Stage sta = new Stage();
				sta.setResizable(false);
		        sta.setTitle("�޸ķ���");
		        sta.setScene(scene);	
		        //���ô���ͼ�꣡
		        Image icon = new Image("/resourcefile/duck100.png");
		        sta.getIcons().add(icon);
		        sta.show();
			        sta.setOnHidden(eventa -> {			            
			            updateData();
			        });
	        
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void searchhandler(ActionEvent event) {
    	data.clear(); 
		try {
				connection=DriverManager.getConnection(urls,user,pwd);
				Statement stmt = connection.createStatement();
				String sql = "SELECT rid,rprice,rtype from room_table";
				ResultSet rs = stmt.executeQuery( sql );			
				while(rs.next()) 
				{   				
					String name = rs.getString("rid");
					int price = rs.getInt("rprice");
					String type = rs.getString("rtype");
					if(name.contains(cekText.getText())) {
						data.add(new Room(name,price,type,""));
					}	
				}
				//��ȡ�Ƿ�շ�
				// ��ȡ�·ݺ���
				String sql1="SELECT bmon,bday,broomid from book_table";
				ResultSet rss = stmt.executeQuery( sql1 );
				while(rss.next()) 
				{   				
					int tmpmon=rss.getInt("bmon");
					int tmpday=rss.getInt("bday");
					
					if(tmpmon==Integer.parseInt(mon)&&tmpday==Integer.parseInt(day))
					{
						String a=rss.getString("broomid");
						//ע�ʹ�ӡ��
						//System.out.println(a);
						for(Room item:data)
						{							
							if(item.getId().equals(a))
							{
								item.setStatus("��Ԥ��");
							}
						}
					}
				}
				for(Room item:data)
				{
					if(item.getStatus()=="")
					{
						item.setStatus("�շ�");
					}
				}
				//�����
				idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		        levelcol.setCellValueFactory(new PropertyValueFactory<>("type"));
		        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
		        statuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
		        rooms.setItems(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
    }

    @FXML
    void delhandler(ActionEvent event) {
    	Room selected = rooms.getSelectionModel().getSelectedItem();
    	if (selected==null)
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("��ѡ����ɾ���ķ��䣡");
    		alert.show();
    		return;
    	}
    	if (statuscol.getCellData(selected).equals("��Ԥ��")==true)  //�ѱ�Ԥ��
    	{
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("�÷�������ѱ�Ԥ��������ɾ����");
    		alert.show();
    		return;
    	}
    	String id = idcol.getCellData(selected);
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//�޸�4
    	alert.setTitle("��ʾ");
    	alert.setHeaderText("");
		alert.setContentText("ȷ��Ҫɾ�����䣺<"+id+"> ��");
		Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.CANCEL) {
    		return ;
    	}
    	
    	try 
		{      
			String sql = "DELETE from room_table WHERE rid = '"+id+"'";
			stmt.executeUpdate(sql);
			updateData();	
		}
		
		catch(SQLException e) {
		    System.out.println("����" + e);
		}	
    	Alert alert1=new Alert(AlertType.INFORMATION);
		alert1.setTitle("��ܰ��ʾ");
		alert1.setHeaderText("");
		alert1.setContentText("ɾ���ɹ���");
		alert1.show();
		return;
    }

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) 
		// TODO Auto-generated method stub
    {
    	try
    	{
    		//�������ݿ�
    		connection=DriverManager.getConnection(urls,user,pwd);
    		stmt=connection.createStatement();
    		
	        //��ʾʱ��
    		try
			{
				Date date=new Date();   //��ȡϵͳʱ��
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				//ע�ʹ�ӡ��
			    //System.out.println(formatter.format(date));
				dateShow.setText(formatter.format(date).substring(0,10));
			}
			catch (Exception e)
		    {
		    	System.out.println(e);
		    }

    		//���±������
    		updateData();
 
	        //���ñ����
	        //scroll.prefWidthProperty().bind(root.widthProperty());
	        //rooms.prefWidthProperty().bind(root.widthProperty());
	    	
    	}
    	catch(Exception e) {
			e.printStackTrace();
		}

       
    }
	
	@FXML
    void handlePriceChange(ActionEvent event) {
    	
		try
		{
			//css��
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PriceChange.fxml"));
			Parent root;
			root = loader.load();
			Scene scene=new Scene(root);
			URL css=this.getClass().getClassLoader().getResource("application/Change.css");
			scene.getStylesheets().add(css.toExternalForm());			
			Stage sta = new Stage();
			sta.setResizable(false);
	        sta.setTitle("�޸ļ۸�");
	        sta.setScene(scene);
	      //���ô���ͼ�꣡
	        Image icon = new Image("/resourcefile/duck100.png");
	        sta.getIcons().add(icon);
	        sta.show();
	        
	        sta.setOnHidden(eventa -> {			            
	            updateData();
	        });
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}			
    }




public class Room {
	private SimpleStringProperty id;
	private SimpleIntegerProperty price;
	private SimpleStringProperty type;
	private SimpleStringProperty status;
	
	Room()
	{
		
	}
	public Room(String a,int b,String c,String d)
	{
		id=new SimpleStringProperty(a);
		price=new SimpleIntegerProperty(b);
		type=new SimpleStringProperty(c);
		status=new SimpleStringProperty(d);		
	}
	public String getId()
	{
		return id.get();
	}
	public int getPrice()
	{
		return price.get();
	}
	public String getType()
	{
		return type.get();
	}
	public String getStatus()
	{
		return status.get();
	}
	
	public void setId(String a)
	{
		id.set(a);
	}
	public void setPrice(int a)
	{
		price.set(a);
	}
	public void setType(String a)
	{
		type.set(a);
	}
	public void setStatus(String a)
	{
		status.set(a);
	}
}
}




