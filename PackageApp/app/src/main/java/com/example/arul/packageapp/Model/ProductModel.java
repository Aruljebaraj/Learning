package com.example.arul.packageapp.Model;

import java.util.ArrayList;

/**
 * Created by AB0019 on 12-03-2018.
 */

public class ProductModel {
    String ProductName;
    String PluCode;
    String Measurement;
    String Size;
    String Weight;
    String Description;
    String SellingPrice;
    public static String SubItemCount;
    String CategoryCode;
    String CategoryName;
    String CategoryId;
    String SubCategory;
    public String ProductId;
    String ProductVariant;
    String CustomerId;
    public String ProductQuantity;
    Double ProductCost;
    int CategoryImage;
    int ProductImage;
    String ItemStatus;
    public String Status = "";


    public String Bagcount = "1";
    static ArrayList<ProductModel> productlist = new ArrayList<>();
    private static ProductModel ourInstance;

    public ProductModel() {

    }

    public static int Addproduct(String ProductQuantity, String Name, String Variant, Double Cost, String CategoryId, String SubcategoryId, String ItemId, String CustomerId) {
        ourInstance = new ProductModel();
        ourInstance.setProductQuantity(ProductQuantity);
        ourInstance.setProductName(Name);
        ourInstance.setProductVariant(Variant);
        ourInstance.setProductCost(Cost);
        ourInstance.setCategoryId(CategoryId);
        ourInstance.setSubCategory(SubcategoryId);
        ourInstance.setProductId(ItemId);
        ourInstance.setCustomerId(CustomerId);
//        products.ProductQuantity = String.valueOf(2);
        if (productlist == null) {
            productlist = new ArrayList<>();
        }
        productlist.add(ourInstance);
        int count = productlist.size();

        return count;
    }

    public static void UpdateQuantity(int position, String ItemId, String Quantity) {
//        int pos = position - 1;
//        ProductModel productModel = productlist.get(pos);
//        productModel.setProductQuantity(Quantity);
//        productlist.set(pos, productModel);

        for (int i = 0; i < productlist.size(); i++) {
            if (productlist.get(i).getProductId().equals(ItemId)) {
                productlist.get(i).setProductQuantity(Quantity);
//                productlist.set(pos, productModel);
            }
        }

    }


    public static ArrayList<ProductModel> getproductlist() {
        return productlist;
    }

    public static void ResetList() {
        productlist = new ArrayList<>();
    }


    public String getSubItemCount() {
        return SubItemCount;
    }

    public static void setSubItemCount(String subItemCount) {
        SubItemCount = subItemCount;
    }


    public static ProductModel getInstance() {
        return ourInstance;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPluCode() {
        return PluCode;
    }

    public void setPluCode(String pluCode) {
        PluCode = pluCode;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public ProductModel(String customerId, String categoryCode, String categoryName, String categoryId, String subCategory, String productId,
                        String productVariant, Double productCost, int categoryImage, int productImage, String productName, String pluCode,
                        String measurement, String size, String weight, String description, String sellingPrice, String subItemCount, String productQuantity, String status) {
        CustomerId = customerId;
        ProductName = productName;
        PluCode = pluCode;
        Measurement = measurement;
        Size = size;
        Weight = weight;
        SubItemCount = subItemCount;

        Description = description;
        SellingPrice = sellingPrice;
        CategoryCode = categoryCode;
        CategoryName = categoryName;
        CategoryId = categoryId;
        SubCategory = subCategory;
        ProductId = productId;
        ProductVariant = productVariant;
        ProductCost = productCost;
        CategoryImage = categoryImage;
        ProductImage = productImage;
        ProductQuantity = productQuantity;
        Status = status;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductVariant() {
        return ProductVariant;
    }

    public void setProductVariant(String productVariant) {
        ProductVariant = productVariant;
    }

    public Double getProductCost() {
        return ProductCost;
    }

    public void setProductCost(Double productCost) {
        ProductCost = productCost;
    }

    public int getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        CategoryImage = categoryImage;
    }

    public int getProductImage() {
        return ProductImage;
    }

    public void setProductImage(int productImage) {
        ProductImage = productImage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBagcount() {
        return Bagcount;
    }

    public void setBagcount(String bagcount) {
        Bagcount = bagcount;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String itemStatus) {
        ItemStatus = itemStatus;
    }
}


