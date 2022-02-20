package com.conveniencestore.models;

import com.conveniencestore.application.Application;
import com.conveniencestore.application.ApplicationImpl;
import com.conveniencestore.customer.CustomerService;
import com.conveniencestore.customer.CustomerServiceImpl;
import com.conveniencestore.enums.Gender;
import com.conveniencestore.enums.Qualification;
import com.conveniencestore.enums.Role;
import com.conveniencestore.storeoperations.StoreOperation;
import com.conveniencestore.storeoperations.StoreOperationImpl;


public class Main {
    public static void main(String[] args) {
        Store sdtStores = new Store("sdt stores");

        Staff sola = new Staff("Sola", "Smith", "Tech Park, Edo", Gender.MALE, Role.MANAGER);
        Applicant jane = new Applicant("Jane", "Emeka", "No 10 Adejo str, Lagos", Gender.FEMALE, Qualification.OND);
        Customer samuel = new Customer("Samuel", "Adjei", "brooks street uyo", Gender.MALE);

        Product bread = new Product("1001", "Agege bread", 500.00);
        Product belt = new Product("1002", "Belt", 500.00);
        Product bag = new Product("1003", "Bag", 500.00);

        Application application = new ApplicationImpl();
        application.apply(jane, sdtStores);

        StoreOperation storeOperation = new StoreOperationImpl();
        storeOperation.hireCashier(jane, sola, sdtStores);
        storeOperation.addProductsToStore(bread, sdtStores, 3, sola);
        storeOperation.addProductsToStore(belt, sdtStores, 3, sola);
        storeOperation.addProductsToStore(bag, sdtStores, 3, sola);

        Staff cashierJane = sdtStores.getStaffs().get(0);

        CustomerService customerMenu = new CustomerServiceImpl();
        customerMenu.fundWallet(samuel, 12000.00);
        customerMenu.addProductsToCart(samuel, sdtStores, bread, 2);
        customerMenu.addProductsToCart(samuel, sdtStores, belt, 2);
        customerMenu.addProductsToCart(samuel, sdtStores, bag, 2);

        String receipt = storeOperation.sellProducts(sdtStores, samuel, cashierJane);

        customerMenu.addProductsToCart(samuel, sdtStores, bag, 1);
        customerMenu.addProductsToCart(samuel, sdtStores, belt, 1);

        String receiptTwo = storeOperation.sellProducts(sdtStores, samuel, cashierJane);


        System.out.println(receipt);
        System.out.println(receiptTwo);

        System.out.println(System.getProperty("user.dir"));

    }
}
