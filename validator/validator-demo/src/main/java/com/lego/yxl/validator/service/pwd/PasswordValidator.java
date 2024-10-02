package com.lego.yxl.validator.service.pwd;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordConsistency, Password> {

    @Override
    public boolean isValid(Password password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            return true;
        }
        if (password.getInput1() == null) {
            return true;
        }
        return password.getInput1().equals(password.getInput2());
    }
}
