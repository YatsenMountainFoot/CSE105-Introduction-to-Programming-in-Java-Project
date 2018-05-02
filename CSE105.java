/*
Student Name: Zheng Sun
Student ID: 1507820
 */
package cse105;
import java.util.*;
import java.text.*;
import java.io.*;
public class CSE105 {

    static Scanner input = new Scanner(System.in);
    static boolean openfileFlag = false;
    public static void main(String[] args) {
        ArrayList<Car> listOfDeals = new ArrayList<Car>();
        
        listOfDeals = readCarsInfoToArrayList();//load history objects to the Array List
        
        controlPannel(listOfDeals);
        
        deleteAndRefactorObjectFile(listOfDeals,"CarInfo.text");//refactor the text file

    }
    
    //method for the user to manpulate the car deals on the basis of a history ArrayList
    public static void controlPannel(ArrayList<Car> x){
    //keep excuting control pannel until exits
    ArrayList<Car> listForSale = new ArrayList<Car>();
    ArrayList<Car> listOfSales = new ArrayList<Car>();
    boolean exit =false;
    String select = "";
    while(exit != true){
    System.out.println("0 = exit, 1 = purchaseCar, 2=sellCar, 3=changeCarInfo, 4=InventoryList, 5= salesReport");
        select = input.nextLine();
         switch(select){
            case "0":
                exit = true;
                break;
            case "1":
                listForSale = selectCarForSale(x);
                System.out.println("The garage can accomodate 20 cars in total. There are currently " + Car.numOfCarInGarage + " cars in garage.");
                if(listForSale.size() < 20){
                    x.add(dataEntryNewCar());
                    sortByPurchaseDate(x);}
                else
                    System.out.println("Sorry, the garage can only accomodate 20 cars, please sell some car to purchase.");
                break;
            case "2":
                listForSale = selectCarForSale(x);
            printCarList(listForSale);
                if(listForSale.size()> 0)
                    sellCarWithID(x);
                else
                    System.out.println("Sorry, the garage is empty, please purchase some car to sell.");
                break;
            case "3":
                listForSale = selectCarForSale(x);
                listOfSales = selectCarSold(x);
                printCarList(listForSale);
                printCarSalesReport(listOfSales);
                changeCarInfoWithID(x);
                ;
                break;
            case "4":
                listForSale = selectCarForSale(x);
                System.out.println("The garage can accomodate 20 cars in total. There are currently " + Car.numOfCarInGarage + " cars in garage.");
                printCarList(listForSale);
                break;
            case "5":
                listOfSales = selectCarSold(x);
                printCarSalesReport(listOfSales);
                
                break;
                    
            default:
                System.out.println("Sorry, invalid option.");
        }
    }
    }

    //method to read a list of cars from a text file and stored in an ArrayList of Car Objects
    public static ArrayList<Car> readCarsInfoToArrayList(){
        ArrayList<Car> x = new ArrayList<Car>();
        File file = new File("CarInfo.text");
        String line;
        double purchasePrice, salePrice;
        String description;
        Date purchaseDate, saleDate;
        boolean isSold;
        System.out.println("Loading history account......");
        try{
            BufferedReader fr = new BufferedReader(new FileReader(file));
        
            while((line = fr.readLine()) != null){
                String[] s = line.split(",");
                purchaseDate = new Date(Long.parseLong(s[1]));
                purchasePrice = Double.parseDouble(s[2]);
                saleDate = new Date(Long.parseLong(s[3]));
                salePrice = Double.parseDouble(s[4]);
                isSold = Boolean.parseBoolean(s[5]);
                description = s[6];
                x.add(new Car(purchaseDate,purchasePrice,saleDate,salePrice,isSold, description));
            }
            fr.close();
            openfileFlag = true;
            System.out.println("Loading finished");
        }catch(Exception e){
            System.out.println("Loading failed");
        }
        sortByPurchaseDate(x);
        return x;//selectCar
    }
    
    //method to select the sold cars from an ArrayList
    public static ArrayList<Car> selectCarSold(ArrayList<Car> x){
        ArrayList<Car> listofSales = new ArrayList<Car>();
        for(Car fec: x){
            if(fec.isSold())
            listofSales.add(fec);
        }
        sortByPurchaseDate(listofSales);
        
        return listofSales;
    }
    
    //method to select the cars for sale from an ArrayList
    public static ArrayList<Car> selectCarForSale(ArrayList<Car> x){
        ArrayList<Car> listForSales = new ArrayList<Car>();
        for(Car fec: x){
            if(!fec.isSold())
            listForSales.add(fec);
        }
        sortByPurchaseDate(listForSales);
        
        return listForSales;
    }
    
    //Sort an arraylist of Car objects by purchase date
    public static ArrayList<Car> sortByPurchaseDate(ArrayList<Car> x){
        Car[] cA = new Car[x.size()];
        x.toArray(cA);
        for(int j = cA.length - 1;j > 0;j--){
            for(int i = 0 ;i < cA.length - 1;i++)
                if(cA[i].getDateOfPurchase().getTime() > cA[i + 1].getDateOfPurchase().getTime()){
                    Car dummyCar;//sort date
                    int dummyID;
                    dummyCar = cA[i];
                    cA[i]= cA[i + 1];
                    cA[i + 1] = dummyCar;
                    dummyID =cA[i].getCarID();
                    cA[i].setCarID(cA[i + 1].getCarID());
                    cA[i + 1].setCarID(dummyID);        
            }
        }
        for(int i=0;i < x.size();i++){
            x.set(i,cA[i]);
        }
        return x;
    }
    
    //method to purchase a car. Create a new Car objrct and note the purchase date and price.
    public static Car dataEntryNewCar(){
    System.out.println("Enter the details of the car:");
    System.out.println("Enter date of purchase(d/M/y):");
    Date dop = inputDate();
    System.out.println("Enter purchase price:");
    double pp = inputDouble();
    System.out.println("Enter the description:");
    String ds = input.nextLine();
    Car c = new Car(dop, pp, ds);
    return c;
    }
    
    //method to sell a new car with specific ID and note the sold date and price.
    public static void sellCarWithID(ArrayList<Car> x){
        System.out.println("Please Enter the ID for sale:");
        int y = inputInteger();
        boolean setFlag = false;
        for(Car fec: x){
            if(fec.getCarID() == y && !fec.isSold()){
            System.out.println("Please Enter the Date of Sale(d/M/y):");
            fec.setDateOfSale(inputDate());
            System.out.println("Please Enter the Sale Price:");
            fec.setSalePrice(inputDouble());
            System.out.println("Enter the description:");
            fec.setDescription(input.nextLine()); 
            setFlag = true;
            fec.setSold(true);
            
            }
        }
        if(setFlag ==false)
            System.out.println("Sorry, the car is not in garage");
        
    }
    
    //method to change the status of an existed Car with specific ID
    public static void changeCarInfoWithID(ArrayList<Car> x){
        System.out.println("Please Enter the ID from the list to change:");
        int y = inputInteger();
        boolean setFlag = false;
        for(Car fec: x){
            if(fec.getCarID() == y && fec.isSold()){
            System.out.println("Please Enter the Date of Purchase(d/M/y):");
            fec.setDateOfPurchase(inputDate());
            System.out.println("Please Enter the Purchase Price:");
            fec.setPurchasePrice(inputDouble());
            System.out.println("Please Enter the Date of Sale(d/M/y):");
            fec.setDateOfSale(inputDate());
            System.out.println("Please Enter the Sale Price:");
            fec.setSalePrice(inputDouble());
            System.out.println("Enter the description:");
            fec.setDescription(input.nextLine());
            setFlag = true;
            }
            else if (fec.getCarID() == y && !fec.isSold()){
            System.out.println("Please Enter the Date of Purchase(d/M/y):");
            fec.setDateOfPurchase(inputDate());
            System.out.println("Please Enter the Purchase Price:");
            fec.setPurchasePrice(inputDouble());
            System.out.println("Enter the description:");
            fec.setDescription(input.nextLine());
            setFlag = true;
            }
        }
        if(setFlag ==false)
            System.out.println("Sorry, the car is not on record");
        sortByPurchaseDate(x);
    }
    
    //method to display a list of all cars for sale in an arraylist of Car objects
    public static void printCarList(ArrayList<Car> x){
        sortByPurchaseDate(x);
        System.out.println("Here are the cars for sale:");
        System.out.println("carID\tdateOfPurchase\tpurchasePrice\tDescription");
        double currentPurchaseExpenditure = 0;
        for(Car fec: x){
        fec.printListFormat();
        currentPurchaseExpenditure += fec.getPurchasePrice();
        }
        System.out.println("Current spending on unsold cars:" + currentPurchaseExpenditure);
    }
    
    //method to display a sales report showing all car sales
    public static void printCarSalesReport(ArrayList<Car> x){
        sortByPurchaseDate(x);
        System.out.println("All car sales:");
        System.out.println("carID\tdateOfPurchase\tpurchasePrice\tdateOfSale\tsalePrice\tprofitOfSale\tDescription");
        double totalProfit = 0;
        for(Car fec: x){
        fec.printReportFormat();
        totalProfit += fec.getProfitOfSale();
        }
        System.out.println("Total profit: " + totalProfit);
    }
    
    //method to delete original info file and refactor Cars info file
    public static void deleteAndRefactorObjectFile(ArrayList<Car> x, String y){
        File dFile = new File("CarInfo.text");//delete original file before writing in
        if(openfileFlag){
        try{
        dFile.delete(); //Try to de for refactor
        System.out.println("ready for refactor...");
        }catch(Exception e){     
        System.out.println("deletion failed");
        }   
        }
        for(Car fec: x){
            String a = fec.toText();
            File file = new File(y);//writing to the new file
            try{
            BufferedWriter fw = new BufferedWriter(new FileWriter(file, true));
            fw.write(a);//Write String contents to file
            fw.newLine();//write CR character to file
            fw.flush();
            fw.close();//close the file
            System.out.println("Item writen to the file");
            }catch(IOException e){
            System.out.println("Error occured when writing to file");
            }
        }
    
    };
    
    //method to check if a Date data type is inputed by the user; format: d/M/y
    public static Date inputDate(){
        boolean dataValidationFlag = false;
        Date date = new Date();
        do{
        String s = input.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/y");
        try{
            date = dateFormat.parse(s);
            dataValidationFlag = true;
        }catch(Exception ex){
            System.out.println("Sorry, input is invalid. Enter the date(d/M/y):");
        }
        }while(dataValidationFlag == false);
        return date;
    }
    
    //method to check if a double data type is inputed by the user
    public static double inputDouble(){
        boolean dataValidationFlag = false;
        double dNum =0;
        do{
        try{
        String s = input.nextLine();
        dNum = Double.parseDouble(s);
        dataValidationFlag = true;
        }catch(Exception ex){
            System.out.println("Sorry, input is invalid. Enter the double type:");
        }
        }while(dataValidationFlag == false);
        return dNum;
    }
    
    //method to check if an integer is inputed by the user
    public static int inputInteger(){
        boolean dataValidationFlag = false;
        int dNum =0 ;
        do{
        try{
        String s = input.nextLine();
        dNum = Integer.parseInt(s);
        dataValidationFlag = true;
        }catch(Exception ex){
            System.out.println("Sorry, input is invalid. Enter the integer:");
        }
        }while(dataValidationFlag == false);
        return dNum;
    }
}
