package com.trollmarket.validator;


import com.trollmarket.dto.account.RegisterDTO;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PasswordComparerValidator implements
        ConstraintValidator<PasswordComparer, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordComparer constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String pass = new BeanWrapperImpl(value).getPropertyValue(password).toString();
        String confirm = new BeanWrapperImpl(value).getPropertyValue(confirmPassword).toString();
        if(pass.isBlank() || confirm.isBlank()) {
            return true;
        }
        return pass.equals(confirm);
    }


}
