import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

public class Main extends JFrame{
    private static Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static DefaultTableModel tableModel;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static DefaultTableModel generateModel(ResultSet resultSet) throws Exception{
        ResultSetMetaData mdt = resultSet.getMetaData();
        Vector<String> ColumnNames = new Vector<String>();
        int ccount = mdt.getColumnCount();
        for(ccount=0;ccount<1;ccount++){
            ColumnNames.add(mdt.getColumnName(ccount));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> row = new Vector<>();
            for(int i = 0; i<1; i++){
                row.add(resultSet.getObject(i));
            }
            data.add(row);
        }
        return new DefaultTableModel(data, ColumnNames);
    }
    public Main() {
        setLayout(new GridLayout(4,2));
        setVisible(true);
        JTextField sidinput = new JTextField();
        JTextField sidinputtext = new JTextField();
        sidinputtext.setText("Enter Student ID: ");
        JTextField startdate = new JTextField();
        JTextField startdatetext = new JTextField();
        startdatetext.setText("Enter Start Date");
        JTextField enddate = new JTextField();
        JTextField enddatetext = new JTextField();
        enddatetext.setText("Enter End Date: ");
        JButton searchbtn = new JButton();
        searchbtn.setText("Search");
        searchbtn.addActionListener(e -> {
                String sid = sidinput.getText();

                try {
                    Timestamp starttimestamp = new java.sql.Timestamp(sdf.parse(startdate.getText()).getTime());
                    Timestamp endtimestamp = new java.sql.Timestamp(sdf.parse(enddate.getText()).getTime());
                    preparedStatement = connect.prepareStatement("select * from students where StudentID =? AND AccessTime >=? AND AccessTime <=?");
                    preparedStatement.setString(1, sid);
                    preparedStatement.setTimestamp(2, starttimestamp);
                    preparedStatement.setTimestamp(3, endtimestamp);
                    ResultSet rset = preparedStatement.executeQuery();
                    JTable table = new JTable(generateModel(rset));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250,130);
        add(sidinputtext);
        add(sidinput);
        add(startdatetext);
        add(startdate);
        add(enddatetext);
        add(enddate);
        add(searchbtn);

    }



    public static void main(String[] args) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet rset;
        connect = DriverManager.getConnection("jdbc:mysql://localhost/student_info?"
                        + "user=shreyas&password=password");
        String sid = null;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter your Student ID: ");
            sid = scanner.nextLine();
            preparedStatement = connect
                    .prepareStatement("insert into `students` values ( ?, now())");
            if(sid.length()==9) {
                preparedStatement.setString(1,sid);
                preparedStatement.executeUpdate();
            }
            if(sid.equals("admin")) {
                Main adminview = new Main();

                    }


            if(sid.equals("1")) {
               return;
            }

        }


    }
}


