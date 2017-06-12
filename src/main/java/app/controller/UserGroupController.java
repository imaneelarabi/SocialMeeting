package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.servlet.ModelAndView;

import app.model.User;
import app.model.UserGroup;
import app.repository.UserGroupRepository;
import app.repository.UserRepository;

@Controller
public class UserGroupController {
	
	 @Autowired
	  private UserGroupRepository usergroupRepository;
	 @Autowired
	  private UserRepository userRepository;
	
	@RequestMapping(value="/joinedGroups", method = RequestMethod.GET)
	  public String getJoinedGroups(Model model, HttpSession session){
		  User user= (User) session.getAttribute("loggeduser");
		   String email= user.getEmail();
		   List<UserGroup> joinedGroups = usergroupRepository.findByuserEmail(email);
		 
		
	    model.addAttribute("joinedgroups", joinedGroups);

	    return "joinedGroups";
	  }
	
	
	@RequestMapping(value="/joingroup/{groupname}", method = RequestMethod.GET)
	  public ModelAndView addUserToGroup(Model model, @PathVariable String groupname, HttpSession session){
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupName(groupname);
			User user= (User) session.getAttribute("loggeduser");
			userGroup.setUserEmail(user.getEmail());
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			response.put("message", "User joined group successfully");
			response.put("userGroup", usergroupRepository.save(userGroup));
		
			return new ModelAndView("redirect:/mygroups") ;}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/leavegroup")
	  public String deleteGroup(@RequestParam String groupname, HttpSession session){
		User user= (User) session.getAttribute("loggeduser");
		String email= user.getEmail();
		UserGroup usergroup= usergroupRepository.findByuserEmailAndGroupName( email, groupname);
		
		
	    usergroupRepository.delete(usergroup.getID());
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Usergroup deleted successfully");
	    return "redirect:/joinedgroups";
	  }
	
	@RequestMapping(value="/viewmembers", method = RequestMethod.GET)
	public String viewmembers(@RequestParam String groupname, Model model){
		
		List<User> userGroups= new ArrayList<User>();
		List<UserGroup> usergroup= usergroupRepository.findBygroupName( groupname);
		List<User> users= userRepository.findAll();
		Iterator it=usergroup.iterator();
		
		while(it.hasNext()){
			UserGroup ug = (UserGroup) it.next();
			Iterator it2=users.iterator();
			while(it2.hasNext()){
				User user = (User) it2.next();
				if(user.getEmail().equals(ug.getUserEmail()) )
				{userGroups.add(user);}
			}
			
		}
		
		model.addAttribute("members", userGroups);
		
		return "viewmembers";
	}
	

}
