package com.findme.controller.userController;

import com.findme.model.User;
import com.findme.service.IServiceCRUD;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserControllerProfileImpl implements IUserControllerProfile {

    private IServiceCRUD<User> serviceCRAD;

    @Autowired
    public UserControllerProfileImpl(IServiceCRUD<User> serviceCRAD) {
        this.serviceCRAD = serviceCRAD;
    }

    @Override
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        User user = (User) serviceCRAD.findById(Long.valueOf(userId));
        if (user == null) {
            model.addAttribute("exception", new ObjectNotFoundException("Object with this id " + userId + " not found"));
            return "profileException";
        } else {
            model.addAttribute("user", user);
            return "profile";
        }
    }
}
