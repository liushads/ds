package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.List;

import com.ppsea.ds.data.BaseObject;

public class Event extends BaseObject {
    /**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private Integer id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.name
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private String name;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.type
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private Integer type;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.probability
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private Integer probability;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.description
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private String description;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.cityfacility_id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private Integer cityfacilityId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.omit
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private Boolean omit;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.icon
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private String icon;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column event.walk_alias
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	private String walkAlias;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.id
	 * @return  the value of event.id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.id
	 * @param id  the value for event.id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.name
	 * @return  the value of event.name
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.name
	 * @param name  the value for event.name
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.type
	 * @return  the value of event.type
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.type
	 * @param type  the value for event.type
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.probability
	 * @return  the value of event.probability
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public Integer getProbability() {
		return probability;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.probability
	 * @param probability  the value for event.probability
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.description
	 * @return  the value of event.description
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.description
	 * @param description  the value for event.description
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.cityfacility_id
	 * @return  the value of event.cityfacility_id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public Integer getCityfacilityId() {
		return cityfacilityId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.cityfacility_id
	 * @param cityfacilityId  the value for event.cityfacility_id
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setCityfacilityId(Integer cityfacilityId) {
		this.cityfacilityId = cityfacilityId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.omit
	 * @return  the value of event.omit
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public Boolean getOmit() {
		return omit;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.omit
	 * @param omit  the value for event.omit
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setOmit(Boolean omit) {
		this.omit = omit;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.icon
	 * @return  the value of event.icon
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.icon
	 * @param icon  the value for event.icon
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column event.walk_alias
	 * @return  the value of event.walk_alias
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public String getWalkAlias() {
		return walkAlias;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column event.walk_alias
	 * @param walkAlias  the value for event.walk_alias
	 * @abatorgenerated  Mon Mar 23 22:35:44 CST 2009
	 */
	public void setWalkAlias(String walkAlias) {
		this.walkAlias = walkAlias;
	}

	public static final int TYPE_SAIL = 0;
    public static final int TYPE_FISHING = 1;
    public static final int TYPE_DIVING = 2;
    public static final int TYPE_WALK = 3;
    public static final int TYPE_DIG = 4;
    
    private transient List<EventResult> results = new ArrayList<EventResult>();
    
    private transient List<EventPlayer> eventPlayers = null;
    
    private transient List<Action> actions = new ArrayList<Action>();
    
    private transient List<EventMonster> eventMonsters = null;
    
    private transient List<EventSkill> eventSkills = null;
    
    private transient List<EventItem> eventItems = null;
    
    private transient List<EventExtra> eventExtras = null;
    
	public List<EventResult> getResults() {
		return results;
	}

	public void setResults(List<EventResult> results) {
		this.results = results;
	}

	public List<EventPlayer> getEventPlayers() {
		return eventPlayers;
	}

	public void setEventPlayers(List<EventPlayer> eventPlayers) {
		if(eventPlayers == null) eventPlayers = new ArrayList<EventPlayer>();
		
		this.eventPlayers = eventPlayers;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		if(actions == null) actions = new ArrayList<Action>();
		
		this.actions = actions;
	}

	public List<EventMonster> getEventMonsters() {
		return eventMonsters;
	}

	public void setEventMonsters(List<EventMonster> eventMonsters) {
		if(eventMonsters == null) eventMonsters = new ArrayList<EventMonster>();
		
		this.eventMonsters = eventMonsters;
	}

	public List<EventSkill> getEventSkills() {
		return eventSkills;
	}

	public void setEventSkills(List<EventSkill> eventSkills) {
		if(eventSkills == null) eventSkills = new ArrayList<EventSkill>();
		
		this.eventSkills = eventSkills;
	}

	public List<EventItem> getEventItems() {
		return eventItems;
	}

	public void setEventItems(List<EventItem> eventItems) {
		if(eventItems == null) eventItems = new ArrayList<EventItem>();
		
		this.eventItems = eventItems;
	}

	public List<EventExtra> getEventExtras() {
		return eventExtras;
	}

	public void setEventExtras(List<EventExtra> eventExtras) {
		this.eventExtras = eventExtras;
	}

}