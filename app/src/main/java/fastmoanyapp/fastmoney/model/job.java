package fastmoanyapp.fastmoney.model;

import android.animation.TimeInterpolator;

/**
 * Created by FTF-ANDREA on 23/11/2017.
 */

public class job {
    private String Id;
    private String Title;
    private String Description;
    private String JobType;
    private String Payment;
    private String PaymentType;
    private String Country;
    private String City;
    private String CreatedAt;
    public final TimeInterpolator interpolator;

    private int TypeLoad;

    public job(String id, String title, String description, String jobType, String payment, String paymentType, String country, String city, String createdAt, TimeInterpolator interpolator, int typeLoad) {
        this.Id           = id;
        this.Title        = title;
        this.Description  = description;
        this.JobType      = jobType;
        this.Payment      = payment;
        this.PaymentType  = paymentType;
        this.Country      = country;
        this.City         = city;
        this.CreatedAt    = createdAt;
        this.interpolator = interpolator;
        this.TypeLoad     = typeLoad;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getTypeLoad() {
        return TypeLoad;
    }

    public void setTypeLoad(int typeLoad) {
        TypeLoad = typeLoad;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }
}
