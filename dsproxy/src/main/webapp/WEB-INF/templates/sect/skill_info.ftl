<#-- 显示技能详细信息 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if curr_facility?exists>
${curr_facility.name}<br/>
</#if>
<#if last_npc?exists>${last_npc.name}:</#if>
<#if desc?exists>${desc}。<br/></#if>
你想要学习什么呢?<br/>
【<#list skillType?keys as key>
	<@showSkillType key,return/><#if key_has_next>|</#if>
</#list>】
<br/>
【可学】<br/>
<#if sect_skills?exists>
	<#list sect_skills as skill>
	<@a href=""/>学习</a>&nbsp;<@a href=""/>${skill.name}(${skill.conPlayerLevel}级)<br/></a>
	</#list>
<#else>
你目前没有可以学习的技能<br/>
</#if>
<#-- 高级技能 -->
<#if sect_advanced_skills?exists>
	【高级】<br/>
	<#list sect_advanced_skills as skill>
	<@a href=""/>学习</a>&nbsp;<@a href=""/>${skill.name}(${skill.conPlayerLevel}级)<br/></a>
	</#list>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>