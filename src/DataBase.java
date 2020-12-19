import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.sql.*;


public class DataBase {
    //String url = "jdbc:ucanaccess://src//DataBaseAccess.accdb";
    String url = "database/empresa.accdb";
    public DataBase() {
        //CreateDataBase();

    /*  if(!DoesDataBaseExist()){
          CreateDataBase();
      }

     */
    }
    private Connection connect() {
        String link = "jdbc:mysql://localhost:3306/practicajdbc";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(link,"root","qwerty");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private boolean DoesDataBaseExist(){
        File file = new File(url);
        if(file.exists()){
            return true;
        }
        return false;
    }
    private void CreateDataBase(){
        File file = new File(url);
        try {
            file.createNewFile();
            CreateTables();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CreateTables(){
        String createProduct = "CREATE TABLE product (" +
                "product_id  int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "product_name TEXT    NOT NULL," +
                "product_price DECIMAL(6,4) NOT NULL" +
                ");";
        String createClient = "CREATE TABLE client (\n" +
                "client_id  int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "client_name TEXT    NOT NULL,\n" +
                "client_manager TEXT    NOT NULL,\n" +
                "client_address TEXT    NOT NULL,\n" +
                "client_sign_date DATE " +
                ");";
        String createSale = "CREATE TABLE sale (\n" +
                "date DATE,"+
                "sale_id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n" +
                "client_id INTEGER  NOT NULL,\n" +
                "product_id INTEGER   NOT NULL,\n" +
                "amount INTEGER NOT NULL,\n" +
                "paid TEXT,"+
                "FOREIGN KEY (client_id)\n" +
                "REFERENCES client (client_id),\n" +
                "FOREIGN KEY (product_id)\n" +
                "REFERENCES product (product_id)\n" +
                ");";
        try(Connection conn = connect()) {
            Statement products = conn.createStatement();
            Statement clients = conn.createStatement();
            Statement sale = conn.createStatement();
            products.execute(createProduct);
          clients.execute(createClient);
            sale.execute(createSale);
            JOptionPane.showMessageDialog(null,"Data Base has been created");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null,("Something went wrong while creating data base\n"+throwables.getMessage()));
        }
    }

    public boolean AddProduct(String name,double price){
        String sql = "INSERT INTO product(product_name,product_price) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2,price);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean AddClient(String name,String address,String manager,java.sql.Date date){
        String sql = "INSERT INTO client(client_name,client_manager,client_address,client_sign_date) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2,address);
            pstmt.setString(3,manager);
            pstmt.setDate(4,date);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean AddSale(java.sql.Date date,int client_id,int product_id,int amount,String pagado)
    {
        String sql = "INSERT INTO sale (date,client_id,product_id,amount,paid) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            pstmt.setInt(2,client_id);
            pstmt.setInt(3,product_id);
            pstmt.setInt(4,amount);
            pstmt.setString(5,pagado);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public JTable GetTable(int option) {
        String sql;
        ResultSet rs;
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel();
        Object[] data;
        switch (option) {
            //Producto
            case 1:
                sql = "SELECT * FROM product";
                dtm.setRowCount(0);
                dtm.addColumn("product_id");
                dtm.addColumn("product_name");
                dtm.addColumn("product_price");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            //Cliente
            case 2:
                sql = "SELECT * FROM client";
                dtm.setRowCount(0);
                dtm.addColumn("client_id");
                dtm.addColumn("client_name");
                dtm.addColumn("client_manager");
                dtm.addColumn("client_address");
                dtm.addColumn("client_sign_date");
                table.setModel(dtm);
                data = new Object[5];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = rs.getDate(5);
                        dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            //Sales
            case 3:
                sql = "SELECT sale.date,sale.sale_id,sale.client_id,sale.product_id,sale.amount,(sale.amount*product.product_price) as TEST,sale.paid from sale,product where sale.product_id=product.product_id ;";
                dtm.setRowCount(0);
                dtm.addColumn("Fecha");
                dtm.addColumn("sale_id");
                dtm.addColumn("client_id");
                dtm.addColumn("product_id");
                dtm.addColumn("amount");
                dtm.addColumn("total");
                dtm.addColumn("paid");
                table.setModel(dtm);
                data = new Object[7];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getDate(1);
                        data[1] = rs.getInt(2);
                        data[2] = rs.getInt(3);
                        data[3] = rs.getInt(4);
                        data[4] = rs.getInt(5);
                        data[5] = rs.getDouble(6);
                        data[6] = rs.getString(7);
                        dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
        }
        return null;
    }
    public boolean ModifyProduct(int product_id,String product_name,double product_price){
        String sql = "UPDATE product SET product_name = ?, product_price=? WHERE product_id = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product_name);
            pstmt.setDouble(2,product_price);
            pstmt.setInt(3,product_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean ModifyClient(int client_id,String client_name,String client_manager,String client_address){
        String sql = "UPDATE client SET client_name = ?, client_manager=?, client_address=? WHERE client_id = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client_name);
            pstmt.setString(2,client_manager);
            pstmt.setString(3,client_address);
            pstmt.setInt(4,client_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
    public boolean ModifySale(java.sql.Date date,int client_id,int product_id,int amount,String pagado,int sale_id){
        String sql = "UPDATE sale SET date = ?, client_id=?, product_id=?, amount=?, paid=? WHERE sale_id = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            pstmt.setInt(2,client_id);
            pstmt.setInt(3,product_id);
            pstmt.setInt(4,amount);
            pstmt.setString(5,pagado);
            pstmt.setInt(6,sale_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean DeleteData(int option, int id){
        String sql=null;
        switch (option){
            case 1:
                 sql = "DELETE FROM product WHERE product_id=?";
                 break;
            case 2:
                sql = "DELETE FROM client WHERE client_id=?";
                break;
            case 3:
                sql = "DELETE FROM sale WHERE sale_id=?";
                break;
        }
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public JTable GetProductCons(int option,int numofresults) {
        String sql;
        ResultSet rs;
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel();
        Object[] data;
        switch (option) {
            case 1:
                sql = "SELECT * FROM product ORDER BY product_price DESC LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("product_id");
                dtm.addColumn("product_name");
                dtm.addColumn("product_price");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 2:
                sql = "SELECT * FROM product ORDER BY product_price ASC LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("product_id");
                dtm.addColumn("product_name");
                dtm.addColumn("product_price");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 3:
                sql  = "select product.product_id,product.product_name,count(sale.product_id) as result from product,sale where sale.product_id = product.product_id group by product.product_id  ORDER BY result DESC LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("product_id");
                dtm.addColumn("product_name");
                dtm.addColumn("numero de ventas");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getInt(3);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 4:
                sql  = "select product.product_id,product.product_name,count(sale.product_id) as result from product,sale where sale.product_id = product.product_id group by product.product_id ORDER BY result ASC  LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("product_id");
                dtm.addColumn("product_name");
                dtm.addColumn("numero de ventas");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getInt(3);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
        }
        return null;
    }

    public JTable GetClientAndSaleCons(int option,String id_or_date,int numofresults) {
        String sql;
        ResultSet rs;
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel();
        Object[] data;
        switch (option) {
            case 1:
                sql = "select client.client_name from sale,client where sale.client_id=client.client_id AND YEAR(date)="+id_or_date+" LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("client_name");
                table.setModel(dtm);
                data = new Object[1];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                            data[0] = rs.getString(1);
                            dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 2:
                sql = "select client.client_name,count(sale.client_id) as result from client,sale where sale.client_id = client.client_id  group by client.client_id ORDER BY result DESC LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("client_name");
                dtm.addColumn("compras");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getInt(2);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 3:
                sql = "select client.client_name,count(sale.client_id) as result from client,sale where sale.client_id = client.client_id  group by client.client_id ORDER BY result ASC LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("client_name");
                dtm.addColumn("compras");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getInt(2);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 4:
                sql ="select sale.sale_id, sale.date, client.client_name from sale,client where sale.client_id=client.client_id AND client.client_id="+id_or_date+" ORDER BY date DESC LIMIT " +numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("sale_id");
                dtm.addColumn("sale_date");
                dtm.addColumn("client_name");
                table.setModel(dtm);
                data = new Object[3];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getDate(2);
                        data[2] = rs.getString(3);
                        dtm.addRow(data);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
            case 5:
                sql = "SELECT * FROM sale where year(date)="+id_or_date+" LIMIT "+numofresults;
                dtm.setRowCount(0);
                dtm.addColumn("Fecha");
                dtm.addColumn("sale_id");
                dtm.addColumn("client_id");
                dtm.addColumn("product_id");
                dtm.addColumn("total");
                dtm.addColumn("paid");
                table.setModel(dtm);
                data = new Object[6];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getDate(1);
                        data[1] = rs.getInt(2);
                        data[2] = rs.getInt(3);
                        data[3] = rs.getInt(4);
                        data[4] = rs.getInt(5);
                        data[5] = rs.getString(6);
                        dtm.addRow(data);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }
                return table;
            case 6:
                String separator = "=SEPARATE=";
                String[] year = id_or_date.split(separator);
                int year1 = Integer.parseInt(year[0]);
                int year2 = Integer.parseInt(year[1]);
                sql ="select (select count(sale_id) from sale where year(date)="+year1+") as 'YEAR ONE',(select count(sale_id) from sale where year(date)="+year2+") as 'YEAR TWO';";
                //sql = "select count(sale_id) from sale where year(date)="+year1 +" or year(date)="+year2+" group by year(date)";
                dtm.setRowCount(0);
                dtm.addColumn("Year: "+year1);
                dtm.addColumn("Year: "+year2);
                table.setModel(dtm);
                data = new Object[2];
                try {
                    Connection conn = this.connect();
                    Statement stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        data[0] = rs.getInt(1);
                        data[1] = rs.getInt(2);
                        dtm.addRow(data);

                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "An error occured" + "\n" + e.getMessage());
                }


                return table;
        }
        return null;
    }


}