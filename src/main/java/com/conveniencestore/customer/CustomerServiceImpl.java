package com.conveniencestore.customer;

import com.conveniencestore.models.Customer;
import com.conveniencestore.models.Product;
import com.conveniencestore.models.Store;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public void addProductsToCart(Customer customer, Store store, Product product, int quantity) {
        if (store.getProducts().containsKey(product)) {
            if (store.getProducts().get(product) >= quantity) {
                customer.getCart().getProductsInCart().put(product, quantity);
            } else {
                System.out.println("We do not have " + quantity + " piece(s) of the product in our store");
            }
        } else {
            System.out.println("Product " + product.getProductName() + " is not available in the this store");
        }
    }

    @Override
    public String fundWallet(Customer customer, double amount) {
        double newBalance = customer.getWallet().getBalance() + amount;
        customer.getWallet().setBalance(newBalance);
        return "Successfully added " + amount + " to wallet. Your new balance is " + customer.getWallet().getBalance();
    }
}
