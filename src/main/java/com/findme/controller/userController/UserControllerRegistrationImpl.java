package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerRegistrationImpl implements IUserControllerRegistration {

    private IServiceCRAD<User> serviceCRAD;

    @Autowired
    public UserControllerRegistrationImpl(IServiceCRAD<User> serviceCRAD) {
        this.serviceCRAD = serviceCRAD;
    }

    @Override
    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute User user) {
        try {
            serviceCRAD.save(user);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
