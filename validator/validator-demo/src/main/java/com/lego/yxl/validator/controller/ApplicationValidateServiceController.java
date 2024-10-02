package com.lego.yxl.validator.controller;

import com.lego.yxl.validator.service.ApplicationValidateService;
import com.lego.yxl.validator.service.SingleForm;
import com.lego.yxl.validator.service.UserValidateForm;
import com.lego.yxl.validator.service.pwd.Password;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validator/app")
public class ApplicationValidateServiceController {

    @Autowired
    private ApplicationValidateService applicationValidateService;

    @RequestMapping("/singleValidate_error")
    public void singleValidate_error() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                applicationValidateService.singleValidate((Long) null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/singleValidate")
    public void singleValidate() {
        applicationValidateService.singleValidate(1L);
    }

    @RequestMapping("/testSingleValidate_error1")
    public void testSingleValidate_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                this.applicationValidateService.singleValidate((SingleForm) null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });

    }

    @RequestMapping("/testSingleValidate_error2")
    public void testSingleValidate_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                SingleForm singleForm = new SingleForm();
                this.applicationValidateService.singleValidate(singleForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/testSingleValidate_error3")
    public void testSingleValidate_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setId(1L);
                this.applicationValidateService.singleValidate(singleForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/testSingleValidate_error4")
    public void testSingleValidate_error4() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setName("");
                this.applicationValidateService.singleValidate(singleForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/testSingleValidate_error5")
    public void testSingleValidate_error5() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setName("name");
                this.applicationValidateService.singleValidate(singleForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/testSingleValidate")
    public void testSingleValidate() {
        SingleForm singleForm = new SingleForm();
        singleForm.setId(1L);
        singleForm.setName("name");
        this.applicationValidateService.singleValidate(singleForm);
    }

    @RequestMapping("/customSingleValidate_error1")
    public void customSingleValidate_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                this.applicationValidateService.customSingleValidate(null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/customSingleValidate_error2")
    public void customSingleValidate_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                Password password = new Password();
                this.applicationValidateService.customSingleValidate(password);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/customSingleValidate_error3")
    public void customSingleValidate_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                Password password = new Password();
                password.setInput1("123");
                password.setInput2("456");
                this.applicationValidateService.customSingleValidate(password);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/customSingleValidate_success1")
    public void customSingleValidate_success1() {
        Password password = new Password();
        password.setInput1("123");
        password.setInput2("123");
        this.applicationValidateService.customSingleValidate(password);
    }

    @RequestMapping("/validateForm_error1")
    public void validateForm_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                this.applicationValidateService.validateForm(null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error2")
    public void validateForm_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error3")
    public void validateForm_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName(null);
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error4")
    public void validateForm_error4() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("");
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error5")
    public void validateForm_error5() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword(null);
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error6")
    public void validateForm_error6() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword("");
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm_error7")
    public void validateForm_error7() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword("name");
                this.applicationValidateService.validateForm(userValidateForm);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/validateForm")
    public void validateForm() {
        UserValidateForm userValidateForm = new UserValidateForm();
        userValidateForm.setName("name");
        userValidateForm.setPassword("namename");
        this.applicationValidateService.validateForm(userValidateForm);
    }

}
