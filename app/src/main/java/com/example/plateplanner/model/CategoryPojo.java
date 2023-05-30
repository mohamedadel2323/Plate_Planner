package com.example.plateplanner.model;

public class CategoryPojo {
    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;


    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    public void setStrCategoryDescription(String strCategoryDescription) {
        this.strCategoryDescription = strCategoryDescription;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    @Override
    public String toString() {
        return "CategoryPojo{" +
                "idCategory='" + idCategory + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                ", strCategoryDescription='" + strCategoryDescription + '\'' +
                '}';
    }
}
