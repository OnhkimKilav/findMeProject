package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.IServiceCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserControllerRegistrationImpl implements IUserControllerRegistration {

    private IServiceCRUD<User> serviceCRAD;

    @Autowired
    public UserControllerRegistrationImpl(IServiceCRUD<User> serviceCRAD) {
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
