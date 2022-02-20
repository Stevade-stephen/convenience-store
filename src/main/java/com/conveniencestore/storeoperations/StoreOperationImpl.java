package com.conveniencestore.storeoperations;

import com.conveniencestore.enums.Role;
import com.conveniencestore.exception.*;
import com.conveniencestore.models.*;

import java.util.Map;

import static com.conveniencestore.enums.Qualification.*;

public class StoreOperationImpl implements StoreOperation {
    @Override
    public void hireCashier(Applicant applicant, Staff staff, Store store) {
        if (staff.getRole().equals(Role.MANAGER)) {
            if (store.getApplicants().contains(applicant)) {
                if (applicant.getQualification().equals(OND)) {
                    Staff newStaff = new Staff(
                            applicant.getFirstName(),
                            applicant.getLastName(),
                            applicant.getAddress(),
                            applicant.getGender(),
                            Role.CASHIER
                    );
                    store.getStaffs().add(newStaff);
                    store.getApplicants().remove(applicant);
                    System.out.println("Congratulations, " + applicant.getFirstName() + " you have been hired!");

                } else {
                    throw new ApplicantNotQualifiedException("You must have an " + OND + " to be eligible for this position");
                }
            } else {
                throw new ApplicantNotAppliedException(applicant.getFirstName() + " did not apply for this role");
            }
        } else {
            throw new StaffNotAuthorizedException("You are not authorized to hire an applicant");
        }
    }

    @Override
    public void addProductsToStore(Product product, Store store, int quantity, Staff staff) {
        if (staff.getRole().equals(Role.MANAGER)) {
            if (store.getProducts().containsKey(product)) {
                int numberOfProductPresent = store.getProducts().get(product);
                store.getProducts().put(product, numberOfProductPresent + quantity);
                System.out.println("Quantity for product " + product.getProductName() + " successfully updated");
            } else {
                store.getProducts().put(product, quantity);
                System.out.println("Product " + product.getProductName() + " successfully added");
            }
        } else {
            throw new StaffNotAuthorizedException("You are not authorized tp perform this operation");
        }
    }

    @Override
    public String sellProducts(Store store, Customer customer, Staff staff) {
        if (staff.getRole().equals(Role.CASHIER)) {
            double totalCostOfProductsInCart = customer.getCart().getTotalCostOfProductsInCart();
            Map<Product, Integer> productsInCart = customer.getCart().getProductsInCart();
            for (Map.Entry<Product, Integer> products : productsInCart.entrySet()) {
                Product product = products.getKey();
                Integer quantity = products.getValue();
                totalCostOfProductsInCart += getTotalCostOfProductsInCart(store, product, quantity);
            }

            if (customer.getWallet().getBalance() >= totalCostOfProductsInCart) {
                double newWalletBalance = customer.getWallet().getBalance() - totalCostOfProductsInCart;
                customer.getWallet().setBalance(newWalletBalance);
                String receipt = generateReceipt(store, customer, totalCostOfProductsInCart, staff);
                customer.getCart().setTotalCostOfProductsInCart(0);
                customer.getCart().getProductsInCart().clear();
                return receipt;
            } else {
                throw new InSufficientFundsException("Your balance is not enough to perform this transaction");
            }
        } else {
            throw new StaffNotAuthorizedException("You are not authorized to sell products");
        }

    }

    private String generateReceipt(Store store, Customer customer, double totalCostOfProductsInCart, Staff staff) {
        StringBuilder productsBought = new StringBuilder();

        for (Product product : customer.getCart().getProductsInCart().keySet()) {
            productsBought.append(product.getProductName())
                    .append(", ")
                    .append("Price: ")
                    .append(product.getPrice())
                    .append(",")
                    .append(" Quantity bought: ")
                    .append(customer.getCart().getProductsInCart().get(product))
                    .append("\n");
        }

        return "\n" +
                "Payment Successful! \n" +
                "Receipt For : " + customer.getFirstName() + "\n" +
                "======================\n" +
                "Products bought : " + productsBought + "\n" +
                "======================\n" +
                "Total cost of Products bought : " + totalCostOfProductsInCart + "\n" +
                "======================\n" +
                "Processed by " + staff.getFirstName() + "\n" +
                "======================\n" +
                "Thank you for shopping with us at " + store.getName() + "!" + "\n";
    }

    private double getTotalCostOfProductsInCart(Store store, Product product, Integer quantity) {
        double totalCostOfProductsInCart;
        if (store.getProducts().get(product) >= quantity) {
            totalCostOfProductsInCart = product.getPrice() * quantity;
            store.getProducts().put(product, store.getProducts().get(product) - quantity);
        } else {
            throw new OutOfStockException("Product " + product.getProductName() + " is no longer available in the store");
        }
        return totalCostOfProductsInCart;
    }
}
