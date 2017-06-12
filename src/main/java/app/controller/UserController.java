package app.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.model.User;
import app.service.UserService;
import app.repository.UserRepository;

@Controller

public class UserController {

	 @Autowired
	  private UserRepository userRepository;
		@Autowired
		private UserService userService;
	 @RequestMapping("/")
	 String index(){
		 return "index";
	 }
	 
	 
	 @RequestMapping(value="/users", method = RequestMethod.POST)
	  public @ResponseBody ModelAndView  createUser(User user, HttpSession session) {
		   		    
		    Map<String, Object> response = new LinkedHashMap<String, Object>();
		    response.put("message", "User created successfully");
		    response.put("user", userRepository.save(user));
		    session.setAttribute("loggeduser", user);
		    return new ModelAndView("redirect:/mygroups");
	  }
	  
	  @RequestMapping(method = RequestMethod.GET, value="/{email}")
	  public User getUserDetails(@PathVariable("email") String email){
	    return userRepository.findOne(email);
	  }
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/updateUser")
  public String editUser(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String shortbiography,HttpSession session){
		  User user= (User) session.getAttribute("loggeduser");
		  System.out.println("lastname"+lastname);
		  if(lastname != ""){user.setLastname(lastname);}
		  if(firstname != ""){user.setFirstname(firstname);}
		  if(shortbiography != ""){user.setShortbiography(shortbiography);}
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "User Updated successfully");
	    response.put("user", userRepository.save(user));
	    return "redirect:mygroups";
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/deleteUser")
	  public String deleteUser(HttpSession session){
		  User user= (User) session.getAttribute("loggeduser");
	    userRepository.delete(user.getEmail());
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "user deleted successfully");
	    
	    return "redirect:/";
	  }
	  
	  @RequestMapping(value="allUsers")
	  public String getAllUsers(Model model){
	    List<User> storedusers = userRepository.findAll();
	    model.addAttribute("usersList", storedusers);

	    return "allUsers";
	  }
	  @RequestMapping(method = RequestMethod.POST, value="/login")
	  public String verifyLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
		  
		  User user = userService.LoginUser(username, password);
		  if(user == null){
			  model.addAttribute("loginerror", "Failed to login, Please try again");
			  return "index";
		  }else{
			  session.setAttribute("loggeduser", user);
			  return "redirect:mygroups";
		  }		  
	  }
	  
//	  @RequestMapping("/mygroups")
//		 String groups(HttpSession session){
//			 if(session.getAttribute("loggeduser") == null){
//				 return "index";
//			 }
//			 return "/mygroups";
//		 }
	  
	  @RequestMapping(value="seeProfile/{email}", method = RequestMethod.GET)
	  public String getUser(Model model, @PathVariable String email){
		 
		    model.addAttribute("userInfo", userRepository.findOne(email));

		    return "seeProfile";
		  }
	  
	  
	  
	
}
