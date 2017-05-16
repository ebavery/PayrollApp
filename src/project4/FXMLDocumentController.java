/*****************************************************************************
 * Program Name:        Project 4 -- FXMLDocumentController.java
 * Program Description: This program creates a payroll application.
 * Program Author:      Elizabeth Avery
 * Date Created:        12/09/2016
 * Change#      Change Date     Programmer Name     Description
 * -------      -----------     ---------------     ----------------------
*****************************************************************************/
package project4;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtEmpNo;
    @FXML
    private TextField txtLast;
    @FXML
    private TextField txtFirst;
    @FXML
    private TextField txtMI;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtDept;
    @FXML
    private TextField txtJobLevel;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtSalMin;
    @FXML
    private TextField txtSalMax;
    @FXML
    private TextField txtLevelPercent;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Label lblEmpNo;
    @FXML
    private Label lblLast;
    @FXML
    private Label lblFirst;
    @FXML
    private Label lblMI;
    @FXML
    private Label lblDept;
    @FXML
    private Label lblJobLevel;
    @FXML
    private Label lblSalary;
    @FXML
    private Label lblSalMin;
    @FXML
    private Label lblSalMax;
    @FXML
    private Label lblLevelPercent;
    @FXML
    private TextField txtEmpNoSearch;
    @FXML
    private Label lblEmpNoSearch;
    @FXML
    private Label lblNewEmpNo;
    @FXML
    private TextField txtNewEmpNo;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblState;
    @FXML
    private Rectangle rectMessage;
    @FXML
    private Button btnDeleteCancel;
    @FXML
    private Button btnDeleteConfirm;
    @FXML
    private Label lblDeleteConfirm;
    @FXML
    private Polygon polyMessage;
    private Statement stmt;
    private DecimalFormat salaryFormat = new DecimalFormat("'$'#,###.##");
    private double salary, salMin, salMax;    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnAdd.setVisible(false);
        lblDeleteConfirm.setVisible(false);
        btnDeleteConfirm.setVisible(false);
        btnDeleteCancel.setVisible(false);
        rectMessage.setVisible(false);
        polyMessage.setVisible(false);
        initializeDB();
    } 
//*****************************BUTTON ACTION EVENTS****************************
//SEARCH
    @FXML
    private void handleButtonActionBtnSearch(MouseEvent event) {
       search();        
    }
//CREATE NEW RECORD--CHECK IF EMPNO EXISTS
    @FXML
    private void handleButtonActionBtnNew(MouseEvent event) throws SQLException {
        String empNoString = txtNewEmpNo.getText().trim();
        int empNo = Integer.parseInt(empNoString);
               
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee WHERE EMPNO = "  + empNo);
            if (rs.next()){
                if (rs.getString(1).equals(empNoString)){
                    lblMessage.setText("EmpNo already exists");
                    clear();
                }
            }    
            else {
                clear();
                txtNewEmpNo.setText(null);
                txtEmpNo.setText(empNoString);
                btnAdd.setVisible(true);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                txtLast.requestFocus();
            }                
        }
        catch (SQLException ex){            
        }
    }
//CREATE NEW RECORD
   @FXML
    private void handleButtonActionBtnAdd(MouseEvent event) {
        insert();
        txtSalary.setText(salaryFormat.format(Double.parseDouble(txtSalary.getText().trim())));
        btnAdd.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
    }
//UPDATE
    @FXML
    private void handleButtonActionBtnUpdate(MouseEvent event) {
        update();
        btnNew.setVisible(true);
        btnSearch.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }
//DELETE--CONFIRM PROMPT
    @FXML
    private void handleButtonActionBtnDelete(MouseEvent event) {
        lblDeleteConfirm.setVisible(true);
        btnDeleteConfirm.setVisible(true);
        btnDeleteCancel.setVisible(true);
        rectMessage.setVisible(true);
        polyMessage.setVisible(true);
    }
//DELETE--CANCEL DELETE
    @FXML
    private void handleButtonActionBtnDeleteCancel(MouseEvent event) {
        lblDeleteConfirm.setVisible(false);
        btnDeleteConfirm.setVisible(false);
        btnDeleteCancel.setVisible(false);
        rectMessage.setVisible(false);
        polyMessage.setVisible(false);
    }
//DELETE--ACTUALLY DELETE
    @FXML
    private void handleButtonActionBtnDeleteConfirm(MouseEvent event) {
        delete();
        btnNew.setVisible(true);
        btnSearch.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        lblDeleteConfirm.setVisible(false);
        btnDeleteConfirm.setVisible(false);
        btnDeleteCancel.setVisible(false);
        rectMessage.setVisible(false);
        polyMessage.setVisible(false);
    }
//CLEAR FORM
    @FXML
    private void handleButtonActionBtnClear(MouseEvent event) {
        clear();
        lblMessage.setText(null);
        btnNew.setVisible(true);
        btnSearch.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }
//EXIT PROGRAM
    @FXML
    private void handleButtonActionBtnExit(MouseEvent event) {
        System.exit(0);
    }
//**********************************************METHODS*************************
//INITIALIZE DATABASE METHOD
     private void initializeDB(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project4;create=true;user=nbuser;password=nbuser");
            
            lblMessage.setText("Database connected");
            
            stmt = conn.createStatement();
        }
        catch (Exception ex){
            lblMessage.setText("Connection failed: " + ex);
        }
    }
//SEARCH METHOD
    private void search(){
        String empNoString = txtEmpNoSearch.getText().trim();
        int empNo = Integer.parseInt(empNoString);
        
        try{
            ResultSet rsEmpNo = stmt.executeQuery("SELECT * FROM Employee WHERE EMPNO = "  + empNo);
            if (rsEmpNo.next()){
                if (rsEmpNo.getString(1).equals(empNoString)) {
                    txtEmpNo.setText(empNoString);
                    ResultSet rsEmp = stmt.executeQuery("SELECT * FROM Employee WHERE EMPNO = " + empNo);
                    populateEmployeeFields(rsEmp);
                    ResultSet rsSal = stmt.executeQuery("SELECT * FROM Salary WHERE EMPNO = " + empNo);
                    populateSalaryFields(rsSal);  
                    String levelQuery = "SELECT * FROM Salary_Level WHERE JOB_LEVEL = '" + txtJobLevel.getText().trim() + "'";
                    try{
                        ResultSet rsLevel = stmt.executeQuery(levelQuery);
                        populateLevelFields(rsLevel);
                    }
                    catch(SQLException ex){
                        lblMessage.setText("Search failed: " + ex);
                    }
                    calculateLevelPercent(salary, salMin, salMax);
                    txtEmpNoSearch.setText(null);
                    txtEmpNoSearch.requestFocus(); 
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                }
            }    
            else {
                lblMessage.setText("Record not found");
                clear();
            }            
        }
        catch(SQLException ex){
            lblMessage.setText("Search failed: " + ex);
        }
    } 
//ADD NEW RECORD METHOD
    private void insert(){
        String middleInitial = txtMI.getText().trim();
        if (middleInitial.equals(""))
                middleInitial = " ";
        int empNo = Integer.parseInt(txtEmpNo.getText().trim()); 
        String insertEmpStmt = "INSERT INTO Employee (EMPNO, LASTNAME, FIRSTNAME, MI, STATE) " +
                "VALUES (" + empNo + ", '" +
                txtLast.getText().trim() + "', '" +
                txtFirst.getText().trim() + "', '" +
                middleInitial + "', '" +
                txtState.getText().trim() + "')";
        salary = Double.parseDouble(txtSalary.getText().trim());
        String insertSalStmt = "INSERT INTO Salary (EMPNO, DEPT_NO, JOB_LEVEL, SALARY_AMOUNT) VALUES (" +
                Integer.parseInt(txtEmpNo.getText().trim()) + ", '" +
                txtDept.getText().trim() + "', '" +
                txtJobLevel.getText().trim() + "', " +
                salary + ")";
        try{
            stmt.executeUpdate(insertEmpStmt);
            stmt.executeUpdate(insertSalStmt);
            lblMessage.setText("Record added");
            
        }
        catch (SQLException ex) {
            lblMessage.setText("New record failed: " + ex);
        }
        String levelQuery = "SELECT * FROM Salary_Level WHERE JOB_LEVEL = '" + txtJobLevel.getText().trim() + "'";
        try{
            ResultSet rsLevel = stmt.executeQuery(levelQuery);
            populateLevelFields(rsLevel);
        }
        catch(SQLException ex){
            lblMessage.setText("Search failed: " + ex);
        }
        
        calculateLevelPercent(salary, salMin, salMax);
    }   
//UPDATE METHOD
    private void update(){
        int empNo = Integer.parseInt(txtEmpNo.getText().trim());
        String updateEmpStmt = "UPDATE Employee " + 
                "SET LASTNAME = '" + txtLast.getText().trim() + "', " +
                "FIRSTNAME = '" + txtFirst.getText().trim() + "', " +
                "MI = '" + txtMI.getText().trim() + "', " +
                "STATE = '" + txtState.getText().trim() + "' " +
                "WHERE EMPNO = " + txtEmpNo.getText().trim();
        String salString = txtSalary.getText().trim();
        if (salString.startsWith("$")) {
            salString = salString.substring(1);
        }
        if (salString.contains(",")){
            int k = salString.indexOf(",");
            salString = salString.substring(0, k) + salString.substring(k + 1);
        }
        salary = Double.parseDouble(salString);
        String updateSalStmt = "UPDATE Salary " +
                "SET DEPT_NO = '" + txtDept.getText().trim() + "', " +
                "JOB_LEVEL = '" + txtJobLevel.getText().trim() + "', " +
                "SALARY_AMOUNT = " + salary + 
                " WHERE EMPNO = " + txtEmpNo.getText().trim();
        try {
            stmt.executeUpdate(updateEmpStmt);
            stmt.executeUpdate(updateSalStmt);
            calculateLevelPercent(salary, salMin, salMax);
            lblMessage.setText("Record updated");
        }
        catch (SQLException ex){
            lblMessage.setText("Update failed: " + ex);
        }
    }
//DELETE METHOD
    private void delete(){
        
        int empNo = Integer.parseInt(txtEmpNo.getText().trim());

        try {
            stmt.executeUpdate("DELETE FROM Employee WHERE EMPNO = " + empNo);
            stmt.executeUpdate("DELETE FROM Salary WHERE EMPNO = " + empNo);
            lblMessage.setText("Record deleted");
            clear();
        }
        catch (SQLException ex) {
            lblMessage.setText("Delete failed: " + ex);
        }
    } 
//CLEAR METHOD
    private void clear(){
        txtEmpNo.setText(null);
        txtLast.setText(null);
        txtFirst.setText(null);
        txtMI.setText(null);
        txtState.setText(null);
        txtDept.setText(null);
        txtJobLevel.setText(null);
        txtSalary.setText(null);
        txtSalMin.setText(null);
        txtSalMax.setText(null);
        txtLevelPercent.setText(null);
        txtLevelPercent.setStyle("-fx-background-color: lightgrey;");    
    }
//*****************************SUB-METHODS*************************************
//USED IN SEARCH METHOD--SET TEXT IN EMPLOYEE FIELDS
    private void populateEmployeeFields(ResultSet rs) throws SQLException{
        if (rs.next()){

            txtLast.setText(rs.getString(2));
            txtFirst.setText(rs.getString(3));
            txtMI.setText(rs.getString(4));
            txtState.setText(rs.getString(7));
            lblMessage.setText("Record found");
        }
    }
//USED IN SEARCH METHOD--SET TEXT IN SALARY FIELDS
    private void populateSalaryFields(ResultSet rs) throws SQLException {
        if (rs.next()) {
            txtDept.setText(rs.getString(2));
            txtJobLevel.setText(rs.getString(3));
            salary = Double.parseDouble(rs.getString(4));
            txtSalary.setText(salaryFormat.format(salary));
        }
    }
//USED IN SEARCH AND INSERT METHODS--SET TEXT IN SALARY LEVEL FIELDS
    private void populateLevelFields(ResultSet rs) throws SQLException {
        if (rs.next()) {
            salMin = Double.parseDouble(rs.getString(2));
            salMax = Double.parseDouble(rs.getString(3));
            txtSalMin.setText(salaryFormat.format(salMin));
            txtSalMax.setText(salaryFormat.format(salMax));  
       }      
    }    
//USED IN SEARCH AND UPDATE METHODS--CALCULATE AND SET TEXT IN PENETRATION LEVEL FIELD
    private void calculateLevelPercent(double sal, double salMn, double salMx){
        int levelPercent = (int)(100 * (salary - salMin) / (salMax - salMin));
        String levelString = levelPercent + "%";
        txtLevelPercent.setText(levelString);
        if (levelPercent > 90 || levelPercent < 0){
            txtLevelPercent.setStyle("-fx-background-color: red;");
        }
        else
            txtLevelPercent.setStyle("fx-background-color: lightgrey;");
    }
}
