import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class GUI {
    JScrollPane DataTable ;
    DataBase db = new DataBase();
    JFrame mainFrame;
    JMenuBar menuBar;
    int w = 800;
    int h = 600;

    public GUI() {
        //start
        mainFrame = new JFrame("Practica databases empresa");
        DataTable = GetData(1);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(w, h);
        mainFrame.setLocationRelativeTo(null);
        Interface();
        mainFrame.add(DataTable);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
    }

    private void Interface() {
        menuBar = new JMenuBar();
        //MAIN MENU
        JMenu entry = new JMenu("Entrada");
        JMenu modify = new JMenu("Modificar Entrada");
        JMenuItem delete = new JMenuItem("Eliminar Entrada");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteData();
            }
        });
        //SUB MENU
        //block new entry
        JMenuItem newEntryProduct, newEntryClient, newEntrySale;
        JMenu newEntry = new JMenu("Nueva Entrada");
        newEntryProduct = new JMenuItem("Nuevo Producto");
        newEntryProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewProductInterface();
            }
        });

        newEntryClient = new JMenuItem("Nuevo Cliente");
        newEntryClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewClientInterface();
            }
        });
        newEntrySale = new JMenuItem("Nueva Venta");
        newEntrySale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewSaleInterface();
            }
        });
        newEntry.add(newEntryProduct);//add to new Entry
        newEntry.add(newEntryClient);//add to new Entry
        newEntry.add(newEntrySale);//add to new Entry
        entry.add(newEntry);//add to main Entry
        //block modify entry
        JMenuItem modifyEntryProduct, modifyEntryClient, modifyEntrySale;
        modifyEntryProduct = new JMenuItem("Modificar Producto");
        modifyEntryProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyProduct();
            }
        });
        modifyEntryClient = new JMenuItem("Modificar Cliente");
        modifyEntryClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyClient();
            }
        });
        modifyEntrySale = new JMenuItem("Modificar Venta");
        modifyEntrySale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifySale();
            }
        });
        modify.add(modifyEntryProduct);//add to Modify
        modify.add(modifyEntryClient);//add to Modify
        modify.add(modifyEntrySale);//add to Modify
        entry.add(modify);//add to main Entry
        //block delete entry

        entry.add(delete);//add to main Entry
        //Lists
        JMenuItem listProduct, listClient, listSale;
        JMenu list = new JMenu("Listas");
        listProduct = new JMenuItem("Lista Productos");
        listProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateFrame(1);
            }
        });
        listClient = new JMenuItem("Lista Clientes");
        listClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateFrame(2);
            }
        });
        listSale = new JMenuItem("Lista Ventas");
        listSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateFrame(3);
            }
        });
        list.add(listProduct);
        list.add(listClient);
        list.add(listSale);

        //Consults

        JMenu consult = new JMenu("Consultas");
        JMenu product_cons,client_cons,sale_cons;
        product_cons = new JMenu("Productos");
        client_cons = new JMenu("Clientes");
        sale_cons = new JMenu("Ventas");
        JMenuItem product_cons_highest_price,product_cons_lowset_price,product_cons_most_sales,product_cons_least_sales;
        product_cons_highest_price = new JMenuItem("Precio mas alto");
        product_cons_highest_price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //precio alto
                ProductCons(product_cons_highest_price.getText());
            }
        });
        product_cons_lowset_price = new JMenuItem("Precio mas bajo");
        product_cons_lowset_price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //precio bajo
                ProductCons(product_cons_lowset_price.getText());
            }
        });
        product_cons_most_sales = new JMenuItem("Mas vendido");
        product_cons_most_sales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCons(product_cons_most_sales.getText());
            }
        });
        product_cons_least_sales = new JMenuItem("Menos vendido");
        product_cons_least_sales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCons(product_cons_least_sales.getText());
            }
        });
        product_cons.add(product_cons_highest_price);
        product_cons.add(product_cons_lowset_price);
        product_cons.add(product_cons_most_sales);
        product_cons.add(product_cons_least_sales);

        JMenuItem client_cons_most_buys,client_cons_least_buys,client_cons_last_buy,client_cons_year;
        client_cons_most_buys = new JMenuItem("Cliente mayor compras");
        client_cons_most_buys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(client_cons_most_buys.getText());
            }
        });
        client_cons_least_buys = new JMenuItem("Cliente menor compras");
        client_cons_least_buys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(client_cons_least_buys.getText());
            }
        });
        client_cons_last_buy = new JMenuItem("Ultima compra de cliente");
        client_cons_last_buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(client_cons_last_buy.getText());
            }
        });
        client_cons_year = new JMenuItem("Clientes de un año");
        client_cons_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(client_cons_year.getText());
            }
        });
        client_cons.add(client_cons_year);
        client_cons.add(client_cons_most_buys);
        client_cons.add(client_cons_least_buys);
        client_cons.add(client_cons_last_buy);

        JMenuItem sale_cons_select_year,sale_cons_compare_years,sale_cons_total_whole_time;
        sale_cons_select_year = new JMenuItem("compras de un año concreto");
        sale_cons_select_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(sale_cons_select_year.getText());
            }
        });
        sale_cons_compare_years = new JMenuItem("Comparar compras de años");
        sale_cons_compare_years.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientAndSaleCons(sale_cons_compare_years.getText());
            }
        });

        sale_cons.add(sale_cons_select_year);
        sale_cons.add(sale_cons_compare_years);
        consult.add(product_cons);
        consult.add(client_cons);
        consult.add(sale_cons);
        menuBar.add(entry);
        menuBar.add(list);
        menuBar.add(consult);
    }

    private void NewProductInterface() {
        JFrame newEntry = new JFrame("Nuevo producto");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        JLabel productName = new JLabel("Nombre del producto");
        JLabel productPrice = new JLabel("Precio del producto");
        JTextField getProductName = new JTextField();
        JTextField getProductPrice = new JTextField();
        panel.add(productName);
        panel.add(getProductName);
        panel.add(productPrice);
        panel.add(getProductPrice);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product = getProductName.getText();
                double price = 0;
                while (price == 0) {
                    try {
                        price = Double.parseDouble(getProductPrice.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Precio incorrecto" + exception.getMessage());
                    }
                }
                if (db.AddProduct(product, price)) {
                    JOptionPane.showMessageDialog(null, "Producto anadido");
                    UpdateFrame(1);
                    newEntry.setVisible(true);
                    getProductName.setText("");
                    getProductPrice.setText("");
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);


    }

    private void NewClientInterface() {
        JFrame newEntry = new JFrame("Nuevo Cliente");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();
        JLabel clientName = new JLabel("Nombre del cliente");
        JLabel clientAddress = new JLabel("Direccion");
        JLabel clientManager = new JLabel("Manager");
        JLabel clientDate = new JLabel("Fecha de alta");
        JTextField getClientName = new JTextField();
        JTextField getClientAddress = new JTextField();
        JTextField getClientManager = new JTextField();
        JTextField getClientDate= new JTextField();

        panel.add(clientName);
        panel.add(getClientName);
        panel.add(clientDate);
        panel.add(getClientDate);
        panel.add(clientManager);
        panel.add(getClientManager);
        panel.add(clientAddress);
        panel.add(getClientAddress);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String client = getClientName.getText();
                String address = getClientAddress.getText();
                String manager = getClientManager.getText();
                java.sql.Date date1= java.sql.Date.valueOf(getClientDate.getText());

                if(db.AddClient(client, address, manager,date1))
                {
                    JOptionPane.showMessageDialog(null, "Cliente anadido");
                    UpdateFrame(2);
                    newEntry.setVisible(true);

                    getClientAddress.setText("");
                    getClientManager.setText("");
                    getClientName.setText("");
                    getClientDate.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al anadir cliente");
                }


            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }

    private void NewSaleInterface() {
        JFrame newEntry = new JFrame("Nueva Venta");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();

       JLabel date = new JLabel("Fecha");
       JLabel client = new JLabel("Cliente(Identificador)");
       JLabel product = new JLabel("Producto (Identificador)");
       JLabel amount = new JLabel("Cantidad");
       JLabel paid = new JLabel("Pagado");

        JTextField getDate = new JTextField();
        JTextField getClient = new JTextField();
        JTextField getProduct = new JTextField();
        JTextField getAmount = new JTextField();
        JTextField getPaid = new JTextField();

        panel.add(date);
        panel.add(getDate);
        panel.add(client);
        panel.add(getClient);
        panel.add(product);
        panel.add(getProduct);
        panel.add(amount);
        panel.add(getAmount);
        panel.add(paid);
        panel.add(getPaid);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String str = getDate.getText();
                java.sql.Date date1= java.sql.Date.valueOf(str);
                int client_id = Integer.parseInt(getClient.getText());
                int product_id = Integer.parseInt(getProduct.getText());
                int amount = Integer.parseInt(getAmount.getText());
                String paid = getPaid.getText();
                if (db.AddSale(date1, client_id, product_id, amount, paid)) {
                    JOptionPane.showMessageDialog(null, "Venta anadida");
                    UpdateFrame(3);
                    newEntry.setVisible(true);


                } else {
                    JOptionPane.showMessageDialog(null, "Error al anadir venta");
                }

            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }

    private void ModifyProduct() {
            JFrame newEntry = new JFrame("Modificar Producto");
            JPanel panel = new JPanel();
            JPanel buttons = new JPanel();
            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancelar");
            buttons.add(ok);
            buttons.add(cancel);
        JLabel productId = new JLabel("Identificador del producto");
            JLabel productName = new JLabel("Nuevo nombre");
            JLabel productPrice = new JLabel("Nuevo precio");
        JTextField getProductId = new JTextField();
            JTextField getProductName = new JTextField();
            JTextField getProductPrice = new JTextField();
            panel.add(productId);
            panel.add(getProductId);
            panel.add(productName);
            panel.add(getProductName);
            panel.add(productPrice);
            panel.add(getProductPrice);
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String product = getProductName.getText();
                    int product_id = Integer.parseInt(getProductId.getText());
                    double price = 0;
                    while (price == 0) {
                        try {
                            price = Double.parseDouble(getProductPrice.getText());
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Precio incorrecto" + exception.getMessage());
                        }
                    }
                    if (db.ModifyProduct(product_id,product,price)) {
                        JOptionPane.showMessageDialog(null, "Producto modificado");
                        UpdateFrame(1);
                        newEntry.setVisible(true);

                        getProductPrice.setText("");
                        getProductName.setText("");
                        getProductPrice.setText("");
                    }
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newEntry.dispose();
                }
            });
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            newEntry.add(panel, BorderLayout.NORTH);
            newEntry.add(buttons, BorderLayout.SOUTH);
            newEntry.pack();
            newEntry.setLocationRelativeTo(null);
            newEntry.setVisible(true);
        }

    private void ModifyClient() {
        JFrame newEntry = new JFrame("Modificar cliente");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();
        JLabel clientId = new JLabel("Identificador del cliente");
        JLabel clientName = new JLabel("Nombre del cliente");
        JLabel clientAddress = new JLabel("Direccion");
        JLabel clientManager = new JLabel("Manager");
        JTextField getClientId = new JTextField();
        JTextField getClientName = new JTextField();
        JTextField getClientAddress = new JTextField();
        JTextField getClientManager = new JTextField();
        panel.add(clientId);
        panel.add(getClientId);
        panel.add(clientName);
        panel.add(getClientName);
        panel.add(clientManager);
        panel.add(getClientManager);
        panel.add(clientAddress);
        panel.add(getClientAddress);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(getClientId.getText());
                String client = getClientName.getText();
                String address = getClientAddress.getText();
                String manager = getClientManager.getText();

                if(db.ModifyClient(id,client,manager,address))
                {
                    JOptionPane.showMessageDialog(null, "Cliente modificado");
                    UpdateFrame(2);
                    newEntry.setVisible(true);

                    getClientId.setText("");
                    getClientAddress.setText("");
                    getClientManager.setText("");
                    getClientName.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al modificar cliente");
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }

    private void ModifySale() {
        JFrame newEntry = new JFrame("Modificar Venta");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();

        JLabel sale_id = new JLabel("Identificador de venta");
        JLabel date = new JLabel("Fecha");
        JLabel client = new JLabel("Cliente(Identificador)");
        JLabel product = new JLabel("Producto(Identificador)");
        JLabel amount = new JLabel("Cantidad");
        JLabel paid = new JLabel("Pagado");

        JTextField getSaleId = new JTextField();
        JTextField getDate = new JTextField();
        JTextField getClient = new JTextField();
        JTextField getProduct = new JTextField();
        JTextField getAmount = new JTextField();
        JTextField getPaid = new JTextField();

        panel.add(sale_id);
        panel.add(getSaleId);
        panel.add(date);
        panel.add(getDate);
        panel.add(client);
        panel.add(getClient);
        panel.add(product);
        panel.add(getProduct);
        panel.add(amount);
        panel.add(getAmount);
        panel.add(paid);
        panel.add(getPaid);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int sale_id = Integer.parseInt(getSaleId.getText());
                String str = getDate.getText();
                java.sql.Date date1= java.sql.Date.valueOf(str);
                int client_id = Integer.parseInt(getClient.getText());
                int product_id = Integer.parseInt(getProduct.getText());
                int amount = Integer.parseInt(getAmount.getText());
                String paid = getPaid.getText();
                if (db.ModifySale(date1, client_id, product_id, amount, paid,sale_id)) {
                    JOptionPane.showMessageDialog(null, "Venta modificada");

                    UpdateFrame(3);
                    newEntry.setVisible(true);


                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar venta");
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }

    private void DeleteData() {
        JFrame newEntry = new JFrame("Eliminar entrada");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();
        JLabel info = new JLabel("Seleccione una opcion para eliminar");
        JLabel id = new JLabel("Identificador: ");
        JTextField getId = new JTextField();

        String country[]={"Producto","Cliente","Venta"};
        JComboBox cb=new JComboBox(country);
        cb.setBounds(50, 50,90,20);
        panel.add(info);
        panel.add(cb);
        panel.add(id);
        panel.add(getId);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int id_to_delete = Integer.parseInt(getId.getText());
               int selectted_option=-1;
                String option = (String) cb.getItemAt(cb.getSelectedIndex());
                switch (option)
                {
                    case "Producto":
                        selectted_option=1;
                        break;
                    case "Cliente":
                    selectted_option=2;
                    break;
                    case "Venta":
                        selectted_option=3;
                        break;
                }

                if(db.DeleteData(selectted_option,id_to_delete))
                {
                    JOptionPane.showMessageDialog(null, (option +"eliminado"));
                    UpdateFrame(selectted_option);
                    newEntry.setVisible(true);

                    getId.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error al eliminar" + option);
                }



            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }



    private void ProductCons(String consult) {
        JFrame newEntry = new JFrame("Consulta de Productos");
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();
        JLabel info = new JLabel("Seleccione numero de resultados");
        String num_of_results[]={"1","5","10"};
        JComboBox cb=new JComboBox(num_of_results);
        cb.setBounds(50, 50,90,20);
        panel.add(info);
        panel.add(cb);



        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //execute query
                int numofresults = Integer.parseInt((String) cb.getItemAt(cb.getSelectedIndex()));
                switch(consult){
                    case "Precio mas alto":
                       UpdateFrameCons(1,numofresults);
                       newEntry.dispose();
                        break;
                    case "Precio mas bajo":
                    UpdateFrameCons(2,numofresults);
                        newEntry.dispose();

                        break;
                    case "Mas vendido":
                        UpdateFrameCons(3,numofresults);
                        newEntry.dispose();

                        break;
                    case "Menos vendido":
                            UpdateFrameCons(4,numofresults);
                            newEntry.dispose();
                }

            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }


    private void ClientAndSaleCons(String consult) {
        JFrame newEntry = new JFrame("Consulta "+consult);
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancelar");
        buttons.add(ok);
        buttons.add(cancel);
        panel = new JPanel();
        JLabel info = new JLabel("Seleccione numero de resultados");
        String num_of_results[]={"1","5","10"};
        JComboBox cb=new JComboBox(num_of_results);
        cb.setBounds(50, 50,90,20);
        JLabel id = new JLabel("Identificador / Año");
        JTextField getId = new JTextField();

        JLabel year1 = new JLabel("Año 1");
        JTextField getYear1 = new JTextField("");
        JLabel    year2 = new JLabel("Año 2");
        JTextField getYear2 = new JTextField("");

        if(consult!="Comparar compras de años")
        {
            panel.add(info);
            panel.add(cb);
            panel.add(id);
            panel.add(getId);
        }
        else
        {
            panel.add(year1);
            panel.add(getYear1);
            panel.add(year2);
            panel.add(getYear2);
        }

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //execute query
                String id_or_date = getId.getText();
                String compareYears = getYear1.getText() + "=SEPARATE=" + getYear2.getText();
                int numofresults = Integer.parseInt((String) cb.getItemAt(cb.getSelectedIndex()));
                switch(consult){
                    case "Clientes de un año":

                        UpdateFrameClientAndSaleCons(1,id_or_date,numofresults);
                        newEntry.dispose();
                        break;
                    case "Cliente mayor compras":
                        UpdateFrameClientAndSaleCons(2,id_or_date,numofresults);
                        newEntry.dispose();

                        break;
                    case "Cliente menor compras":
                        UpdateFrameClientAndSaleCons(3,id_or_date,numofresults);
                        newEntry.dispose();

                        break;
                    case "Ultima compra de cliente":
                        UpdateFrameClientAndSaleCons(4,id_or_date,numofresults);
                        newEntry.dispose();
                        break;
                    case "compras de un año concreto":
                        UpdateFrameClientAndSaleCons(5,id_or_date,numofresults);
                        newEntry.dispose();
                        break;

                    case "Comparar compras de años":
                        id_or_date = compareYears;
                        UpdateFrameClientAndSaleCons(6,id_or_date,numofresults);
                        newEntry.dispose();
                        break;
                }

            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntry.dispose();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newEntry.add(panel, BorderLayout.NORTH);
        newEntry.add(buttons, BorderLayout.SOUTH);
        newEntry.pack();
        newEntry.setLocationRelativeTo(null);
        newEntry.setVisible(true);
    }





    private JScrollPane GetData(int option) {
        JTable DataOutputTable;
        JScrollPane scrollPane;
        DataOutputTable = db.GetTable(option);
        scrollPane = new JScrollPane(DataOutputTable);
        DataOutputTable.setFillsViewportHeight(true);
        return scrollPane;
    }



    private JScrollPane GetProductCons(int option,int numofresults) {
        JTable DataOutputTable;
        JScrollPane scrollPane;
        DataOutputTable = db.GetProductCons(option,numofresults);
        scrollPane = new JScrollPane(DataOutputTable);
        DataOutputTable.setFillsViewportHeight(true);
        return scrollPane;
    }
    private void UpdateFrameCons(int option,int numofresults){
        mainFrame.remove(DataTable);
        DataTable = null;
        DataTable = GetProductCons(option,numofresults);
        mainFrame.add(DataTable);
        mainFrame.repaint();
        mainFrame.setVisible(true);

    }


    private JScrollPane GetClientAndSaleCons(int option,String id,int numofresults) {
        JTable DataOutputTable;
        JScrollPane scrollPane;
        DataOutputTable = db.GetClientAndSaleCons(option,id,numofresults);
        scrollPane = new JScrollPane(DataOutputTable);
        DataOutputTable.setFillsViewportHeight(true);
        return scrollPane;
    }
    private void UpdateFrameClientAndSaleCons(int option,String id , int numofresults){
        mainFrame.remove(DataTable);
        DataTable = null;
        DataTable = GetClientAndSaleCons(option,id,numofresults);
        mainFrame.add(DataTable);
        mainFrame.repaint();
        mainFrame.setVisible(true);

    }

    private void UpdateFrame(int option){
        mainFrame.remove(DataTable);
        DataTable = null;
        DataTable = GetData(option);
        mainFrame.add(DataTable);
        mainFrame.repaint();
        mainFrame.setVisible(true);

    }
}


