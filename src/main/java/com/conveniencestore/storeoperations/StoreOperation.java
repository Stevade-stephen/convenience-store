package com.conveniencestore.storeoperations;

import com.conveniencestore.models.*;

public interface StoreOperation {
    void hireCashier(Applicant applicant, Staff staff, Store store);
    void addProductsToStore(Product product, Store store, int quantity, Staff staff);
    String sellProducts(Store store, Customer customer, Staff staff);
}
