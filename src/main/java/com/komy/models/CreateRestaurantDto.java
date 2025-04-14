package com.komy.models;

import org.jetbrains.annotations.Nullable;

public class CreateRestaurantDto {
    public String Name;
    public String Description;
    public String Category;
    public Boolean HasDelivery;
    @Nullable
    public String ContactEmail;
    @Nullable
    public String ContactNumber;
    @Nullable
    public String City;
    @Nullable
    public String Street;
    @Nullable
    public String PostalCode;

    public CreateRestaurantDto(@Nullable String postalCode, @Nullable String street, @Nullable String city, @Nullable String contactNumber, @Nullable String contactEmail, Boolean hasDelivery, String category, String description, String name) {
        PostalCode = postalCode;
        Street = street;
        City = city;
        ContactNumber = contactNumber;
        ContactEmail = contactEmail;
        HasDelivery = hasDelivery;
        Category = category;
        Description = description;
        Name = name;
    }
}
