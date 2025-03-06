package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leewyatt.rxcontrols.controls.RXFillButton;

import application.BookRoomController.RoomInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BookInfoController {

	//组件更改！
    @FXML private TextField date;
    @FXML private TextField gid;
    @FXML
    private RXFillButton submitBtn;

    @FXML
    private RXFillButton cancelBtn;
    @FXML private ToggleGroup sex;
    @FXML private TextField gphone;
    @FXML private TextField gname;
    @FXML private TextField type;
    @FXML private TextField orderTime;

    private Connection con;
    private String selType;
    private int mon;
    private int day;
    private int time;
    
    void showInfo(String inType,int mon,int day,int time)
    {
    	try
    	{
    		String DBUrl="jdbc:mysql://localhost:3306/hoteldatabase?useSSL=false&serverTimezone=Hongkong";
			String DBUser="root", DBPassword="sysu";
			Connection con=DriverManager.getConnection(DBUrl,DBUser,DBPassword);
			this.con=con;
			this.selType=inType;
			this.mon=mon;
			this.day=day;
			this.time=time;
			
			try
			{
				Date date=new Date();   //获取系统时间
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    System.out.println(formatter.format(date));
				orderTime.setText(formatter.format(date));
			}
			catch (Exception e)
		    {
		    	System.out.println(e);
		    }
			
			type.setText(inType);
			String tmpdate=String.valueOf(mon)+"月"+String.valueOf(day)+"日";
			
			if (time>1)
			{
				for (int i=0;i<time;i++)         //从订房当日开始+i天
				{
					if (mon==2&&day==29) {mon=3;day=1;}
					else if ((mon==1||mon==3||mon==5||mon==7||mon==8||mon==10)&&day==32) {mon++;day=1;}
					else if (mon==12&&day==32) {mon=1;day=1;}
					else if ((mon==4||mon==6||mon==9||mon==11)&&day==31) {mon++;day=1;}
					day++;
				}
				day--;
				tmpdate=tmpdate+" - "+String.valueOf(mon)+"月"+String.valueOf(day)+"日";
			}
			date.setText(tmpdate);
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    /**
     * 判断字符串中是否全是中文
     * 
     * @param str 待校验字符串
     * @return 是否全是中文
     */
    public static boolean isAllChinese(String str)
    {
        if (str==null)
        	return false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m=p.matcher(str);
        return m.matches();
    }
    
    //判断手机号是否合法
    public boolean phoneValidate(String phoneNumber)
    {
        if (phoneNumber!=null&&!phoneNumber.isEmpty())
        {
            boolean matches = Pattern.matches(
                    "^1[3-9]\\d{9}$|" +
                            "^1[3-9]\\d{1}[-\\s]\\d{4}[-\\s]\\d{4}$|" +
                            "^\\(1[3-9]\\d{1}\\)\\d{4}-\\d{4}$|" +
                            "^(?:\\(\\+\\d{2}\\)|\\+\\d{2})(\\d{11})$|" +
                            "^0\\d{3}-\\d{7}$|" +
                            "^0\\d{2}-\\d{8}$",phoneNumber);

            return matches;
        }
        return false;
    }
    
    @FXML
    void handleSubmit(ActionEvent event) {
    	try
    	{
    		//加上个人信息非法判断
    		RadioButton selectedSex=(RadioButton) sex.getSelectedToggle();
    		String bookName=gname.getText();
	    	String bookId=gid.getText();
	    	String bookPhone=gphone.getText();
	    	if (selectedSex==null||bookName.length()==0||bookId.length()==0||bookPhone.length()==0)
	    	{
    			Alert alert=new Alert(AlertType.INFORMATION);
        		alert.setTitle("温馨提示");
        		alert.setHeaderText("");
        		alert.setContentText("请填写完整住客信息！");
        		alert.show();
        		return;
    		}
	    	int elFlag=0;
	    	if ((bookName.charAt(0)>='A'&&bookName.charAt(0)<='Z')||(bookName.charAt(0)>='a'&&bookName.charAt(0)<='z'))
	    	{
	    		elFlag=1;
	    		//如果有英文必须只含英文/空格
	    		for (int i=1;i<bookName.length();i++)
	    		{
	    			if ((bookName.charAt(i)>='A'&&bookName.charAt(i)<='Z')||(bookName.charAt(i)>='a'&&bookName.charAt(i)<='z'))
	    		    	continue;
	    			System.out.println("bookName:"+bookName.charAt(i));
	    			if (bookName.charAt(i)==' ')
	    				continue;
	    			System.out.println("bookName:"+bookName.charAt(i));
	    			Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("温馨提示");
		    		alert.setHeaderText("");
		    		alert.setContentText("请填写合法住客姓名（纯英文或纯中文）！");
		    		alert.show();
		    		return;
	    		}
	    	}
	    	if (elFlag==0)
	    		if (isAllChinese(bookName)==false)
	    		{
	    			Alert alert=new Alert(AlertType.INFORMATION);
		    		alert.setTitle("温馨提示");
		    		alert.setHeaderText("");
		    		alert.setContentText("请填写合法住客姓名（纯英文或纯中文）！");
		    		alert.show();
		    		return;
	    		}
	    	
	    	if (phoneValidate(bookPhone)==false)
	    	{
	    		Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setTitle("温馨提示");
	    		alert.setHeaderText("");
	    		alert.setContentText("请填写11位合法手机号！");
	    		alert.show();
	    		return;
	    	}
	    	
	    	String bookSex=selectedSex.getText();
	    	Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery("SELECT * FROM room_table WHERE rtype='"+selType+"';");
			while (rs.next())
			{
				String tmpRid=rs.getString("rid");
				int tmpprice=rs.getInt("rprice");
				Statement stmt1=con.createStatement();
				for (int i=0;i<time;i++)         //从订房当日开始+i天
				{
					if (mon==2&&day==29) {mon=3;day=1;}
					else if ((mon==1||mon==3||mon==5||mon==7||mon==8||mon==10)&&day==32) {mon++;day=1;}
					else if (mon==12&&day==32) {mon=1;day=1;}
					else if ((mon==4||mon==6||mon==9||mon==11)&&day==31) {mon++;day=1;}
					
					//插入订房记录
					String sql="INSERT INTO book_table (byear,bmon,bday,broomid,bguestid,bprice,bassess) values (2024,'"+mon+"','"+day+"','"+tmpRid+"','"+bookId+"',"+tmpprice+",0);";					
					System.out.println("bookSQL:"+sql);
					stmt1.executeUpdate(sql);
					day++;
				}
				stmt1.close();
				break;  //随便找到第一间加入就行了
			}
			
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setTitle("温馨提示");
    		alert.setHeaderText("");
    		alert.setContentText("订单提交成功！");
    		alert.show();
    		
			Statement stmt2=con.createStatement();
			String sql="SELECT * FROM guest_table WHERE gid='"+bookId+"'";
			ResultSet rr2=stmt2.executeQuery(sql);
			int flag=0;   //没有顾客
			while (rr2.next())
				flag=1;   //该顾客已存档
			if (flag==0)
			{
				Statement stmt3=con.createStatement();
				sql="INSERT INTO guest_table (gid,gname,gsex,gphonenum) values ('"+bookId+"','"+bookName+"','"+bookSex+"','"+bookPhone+"');";
				System.out.println("sql:"+sql);
				stmt3.executeUpdate(sql);
			}
			
			rs.close();
			stmt.close();
			stmt2.close();
			con.close();
			
			Stage stage=(Stage)submitBtn.getScene().getWindow();
    		stage.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
    	try
    	{
    		Stage stage=(Stage)cancelBtn.getScene().getWindow();
    		stage.close();
    	}
    	catch (Exception e)
	    {
	    	System.out.println(e);
	    }
    }
}
