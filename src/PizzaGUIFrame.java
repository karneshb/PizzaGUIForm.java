import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.BorderFactory.*;
import javax.swing.JOptionPane;

public class PizzaGUIFrame extends JFrame{
    String size [] = new String[4];
    JPanel mainPanel, topPanel, middlePanel, bottomPanel;
    JPanel topPanel1, topPanel2, topPanel3;
    JRadioButton radThin, radRegular, radDeepDish;
    ButtonGroup radioGroup;
    JComboBox cmbSize;
    JCheckBox chkPepperoni, chkSausage, chkGreenPepper, chkBananaPepper, chkTomato, chkPineapple, chkExtraCheese;
    JTextArea txtReceipt;
    JScrollPane receiptScroll;
    JButton btnOrder, btnClear, btnQuit;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screen = kit.getScreenSize();
    int screenHeight = screen.height;
    int screenWidth = screen.width * 3/4;

    public PizzaGUIFrame(){
        super("Pizza Order Form");
         mainPanel = new JPanel();
         mainPanel.setLayout(new BorderLayout());

         loadSizes();
         buildForm();

         setSize(screenWidth /2, screenHeight / 2);
         int x = (screen.width - this.getWidth())/2;
         int y = (screen.height - this.getHeight())/2;
         setLocation(x,y);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadSizes(){
        size[0] = "Small";
        size[1] = "Medium";
        size[2] = "Large";
        size[3] = "Super";
    }

    private void buildForm(){
        createTopPanel();
        createMiddlePanel();
        createBottomPanel();
        add(mainPanel);
    }

    private void createTopPanel(){
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        topPanel1 = new JPanel();
        topPanel2 = new JPanel();
        topPanel3 = new JPanel();

        topPanel1.setLayout(new BorderLayout());
        topPanel3.setLayout(new GridLayout(3,2));

        topPanel1.setBorder(createDashedBorder(Color.BLACK, 5, 5, 5, true));
        topPanel3.setBorder(createDashedBorder(Color.BLUE, 5, 5, 5, true));

        radThin = new JRadioButton("Thin Crust");
        radRegular = new JRadioButton("Regular Crust");
        radDeepDish = new JRadioButton("Deep Dish Crust");

        cmbSize = new JComboBox(size);

        chkPepperoni = new JCheckBox("Pepperoni");
        chkGreenPepper = new JCheckBox("Green Pepper");
        chkBananaPepper = new JCheckBox("Banana Pepper");
        chkTomato = new JCheckBox("Tomato");
        chkPineapple = new JCheckBox("Pineapple");
        chkExtraCheese = new JCheckBox("Extra Cheese");

        radThin.setSelected(true);

        radioGroup = new ButtonGroup();
        radioGroup.add(radThin);
        radioGroup.add(radRegular);
        radioGroup.add(radDeepDish);

        topPanel1.add(radThin, BorderLayout.NORTH);
        topPanel1.add(radRegular, BorderLayout.CENTER);
        topPanel1.add(radDeepDish, BorderLayout.SOUTH);
        topPanel.add(topPanel1, BorderLayout.WEST);

        topPanel2.add(cmbSize);
        topPanel.add(topPanel2, BorderLayout.CENTER);

        topPanel3.add(chkPepperoni);
        topPanel3.add(chkGreenPepper);
        topPanel3.add(chkBananaPepper);
        topPanel3.add(chkTomato);
        topPanel3.add(chkPineapple);
        topPanel3.add(chkExtraCheese);
        topPanel.add(topPanel3, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void createMiddlePanel(){
        middlePanel = new JPanel();

        txtReceipt = new JTextArea(15, 50);
        txtReceipt.setEditable(false);

        receiptScroll = new JScrollPane(txtReceipt);
        middlePanel.add(receiptScroll);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }

    private void createBottomPanel(){
        bottomPanel = new JPanel();

        btnOrder = new JButton("Order");
        btnClear = new JButton("Clear");
        btnQuit = new JButton("Quit");

        btnOrder.setSize(50, 10);
        btnClear.setSize(50, 10);
        btnQuit.setSize(50, 10);

        btnOrder.addActionListener((ActionEvent ae) -> {orderPizza();});
        btnClear.addActionListener((ActionEvent ae) -> {clearForm();});
        btnQuit.addActionListener((ActionEvent ae) -> {quitProgram();});

        bottomPanel.add(btnOrder);
        bottomPanel.add(btnClear);
        bottomPanel.add(btnQuit);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void quitProgram(){
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Program",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    private void clearForm() {
        resetRadioButtons();
        resetCheckBoxes();
        resetComboBox();
        resetTxtArea();
    }

    private void resetRadioButtons(){
        radThin.setSelected(true);
        radRegular.setSelected(false);
        radDeepDish.setSelected(false);
    }

    private void resetComboBox(){
        cmbSize.setSelectedIndex(0);
    }

    private void resetCheckBoxes(){
        chkPepperoni.setSelected(false);
        chkGreenPepper.setSelected(false);
        chkBananaPepper.setSelected(false);
        chkTomato.setSelected(false);
        chkPineapple.setSelected(false);
        chkExtraCheese.setSelected(false);
    }

    private void resetTxtArea(){
        txtReceipt.setText("");
    }

    private void orderPizza(){
        String crust = "";
        String size = "";
        double cost = 0.0;
        double subtotal = 0.0;
        ArrayList<String> toppings = new ArrayList<>();

        crust = whichCrust();
        size = whichSize();
        cost = whatCost(size);
        toppings = saveIngredients();
        subtotal = calcSubTotal(cost, toppings.size());

        txtReceipt.append("======================================================== \n");
        txtReceipt.append(String.format("%s, %s \t\t $%-6.2f %n", size, crust, cost));
        for(int count = 0; count < toppings.size(); count++){
            txtReceipt.append(String.format("%s \t\t\t $1.00 %n", toppings.get(count)));
        }
        txtReceipt.append(String.format("\n\nSub-total: \t\t\t $%-6.2f\n", subtotal));
        txtReceipt.append("--------------------------------------------------------------------------------------------------\n");
        txtReceipt.append(String.format("Total: \t\t\t $%-6.2f %n", subtotal * 1.07));
    }

    private String whichCrust(){
        String crust = "";
        if(radThin.isSelected()){
            crust = "Thin Crust";
        } else if(radRegular.isSelected()){
            crust = "Regular Crust";
        } else if(radDeepDish.isSelected()){
            crust = "Deep Dish Crust";
        }
        return crust;
    }

    private String whichSize(){
        String size = "";
        size = (String)cmbSize.getSelectedItem();
        return size;
    }

    private double whatCost(String size){
        double cost = 0.0;
        switch(size){
            case "Small":
                cost = 8.00;
                break;
            case "Medium":
                cost = 12.00;
                break;
            case "Large":
                cost = 16.00;
                break;
            case "Super":
                cost = 20.00;
                break;
        }
        return cost;
    }

    private ArrayList<String> saveIngredients(){
        ArrayList<String> toppings = new ArrayList<>();
        if(chkPepperoni.isSelected()){
            toppings.add("Pepperoni");
        }
        if(chkGreenPepper.isSelected()){
            toppings.add("Green Pepper");
        }
        if(chkBananaPepper.isSelected()){
            toppings.add("Banana Pepper");
        }
        if(chkTomato.isSelected()){
            toppings.add("Tomato");
        }
        if(chkPineapple.isSelected()){
            toppings.add("Pineapple");
        }
        if(chkExtraCheese.isSelected()){
            toppings.add("Extra Cheese");
        }
        return toppings;
    }

    private double calcSubTotal(double cost, int toppings){
        double subtotal = 0.0;
        subtotal = cost + toppings;
        return subtotal;
    }
}
