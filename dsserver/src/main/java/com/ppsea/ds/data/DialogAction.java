package com.ppsea.ds.data;

import java.util.List;

import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.Event;
import com.ppsea.ds.data.model.Question;

/**
 * ClassName:NpcDialogAction NPC对话和动作或者重定向
 * 
 * @author Daniel.Hao
 * @version
 * @since Ver 1.0
 * @Date 2008 Dec 8, 2008 8:54:22 PM
 * 
 * @see
 */
public class DialogAction extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	// Redirect to new page
	private String redirect = null;
	
	// Dialog
	private String dialog = null;
	
	// Action list
	private List<Action> actions = null;
	
	// Questions
	private Question question = null;
	
	// 随机事件
	private Event event = null;
	
	// Icon
	private String icon = null;
	
	private boolean upgrade = false;

	public DialogAction(String dialog, List<Action> actions, String redirect, Question question, boolean upgrade){
		this(dialog, actions);
		
		this.redirect = redirect;
		this.question = question;
		this.upgrade = upgrade;
	}
	
	public DialogAction(String dialog, List<Action> actions, Question question, boolean upgrade){
		this(dialog, actions);
		
		this.question = question;
		this.upgrade = upgrade;
	}
	
	public DialogAction(Event event){
		this.event = event;
	}

	public DialogAction(String dialog, List<Action> actions){
		this.dialog = dialog;
		this.actions = actions;
	}
	
	public DialogAction(String dialog, List<Action> actions, Event event){
		this(dialog, actions);
		
		this.icon = event.getIcon();
		this.event = event;
	}
	
	public DialogAction(String dialog, List<Action> actions, Event event, boolean upgrade){
		this(dialog, actions, event);
		
		this.upgrade = upgrade;
	}
	
	
	public String getDialog() {
		return dialog;
	}
	
	public void addDialog(String str){
		this.dialog += str;
	}

	public List<Action> getActions() {
		return actions;
	}
	
	public void setActions(List<Action> actions){
		this.actions = actions;
	}

	public String getRedirect() {
		return redirect;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public boolean isUpgrade(){
		return this.upgrade;
	}
	
	public Event getEvent(){
		return event;
	}
	
	public void setEvent(Event event){
		this.event = event;
	}
	
	public String getIcon(){
		return icon;
	}
	
	public void setIcon(String icon){
		this.icon = icon;
	}
}
