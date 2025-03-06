package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.leewyatt.rxcontrols.controls.RXFillButton;
import com.leewyatt.rxcontrols.controls.RXTranslationButton;

import application.GuestMainController.RoomInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TDpageController {

	//������ģ�
	@FXML
    private RXTranslationButton checkBtn;
    @FXML
    private RXFillButton TDbtn;
    @FXML private TableColumn<RoomInfo,String> gnameCol;
    @FXML private TableColumn<RoomInfo,String> dayCol;
    @FXML private TableColumn<RoomInfo,String> gidCol;
    @FXML private TableView<RoomInfo> TDtable;
    @FXML private TableColumn<RoomInfo,String> monCol;
    @FXML private Label label1;
    @FXML private TableColumn<RoomInfo,String> ridCol;
    @FXML private TableColumn<RoomInfo,String> yearCol;
    @FXML private TextField gidField;
    @FXML private ListView<String> resListView;
    @FXML private VBox gidVbox;
    @FXML private Button TDreturn;
    
    private ObservableList<RoomInfo> infoList=FXCollections.observableArrayList();
    private Connection con;
    
    //���ڴ洢ԭʼ����
    ObservableList<String> arrayList=FXCollections.observableArrayList();
    
    public void showTable()
    {
    	try
    	{
    		if (gidField.getText().length()==0) resListView.setVisible(false);
	    	String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
			String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			this.con=con;
			
			Statement stmtid=con.createStatement();      //�������֤��Ӧ����
			String sql="SELECT * FROM guest_table";
			ResultSet rsid=stmtid.executeQuery(sql);
			while (rsid.next())
				arrayList.add(rsid.getString("gid"));
			
			gidField.textProperty().addListener(new ChangeListener<String>() {  //ģ����ѯ���֤
				public void changed(ObservableValue<? extends String> observableValue,String oldValue,String newValue)
				{
					//ʹ�ù���������ģ����ѯ
					if (gidField.getText().length()!=0) resListView.setVisible(true);
					else resListView.setVisible(false);
					ObservableList<String> filteredList=arrayList.filtered(item->item.toLowerCase().contains(newValue.toLowerCase()));
					resListView.setItems(filteredList);
				}
			});
			
			resListView.setOnMouseClicked(event-> {
				String selItem=resListView.getSelectionModel().getSelectedItem();
					//��ѡ������䵽textfield��
				if (selItem!=null)
					gidField.setText(selItem);
			});
			
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM book_table");  //ѡ�����ж�����¼
			
			while (rs.next())
			{
				String thisGid=rs.getString("bguestid");
				Statement stmt1=con.createStatement();      //�������֤��Ӧ����
				sql="SELECT * FROM guest_table WHERE gid='"+thisGid+"'";
				//System.out.println("thisGid��"+thisGid);
				
				ResultSet rs1=stmt1.executeQuery(sql);
				while (rs1.next())
				{
					RoomInfo roomInfo=new RoomInfo(rs.getString("byear"),rs.getString("bmon"),rs.getString("bday"),rs.getString("broomid"),thisGid,rs1.getString("gname"));
					infoList.add(roomInfo);
				}
			}
			yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
			monCol.setCellValueFactory(new PropertyValueFactory<>("mon"));
			dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
			ridCol.setCellValueFactory(new PropertyValueFactory<>("rid"));
			gidCol.setCellValueFactory(new PropertyValueFactory<>("gid"));
			gnameCol.setCellValueFactory(new PropertyValueFactory<>("gname"));
			
			TDtable.setItems(infoList);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    public static class RoomInfo {
		private SimpleStringProperty year;
		private SimpleStringProperty mon;
		private SimpleStringProperty day;
		private SimpleStringProperty rid;
		private SimpleStringProperty gid;
		private SimpleStringProperty gname;
		
		public RoomInfo(String y,String m,String d,String r,String g,String n)
		{
			year=new SimpleStringProperty(y);
			mon=new SimpleStringProperty(m);
			day=new SimpleStringProperty(d);
			rid=new SimpleStringProperty(r);
			gid=new SimpleStringProperty(g);
			gname=new SimpleStringProperty(n);
		}
		
		public String getYear() {
			return year.get();
		}

		public void setYear(String year) {
			this.year.set(year);
		}

		public String getMon() {
			return mon.get();
		}

		public void setMon(String mon) {
			this.mon.set(mon);
		}
		
		public String getDay() {
			return day.get();
		}
		public void setDay(String day) {
			this.day.set(day);
		}
		
		public String getRid() {
			return rid.get();
		}
		public void setRid(String rid) {
			this.rid.set(rid);
		}
		
		public String getGid() {
			return gid.get();
		}
		public void setGid(String gid) {
			this.gid.set(gid);
		}
		
		public String getGname() {
			return gname.get();
		}
		public void setGname(String gname) {
			this.gname.set(gname);
		}
	}
    
    @FXML
    void handleTD(ActionEvent event) {
    	
    	try
    	{
    		RoomInfo selRoom=TDtable.getSelectionModel().getSelectedItem();
    		if (selRoom==null)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("��ܰ��ʾ");
	    		alert.setHeaderText("");
	    		alert.setContentText("��ѡ�񷿼䣡");
	    		alert.show();
	    		return;
	    	}
    		
	    	String selRid=selRoom.getRid();  //ѡ�еķ���id
	    	//System.out.println("ѡ�з��ţ�"+selRid);
	    	
	    	String selYear=selRoom.getYear(); //ѡ�е���
	    	String selMon=selRoom.getMon();  //ѡ�е���
	    	String selDay=selRoom.getDay();  //ѡ�е���
	    	
	    	//�ж��Ƿ��Ѿ����ѹ�
	    	if (selYear.equals("2023")==true)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("��ܰ��ʾ");
	    		alert.setHeaderText("");
	    		alert.setContentText("�ö����Ѿ��������");
	    		alert.show();
	    		return;
	    	}
	    	if (selMon.equals("1")==true)
	    		if (selDay.equals("1")==true||selDay.equals("2")==true||selDay.equals("3")==true)
	    		{
	    			Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("��ܰ��ʾ");
		    		alert.setHeaderText("");
		    		alert.setContentText("�ö����Ѿ��������");
		    		alert.show();
		    		return;
	    		}
	    	
	    	
	    	Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);//�޸�4
	    	alert1.setTitle("��ʾ");
	    	alert1.setHeaderText("");
			alert1.setContentText("ȷ��Ҫ�˶���");
			Optional<ButtonType> result = alert1.showAndWait();
			if(result.get() == ButtonType.CANCEL) {
	    		return ;
	    	}
			
	    	Statement stmt=con.createStatement();
	    	String sql="DELETE FROM book_table WHERE bmon='"+selMon+"' AND bday='"+selDay+"' AND broomid='"+selRid+"';";
	    	//System.out.println("sql��䣺"+sql);
			stmt.executeUpdate(sql);
			
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("��ܰ��ʾ");
    		alert.setHeaderText("");
    		alert.setContentText("�˶��ɹ���");
    		alert.show();
    		
    		TDtable.getItems().clear();
    		showTable();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    

    @FXML
    void handleCheck(ActionEvent event) {
    	
    	try
    	{
    		TDtable.getItems().clear();
    		if (gidField.getText().length()==0) {showTable();return;}
    		String checkId=gidField.getText();     //��ѯס��id
    		
    		Statement stmt=con.createStatement();
	    	String sql="SELECT * FROM book_table WHERE bguestid='"+checkId+"';";
	    	//System.out.println("sql��䣺"+sql);
			ResultSet rs=stmt.executeQuery(sql);
			
			Statement stmt1=con.createStatement();
			ResultSet rs1=stmt1.executeQuery("SELECT * FROM guest_table WHERE gid='"+checkId+"';");
			
			String tmpName="";
			while (rs1.next())
				tmpName=rs1.getString("gname");
			
			while (rs.next())
			{
				RoomInfo roomInfo=new RoomInfo(rs.getString("byear"),rs.getString("bmon"),rs.getString("bday"),rs.getString("broomid"),checkId,tmpName);
				infoList.add(roomInfo);
			}
			
			monCol.setCellValueFactory(new PropertyValueFactory<>("mon"));
			dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
			ridCol.setCellValueFactory(new PropertyValueFactory<>("rid"));
			gidCol.setCellValueFactory(new PropertyValueFactory<>("gid"));
			gnameCol.setCellValueFactory(new PropertyValueFactory<>("gname"));
			
			TDtable.setItems(infoList);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    @FXML
    void handleTDreturn(ActionEvent event) {
    	try
    	{
    		Stage preStage=(Stage)TDreturn.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
}
