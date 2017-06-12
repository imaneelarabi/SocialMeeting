package app.controller;

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

import app.model.Comments;
import app.model.User;
import app.repository.CommentsRepository;


@Controller
public class CommentController {
	@Autowired
	private CommentsRepository commentsRepository;

	@RequestMapping(value="allComments/{groupname}", method = RequestMethod.GET)
	  public String getAllComments(Model model, @PathVariable String groupname){
	    List<Comments> storedusers = commentsRepository.findBygroupName(groupname);
	    model.addAttribute("commentsList", storedusers);

	    return "allComments";
	  }



	@RequestMapping(value="/comments", method = RequestMethod.POST)
	  public @ResponseBody ModelAndView Comment(Model model, Comments comment, HttpSession session){

			User user= (User) session.getAttribute("loggeduser");
			comment.setUserEmail(user.getEmail());

			String groupname = (String) session.getAttribute("group");
			comment.setGroupName(groupname);

			Map<String, Object> response = new LinkedHashMap<String, Object>();
			response.put("message", "Comment added successfully");
			response.put("Comments", commentsRepository.save(comment));

			return new ModelAndView("joinedGroups") ;
		}



}
