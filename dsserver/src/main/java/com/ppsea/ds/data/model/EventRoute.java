package com.ppsea.ds.data.model;

import com.ppsea.ds.data.BaseObject;

public class EventRoute extends BaseObject {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column event_route.id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    private Integer id;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column event_route.event_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    private Integer eventId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column event_route.route_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    private Integer routeId;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column event_route.id
     *
     * @return the value of event_route.id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column event_route.id
     *
     * @param id the value for event_route.id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column event_route.event_id
     *
     * @return the value of event_route.event_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column event_route.event_id
     *
     * @param eventId the value for event_route.event_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column event_route.route_id
     *
     * @return the value of event_route.route_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public Integer getRouteId() {
        return routeId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column event_route.route_id
     *
     * @param routeId the value for event_route.route_id
     *
     * @abatorgenerated Sun Mar 08 12:47:33 CST 2009
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }
}