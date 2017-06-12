package app.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.model.Group;
import app.model.User;
import app.model.UserGroup;
import app.repository.GroupRepository;
import app.repository.UserGroupRepository;




@Controller
public class GroupController {
	
	 @Autowired
	  private GroupRepository groupRepository;
	
	 
	 @Autowired
	  private UserGroupRepository usergroupRepository;
	 
	 @RequestMapping(value="/groups", method = RequestMethod.POST)
	  public @ResponseBody ModelAndView  createGroup(Group group, HttpSession session) {
		     
		    User user= (User) session.getAttribute("loggeduser");
		    group.setAdminEmail(user.getEmail());
		    UserGroup usergroup = new UserGroup();
		    usergroup.setGroupName(group.getGroupname());
		    usergroup.setUserEmail(user.getEmail());
		    
		    Map<String, Object> response = new LinkedHashMap<String, Object>();
		    response.put("message", "Group created successfully");
		    response.put("group", groupRepository.save(group));
		    
		    Map<String, Object> response2 = new LinkedHashMap<String, Object>();
		    response2.put("message", "UserGroup created successfully");
		    response2.put("usergroup", usergroupRepository.save(usergroup));
		    
		    return new ModelAndView("redirect:/mygroups");
	  }
	 
	  @RequestMapping(value="/mygroups", method = RequestMethod.GET)
	  public String getUsersGroups(Model model, HttpSession session){
		  User user= (User) session.getAttribute("loggeduser");
		   String email= user.getEmail();
		   List<Group> storedgroups = groupRepository.findByadminEmail(email);
		
	    model.addAttribute("storedgroups", storedgroups);

	    return "mygroups";
	  }
	  
	  
	  @RequestMapping(value="/AllGroups", method = RequestMethod.GET)
	  public String getAllGroups(Model model){
	   List<Group> storedgroups = groupRepository.findAll();
		
	    model.addAttribute("allstoredgroups", storedgroups);

	    return "AllGroups";
	  }
	 
	  
	  @RequestMapping(method = RequestMethod.GET, value="/updategroups")
	  public String rememberGroup(@RequestParam String groupname, HttpSession session){
		  
		  session.setAttribute("group", groupname);
		    return  "updateGroup";
		  }
	  
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/updateGroup")
	  public String editGroup(@RequestParam String groupDesc,HttpSession session){
			  String groupname= (String) session.getAttribute("group");
			  
			  User user= (User) session.getAttribute("loggeduser");
			   
			  Group group = new Group();
			  group.setGroupDesc(groupDesc);
			  group.setGroupname(groupname);
			  group.setAdminEmail(user.getEmail());
			 
		    Map<String, Object> response = new LinkedHashMap<String, Object>();
		    response.put("message", "Group Updated successfully");
		    response.put("user", groupRepository.save(group));
		    return "redirect:mygroups";
		  }
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/deletegroup")
	  public String deleteGroup(@RequestParam String groupname){
		  
	    groupRepository.delete(groupname);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Group deleted successfully");
	    return "redirect:/mygroups";
	  }
	  
	  
	  
	  
	  
	  }
	 
	
	
	

