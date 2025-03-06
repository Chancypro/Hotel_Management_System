package application;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.GuestMainController.RoomInfo;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommentController implements Initializable{

	//组件更改！
    @FXML
    private ScrollPane scroll;
    @FXML
    private TableColumn<RoomInfo,Double> score;

    @FXML
    private TableColumn<RoomInfo,String> comment;

    @FXML
    private TableView<RoomInfo> table;
    
    private ObservableList<RoomInfo> infoList=FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	try
    	{
    		
    		table.setItems(infoList);
    		
    		String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
			String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM comment_table");
			while (rs.next())
			{
				BigDecimal tmp=new BigDecimal(rs.getDouble("score"));
	    		double newScore=tmp.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
				RoomInfo roomInfo=new RoomInfo(newScore,rs.getString("comment"));
				infoList.add(roomInfo);
			}
			
			score.setCellValueFactory(new PropertyValueFactory<>("rs"));
			comment.setCellValueFactory(new PropertyValueFactory<>("rc"));
			
			rs.close();
			stmt.close();
			con.close();
			
    	}
    	catch (Exception e)
    	{
    		System.out.println(e);
    	}
    }

    
    public static class RoomInfo {
		private SimpleDoubleProperty rs;
		private SimpleStringProperty rc;
		
		public RoomInfo(double i,String t)
		{
			rs=new SimpleDoubleProperty(i);
			rc=new SimpleStringProperty(t);
		}

		public double getRs() {
			return rs.get();
		}

		public void setRs(double rs) {
			this.rs.set(rs);
		}

		public String getRc() {
			return rc.get();
		}

		public void setRtype(String rc) {
			this.rc.set(rc);
		}

	}
}
