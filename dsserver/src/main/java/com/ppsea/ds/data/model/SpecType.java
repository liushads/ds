package com.ppsea.ds.data.model;

import  com.ppsea.ds.data.BaseObject;

public class SpecType extends BaseObject {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spec_type.id
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spec_type.name
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    private String name;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spec_type.id
     *
     * @return the value of spec_type.id
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spec_type.id
     *
     * @param id the value for spec_type.id
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spec_type.name
     *
     * @return the value of spec_type.name
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spec_type.name
     *
     * @param name the value for spec_type.name
     *
     * @ibatorgenerated Thu Dec 27 00:47:58 CST 2012
     */
    public void setName(String name) {
        this.name = name;
    }
}