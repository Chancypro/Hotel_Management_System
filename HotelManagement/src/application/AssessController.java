package application;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AssessController {

    @FXML private CheckBox jiaotong;
    @FXML private CheckBox sheshi;
    @FXML private CheckBox jiage;
    //组件更改！删除ratingbar
    @FXML
    private RXFillButton subBtn;
    @FXML private Slider ratingSlider;
    @FXML private CheckBox ganjing;
    @FXML private CheckBox tingche;
    @FXML private CheckBox fuwu;
    @FXML private TextArea assessArea;
    @FXML private Label scoreLabel;
    private double thisScore;
    
    private String id0,Date;
    
    void setInfo(String roomid,String rdate)
    {
    	id0=roomid;
    	Date=rdate;
    }
    
    public void initialize()
    {
    	ratingSlider.setValue(0);
    	//注释ratingbar！
    	//ratingBar.setProgress(0);
    	scoreLabel.setText("0");
    	//监听滑块数值的变化
    	ratingSlider.valueProperty().addListener((observable,oldValue,newValue)->{
    		//注释ratingbar！
    		//ratingBar.setProgress(newValue.doubleValue()/ratingSlider.getMax());
    		
    		BigDecimal tmp=new BigDecimal(ratingSlider.getValue());
    		double newScore=tmp.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
    		thisScore=newScore;
    		
    		scoreLabel.setText(Double.toString(newScore));
    	});
    	
    }
    
    @FXML
    void handleSubmit()
    {
    	try
    	{
			if (assessArea.getText().length()>40)
			{
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("评价字数不能超过40哦！");
	    		alert.show();
	    		return;
			}
    		
	    	String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
	        String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			
			Statement stmt0=con.createStatement();
			ResultSet rs=stmt0.executeQuery("SELECT * FROM assess_table");
			int jt=0,tc=0,ss=0,gj=0,jg=0,fw=0;
			double score=0;
			while (rs.next())
			{
				jt=rs.getInt("jiaotong");
				tc=rs.getInt("tingche");
				ss=rs.getInt("sheshi");
				gj=rs.getInt("ganjing");
				jg=rs.getInt("jiage");
				fw=rs.getInt("fuwu");
				score=rs.getDouble("score");
				if (jiaotong.isSelected()) jt++;
				if (tingche.isSelected()) tc++;
				if (sheshi.isSelected()) ss++;
				if (ganjing.isSelected()) gj++;
				if (jiage.isSelected()) jg++;
				if (fuwu.isSelected()) fw++;
				score=(thisScore+score)/2;
			}
			String sql="UPDATE assess_table SET jiaotong='"+jt+"',tingche='"+tc+"',sheshi='"+ss+"',ganjing='"+gj+"',jiage='"+jg+"',fuwu='"+fw+"',score='"+score+"' WHERE id='1'";
			System.out.println("sql:"+sql);
			Statement stmt1=con.createStatement();
			stmt1.executeUpdate(sql);
			rs.close();
			stmt0.close();
			stmt1.close();
			
			System.out.println("comment:"+assessArea.getText());
			String as="";
			if (assessArea.getText().length()==0)
				as="该用户没有留下评价~";
			else as=assessArea.getText();
			
			sql="INSERT INTO comment_table (comment,score) VALUES ('"+as+"',"+thisScore+")";
			System.out.println("sql:"+sql);
			Statement stmt2=con.createStatement();
			stmt2.executeUpdate(sql);
			stmt2.close();
			
			//标记是否评价
			
			try
        	{
            	int year11 = 0;//订单日期得从订单读取！
        		int mon11 = 0;
        		int day11 = 0;
        		if(!Date.isEmpty()) {
        			if(Date.contains("~")) {
        		    	String[] parts = Date.split("~");
        		    	String end = parts[1];
        			   	String[] bt = end.split("-");
        			   	String by = bt[0];
        			   	String bm = bt[1];
        			   	String bd = bt[2];
        		//	        	System.out.println("!:"+by+bm+bd);
        		    	year11 = Integer.parseInt(by);
        			   	mon11 = Integer.parseInt(bm);
        			   	day11 = Integer.parseInt(bd);  //订单时间
        		   }
        		   else {  //只订了一天
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
        			date2=new Date();   //获取系统时间
        			formatter2=new SimpleDateFormat("yyyy-MM-dd");  
        		    System.out.println(formatter2.format(date2));
//        			orderTime.setText(formatter.format(date));
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
            	
                if(Date.contains("~")) {
                	String[] parts = Date.split("~");
                	String begin = parts[0];   //起始时间
                	String end = parts[1];     //结束时间
                	String[] bt = begin.split("-");
                	String by = bt[0];
                	String bm = bt[1];
                	String bd = bt[2];
//                	System.out.println("!:"+by+bm+bd);
                	
                	LocalDate beginday = LocalDate.of(Integer.parseInt(by), Integer.parseInt(bm), Integer.parseInt(bd));
                	
                	String[] et = end.split("-");
                	String ey = et[0];
                	String em = et[1];
                	String ed = et[2];
//                	System.out.println("!!:"+ey+em+ed);
                	
                	LocalDate endday = LocalDate.of(Integer.parseInt(ey), Integer.parseInt(em), Integer.parseInt(ed));
                	System.out.println("开始日期："+beginday.toString());
            		System.out.println("结束日期："+endday.toString());
                	try {
        				Statement stmt = con.createStatement();
        				sql = "UPDATE book_table SET bassess=1 WHERE broomid = '"+id0+"' AND byear = "+by+" AND bmon = "+bm+" AND bday="+bd ;
        				stmt.executeUpdate(sql);
//        				roomData.clear();
        			} catch (SQLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}//更新第一天
                	while(!beginday.equals(endday)) {//中间天数
                		System.out.println("开始日期："+beginday.toString());
                		System.out.println("结束日期："+endday.toString());
                		LocalDate mindate = beginday.plusDays(1);
                		int minyear = mindate.getYear();
                		int minmon = mindate.getMonthValue();
                		int minday = mindate.getDayOfMonth();
                		try {
            				Statement stmt = con.createStatement();
            				sql = "UPDATE book_table SET bassess=1 WHERE broomid = '"+id0+"' AND byear = "+minyear+" AND bmon = "+minmon+" AND bday="+minday ;
            				stmt.executeUpdate(sql);
//            				roomData.clear();
            			} catch (SQLException e1) {
            				// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}//更新中间天数
                		beginday = mindate;//重置一下开始日期
                	}
                	if(beginday.equals(endday)) {//更新最后一天
                    	try {
            				Statement stmt = con.createStatement();
            				sql = "UPDATE book_table SET bassess=1 WHERE broomid = '"+id0+"' AND byear = "+ey+" AND bmon = "+em+" AND bday="+ed ;
            				stmt.executeUpdate(sql);
//            				roomData.clear();
            			} catch (SQLException e1) {
            				// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}
                	}
                }
                else {  //只有一天
                	System.out.println(11111);
                	String[] bt = Date.split("-");
                	String by = bt[0];
                	String bm = bt[1];
                	String bd = bt[2];
                	
//                	System.out.println(by+bm+bd);
                	try {
        				Statement stmt = con.createStatement();
        				sql = "UPDATE book_table SET bassess=1 WHERE broomid = '"+id0+"' AND byear = "+by+" AND bmon = "+bm+" AND bday="+bd ;
        				stmt.executeUpdate(sql);
        			} catch (SQLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
                }
        	}
        	catch(Exception e)
        	{
        		System.out.println(e);
        	}
			
			//-------------------------------------------------
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("提交成功，感谢您的反馈！");
    		alert.show();
    		
    		Stage preStage=(Stage)subBtn.getScene().getWindow();
			preStage.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }
}
