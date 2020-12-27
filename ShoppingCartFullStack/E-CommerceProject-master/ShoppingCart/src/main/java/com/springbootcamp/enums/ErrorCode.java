package com.springbootcamp.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public enum  ErrorCode {

    INVALID_PASSWORD("Password is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXITS("Email already exits with the given username",HttpStatus.CONFLICT),
    ACCOUNT_ALREADY_EXITS("Account already exits with  this email ",HttpStatus.CONFLICT),
    NO_USER_FOUND_WITH_GIVEN_ID("No account is found with the given Id",HttpStatus.NOT_FOUND),
    NO_USER_FOUND_WITH_GIVEN_EMAIL("No account is found with the given email",HttpStatus.NOT_FOUND),
    USER_IS_NOT_ACTIVE("User is not active",HttpStatus.BAD_REQUEST),
    USER_IS_ALREADY_ACTIVE("User is already active",HttpStatus.BAD_REQUEST),
    PASSWORD_DOES_NOT_MATCH("Password and confirm Password does not match",HttpStatus.BAD_REQUEST),
    STRONG_PASSWORD("Please enter a strong password with 8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character and 1 Number",HttpStatus.BAD_REQUEST),
    NULL_VALUES("Please enter all the mandatory details in all fields",HttpStatus.BAD_REQUEST),
    NO_ADDRESS_FOUND_WITH_GIVEN_ID("No address is found with the given id",HttpStatus.NOT_FOUND),
    NO_USER_IS_FOUND_WITH_GIVEN_ADDRESS_ID("No user is associated with the given address id",HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND_EXCEPTION("No file can befound with the given id",HttpStatus.NOT_FOUND),
    NO_CATEGORY_FOUND_WITH_GIVEN_ID("No category is found with the entered category Id",HttpStatus.NOT_FOUND),
    CATEGORY_ID_IS_NOT_OF_LEAF_NODE("The entered category Id is not of leaf node",HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_EXISTS("A product with throw new ECommerceException(ErrorCode.STRONG_PASSWORD); similar deatils already exists",HttpStatus.CONFLICT),
    VALIDATION_FAILS("Validation fails for the given product",HttpStatus.BAD_REQUEST),
    NO_PRODUCT_FOUND("No product is found with the given product id",HttpStatus.NOT_FOUND),
    PRODUCT_NOT_MATCHES_WITH_CREATOR("This product has not been registered from your account",HttpStatus.BAD_REQUEST),
    PRODUCT_IS_DELETED("Product is deleted",HttpStatus.NOT_FOUND),
    INVALID_QUANTITY("Entered quantity is not valid",HttpStatus.BAD_REQUEST),
    INVALID_PRICE("Entered price is not valid",HttpStatus.BAD_REQUEST),
    INVALID_IMAGE("Entered image is not valid",HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT("Entered product is not valid",HttpStatus.BAD_REQUEST),
    NO_PRODUCT_VARIATION_FOUND("No productVaariation is found with the given  id",HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_ACTIVE("The product associated with the entered product id is already active",HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_INACTIVE("The product associated with the entered product id is already inactive",HttpStatus.BAD_REQUEST),
    SELLER_NOT_FOUND("You are not authorized for adding this product",HttpStatus.BAD_REQUEST),
    METADATA_FIELD_ALREADY_EXISTS("Metadata field already exists",HttpStatus.CONFLICT),
    INVALID_TOKEN("entered token is invalid",HttpStatus.NOT_FOUND),
    INVALID_CONTACT_NUMBER("Please enter a valid contact number",HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("Please enter a valid email",HttpStatus.BAD_REQUEST),
    INVALID_COMPANY_NAME("Please enter a unique company Name",HttpStatus.BAD_REQUEST),
    INVALID_GST("Please enter a valid gstin",HttpStatus.BAD_REQUEST),
    UNIQUE_GST_NUMBER("Please enter a unique gst Number",HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED("Your account has been locked for multiple log in fail attempts",HttpStatus.BAD_REQUEST),
    INVALID_ACTIVATION_TOKEN("Please enter a valid activation token",HttpStatus.BAD_REQUEST),
    EXPIRED_ACTIVATION_TOKEN("Your activation token has been expired.A new has been generated and send to your mail.Please enter that.",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_FOUND("The entered token cannot be found",HttpStatus.BAD_REQUEST),
    INVALID_ZIPCODE("Please enter avlid zipCode",HttpStatus.BAD_REQUEST),
    INVALID_UPDATE("Please enter any one of the field to update",HttpStatus.BAD_REQUEST),
    INVALID_SELLER_ADDRESS("A seller can have only one assosiated address",HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_ID("Plese enter a vlaid address Id",HttpStatus.BAD_REQUEST),
    IMAGE_UPLOAD_FAILED("Image uploading failed.Please after some time",HttpStatus.BAD_REQUEST),
    PRESTORED_CATEGORY("Category already exists. So, can not insert at the root level",HttpStatus.BAD_REQUEST),
    REGISTERED_EMAIL("Please enter the registered email",HttpStatus.BAD_REQUEST);

    private String errorDescription;
    private HttpStatus status;

    ErrorCode(String errorDescription, HttpStatus status) {
        this.errorDescription = errorDescription;
        this.status = status;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
